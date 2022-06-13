package tictactoe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The class represents TicTacToeConsoleController. It implements all methods in TicTacToeController
 * interface. It has three private fields. An In field which accepts user input. An out field which
 * shows system output. A move arraylist records valid move from user.
 */
public class TicTacToeConsoleController implements TicTacToeController {

  private final Readable in;
  private final Appendable out;
  private final ArrayList<Integer> move;

  /**
   * Constructs a TicTacToeConsoleController with two parameters, and initializes them to in field
   * and out field.
   *
   * @param in  user input
   * @param out system output
   */
  public TicTacToeConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Input for constructor is null.");
    }
    this.in = in;
    this.out = out;
    this.move = new ArrayList<Integer>();
  }

  @Override
  public void playGame(TicTacToe m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Input model is null.");
    }

    Scanner s1 = new Scanner(this.in);
    boolean updated = true;

    while (!m.isGameOver()) {

      if (updated) {
        StringBuilder str = new StringBuilder();
        str.append(m.toString()).append("\n")
            .append("Enter a move for ").append(m.getTurn().toString()).append(":\n");
        this.appendOut(str);
        updated = false;
      }

      int num1;
      num1 = next(s1);

      if (num1 == -2) {
        StringBuilder str = new StringBuilder();
        str.append("Game quit! Ending game state:\n").append(m.toString()).append("\n");
        this.appendOut(str);
        break;
      }

      if (num1 == -1) {
        continue;
      }

      this.move.add(num1);

      if (this.move.size() == 2) {
        try {
          m.move(move.get(0) - 1, move.get(1) - 1);
          this.move.clear();
          updated = true;
        } catch (IllegalArgumentException | IllegalStateException e) {
          StringBuilder str = new StringBuilder();
          str.append(String.format("Not a valid move: %d, %d\n", move.get(0), move.get(1)));
          this.appendOut(str);
          this.move.clear();
        }
      }
    }

    if (m.isGameOver()) {
      StringBuilder str = new StringBuilder();
      str.append(m.toString()).append("\n").append("Game is over! ");

      if (m.getWinner() == null) {
        str.append("Tie game.");
      } else {
        str.append(m.getWinner()).append(" wins.");
      }
      this.appendOut(str);
    }
  }

  /**
   * Return the state of getting next input.
   *
   * @param s1 scanner to get input from user
   * @return the state of getting next input
   */
  private int next(Scanner s1) {
    try {
      int num = s1.nextInt();
      return num;
    } catch (InputMismatchException e1) {
      String n = s1.next();
      if (n.equalsIgnoreCase("q")) {
        return -2;
      }
      StringBuilder str = new StringBuilder();
      str.append("Not a valid number: ").append(n).append("\n");
      this.appendOut(str);
      return -1;
    } catch (NoSuchElementException e2) {
      return -2;
    }
  }

  /**
   * Abstract the try-catch logic of appending a string to game log.
   *
   * @param s string ready to be appended to game log
   */
  private void appendOut(StringBuilder s) {
    try {
      this.out.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Appenable class throws an IOException.");
    }
  }

}
