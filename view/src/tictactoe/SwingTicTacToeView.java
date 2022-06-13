package tictactoe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingTicTacToeView extends JFrame implements TicTacToeView {
  JButton[][] cells;
  JLabel status;

  public SwingTicTacToeView(String caption) {
    super(caption);
    cells = new JButton[3][3];


    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(800, 800);
    this.setLocation(400, 400);
    this.setMinimumSize(new Dimension(600, 600));
    this.setLayout(new BorderLayout());

    // Set top part
    JPanel topPanel = new JPanel();
    status = new JLabel();
    status.setText("Turn: X");
    status.setFont(new Font(status.getName(), Font.BOLD, 40));
    topPanel.add(status, BorderLayout.CENTER);
    this.add(topPanel, BorderLayout.NORTH);

    // Set center part
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridLayout(3, 3));
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        cells[row][col] = new JButton();
        cells[row][col].setFont(new Font(cells[row][col].getName(), Font.PLAIN, 50));
        cells[row][col].setActionCommand(row + " " + col);
        centerPanel.add(cells[row][col]);
      }
    }
    this.add(centerPanel, BorderLayout.CENTER);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeature(Features features) {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        cells[row][col].addActionListener(evt -> features.placePiece(evt.getActionCommand()));
      }
    }

  }

  @Override
  public void displayChess(int row, int col, String turn) {
    cells[row][col].setText(turn);
  }

  @Override
  public void updateStatus(String text) {
    status.setText(text);
  }
}
