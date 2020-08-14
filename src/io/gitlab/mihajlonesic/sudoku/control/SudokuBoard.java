package io.gitlab.mihajlonesic.sudoku.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SudokuBoard extends VBox {

    private SudokuCell[][] cellBoard;

    public SudokuBoard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sudoku_board.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initControls();
    }

    private void initControls() {
        this.cellBoard = new SudokuCell[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuCell cell = (SudokuCell) this.lookup("#sc" + i + j);
                this.cellBoard[i][j] = cell;
            }
        }
    }

    /**
     * @return 2D array of {@link SudokuCell} objects
     */
    public SudokuCell[][] getCells() {
        return this.cellBoard;
    }

    /**
     * Gets a cell in row and column
     *
     * @param row row number index (0-8)
     * @param col column number index (0-8)
     * @return Cell as {@link SudokuCell} object
     */
    public SudokuCell getCell(int row, int col) {
        return this.cellBoard[row][col];
    }

    /**
     * Sets text box values to board matrix values
     *
     * @param board 9x9 2D array
     */
    public void setValues(int[][] board) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                this.cellBoard[i][j].setValue(board[i][j]);
    }

    /**
     * Sets text box values to matrix values. no zeros
     *
     * @param board 9x9 2D array
     */
    public void setValuesNoZeros(int[][] board) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] != 0)
                    this.cellBoard[i][j].setValue(board[i][j]);
    }

    /**
     * Disables all cells for input
     */
    public void disableAll() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                this.cellBoard[i][j].disable();
    }

    /**
     * Clears all text boxes.
     * Clears the color and sets to editable.
     */
    public void clear() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                this.cellBoard[i][j].clear();
    }
}
