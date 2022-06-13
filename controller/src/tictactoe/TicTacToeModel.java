package tictactoe;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents TicTacToeModel. It implements all methods in TicTacToe interface. It by
 * default has two private fields. A turn field of Player type which represents the player who takes
 * the turn. A 2D array of Player type represents the board of tic-tac-toe.
 */
public class TicTacToeModel implements TicTacToe {

  // add your implementation here
  private Player turn;
  private final Player[][] board;

  /**
   * Constructs a tictactoe model without parameter, but initializes it to two components, board and
   * turn.
   */
  public TicTacToeModel() {
    this.board = new Player[3][3];
    this.turn = Player.X;
  }

  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
            row -> " " + Arrays.stream(row).map(
                p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
        .collect(Collectors.joining("\n-----------\n"));
    // This is the equivalent code as above, but using iteration, and still using 
    // the helpful built-in String.join method.
    /**********
     List<String> rows = new ArrayList<>();
     for(Player[] row : getBoard()) {
     List<String> rowStrings = new ArrayList<>();
     for(Player p : row) {
     if(p == null) {
     rowStrings.add(" ");
     } else {
     rowStrings.add(p.toString());
     }
     }
     rows.add(" " + String.join(" | ", rowStrings));
     }
     return String.join("\n-----------\n", rows);
     ************/
  }

  @Override
  public void move(int r, int c) throws IllegalArgumentException, IllegalStateException {
    this.checkIllegalIndex(r, c);
    if (this.board[r][c] != null) {
      throw new IllegalArgumentException("The movement is out of board's range "
          + "or is occupied.");
    }
    if (this.isGameOver()) {
      throw new IllegalStateException("The game is over. No more movement is available.");
    }
    this.board[r][c] = this.getTurn();
    this.updateTurn();
  }

  @Override
  public Player getTurn() {
    return this.turn;
  }

  @Override
  public boolean isGameOver() {
    return this.isRowWin(this.getBoard()) ||
        this.isColWin(this.getBoard()) ||
        this.isDiagWin(this.getBoard()) ||
        this.isBoardFull(this.getBoard());
  }

  @Override
  public Player getWinner() {
    if (this.isRowWin(this.getBoard()) ||
        this.isColWin(this.getBoard()) ||
        this.isDiagWin(this.getBoard())) {
      return this.getNextPlayer();
    }
    return null;
  }

  @Override
  public Player[][] getBoard() {
    return Arrays.stream(this.board)
        .map(a -> Arrays.copyOf(a, a.length))
        .toArray(Player[][]::new);
  }

  @Override
  public Player getMarkAt(int r, int c) {
    this.checkIllegalIndex(r, c);
    return this.getBoard()[r][c];
  }

  /**
   * Change the value of a private field, turn.
   */
  private void updateTurn() {
    if (this.turn == Player.X) {
      this.turn = Player.O;
    } else {
      this.turn = Player.X;
    }
  }

  /**
   * Returns the next player based on the current turn.
   *
   * @return the next player based on the current turn
   */
  private Player getNextPlayer() {
    return this.turn == Player.O ? Player.X : Player.O;
  }

  /**
   * Returns whether a winner appears in a row of tictactoe.
   *
   * @param board the board of tictactoe game
   * @return whether a winner appears in a row of tictactoe
   */
  private boolean isRowWin(Player[][] board) {

    return Arrays.stream(board)
        .anyMatch(row -> Arrays.stream(row).allMatch(p -> p == Player.O) ||
            Arrays.stream(row).allMatch(p -> p == Player.X));
  }

  /**
   * Returns whether a winner appears in a column of tictactoe.
   *
   * @param board the board of tictactoe game
   * @return whether a winner appears in a row of tictactoe
   */
  private boolean isColWin(Player[][] board) {
    Player[][] transposedBoard = new Player[board[0].length][board.length];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        transposedBoard[j][i] = board[i][j];
      }
    }
    return this.isRowWin(transposedBoard);
  }

  /**
   * Returns whether a winner appears in a diagonal of tictactoe.
   *
   * @param board the board of tictactoe game
   * @return whether a winner appears in a diagonal of tictactoe
   */
  private boolean isDiagWin(Player[][] board) {
    Player[][] diagBoard = new Player[2][board.length];

    int j = 0;
    int q = board.length - 1;
    for (int i = 0; i < board.length; i++) {
      diagBoard[0][i] = board[i][j];
      diagBoard[1][i] = board[i][q];
      j++;
      q--;
    }

    return this.isRowWin(diagBoard);
  }

  /**
   * Returns whether the board of tictactoe is full.
   *
   * @param board the board of tictactoe game
   * @return whether the board of tictactoe is full
   */
  private boolean isBoardFull(Player[][] board) {
    return Arrays.stream(board)
        .allMatch(row -> Arrays.stream(row).allMatch(Objects::nonNull));
  }


  /**
   * Check illegal input of row and column.
   *
   * @param r row of a grid in the board
   * @param c column of a grid in the board
   * @throws IllegalArgumentException if row or column is out of boundary
   */
  private void checkIllegalIndex(int r, int c) throws IllegalArgumentException {
    if (r < LOWER_ROW || c < LOWER_COL ||
        r > UPPER_ROW || c > UPPER_COL) {
      throw new IllegalArgumentException("The movement is out of board's range "
          + "or is occupied.");
    }
  }
}
