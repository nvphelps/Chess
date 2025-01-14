import java.util.LinkedList;

public class Board {

    //TODO set all variables to private
    public Square[][] array;
    public LinkedList<BasePiece> whiteTeamPieces = new LinkedList<>();
    public LinkedList<BasePiece> blackTeamPieces = new LinkedList<>();
    public BasePiece whiteKing;
    public BasePiece blackKing;

    public Board() {
        this(false);
    }

    public Board(boolean isCopy) {
        if (isCopy) {
            array = createNewEmptyBoard();
        } else {
            array = createNewBoard();
            updateAllPossibleMoves();
        }
    }

    public Board copy() {
        Board copy = new Board(true);
        for (Square[] row : array) {
            for (Square square : row) {

                BasePiece originalPiece = square.getPiece();
                if (originalPiece != null) {

                    int x = square.getCoord().x;
                    int y = square.getCoord().y;
                    BasePiece duplicate = originalPiece.duplicate();
                    copy.array[x][y].setPiece(duplicate);

                    if (duplicate.isWhite()) {
                        copy.whiteTeamPieces.add(duplicate);
                        if (duplicate instanceof King) {
                            copy.whiteKing = duplicate;
                        }
                    } else {
                        copy.blackTeamPieces.add(duplicate);
                        if (duplicate instanceof King) {
                            copy.blackKing = duplicate;
                        }
                    }

                }

            }
        }
        return copy;
    }

    private Square[][] createNewBoard() {
        Square[][] board = createNewEmptyBoard();

        //King: 0
        whiteKing = board[4][0].setPiece(new King(true, board[4][0]));
        blackKing = board[4][7].setPiece(new King(false, board[4][7]));
        whiteTeamPieces.add(whiteKing);
        blackTeamPieces.add(blackKing);


        //Queen: 1
        whiteTeamPieces.add(board[3][0].setPiece(new Queen(true, board[3][0])));
        blackTeamPieces.add(board[3][7].setPiece(new Queen(false, board[3][7])));

        //Rook: 2-3
        whiteTeamPieces.add(board[0][0].setPiece(new Rook(true, board[0][0])));
        whiteTeamPieces.add(board[7][0].setPiece(new Rook(true, board[7][0])));
        blackTeamPieces.add(board[0][7].setPiece(new Rook(false, board[0][7])));
        blackTeamPieces.add(board[7][7].setPiece(new Rook(false, board[7][7])));

        //Bishop: 4-5
        whiteTeamPieces.add(board[2][0].setPiece(new Bishop(true, board[2][0])));
        whiteTeamPieces.add(board[5][0].setPiece(new Bishop(true, board[5][0])));
        blackTeamPieces.add(board[2][7].setPiece(new Bishop(false, board[2][7])));
        blackTeamPieces.add(board[5][7].setPiece(new Bishop(false, board[5][7])));

        //Knight: 6-7
        whiteTeamPieces.add(board[1][0].setPiece(new Knight(true, board[1][0])));
        whiteTeamPieces.add(board[6][0].setPiece(new Knight(true, board[6][0])));
        blackTeamPieces.add(board[1][7].setPiece(new Knight(false, board[1][7])));
        blackTeamPieces.add(board[6][7].setPiece(new Knight(false, board[6][7])));

        //Pawns
        for (int i = 0; i < 8; i++) {
            BasePiece pawn = new Pawn(true, board[i][1]);
            whiteTeamPieces.add(pawn);
            board[i][1].setPiece(pawn);
            pawn = new Pawn(false, board[i][6]);
            blackTeamPieces.add(pawn);
            board[i][6].setPiece(pawn);
        }

        return board;
    }

    private Square[][] createNewEmptyBoard() {
        Square[][] board = new Square[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = new Square(new Coord(i, j));
            }
        }
        return board;
    }

    /**
     *
     */
    public void updateAllPossibleMoves() {
        for (BasePiece piece : whiteTeamPieces) {
            if (piece.location != null) {
                piece.updatePossibleMoves(this);
            } else {
                piece.legalMovesSet.clear();
            }
        }
        for (BasePiece piece : blackTeamPieces) {
            if (piece.location != null) {
                piece.updatePossibleMoves(this);
            } else {
                piece.legalMovesSet.clear();
            }
        }
    }

    public void updateAllLegalMoves() {
        updateAllPossibleMoves();
        for (BasePiece piece : whiteTeamPieces) {
            if (piece.location != null) {
                piece.cleanPossibleMoves(this);
            } else {
                piece.legalMovesSet.clear();
            }
        }
        for (BasePiece piece : blackTeamPieces) {
            if (piece.location != null) {
                piece.cleanPossibleMoves(this);
            } else {
                piece.legalMovesSet.clear();
            }
        }
    }

    //Todo fix this
    public boolean move(BasePiece piece, Square destination) {
        if (piece.checkMoveIsIllegal(destination)) {
            return false;
        }

        Square originalLocation = piece.location;
        BasePiece originalPiece = destination.getPiece();
        destination.setPiece(piece);
        piece.incrementMovesCounter();
        updateAllLegalMoves();

        piece.specialRulePostMove(this, originalLocation, destination, originalPiece);

        if (isKingInCheck(!piece.isWhite())) {
            System.out.print("Check!");
        }

        return true;
    }

    public boolean isKingInCheck(boolean white) {
        boolean output = false;
        if (white) {
            for (BasePiece piece : blackTeamPieces) {
                for (Square square : piece.legalMovesSet) {
                    if (whiteKing.getLocation().equals(square)) {
                        output = true;
                    }
                }
            }
        } else {
            for (BasePiece piece : whiteTeamPieces) {
                for (Square square : piece.legalMovesSet) {
                    if (blackKing.getLocation().equals(square)) {
                        output = true;
                    }
                }
            }
        }
        return output;
    }

    public void resetHighlightedSquares() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                array[i][j].setHighlighted(false);
            }
        }
    }

    public Square getSquare(Coord coord) {
        return array[coord.x][coord.y];
    }

    //TODO move this method to PlayChess Class
    public static Coord stringToCoord(String input) throws IndexOutOfBoundsException {
        input = input.toLowerCase();
        char[] location = input.toCharArray();

        if (location[0] > 104 || location[0] < 97 || location[1] > 56 || location[1] < 49) {
            throw new IndexOutOfBoundsException(input + " is not a valid coordinate");
        }

        int x = ((int) location[0]) - 97;
        int y = location[1] - 49;
        return new Coord(x, y);
    }

    public String toString() {
        String string = "";
        boolean light = true;
        for (int x = 7; x >= 0; x--) {
            string = string + (x + 1) + " ";
            for (int y = 0; y < 8; y++) {
                String tile;
                if (this.array[y][x].isHighlighted()) {
                    tile = "\033[48;2;77;163;99m";
                } else if (light) {
                    tile = "\033[48;2;157;163;179m";
                } else {
                    tile = "\033[48;2;36;44;64m";
                }
                if (array[y][x].getPiece() == null) {
                    tile = tile + "  \u001B[0m";
                } else {
                    tile = tile + array[y][x].getPiece().toString() + "\u001B[0m";
                }
                string = string + tile;
                light = !light;
            }
            string = string + "\n";
            light = !light;
        }
        string = string + "  ";
        for (int i = 1; i <= 8; i++) {
            string = string + String.format("%c ", (char) i + 64);
        }
        string = string + "\n";
        return string;
    }
}
