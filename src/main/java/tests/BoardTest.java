package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import com.example.arimaa2.Model.*;
import com.example.arimaa2.*;

/**
 * Unit tests for the Board class.
 */
public class BoardTest {

    private Board board;

    /**
     * Set up method to initialize the Board object before each test case.
     */
    @Before
    public void setup() {
        board = new Board();
    }

    /**
     * Test case for the movePieceToCell() method.
     * It verifies the movement of a piece to a cell and checks the resulting state of the board.
     */
    @Test
    public void testMovePieceToCell() {
        // Set up initial conditions
        board.setPlayerColor(PieceColor.SILVER);
        board.createPiece(1, 1, PieceType.DOG, PieceColor.SILVER);
        board.selectPiece(board.getSelectedPiece(1, 1));

        // Perform the move
        board.movePieceToCell(1, 0, 1, 1);

        // Assertions to verify the resulting state of the board
        Assert.assertNull(board.getSelectedPiece(1, 1));
        Assert.assertEquals(PieceType.DOG, board.getSelectedPiece(1, 0).getType());
        Assert.assertEquals(PieceColor.SILVER, board.getSelectedPiece(1, 0).getColor());
        Assert.assertTrue(board.getRemainingMoves());
        Assert.assertEquals(PieceColor.SILVER, board.getPlayerColor());
    }

    /**
     * Another test case for the movePieceToCell() method.
     * It tests a series of consecutive moves and checks the resulting state of the board.
     */
    @Test
    public void testMovePieceToCell1() {
        // Set up initial conditions
        board.setPlayerColor(PieceColor.SILVER);
        board.createPiece(1, 1, PieceType.DOG, PieceColor.SILVER);
        board.selectPiece(board.getSelectedPiece(1, 1));

        // Perform a series of moves
        board.movePieceToCell(1, 2, 1, 1);
        board.movePieceToCell(1, 3, 1, 2);
        board.movePieceToCell(1, 4, 1, 3);
        board.movePieceToCell(1, 5, 1, 4);

        // Assertions to verify the resulting state of the board
        Assert.assertNull(board.getSelectedPiece(1, 1));
        Assert.assertEquals(PieceType.DOG, board.getSelectedPiece(1, 5).getType());
        Assert.assertEquals(PieceColor.SILVER, board.getSelectedPiece(1, 5).getColor());
        Assert.assertFalse(board.getRemainingMovesForTimer());
        Assert.assertEquals(PieceColor.GOLD, board.getPlayerColor());
    }

    /**
     * Test case for the movePieceToCell() method when the destination cell is a trap.
     * It verifies that the piece is not moved and the trap condition is correctly set.
     */
    @Test
    public void testMovePieceToCellTrap() {
        // Set up initial conditions
        board.setPlayerColor(PieceColor.SILVER);
        board.createPiece(2, 1, PieceType.DOG, PieceColor.SILVER);
        board.selectPiece(board.getSelectedPiece(2, 1));

        // Perform the move
        board.movePieceToCell(2, 2, 2, 1);

        // Assertions to verify the resulting state of the board
        Assert.assertTrue(board.isTrap(2, 2));
        Assert.assertNull(board.getSelectedPiece(2, 1));
        Assert.assertNull(board.getSelectedPiece(2, 2));
    }


    /**
     * Test case for the movePieceToCell() method when the destination cell is already occupied.
     * It verifies that the piece is not moved and remains in its original cell.
     */
    @Test
    public void testMovePieceToCellOccupied() {
        // Set up initial conditions
        board.setPlayerColor(PieceColor.SILVER);
        board.createPiece(3, 3, PieceType.DOG, PieceColor.SILVER);
        board.selectPiece(board.getSelectedPiece(3, 3));
        board.createPiece(3, 4, PieceType.CAT, PieceColor.SILVER);
        board.selectPiece(board.getSelectedPiece(3, 4));

        // Perform the move
        board.movePieceToCell(3, 4, 3, 3);

        // Assertions to verify the resulting state of the board
        Assert.assertNotNull(board.getSelectedPiece(3, 3));
        Assert.assertEquals(PieceType.DOG, board.getSelectedPiece(3, 3).getType());
        Assert.assertEquals(PieceColor.SILVER, board.getSelectedPiece(3, 3).getColor());
    }

