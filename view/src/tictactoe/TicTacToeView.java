package tictactoe;

/**
 * The interface of TicTacToe View.
 */
public interface TicTacToeView {

  /**
   * The main callback method.
   * @param features that controller should support
   */
  void addFeature(Features features);

  /**
   * Display the chess on the button.
   * @param row button row position
   * @param col button col position
   * @param turn previous turn.
   */
  void displayChess(int row, int col, String turn);

  /**
   * Display the current turn, or game is over.
   * @param text to display.
   */
  void updateStatus(String text);

}
