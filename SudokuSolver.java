import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver extends JFrame {
    private JTextField[][] cells;
    private int[][] grid;
    private JButton solveButton, clearButton;

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 9)); // 9 rows for grid, 2 for buttons

        cells = new JTextField[9][9];
        grid = new int[9][9];

        // Create the grid of text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("Arial", Font.PLAIN, 18));
                add(cells[i][j]);
            }
        }

        // Solve button
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solvePuzzle();
            }
        });
        add(solveButton);

        // Clear button
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGrid();
            }
        });
        add(clearButton);

        // Add empty panels to fill the grid layout
        for (int i = 0; i < 7; i++) {
            add(new JPanel());
        }

        pack();
        setVisible(true);
    }

    private void getGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText();
                if (text.matches("\\d") && Integer.parseInt(text) >= 1 && Integer.parseInt(text) <= 9) {
                    grid[i][j] = Integer.parseInt(text);
                } else {
                    grid[i][j] = 0;
                }
            }
        }
    }

    private void setGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(grid[i][j]));
                }
            }
        }
    }

    private boolean isValid(int row, int col, int num) {
        // Check row
        for (int j = 0; j < 9; j++) {
            if (grid[row][j] == num) {
                return false;
            }
        }
        // Check column
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }
        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[boxRow + i][boxCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(row, col, num)) {
                            grid[row][col] = num;
                            if (solve()) {
                                return true;
                            }
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void solvePuzzle() {
        getGrid();
        if (solve()) {
            setGrid();
            JOptionPane.showMessageDialog(this, "Sudoku solved!");
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists for this puzzle.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
                grid[i][j] = 0;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuSolver());
    }
}
