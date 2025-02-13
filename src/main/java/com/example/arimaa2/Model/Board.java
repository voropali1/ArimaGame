package com.example.arimaa2.Model;

import com.example.arimaa2.*;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the game board.
 */
public class Board {
    int playerfortime = 0;
    Piece[][] boardpiece = new Piece[8][8];
    List<String> savailablePieces = Arrays.asList("rabbits", "cats", "dogs", "horses", "elephants", "camels");
    List<String> gavailablePieces = Arrays.asList("rabbitg", "catg", "dogg", "horseg", "elephantg", "camelg");
    List<String> powerlist = Arrays.asList("RABBIT", "CAT", "DOG", "HORSE", "ELEPHANT", "CAMEL");
    String nextPieceName;
    String winner = "";
    boolean trueRabbitRowEnd = false;
    PieceColor playerColor = PieceColor.GOLD;
    private Piece selectedPiece;
    private int remainingMoves = 4;
    private Piece[][] board1;
    private GameStatus status;

    /**
     * Initializes a new instance of the Board class.
     * Creates an 8x8 board and sets the initial game status to START.
     */
    public Board() {
        this.board1 = new Piece[8][8];
        this.status = GameStatus.START;
    }

    /**
     * Creates a new piece at the specified position with the given type and color.
     *
     * @param x     The x-coordinate of the position.
     * @param y     The y-coordinate of the position.
     * @param type  The type of the piece.
     * @param color The color of the piece.
     */
    public void createPiece(int x, int y, PieceType type, PieceColor color) {
        Piece piece = new Piece(type, color);
        boardpiece[x][y] = piece;
    }

    /**
     * Retrieves the piece at the specified position.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The piece at the specified position.
     */
    public Piece getSelectedPiece(int x, int y) {

        return boardpiece[x][y];
    }

    /**
     * Retrieves the current game status.
     *
     * @return The current game status.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Changes the game status to the specified new status.
     *
     * @param newStatus The new game status.
     */
    public void changeGameStatus(GameStatus newStatus) {
        this.status = newStatus;
    }

    /**
     * Retrieves the name of the next piece based on the given parameters.
     *
     * @param gcolI      The gold player's column index.
     * @param growI      The gold player's row index.
     * @param pieceType  The type of the piece.
     * @param pieceColor The color of the piece.
     * @return The name of the next piece.
     */
    public String getNextPieceName(int gcolI, int growI, PieceType pieceType, PieceColor pieceColor) {
        int nextPiece = 0 % gavailablePieces.size();
        if (pieceColor == PieceColor.GOLD) {
            if (pieceType == PieceType.CAT) {
                nextPiece = 1 % gavailablePieces.size();
            }
            if (pieceType == PieceType.DOG) {
                nextPiece = 2 % gavailablePieces.size();
            }
            if (pieceType == PieceType.HORSE) {
                nextPiece = 3 % gavailablePieces.size();
            }
            if (pieceType == PieceType.ELEPHANT) {
                nextPiece = 4 % gavailablePieces.size();
            }
            if (pieceType == PieceType.CAMEL) {
                nextPiece = 5 % gavailablePieces.size();
            }
            nextPieceName = gavailablePieces.get(nextPiece);
        } else {
            if (pieceType == PieceType.CAT) {
                nextPiece = 1 % savailablePieces.size();
            }
            if (pieceType == PieceType.DOG) {
                nextPiece = 2 % savailablePieces.size();
            }
            if (pieceType == PieceType.HORSE) {
                nextPiece = 3 % savailablePieces.size();
            }
            if (pieceType == PieceType.ELEPHANT) {
                nextPiece = 4 % savailablePieces.size();
            }
            if (pieceType == PieceType.CAMEL) {
                nextPiece = 5 % savailablePieces.size();
            }
            nextPieceName = savailablePieces.get(nextPiece);
        }
        return nextPieceName;
    }

