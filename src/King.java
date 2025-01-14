import java.util.HashSet;

public class King extends BasePiece{

    public King(boolean white, Square location) {
        super(white, location);
    }

    public void specialRulePostMove(Board board, Square originalLocation, Square destination, BasePiece originalPiece) {
        //Check if king castled
        if (originalPiece instanceof Rook && originalPiece.isWhite() == this.isWhite()) {
            int x = originalLocation.getCoord().x;
            int y = originalLocation.getCoord().y;
            int horizontalDistanceTraveled = this.location.getCoord().x - x;

            //Check if king or queen side castle
            if (horizontalDistanceTraveled > 0) {
                board.array[x + 2][y].setPiece(this);
                board.array[x + 1][y].setPiece(originalPiece);
            } else {
                board.array[x - 2][y].setPiece(this);
                board.array[x - 1][y].setPiece(originalPiece);
            }

            originalPiece.incrementMovesCounter();
            board.updateAllPossibleMoves();
        }

    }

    @Override
    public void updatePossibleMoves(Board board) {
        HashSet<Square> legalMovesSet = new HashSet<>();
        Coord orthogonal = new Coord(1,0);
        Coord diagonal = new Coord(1,1);

        for (int i = 0; i < 4; i++) {
            legalMovesSet.addAll(createNewFeeler(board, orthogonal, 1));
            legalMovesSet.addAll(createNewFeeler(board, diagonal, 1));
            orthogonal = orthogonal.rotate90CW();
            diagonal = diagonal.rotate90CW();
        }

        int y = location.getCoord().y;
        HashSet<Square> castlingSet = new HashSet<>();
        if (getMovesCounter() == 0) {
            //King Side Castling
            boolean flag;
            castlingSet.addAll(createNewFeeler(board, new Coord(1,0)));
            flag = isCastlingPathInCheck(board, castlingSet);
            if (castlingSet.size() != 2) {
                flag = false;
            }
            if (board.array[7][y].getPiece() == null || board.array[7][y].getPiece().getMovesCounter() != 0) {
                flag = false;
            }
            if (flag) {
                legalMovesSet.add(board.array[7][y]);
            }

            //Queen Side Castling
            castlingSet.clear();
            castlingSet.addAll(createNewFeeler(board, new Coord(-1,0)));
            flag = isCastlingPathInCheck(board, castlingSet);
            if (castlingSet.size() != 3) {
                flag = false;
            }
            if (board.array[0][y].getPiece() == null || board.array[0][y].getPiece().getMovesCounter() != 0) {
                flag = false;
            }
            if (flag) {
                legalMovesSet.add(board.array[0][y]);
            }
        }

        this.legalMovesSet = legalMovesSet;
    }

    private boolean isCastlingPathInCheck(Board board, HashSet<Square> castlingList) {
        boolean flag = true;
        for (Square square : castlingList) {
                if (isWhite()) {
                    for (BasePiece piece : board.blackTeamPieces) {
                        if (piece.legalMovesSet.contains(square)) {
                            flag = false;
                        }
                    }
                } else {
                    for (BasePiece piece : board.whiteTeamPieces) {
                        if (piece.legalMovesSet.contains(square)) {
                            flag = false;
                        }
                    }
                }
        }
        return flag;
    }


    @Override
    public String toString() {
        if (this.isWhite()) {
            return "\u265A ";
        } else {
            return "\u001B[30m\u265A ";
        }
    }
}
