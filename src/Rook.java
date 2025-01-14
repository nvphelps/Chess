import java.util.HashSet;

public class Rook extends BasePiece{

    public Rook(boolean white, Square location) {
        super(white, location);
    }

    @Override
    public void updatePossibleMoves(Board board) {
        HashSet<Square> legalMovesSet = new HashSet<>();
        Coord direction = new Coord(1,0);

        for (int i = 0; i < 4; i++) {
            legalMovesSet.addAll(createNewFeeler(board, direction));
            direction = direction.rotate90CW();
        }

        this.legalMovesSet = legalMovesSet;
    }

    @Override
    public String toString() {
        if (this.isWhite()) {
            return "\u265C ";
        } else {
            return "\u001B[30m\u265C ";
        }
    }
}
