package io.gitlab.mihajlonesic.sudoku;

import io.gitlab.mihajlonesic.sudoku.control.SudokuBoard;
import io.gitlab.mihajlonesic.sudoku.control.SudokuCell;

public class SudokuHelper {

    /**
     * Converts 2D array of {@link SudokuCell} objects to 2D array of integers
     *
     * @param cells 2D array of SudokuCell
     * @return 2D array of integers
     */
    public static int[][] to2DArray(SudokuCell[][] cells) {
        int[][] sudoku = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].isEmpty())
                    sudoku[i][j] = 0;
                else sudoku[i][j] = cells[i][j].getValue();
            }
        }

        return sudoku;
    }

    /**
     * Prints 2D array. New line at the end.
     *
     * @param matrix 2D array to print
     */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Prints 2D array in Sudoku style. New line at the end.
     *
     * @param matrix 2D array to print
     */
    public static void printMatrixStyle(int[][] matrix) {
        for (int i = 0; i < 9; i++) {
            System.out.println();
            if (i % 3 == 0) System.out.println();

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) System.out.print(" ");
                if (matrix[i][j] == 0) System.out.print(". ");
                else System.out.print(matrix[i][j] + " ");
            }
        }
        System.out.println();
    }

    /**
     * Prints sudoku board.
     *
     * @param cells 2D array of {@link SudokuCell} objects
     */
    public static void printSudoku(SudokuCell[][] cells) {
        for (int i = 0; i < 9; i++) {
            System.out.println();
            if (i % 3 == 0) System.out.println();

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) System.out.print(" ");
                if (cells[i][j].isZero()) System.out.print(". ");
                else System.out.print(cells[i][j].getValue() + " ");
            }
        }
        System.out.println();
    }
}