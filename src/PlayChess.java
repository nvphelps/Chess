import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

public class PlayChess {
    public static void main(String[] args) {

        //Initialize variables
        Board chessBoard = new Board();
        Scanner sc = new Scanner(System.in);
        Square highlighted1 = null;
        Square highlighted2;
        boolean whiteTurn = true;

        //Launch Sequence
        while (true) {
            System.out.println("Welcome to Chess.\nType 'start' to start a new game.\nType 'help' for a list of commands.");
            System.out.print("> ");
            String command = sc.next();
            if (command.equals("quit")) {
                System.out.println("Exiting...");
                System.exit(0);
            } else if (command.equals("start")) {
                break;
            } else if (command.equals("help")) {
                handleHelpMenu();
            }
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        String message = "";

        //Main Game Loop
        while (true) {
            //Display chessBoard
            System.out.println("\n\n" + chessBoard);
            if (whiteTurn) {
                System.out.println("White's Turn");
            } else {
                System.out.println("Black's Turn");
            }
            System.out.print("> ");

            //Waits for next input
            String command = sc.next();

            //Clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            if (command.equals("quit")) {
                chessBoard.resetHighlightedSquares();
                System.out.print(message);
                System.out.println("\n" + chessBoard);
                System.out.println("Exiting...");
                System.exit(0);
            } else if (command.equals("cancel")) {
                highlighted1 = null;
                chessBoard.resetHighlightedSquares();
                continue;
            } else if (command.equals("help")) {
                handleHelpMenu();
            }

            Coord coord;
            try {
                coord = Board.stringToCoord(command);
            } catch (IndexOutOfBoundsException e) {
                System.out.print("That is an invalid command.");
                continue;
            }

            if (highlighted1 == null) {
                highlighted1 = chessBoard.getSquare(coord);
                if (highlighted1.getPiece() == null) {
                    System.out.print("That is an invalid location");
                    highlighted1 = null;
                    continue;
                }
                if (highlighted1.getPiece().isWhite() != whiteTurn) {
                    System.out.print("That is not your piece");
                    highlighted1 = null;
                    continue;
                }
                for (Square square : highlighted1.getPiece().legalMovesSet) {
                    square.setHighlighted(true);
                }
                highlighted1.setHighlighted(true);
            } else {
                highlighted2 = chessBoard.array[coord.x][coord.y];
                boolean output = chessBoard.move(highlighted1.getPiece(), highlighted2);
                if (!output) {
                    System.out.print("That was an illegal move.");
                    continue;
                }
                highlighted1 = null;
                chessBoard.resetHighlightedSquares();
                whiteTurn = !whiteTurn;
                handleGameEndConditions(chessBoard, whiteTurn);
            }

        }
    }

    public static void handleGameEndConditions(Board chessBoard, boolean whiteTurn) {
        String message;
        if (whiteTurn) {
            for (BasePiece piece : chessBoard.whiteTeamPieces) {
                if (!piece.legalMovesSet.isEmpty() && piece.location != null) { //Todo check if second condition is necessary
                    return;
                }
            }
            if (chessBoard.isKingInCheck(true)) {
                message = "Check Mate! Black Wins.";
            } else {
                message = "Game Over! Draw.";
            }
        } else {
            for (BasePiece piece : chessBoard.whiteTeamPieces) {
                if (!piece.legalMovesSet.isEmpty() && piece.location != null) {
                    return;
                }
            }
            if (chessBoard.isKingInCheck(true)) {
                message = "Check Mate! White Wins.";
            } else {
                message = "Game Over! Draw.";
            }
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.print(message);
        System.out.println("\n\n" + chessBoard);

        System.out.print("Type 'quit' to leave the game\n> ");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.next().toLowerCase();
            if (command.equals("quit")) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        }
    }

    public static void handleHelpMenu() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("This is the help menu.\nCurrently under construction.");
        System.out.println("To select a piece, type in its corresponding coordinate letter first.");
        System.out.println("To move the selected piece, type in the coordinate of the square you want to move it to." +
                "\n Legal moves will be highlighted green for the selected piece.");
        System.out.println("To de-select a piece, type 'cancel'");
        System.out.println("To exit the game, type 'quit'.");

        System.out.println("Type anything to exit this menu");
        Scanner sc = new Scanner(System.in);
        String command = sc.next().toLowerCase();
        if (command.equals("quit")) {
            System.out.println("Exiting...");
            System.exit(0);
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}