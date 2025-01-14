import java.util.HashSet;

public class Queen extends BasePiece {

    public Queen(boolean white, Square location) {
        super(white, location);
    }

    @Override
    public void updatePossibleMoves(Board board) {
        HashSet<Square> legalMovesSet = new HashSet<>();
        Coord orthogonal = new Coord(1,0);
        Coord diagonal = new Coord(1,1);

        for (int i = 0; i < 4; i++) {
            legalMovesSet.addAll(createNewFeeler(board, orthogonal));
            legalMovesSet.addAll(createNewFeeler(board, diagonal));
            orthogonal = orthogonal.rotate90CW();
            diagonal = diagonal.rotate90CW();
        }

        this.legalMovesSet = legalMovesSet;
    }

    @Override
    public String toString() {
        if (this.isWhite()) {
            return "\u265B ";
        } else {
            return "\u001B[30m\u265B ";
        }
    }
}
