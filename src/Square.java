import java.util.Objects;

public class Square {
    private Coord coord;
    private BasePiece piece = null;
    private boolean highlighted = false;

    public Square(Coord coord) {
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }

    public BasePiece getPiece() {
        return piece;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public BasePiece setPiece(BasePiece piece) {
        BasePiece temp = this.piece;
        if (temp != null) {
            temp.setLocation(null);
        } else {
            temp = piece;
        }
        if (piece != null && piece.location != null) {
            piece.location.empty();
            piece.setLocation(this);
        } else if (piece != null) {
            piece.setLocation(this);
        }
        this.piece = piece;
        return temp;
    }

    public void empty() {
        this.piece = null;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(coord, square.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coord);
    }
}
