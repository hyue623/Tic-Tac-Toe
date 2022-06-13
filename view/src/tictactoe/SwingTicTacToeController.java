package tictactoe;

import java.util.Scanner;

/**
 * This is the controller implemented the TicTacToeController interface
 */
public class SwingTicTacToeController implements TicTacToeController, Features{
  private final TicTacToeView v;
  private final TicTacToe m;

  /**
   * This is the constructor of TicTacToe controller
   * @param v type should be TicTacToeView.
   * @param m type should be TicTacToeModel.
   * @throws IllegalArgumentException if arguments are not correct type.
   */
  public SwingTicTacToeController( TicTacToeView v,  TicTacToe m) throws IllegalArgumentException {
    if (v == null || m == null) {
      throw new IllegalArgumentException("Arguments must be right type.");
    }
    this.v = v;
    this.m = m;
  }

  @Override
  public void playGame() {
    v.addFeature(this);
  }

  @Override
  public void placePiece(String coordinate) {
    Scanner sc = new Scanner(coordinate);
    int row = sc.nextInt();
    int col = sc.nextInt();
    String turn = m.getTurn().toString();
    if (!m.isGameOver()) {
      try {
        m.move(row, col);
        if (m.isGameOver()) {
          if (m.getWinner() != null) {
            v.displayChess(row, col, turn);
            v.updateStatus("Winner is " + m.getWinner());
          } else {
            v.displayChess(row, col, turn);
            v.updateStatus("It's a Tie game.");
          }
        } else {
          v.updateStatus("Turn: " + m.getTurn().toString());
          v.displayChess(row, col, turn);
        }
      } catch (IllegalArgumentException e) {
      }
    }
  }

}
