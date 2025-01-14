import java.util.HashSet;

public class Bishop extends BasePiece {

    public Bishop(boolean white, Square location) {
        super(white, location);
    }

    @Override
    public void updatePossibleMoves(Board board) {
        HashSet<Square> legalMovesSet = new HashSet<>();
        Coord direction = new Coord(1,1);

        for (int i = 0; i < 4; i++) {
            legalMovesSet.addAll(createNewFeeler(board, direction));
            direction = direction.rotate90CW();
        }

        this.legalMovesSet = legalMovesSet;
    }

    @Override
    public String toString() {
        if (this.isWhite()) {
            return "\u265D ";
        } else {
            return "\u001B[30m\u265D ";
        }
    }
}
