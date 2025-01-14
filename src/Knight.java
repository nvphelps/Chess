import java.util.HashSet;

public class Knight extends BasePiece {

    public Knight(boolean white, Square location) {
        super(white, location);
    }

    @Override
    public void updatePossibleMoves(Board board) {
        HashSet<Square> legalMovesSet = new HashSet<>();

        legalMovesSet.addAll(createNewFeeler(board, new Coord(1,2), 1));
        legalMovesSet.addAll(createNewFeeler(board, new Coord(2,1), 1));
        legalMovesSet.addAll(createNewFeeler(board, new Coord(2,-1), 1));
        legalMovesSet.addAll(createNewFeeler(board, new Coord(1,-2), 1));
        legalMovesSet.addAll(createNewFeeler(board, new Coord(-1,-2), 1));
        legalMovesSet.addAll(createNewFeeler(board, new Coord(-2,-1), 1));
        legalMovesSet.addAll(createNewFeeler(board, new Coord(-2,1), 1));
        legalMovesSet.addAll(createNewFeeler(board, new Coord(-1,2), 1));

        this.legalMovesSet = legalMovesSet;
    }

    @Override
    public String toString() {
        if (this.isWhite()) {
            return "\u265E ";
        } else {
            return "\u001B[30m\u265E ";
        }
    }
}
