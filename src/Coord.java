import java.util.Objects;

/**
 * Coord class acts as both a coordinate on the array and a vector
 */
public class Coord {

    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord multiply(int num) {
        return new Coord(this.x * num, this.y * num);
    }

    public Coord add(Coord coord) {
        return new Coord(this.x + coord.x, this.y + coord.y);
    }

    public Coord rotate90CW() {
        return new Coord(this.y,this.x * -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return this.x == coord.x && this.y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coord (" + x + ", " + y + ")";
    }
}
