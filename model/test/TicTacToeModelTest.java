import java.util.Arrays;
import org.junit.Test;

import tictactoe.Player;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test cases for the tic tac toe model. Verifying that game state is properly managed, and all game
 * actions are properly validated.
 */
public class TicTacToeModelTest {

  private TicTacToe ttt1 = new TicTacToeModel();

  /**
   * Tests the toString method of enum Player.
   */
  @Test
  public void testPlayerToString() {
    Player p = Player.O;
    assertEquals("O", p.toString());
  }

  /**
   * Tests the game starting with Player X.
   */
  @Test
  public void testDefaultPlayer() {
    assertEquals(Player.X, ttt1.getTurn());
  }

  /**
   * Tests that the turn is changed when move method is invoked.
   */
  @Test
  public void testChangeTurnInMove() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
  }

  /**
   * Tests a legal move.
   */
  @Test
  public void testLegalMove1() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
  }

  /**
   * Tests a move at an occupied position.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveDuplicateMove() {
    ttt1.move(0, 0);
    ttt1.move(0, 0);
  }

  /**
   * Tests an invalid move where row is smaller than the lowest bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveRow1() {
    ttt1.move(-1, 0);
  }

  /**
   * Tests an invalid move where row is larger than the highest bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveRow2() {
    ttt1.move(3, 0);
  }

  /**
   * Tests an invalid move where column is smaller than the lowest bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveCol1() {
    ttt1.move(0, -1);
  }

  /**
   * Tests an invalid move where column is larger than the highest bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveCol2() {
    ttt1.move(0, 3);
  }

  /**
   * Tests an invalid move when the board is full but no winner.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveFullBoard() {
    ttt1.move(0, 0);//X takes upper left
    ttt1.move(1, 1);//O takes middle
    ttt1.move(1, 0);//X takes middle left
    ttt1.move(2, 0);//O takes bottom left
    ttt1.move(0, 2);//X takes upper right
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(2, 1);//X takes bottom middle
    ttt1.move(1, 2);//O takes middle right
    ttt1.move(2, 2);//X takes bottom right

    ttt1.move(0, 0);
  }

  /**
   * Tests an invalid move when there is a winner.
   */
  @Test(expected = IllegalStateException.class)
  public void testIllegalMoveGameOver() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(0, 1); // X takes upper middle
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(0, 2); // X takes upper right

    ttt1.move(2, 2);
  }

  /**
   * Tests getTurn method.
   */
  @Test
  public void testGetTurn() {
    assertEquals(Player.X, ttt1.getTurn());
  }

  /**
   * Tests a false condition of isGameOver.
   */
  @Test
  public void testFalseGameOver(){
    ttt1.move(0,0);
    assertFalse(ttt1.isGameOver());
  }

  /**
   * Tests horizontally winning the game at first row.
   */
  @Test
  public void testHorizontalWin1() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(0, 1); // X takes upper middle
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(0, 2); // X takes upper right
    assertTrue(ttt1.isGameOver()); //X wins
  }

  /**
   * Tests horizontally winning the game at second row.
   */
  @Test
  public void testHorizontalWin2() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(2, 0); // X takes bottom left
    ttt1.move(1, 1); // O takes middle
    ttt1.move(0, 2); // X takes upper right
    ttt1.move(1, 2); // O takes middle right
    assertTrue(ttt1.isGameOver()); // O wins
  }

  /**
   * Tests horizontally winning the game at third row.
   */
  @Test
  public void testHorizontalWin3() {
    ttt1.move(2, 0); // X takes bottom left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(2, 1); // X takes bottom middle
    ttt1.move(1, 1); // O takes middle
    ttt1.move(2, 2); // X takes bottom right
    assertTrue(ttt1.isGameOver());// X wins
  }

  /**
   * Tests vertically winning the game at first column.
   */
  @Test
  public void testColWin1() {
    ttt1.move(0, 0);//X takes upper left
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(1, 0);//X takes middle left
    ttt1.move(0, 2);//O takes upper right
    ttt1.move(2, 0);//X takes down left
    assertTrue(ttt1.isGameOver());//X wins
  }

  /**
   * Tests vertically winning the game at second column.
   */
  @Test
  public void testColWin2() {
    ttt1.move(0, 0);//X takes upper left
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(1, 0);//X takes middle left
    ttt1.move(1, 1);//O takes middle
    ttt1.move(0, 2);//X takes upper right
    ttt1.move(2, 1);//O takes bottom middle
    assertTrue(ttt1.isGameOver());//O wins
  }

  /**
   * Tests vertically winning the game at third column.
   */
  @Test
  public void testColWin3() {
    ttt1.move(0, 2);//X takes upper right
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(1, 2);//X takes middle right
    ttt1.move(1, 1);//O takes middle
    ttt1.move(2, 2);//X takes bottom right
    assertTrue(ttt1.isGameOver());//X wins
  }

  /**
   * Tests diagonally winning the game from upper right to bottom left.
   */
  @Test
  public void testDiagWin1() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(1, 0); // X takes middle left
    ttt1.move(1, 1); // O takes center
    ttt1.move(0, 1); // X takes upper middle
    ttt1.move(0, 2); // O takes upper right
    assertTrue(ttt1.isGameOver());//O wins
  }

  /**
   * Tests diagonally winning the game from upper left to bottom right.
   */
  @Test
  public void testDiagWin2() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(1, 1); // X takes center
    ttt1.move(1, 2); // O takes center
    ttt1.move(2, 2); // X takes bottom right
    assertTrue(ttt1.isGameOver());//X wins
  }

  /**
   * Tests the situation when the board is full without a winner.
   */
  @Test
  public void testFullBoardOver() {
    ttt1.move(0, 0);//X takes upper left
    ttt1.move(1, 1);//O takes middle
    ttt1.move(1, 0);//X takes middle left
    ttt1.move(2, 0);//O takes bottom left
    ttt1.move(0, 2);//X takes upper right
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(2, 1);//X takes bottom middle
    ttt1.move(1, 2);//O takes middle right
    assertFalse(ttt1.isGameOver());
    ttt1.move(2, 2);//X takes bottom right

    assertTrue(ttt1.isGameOver());
  }

  /**
   * Tests that a tied game properly ends the game.
   */
  @Test
  public void testCatGame(){
    ttt1.move(0, 0);
    ttt1.move(1, 1);
    ttt1.move(0, 2);
    ttt1.move(0, 1);
    ttt1.move(2, 1);
    ttt1.move(1, 0);
    ttt1.move(1, 2);
    ttt1.move(2, 2);
    ttt1.move(2, 0);

    assertTrue(ttt1.isGameOver());
  }

  /**
   * Tests the board being full, with a winner.
   */
  @Test
  public void testFullBoardWinner(){
    ttt1.move(0,0);//X takes upper left
    ttt1.move(0,1);//O takes upper middle
    ttt1.move(2,2);//X takes bottom right
    ttt1.move(2,0);//O takes bottom left
    ttt1.move(0,2);//X takes upper right
    ttt1.move(1,2);//O takes middle right
    ttt1.move(1,0);//X takes middle left
    ttt1.move(2,1);//O takes bottom middle
    ttt1.move(1,1);//X takes center

    assertTrue(ttt1.isGameOver());
  }

  /**
   * Tests getWinner when X wins horizontally.
   */
  @Test
  public void getHoriWinner1() {
    ttt1.move(2, 0); // X takes bottom left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(2, 1); // X takes bottom middle
    ttt1.move(1, 1); // O takes middle
    ttt1.move(2, 2); // X takes bottom right
    assertEquals(Player.X, ttt1.getWinner());
  }

  /**
   * Tests getWinner when O wins horizontally.
   */
  @Test
  public void getHoriWinner2() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(2, 0); // X takes bottom left
    ttt1.move(1, 1); // O takes middle
    ttt1.move(0, 2); // X takes upper right
    ttt1.move(1, 2); // O takes middle right
    assertEquals(Player.O, ttt1.getWinner());
  }

  /**
   * Tests getWinner when O wins vertically.
   */
  @Test
  public void getColWinner1() {
    ttt1.move(0, 0);//X takes upper left
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(1, 0);//X takes middle left
    ttt1.move(1, 1);//O takes middle
    ttt1.move(0, 2);//X takes upper right
    ttt1.move(2, 1);//O takes bottom middle
    assertEquals(Player.O, ttt1.getWinner());
  }

  /**
   * Tests getWinner when X wins vertically.
   */
  @Test
  public void getColWinner2() {
    ttt1.move(0, 2);//X takes upper right
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(1, 2);//X takes middle right
    ttt1.move(1, 1);//O takes middle
    ttt1.move(2, 2);//X takes bottom right
    assertEquals(Player.X, ttt1.getWinner());
  }

  /**
   * Tests getWinner when O wins diagonally.
   */
  @Test
  public void getDiagWinner1() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(1, 0); // X takes middle left
    ttt1.move(1, 1); // O takes center
    ttt1.move(0, 1); // X takes upper middle
    ttt1.move(0, 2); // O takes upper right
    assertEquals(Player.O, ttt1.getWinner());
  }

  /**
   * Tests getWinner when X wins diagonally.
   */
  @Test
  public void getDiagWinner2() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(1, 1); // X takes center
    ttt1.move(1, 2); // O takes center
    ttt1.move(2, 2); // X takes bottom right
    assertEquals(Player.X, ttt1.getWinner());
  }

  /**
   * Tests getWinner when the board is full without winner.
   */
  @Test
  public void getWinnerNullFullBoard() {
    ttt1.move(0, 0);//X takes upper left
    ttt1.move(1, 1);//O takes middle
    ttt1.move(1, 0);//X takes middle left
    ttt1.move(2, 0);//O takes bottom left
    ttt1.move(0, 2);//X takes upper right
    ttt1.move(0, 1);//O takes upper middle
    ttt1.move(2, 1);//X takes bottom middle
    ttt1.move(1, 2);//O takes middle right
    ttt1.move(2, 2);//X takes bottom right

    assertNull(ttt1.getWinner());
  }

  /**
   * Tests getWinner when no winner appears.
   */
  @Test
  public void getWinnerNull() {
    ttt1.move(0, 0);//X takes upper left
    ttt1.move(1, 1);//O takes middle
    assertNull(ttt1.getWinner());
  }

  /**
   * Tests getBoard method.
   */
  @Test
  public void getBoard1() {
    Player[][] temp = {{Player.X, null, null}, {Player.O, null, null}, {null, null, null}};
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(1, 0); // O takes middle left
    assertEquals(temp[0][0], ttt1.getBoard()[0][0]);
    assertEquals(temp[1][0], ttt1.getBoard()[1][0]);
  }

  /**
   * Tests getBoard method which returns a new board instead of referencing the original board.
   */
  @Test
  public void getBoard2() {
    ttt1.getBoard()[0][0] = Player.O;
    assertEquals(null, ttt1.getBoard()[0][0]);
  }

  /**
   * Tests illegal row input for getMarkAt.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getMarkIllegalRow1() {
    ttt1.getMarkAt(-1, 0);
  }

  /**
   * Tests illegal row input for getMarkAt.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getMarkIllegalRow2() {
    ttt1.getMarkAt(4, 0);
  }

  /**
   * Tests illegal column input for getMarkAt.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getMarkIllegalCol1() {
    ttt1.getMarkAt(0, -1);
  }

  /**
   * Tests illegal column input for getMarkAt.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getMarkIllegalCol2() {
    ttt1.getMarkAt(0, 4);
  }

  /**
   * Tests a legal usage of getMarkAt method.
   */
  @Test
  public void testGetMarkAt() {
    ttt1.move(0, 0);
    assertEquals(Player.X, ttt1.getMarkAt(0, 0));
  }


  //The following tests are provided by default and are not written by myself.

  /**
   * Tests horizontal winning.
   */
  @Test
  public void testHorizontalWin() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(0, 1); // X takes upper middle
    assertNull(ttt1.getWinner());
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(0, 2); // X takes upper right
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getWinner());
    assertEquals(" X | X | X\n"
        + "-----------\n"
        + " O |   |  \n"
        + "-----------\n"
        + " O |   |  ", ttt1.toString());
  }

  /**
   * Tests diagonal winning.
   */
  @Test
  public void testDiagonalWin() {
    diagonalWinHelper();
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.O, ttt1.getWinner());
    assertEquals(" X | X | O\n"
        + "-----------\n"
        + " X | O |  \n"
        + "-----------\n"
        + " O |   |  ", ttt1.toString());
  }

  /**
   * set up situation where game is over, O wins on the diagonal, board is not full
   */
  // set up situation where game is over, O wins on the diagonal, board is not full
  private void diagonalWinHelper() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(1, 0); // X takes middle left
    assertNull(ttt1.getWinner());
    ttt1.move(1, 1); // O takes center
    ttt1.move(0, 1); // X takes upper middle
    ttt1.move(0, 2); // O takes upper right
  }

  /**
   * Tests invalid move.
   */
  @Test
  public void testInvalidMove() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
    assertEquals(Player.X, ttt1.getMarkAt(0, 0));
    try {
      ttt1.move(0, 0);
      fail("Invalid move should have thrown exception");
    } catch (IllegalArgumentException iae) {
      //assertEquals("Position occupied", iae.getMessage());
      assertTrue(iae.getMessage().length() > 0);
    }
    try {
      ttt1.move(-1, 0);
      fail("Invalid move should have thrown exception");
    } catch (IllegalArgumentException iae) {
      //assertEquals("Position occupied", iae.getMessage());
      assertTrue(iae.getMessage().length() > 0);
    }
  }

  /**
   * Tests move attempt after game over
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveAttemptAfterGameOver() {
    diagonalWinHelper();
    ttt1.move(2, 2); // 2,2 is an empty position
  }

  /**
   * Tests cats game.
   */
  @Test
  public void testCatsGame() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
    ttt1.move(1, 1);
    assertEquals(Player.X, ttt1.getTurn());
    ttt1.move(0, 2);
    ttt1.move(0, 1);
    ttt1.move(2, 1);
    ttt1.move(1, 0);
    ttt1.move(1, 2);
    ttt1.move(2, 2);
    ttt1.move(2, 0);
    assertTrue(ttt1.isGameOver());
    assertNull(ttt1.getWinner());
    assertEquals(" X | O | X\n"
        + "-----------\n"
        + " O | O | X\n"
        + "-----------\n"
        + " X | X | O", ttt1.toString());
  }

  /**
   * Tests invalid getMarkAtRow method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetMarkAtRow() {
    ttt1.getMarkAt(-12, 0);
  }

  /**
   * Tests invalid getMarkAtCol method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetMarkAtCol() {
    ttt1.getMarkAt(0, -30);
  }

  /**
   * Tests getBoard method.
   */
  @Test
  public void testGetBoard() {
    diagonalWinHelper();
    Player[][] bd = ttt1.getBoard();
    assertEquals(Player.X, bd[0][0]);
    assertEquals(Player.O, bd[1][1]);
    assertEquals(Player.X, bd[0][1]);

    // attempt to cheat by mutating board returned by getBoard()
    // check correct preconditions
    assertEquals(Player.O, bd[2][0]);
    assertEquals(Player.O, ttt1.getMarkAt(2, 0));
    bd[2][0] = Player.X;  // mutate
    // check correct post conditions
    assertEquals(Player.O, ttt1.getMarkAt(2, 0));
    Player[][] bd2 = ttt1.getBoard();
    assertEquals(Player.O, bd2[2][0]);
  }

  // TODO: test case where board is full AND there is a winner
}
