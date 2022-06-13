package tictactoe;

/**
 * The enum represents the type of Player. It can be either X or O.
 */
public enum Player {
  X {
    @Override
    public String toString() {
      return "X";
    }
  },
  O {
    @Override
    public String toString() {
      return "O";
    }
  }
}



