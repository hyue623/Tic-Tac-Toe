import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import tictactoe.FailingAppendable;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeConsoleController;
import tictactoe.TicTacToeController;
import tictactoe.TicTacToeModel;
import java.io.StringReader;
import java.util.Arrays;
import org.junit.Test;

/**
 * Test cases for the tic tac toe controller, using mocks for readable and appendable.
 */
public class TicTacToeControllerTest {

  // ADDITIONAL TEST CASES TO IMPLEMENT:
  // Play game to completion, where there is a winner
  // Input where the q comes instead of an integer for the row
  // Input where the q comes instead of an integer for the column
  // Input where non-integer garbage comes instead of an integer for the row
  // Input where non-integer garbage comes instead of an integer for the column
  // Input where the move is integers, but outside the bounds of the board
  // Input where the move is integers, but invalid because the cell is occupied
  // Multiple invalid moves in a row of various kinds
  // Input including valid moves interspersed with invalid moves, game is played to completion
  // What happens when the input ends "abruptly" -- no more input, but not quit, and game not over
  // THIS IS NOT AN EXHAUSTIVE LIST

  /**
   * Tests single valid move and quitting the game.
   */
  @Test
  public void testSingleValidMove() {
    TicTacToe m = new TicTacToeModel();
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(new StringReader("2 2 q"), gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Tests invalid input in row.
   */
  @Test
  public void testBogusInputAsRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("!#$ 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    // split the output into an array of lines
    String[] lines = gameLog.toString().split("\n");
    // check that it's the correct number of lines
    assertEquals(gameLog.toString(), 13, lines.length);
    // check that the last 6 lines are correct
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  ", lastMsg);
    // note no trailing \n here, because of the earlier split
  }

  /**
   * Tests tie game.
   */
  @Test
  public void testTieGame() {
    TicTacToe m = new TicTacToeModel();
    // note the entire sequence of user inputs for the entire game is in this one string:
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 60, lines.length);
    assertEquals("Game is over! Tie game.", lines[lines.length - 1]);
  }

  /**
   * Tests failing appendable.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Tests completing a game with all valid inputs and X wins.
   */
  @Test
  public void testXwins() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 1 2 2 2 3 3 2 1 3 1 2 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 48, lines.length);
    assertEquals("Game is over! X wins.", lines[lines.length - 1]);
  }

  /**
   * Tests completing a game with some invalid inputs and X wins.
   */
  @Test
  public void testXwinsInvalidInput() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 invalid 1 1 2 four 2 2 3 3 2 1 3 1 2 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 50, lines.length);
    assertEquals("Game is over! X wins.", lines[lines.length - 1]);
  }

  /**
   * Tests completing a game with all valid inputs and O wins.
   */
  @Test
  public void testOwins() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 2 2 1 3 1 2 3 2 2 1 3 3 2 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 54, lines.length);
    assertEquals("Game is over! O wins.", lines[lines.length - 1]);
  }

  /**
   * Tests completing a game with some invalid inputs and X wins.
   */
  @Test
  public void testOwinsInvalidInput() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 trap 1 trap 2 2 1 3 1 2 3 2 trap 2 1 3 3 2 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 57, lines.length);
    assertEquals("Game is over! O wins.", lines[lines.length - 1]);
  }

  /**
   * Tests null input for constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = null;
    StringBuilder gameLog = null;
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Tests null input for model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelInPlayGame() {
    TicTacToe m = null;
    StringReader input = new StringReader("1 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Tests invalid move, which is outside of board.
   */
  @Test
  public void testMoveOutsideBoard() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("5 6 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 13, lines.length);
  }

  /**
   * Tests invalid move, which is occupied in the board.
   */
  @Test
  public void testSameMove() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 1 2 1 2 1 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 21, lines.length);
  }

  /**
   * Tests input where the q comes instead of an integer for the row
   */
  @Test
  public void testQRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 1 1 q 1 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 24, lines.length);
  }

  /**
   * Tests input where the q comes instead of an integer for the column
   */
  @Test
  public void testQCol() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 1 1 3 q 2 2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 24, lines.length);
  }

  /**
   * Tests input where non-integer garbage comes instead of an integer for the row
   */
  @Test
  public void testGarbageRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 1 1 garbage 3 1 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 31, lines.length);
  }

  /**
   * Tests input where non-integer garbage comes instead of an integer for the column
   */
  @Test
  public void testGarbageCol() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 1 1 3 garbage 1 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 31, lines.length);
  }

  /**
   * Tests the input ending "abruptly" -- no more input, but not quit, and game. The controller will
   * catch an error and stop running.
   */
  @Test
  public void testAbruptEnding() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 1 1 3 1");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(gameLog.toString(), 30, lines.length);
  }

}
