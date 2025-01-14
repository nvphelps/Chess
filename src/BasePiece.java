import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public abstract class BasePiece {

    private boolean white;
    public HashSet<Square> legalMovesSet = new HashSet<>();
    public Square location;
    private int movesCounter = 0;

    public BasePiece(boolean white, Square location) {
        this(white, location, 0);
    }

    public BasePiece(boolean white, Square location, int movesCounter) {
        this.white = white;
        this.location = location;
        this.movesCounter = movesCounter;
    }

    /**
     * Deep copies a piece without setting its location.
     *
     * @return the duplicate piece
     */
    public BasePiece duplicate() {
        Class<? extends BasePiece> clazz = this.getClass();
        boolean white = this.isWhite();
        BasePiece duplicate;

        try {
            duplicate =  clazz.getConstructor(boolean.class, Square.class).newInstance(white, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to duplicate piece", e);
        }

        duplicate.movesCounter = this.movesCounter;
        return duplicate;
    }

    /**
     * Does nothing unless overridden
     *
     * @param board
     * @param originalLocation
     * @param destination
     * @param originalPiece
     */
    public void specialRulePostMove(Board board, Square originalLocation, Square destination, BasePiece originalPiece) {}

    public LinkedList<Square> createNewFeeler(Board board, Coord direction) {
        Feeler feeler = new Feeler(this.location.getCoord(), this.isWhite());
        return feeler.go(board, direction);
    }

    public LinkedList<Square> createNewFeeler(Board board, Coord direction, int maxDistance) {
        Feeler feeler = new Feeler(this.location.getCoord(), this.isWhite());
        return feeler.go(board, direction, maxDistance);
    }

    public boolean checkMoveIsIllegal(Square destination) {
        if (location == null || !legalMovesSet.contains(destination)) {
            return true;
        }
        return false;
    }

    abstract public void updatePossibleMoves(Board board);

    //TODO make more efficient
    //is update moves or copy board more costly?
    public void cleanPossibleMoves(Board board) {
        LinkedList<Square> list = new LinkedList<>();
        Coord pieceCoord = this.location.getCoord();

        for (Square square : legalMovesSet) {
            //Variable Setup
            Board copyBoard = board.copy();
            BasePiece copyPiece = copyBoard.getSquare(pieceCoord).getPiece();
            Square copySquare = copyBoard.getSquare(square.getCoord());

            //Move
            copySquare.setPiece(copyPiece);
            copyPiece.incrementMovesCounter();
            copyBoard.updateAllPossibleMoves();

            if (copyBoard.isKingInCheck(copyPiece.isWhite())) {
                list.add(board.getSquare(copySquare.getCoord()));
            }
        }
        boolean remove = legalMovesSet.removeAll(list);
    }

    abstract public String toString();

    public boolean isWhite() {
        return white;
    }

    public int getMovesCounter() {
        return movesCounter;
    }

    public void incrementMovesCounter() {
        movesCounter++;
    }

    public void decrementMovesCounter() {
        movesCounter--;
    }

    public void setLocation(Square square) {
        this.location = square;
    }

    public Square getLocation() {
        return location;
    }

    public boolean isEnemy(BasePiece piece) {
        return this.isWhite() != piece.isWhite();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasePiece basePiece = (BasePiece) o;
        return Objects.equals(location, basePiece.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }

}
