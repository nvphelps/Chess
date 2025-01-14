import java.util.LinkedList;

public class Feeler {

    private Coord location;
    private boolean white;

    public Feeler(Coord location, boolean white) {
        this.location = location;
        this.white = white;
    }

    public LinkedList<Square> go(Board board, Coord direction, int maxDistance) {
        LinkedList<Square> list = new LinkedList<>();
        for (int i = 1; i <= maxDistance; i++) {
            location = location.add(direction);
            Square square;
            //System.out.println("x: " + location.x);
            //System.out.println("y: " + location.y);
            try {
                square = board.array[location.x][location.y];
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            if (square.getPiece() == null) {
                list.addLast(square);
            } else if (square.getPiece().isWhite() == this.white) {
                break;
            } else {
                list.addLast(square);
                break;
            }

        }
        return list;
    }

    public LinkedList<Square> go(Board board, Coord direction) {
        LinkedList<Square> list = new LinkedList<>();
        while (true) {
            location = location.add(direction);
            Square square;

            try {
                square = board.array[location.x][location.y];
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            if (square.getPiece() == null) {
                list.addLast(square);
            } else if (square.getPiece().isWhite() == this.white) {
                break;
            } else {
                list.addLast(square);
                break;
            }
        }
        return list;
    }

}
