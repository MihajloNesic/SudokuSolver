package io.gitlab.mihajlonesic.sudoku;

import io.gitlab.mihajlonesic.sudoku.control.SudokuBoard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Backtracking {

    /**
     * Check if the number is at valid position in sudoku board.
     *
     * @param board Sudoku board; 2D array of integers
     * @param row Row of the number
     * @param col Column of the number
     * @param num Number to check
     * @return true - number is at valid position;
     *         false - number is not at valid position
     */
    public static boolean valid(int[][] board, int row, int col, int num) {

        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num)
                return false;

            if (board[i][col] == num)
                return false;
        }

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = boxRow; i < boxRow + 3; i++)
            for (int j = boxCol; j < boxCol + 3; j++)
                if (board[i][j] == num)
                    return false;

        return true;
    }

    /**
     * Recursive backtracking<br>
     * Tests each empty cell with a number.
     *
     * @param board Sudoku board; 9x9 2D array of integers
     * @return true - sudoku solved<br>false - sudoku cannot be solved
     */
    public static boolean solve(int[][] board) {
        int row = -1, col = -1;
        boolean isFull = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isFull = false;
                    break;
                }
            }

            if (!isFull) break;
        }

        if (isFull) return true;

        for (int num = 1; num <= 9; num++) {
            if (valid(board, row, col, num)) {
                board[row][col] = num;

                if (solve(board))
                    return true;
                else board[row][col] = 0;
            }
        }
        return false;
    }

    // visualizing

    private static ScheduledExecutorService executorService;
    private static AtomicBoolean isVisualizationRunning = new AtomicBoolean(false);

    public static void steps(SudokuBoard sudokuBoard, int[][] sudoku) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        isVisualizationRunning.set(true);
        executorService.scheduleAtFixedRate(() -> solveStep(sudokuBoard, sudoku), 500, 3000, TimeUnit.MILLISECONDS);
        Controller.getInstance().disableControlButtons(true);
    }

    /**
     * Recursive backtracking<br>
     * Tests each empty cell with a number.
     * @param sudoku Sudoku board; 9x9 2D array of integers
     * @return true - sudoku solved<br>false - sudoku cannot be solved
     */
    public static boolean solveStep(SudokuBoard sudokuBoard, int[][] sudoku) {
        int row = -1, col = -1;
        boolean isFull = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j] == 0) {
                    row = i;
                    col = j;
                    isFull = false;
                    break;
                }
            }

            if (!isFull) break;
        }

        if (isVisualizationRunning.compareAndSet(isFull, false)) {
            executorService.shutdown();
            Controller.getInstance().disableControlButtons(false);
        }

        if (isFull) return true;

        for (int num = 1; num <= 9; num++) {
            if (valid(sudoku, row, col, num)) {
                sudoku[row][col] = num;
                sudokuBoard.getCell(row, col).setValue(num);

                try {
                    sudokuBoard.getCell(row, col).changeColor();
                    executorService.awaitTermination(120, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    System.err.println("Error interrupted");
                }

                if (solveStep(sudokuBoard, sudoku)) {
                    return true;
                }
                else {
                    try {
                        sudoku[row][col] = 0;
                        //sudokuBoard.getCell(row, col).setValue(0);
                        sudokuBoard.getCell(row, col).changeColorRed();
                        executorService.awaitTermination(120, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException ex) {
                        System.err.println("Error, interrupted");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}