    /**
     * Checks if the count of each type of piece is correct.
     *
     * @param rabbitcount   The count of rabbits.
     * @param catcount      The count of cats.
     * @param dogcount      The count of dogs.
     * @param horsecount    The count of horses.
     * @param elephantcount The count of elephants.
     * @param camelcount    The count of camels.
     * @return {@code true} if the counts are correct, {@code false} otherwise.
     */
    public boolean getCount(Integer rabbitcount, Integer catcount, Integer dogcount, Integer horsecount, Integer elephantcount, Integer camelcount) {
        if (rabbitcount == 16 && catcount == 4 && dogcount == 4 && horsecount == 4 && elephantcount == 2 && camelcount == 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Selects a piece to be moved.
     *
     * @param piece The piece to be selected.
     */
    public void selectPiece(Piece piece) {
        selectedPiece = piece;
    }

    /**
     * Moves a selected piece to a specific cell on the board.
     *
     * @param x2 The x-coordinate of the destination cell.
     * @param y2 The y-coordinate of the destination cell.
     * @param x1 The x-coordinate of the source cell.
     * @param y1 The y-coordinate of the source cell.
     */
    public void movePieceToCell(int x2, int y2, int x1, int y1) {
        if (getSelectedPiece(x1, y1) != null && remainingMoves >= 1 && (!(getPlayerRabbitRow(PieceColor.GOLD))) && (!(getPlayerRabbitRow(PieceColor.SILVER)))) {
            update(x2, y2, x1, y1);
            remainingMoves--;
            if (remainingMoves == 0) {
                selectedPiece = null;
                if (playerColor == PieceColor.GOLD) {
                    setPlayerColor(PieceColor.SILVER);
                } else {
                    setPlayerColor(PieceColor.GOLD);
                }
            }
        }

    }

    /**
     * Gets the color of the current player.
     *
     * @return The color of the current player.
     */
    public PieceColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Sets the color of the current player.
     *
     * @param color The color to set.
     */
    public void setPlayerColor(PieceColor color) {
        if (playerColor == PieceColor.GOLD && color == PieceColor.GOLD) {
            playerColor = color;
        } else if (playerColor == PieceColor.SILVER && color == PieceColor.SILVER) {
            playerColor = color;
        } else {
            playerColor = color;
            remainingMoves = 4;
            playerfortime++;
        }
    }

    /**
     * Updates the game board by moving the selected piece to the specified cell.
     * If the destination cell is not a trap and is empty, the selected piece is moved.
     * If the target cell is empty and trapped, the selected fragment is removed from the source cell.
     * The player's rabbit row is also updated.
     *
     * @param x2 The x-coordinate of the destination cell.
     * @param y2 The y-coordinate of the destination cell.
     * @param x1 The x-coordinate of the source cell.
     * @param y1 The y-coordinate of the source cell.
     */
    public void update(int x2, int y2, int x1, int y1) {
        if ((!(isTrap(x2, y2))) && getSelectedPiece(x2, y2) == null) {
            boardpiece[x1][y1] = null;
            boardpiece[x2][y2] = selectedPiece;
        } else if (getSelectedPiece(x2, y2) == null) {
            boardpiece[x1][y1] = null;
            selectedPiece = null;
        }
        getPlayerRabbitRow(getPlayerColor());
    }

    /**
     * Updates the game board by removing the piece from the specified cell.
     *
     * @param x1 The x-coordinate of the cell.
     * @param y1 The y-coordinate of the cell.
     */
    public void updateForChange(int x1, int y1) {
        boardpiece[x1][y1] = null;
    }

    /**
     * Checks if the specified cell on the game board is empty.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return {@code true} if the cell is empty, {@code false} otherwise.
     */
    public boolean isEmpty(int x, int y) {
        if (boardpiece[x][y] == null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if there are remaining moves for the current player.
     *
     * @return {@code true} if there are remaining moves, {@code false} otherwise.
     */
    public boolean getRemainingMoves() {
        if (remainingMoves == 0) {
            return false;
        }
        return true;
    }

    /**
     * Checks if there are remaining moves for the current player's timer.
     *
     * @return {@code true} if there are remaining moves for the timer, {@code false} otherwise.
     */
    public boolean getRemainingMovesForTimer() {
        if (remainingMoves == 4) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the current player has any moves.
     *
     * @return {@code true} if the player has moves, {@code false} otherwise.
     */
    public boolean getHaveMoves() {
        if (remainingMoves == 4) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the specified cell on the game board is a trap.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return {@code true} if the cell is a trap, {@code false} otherwise.
     */
    public boolean isTrap(int x, int y) {
        if ((x == 2 && y == 2) || (x == 5 && y == 2) || (x == 2 && y == 5) || (x == 5 && y == 5)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a piece of the specified type can make a move.
     *
     * @param pieceType The type of the piece to check.
     * @return {@code true} if the piece type can move, {@code false} otherwise.
     */
    public boolean getCanMoveType(PieceType pieceType) {
        if (pieceType == PieceType.RABBIT) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the player's rabbit has reached the opponent's side and ends the game if true.
     *
     * @param playerColor The color of the player.
     * @return {@code true} if the player's rabbit has reached the opponent's side, {@code false} otherwise.
     */
    public boolean getPlayerRabbitRow(PieceColor playerColor) {
        if (playerColor == PieceColor.GOLD) {
            for (int x = 0; x < 8; x++) {
                if (getSelectedPiece(x, 0) != null && getSelectedPiece(x, 0).getType().equals(PieceType.RABBIT) && getSelectedPiece(x, 0).getColor().equals(PieceColor.GOLD)) {
                    trueRabbitRowEnd = true;
                    winner = "GOLD";
                    changeGameStatus(GameStatus.END);
                }
            }
        } else {
            for (int y = 0; y < 8; y++) {
                if (getSelectedPiece(y, 7) != null && getSelectedPiece(y, 7).getType().equals(PieceType.RABBIT) && getSelectedPiece(y, 7).getColor().equals(PieceColor.SILVER)) {
                    trueRabbitRowEnd = true;
                    winner = "SILVER";
                    changeGameStatus(GameStatus.END);
                }
            }
        }
        return trueRabbitRowEnd;
    }

    /**
     * Gets the winner of the game.
     *
     * @return The winner of the game.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Sets the text area to the specified string.
     *
     * @param strset The string to set in the text area.
     */
    public void setTextArea(String strset) {
        String str = strset;
    }

    /**
     * Gets the color of the next player based on the provided string.
     *
     * @param string The string indicating the next player's turn.
     * @return The color of the next player.
     */
    public PieceColor getNextPlayer(String string) {
        if (string == "THE GOLD PLAYER MAKES A MOVE") {
            return PieceColor.GOLD;
        } else {
            return PieceColor.SILVER;
        }
    }

    /**
     * Checks if a piece can move to the specified cell based on the coordinates.
     *
     * @param x2 The x-coordinate of the destination cell.
     * @param y2 The y-coordinate of the destination cell.
     * @param x1 The x-coordinate of the source cell.
     * @param y1 The y-coordinate of the source cell.
     * @return {@code true} if the piece can move to the cell, {@code false} otherwise.
     */
    public boolean canMoveXY(int x2, int y2, int x1, int y1) {
        if (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 0 || Math.abs(x2 - x1) == 0 && Math.abs(y2 - y1) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a piece can pull another piece to the specified cell based on the coordinates.
     *
     * @param x2 The x-coordinate of the destination cell.
     * @param y2 The y-coordinate of the destination cell.
     * @param x1 The x-coordinate of the source cell.
     * @param y1 The y-coordinate of the source cell.
     * @return {@code true} if the piece can pull another piece, {@code false} otherwise.
     */
    public boolean canPull(int x2, int y2, int x1, int y1) {
        if (Math.abs(x2 - x1) == 2 && Math.abs(y2 - y1) == 0 || Math.abs(x2 - x1) == 0 && Math.abs(y2 - y1) == 2 || Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a piece can push another piece to the specified cell based on the coordinates.
     *
     * @param x2 The x-coordinate of the destination cell.
     * @param y2 The y-coordinate of the destination cell.
     * @param x1 The x-coordinate of the source cell.
     * @param y1 The y-coordinate of the source cell.
     * @return {@code true} if the piece can push another piece, {@code false} otherwise.
     */
    public boolean canPush(int x2, int y2, int x1, int y1) {
        if (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 0 || Math.abs(x2 - x1) == 0 && Math.abs(y2 - y1) == 1) { //|| Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 1
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the piece in the source cell has more power than the piece in the destination cell.
     *
     * @param x2 The x-coordinate of the destination cell.
     * @param y2 The y-coordinate of the destination cell.
     * @param x1 The x-coordinate of the source cell.
     * @param y1 The y-coordinate of the source cell.
     * @return {@code true} if the piece in the source cell has more power, {@code false} otherwise.
     */
    public boolean getPower(int x2, int y2, int x1, int y1) {
        if (boardpiece[x1][y1] != null && boardpiece[x2][y2] != null) {
            String piece1 = boardpiece[x1][y1].getType().toString();
            String piece2 = boardpiece[x2][y2].getType().toString();
            int indexPiece1 = powerlist.indexOf(piece1);
            int indexPiece2 = powerlist.indexOf(piece2);
            if (indexPiece1 > indexPiece2) {
                return true;
            }
        }
        return false;
    }
}

