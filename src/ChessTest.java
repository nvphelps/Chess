import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ChessTest {


    @Test
    public void testFeelerGo() {
        Feeler feeler = new Feeler(new Coord(1,0), true);
        Board chessBoard = new Board();
        LinkedList<Square> compareList = new LinkedList<>();
        compareList.add(chessBoard.array[2][0]);
        compareList.add(chessBoard.array[3][0]);
        //System.out.println("Feeler.go():");
        assertEquals(compareList, feeler.go(chessBoard, new Coord(1,0), 2));

    }

    @Test
    public void testPawnLegalMoveSet() {
        Board chessBoard = new Board();
        BasePiece pawn = chessBoard.array[0][1].getPiece();
        HashSet<Square> legalMoveSetCompare = new HashSet<>();
        legalMoveSetCompare.add(chessBoard.array[2][0]);
        legalMoveSetCompare.add(chessBoard.array[2][1]);
        for (Square square : pawn.legalMovesSet) {
            square.setHighlighted(true);
        }
        System.out.println("Pawn legal move set: " + pawn.legalMovesSet);
        System.out.println("legal move set: " + legalMoveSetCompare);
        System.out.println(chessBoard);
        assertEquals(legalMoveSetCompare, pawn.legalMovesSet);
    }

    /*@Test
    public void testPawnMove() {
        Board chessBoard = new Board();
        BasePiece pawn = chessBoard.array[0][1].getPiece();
        assertEquals(true, pawn.legalMovesSet.contains(chessBoard.array[0][2]));

        boolean printValue = pawn.move(chessBoard, chessBoard.array[4][0]);

        pawn.move(chessBoard, chessBoard.array[2][1]);
        System.out.println(printValue);
        System.out.println(chessBoard);
    }*/

    @Test
    public void testRookLegalMoveSet() {
        Board chessBoard = new Board();
        BasePiece rook = chessBoard.array[0][0].getPiece();
        HashSet<Square> legalMoveSetCompare = new HashSet<>();
        legalMoveSetCompare.add(chessBoard.array[0][1]);
        legalMoveSetCompare.add(chessBoard.array[1][0]);
        legalMoveSetCompare.add(chessBoard.array[2][0]);

        System.out.println(chessBoard);
        assertEquals(legalMoveSetCompare, rook.legalMovesSet);

    }

    @Test
    public void testPrintTitleScreen() {
        System.out.println("/%%%%%%/");
    }

    @Test
    public void testBasePieceCopy() {
        BasePiece piece1 = new Knight(true, new Square(new Coord(1,1)));
        BasePiece piece2 = new Rook(true, new Square(new Coord(3,1)));

        BasePiece copy1 = piece1.duplicate();
        System.out.println("" + copy1 + ", " + copy1.getMovesCounter() + ", " + copy1.location);
        System.out.println("" + piece1 + ", " + piece1.getMovesCounter() + ", " + piece1.location);

    }

    @Test
    public void testCopyWholeBoard() {
        Board original = new Board();
        Board copy = original.copy();
        //System.out.println(original); System.out.println(copy);

        original.move(original.array[0][1].getPiece(), original.array[0][3]);
        System.out.println(original); System.out.println(copy);
        assertEquals(original.array[0][3], copy.array[0][3]);

        Board copy2 = original.copy();
        System.out.println("After moving: \n"); System.out.println(copy2);
    }

    @Test
    public void testDivision() {
        System.out.println(-2/4);
    }

}