    /**
     * Test case for the update() method.
     * It tests updating a piece's position on the board.
     */
    @Test
    public void testUpdate() {
        // Set up initial conditions
        board.createPiece(1, 1, PieceType.DOG, PieceColor.GOLD);
        board.selectPiece(board.getSelectedPiece(1, 1));

        // Perform the update
        board.update(2, 1, 1, 1);

        // Assertions to verify the resulting state of the board
        Assert.assertNull(board.getSelectedPiece(1, 1));
        Assert.assertEquals(PieceType.DOG, board.getSelectedPiece(2, 1).getType());
        Assert.assertEquals(PieceColor.GOLD, board.getSelectedPiece(2, 1).getColor());
    }

    /**
     * Test case for the getPlayerRabbitRow() method.
     * It verifies the correct identification of the player's rabbit row.
     */
    @Test
    public void testGetPlayerRabbitRow() {
        // Set up initial conditions
        board.setPlayerColor(PieceColor.GOLD);
        board.createPiece(0, 1, PieceType.RABBIT, PieceColor.GOLD);
        board.selectPiece(board.getSelectedPiece(0, 1));
        board.movePieceToCell(0, 0, 0, 1);
        board.createPiece(0, 6, PieceType.RABBIT, PieceColor.SILVER);

        // Assertions to verify the resulting state of the board
        Assert.assertNull(board.getSelectedPiece(0, 1));
        Assert.assertEquals(PieceType.RABBIT, board.getSelectedPiece(0, 0).getType());
        Assert.assertEquals(PieceColor.GOLD, board.getSelectedPiece(0, 0).getColor());
        Assert.assertEquals(PieceColor.GOLD, board.getPlayerColor());
        Assert.assertTrue(board.getPlayerRabbitRow(PieceColor.GOLD));
        Assert.assertEquals("GOLD", board.getWinner());
    }

    /**
     * Test case for the createPiece() method.
     * It verifies the creation of a new piece on the board.
     */
    @Test
    public void testCreatePiece() {
        // Perform the creation of a new piece
        board.createPiece(0, 0, PieceType.CAT, PieceColor.GOLD);

        // Get the created piece
        Piece piece = board.getSelectedPiece(0, 0);

        // Assertions to verify the resulting state of the board
        Assert.assertNotNull(piece);
        Assert.assertEquals(PieceType.CAT, piece.getType());
        Assert.assertEquals(PieceColor.GOLD, piece.getColor());
    }

    /**
     * Test case for the getSelectedPiece() method.
     * It verifies retrieving a selected piece from the board.
     */
    @Test
    public void testGetSelectedPiece() {
        // Set up initial conditions
        board.createPiece(1, 1, PieceType.DOG, PieceColor.SILVER);

        // Get the selected piece
        Piece piece = board.getSelectedPiece(1, 1);

        // Assertions to verify the retrieved piece
        Assert.assertNotNull(piece);
        Assert.assertEquals(PieceType.DOG, piece.getType());
        Assert.assertEquals(PieceColor.SILVER, piece.getColor());
    }

    /**
     * Test case for the changeGameStatus() method.
     * It verifies changing the game status on the board.
     */
    @Test
    public void testChangeGameStatus() {
        // Change the game status
        board.changeGameStatus(GameStatus.PROCESS);

        // Get the current game status
        GameStatus status = board.getStatus();

        // Assertion to verify the changed game status
        Assert.assertEquals(GameStatus.PROCESS, status);
    }

    /**
     * Test case for the getNextPieceName() method.
     * It verifies the generation of the next piece name based on the position, type, and color.
     */
    @Test
    public void testGetNextPieceName() {
        // Get the next piece name
        String nextPieceName = board.getNextPieceName(0, 0, PieceType.CAT, PieceColor.GOLD);

        // Assertion to verify the generated next piece name
        Assert.assertEquals("catg", nextPieceName);
    }

}

