import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Pawn extends BasePiece {

    public Pawn(boolean white, Square location) {
        super(white, location);
    }

    @Override
    public void specialRulePostMove(Board board, Square originalLocation, Square destination, BasePiece originalPiece) {
        //Activate if Pawn makes it to back row
        if (isWhite() && this.location.getCoord().y == 7 || !isWhite() && this.location.getCoord().y == 0) {
            handlePromotion(board);
            return;
        }

        //Activate if pawn double moves on first turn
        int verticalDistanceTraveled = this.location.getCoord().y - originalLocation.getCoord().y;
        if (Math.abs(verticalDistanceTraveled) == 2) {
            setEnPassant(board, originalLocation, verticalDistanceTraveled);
        }

        //Activate if pawn moves left/right onto a square with no piece. Should only be possible with en passant
        int horizontalDistanceTraveled = this.location.getCoord().x - originalLocation.getCoord().x;
        if (Math.abs(horizontalDistanceTraveled) > 0 && originalPiece == null) {
            board.array[this.location.getCoord().x][originalLocation.getCoord().y].setPiece(null);
            board.updateAllLegalMoves();
        }
    }

    private void handlePromotion(Board board) {

        boolean flag = true;
        BasePiece promotion = null;
        String message = "";
        while (flag) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.print(message);
            System.out.println("\n" + board);
            System.out.println("A Pawn has been promoted. Select a piece:");
            System.out.println("'Queen', 'Rook', 'Knight', or 'Bishop'");
            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            String command = sc.next().toLowerCase();
            flag = false;

            switch (command) {
                case "queen":
                    promotion = new Queen(isWhite(), null);
                    continue;
                case "rook":
                    promotion = new Rook(isWhite(), null);
                    continue;
                case "knight":
                    promotion = new Knight(isWhite(), null);
                    continue;
                case "bishop":
                    promotion = new Bishop(isWhite(), null);
                    continue;
                case "quit":
                    System.exit(0);
                default:
                    message = "Invalid Input, try again";
                    flag = true;
            }
        }

        int x = location.getCoord().x;
        int y = location.getCoord().y;
        if (isWhite()) {
            board.whiteTeamPieces.add(promotion);
            board.whiteTeamPieces.remove(this);
        } else {
            board.blackTeamPieces.add(promotion);
            board.blackTeamPieces.remove(this);
        }
        board.array[x][y].setPiece(promotion);
        board.updateAllLegalMoves();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * SpecialRule helper method.
     * Adds a new Square to an enemy pawn's legalMovesSet that only lasts for one turn.
     * (Move removed when legal moves are refreshed)
     *
     * @param board
     * @param originalLocation
     * @param verticalDistanceTraveled
     */
    private void setEnPassant(Board board, Square originalLocation, int verticalDistanceTraveled) {
        int x = this.location.getCoord().x;
        int y = this.location.getCoord().y;

        //Where potential attacking pawn will move
        Square enPassantLocation = verticalDistanceTraveled > 0 ? board.array[x][y - 1] : board.array[x][y + 1];

        //Check if left square is within board limits and piece in left square is an enemy pawn
        if (x - 1 >= 0) {
            BasePiece piece = board.array[x - 1][y].getPiece();
            if (piece instanceof Pawn && piece.isEnemy(this)) {
                piece.legalMovesSet.add(enPassantLocation);
            }
        }
        //Repeat for right square
        if (x + 1 <= 7) {
            BasePiece piece = board.array[x + 1][y].getPiece();
            if (piece instanceof Pawn && piece.isEnemy(this)) {
                piece.legalMovesSet.add(enPassantLocation);
            }
        }
    }

    @Override
    public void updatePossibleMoves(Board board) {
        HashSet<Square> legalMovesSet = new HashSet<>();
        LinkedList<Square> list;
        Coord straight = new Coord(0,1);
        Coord right = new Coord(1, 1);
        Coord left = new Coord(-1, 1);

        if (!isWhite()) {
            straight = straight.multiply(-1);
            right = right.multiply(-1);
            left = left.multiply(-1);
        }

        if (this.getMovesCounter() == 0) {
            list = createNewFeeler(board, straight, 2);
        } else {
            list = createNewFeeler(board, straight, 1);
        }
        
        //Pawn can only move forward if spaces are empty
        if (!list.isEmpty() && list.getLast().getPiece() == null) {
            legalMovesSet.addAll(list);
        } else if (list.size() >= 2) {
            legalMovesSet.add(list.getFirst());
        }

        list = createNewFeeler(board, right, 1);
        list.addAll(createNewFeeler(board, left, 1));
        //Pawn can only move left/right on capture
        for (Square square : list) {
            if (square.getPiece() != null && square.getPiece().isWhite() != isWhite()) {
                legalMovesSet.add(square);
            }
        }

        this.legalMovesSet = legalMovesSet;
    }

    @Override
    public String toString() {
        if (this.isWhite()) {
            return "\u265F ";
        } else {
            return "\u001B[30m\u265F ";
        }
    }
}
