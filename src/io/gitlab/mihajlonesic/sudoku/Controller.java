package io.gitlab.mihajlonesic.sudoku;

import io.gitlab.mihajlonesic.sudoku.control.SudokuBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    public static Controller instance;

    @FXML
    private SudokuBoard sudokuBoard;
    @FXML
    private Button btnSolve;
    @FXML
    private Button btnVisualize;
    @FXML
    private Button btnClear;

    private final int[][] presetOne = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    private final int[][] presetTwo = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 8, 5},
            {0, 0, 1, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 5, 0, 7, 0, 0, 0},
            {0, 0, 4, 0, 0, 0, 1, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 7, 3},
            {0, 0, 2, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 4, 0, 0, 0, 9}
    };

    private final int[][] presetThree = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {0, 0, 0, 7, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 8, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 9, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Controller(Stage stage) {
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }

    public void init() {
        btnSolve.setOnAction(this::handleBtnSolveClick);
        btnVisualize.setOnAction(this::handleBtnVisualizeClick);
        btnClear.setOnAction(this::handleBtnClearClick);
    }

    private void handleBtnSolveClick(ActionEvent actionEvent) {
        int[][] sudoku = SudokuHelper.to2DArray(this.sudokuBoard.getCells());

        if (Backtracking.solve(sudoku)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    this.sudokuBoard.getCells()[i][j].disable();

                    if (this.sudokuBoard.getCells()[i][j].isZero()) {
                        this.sudokuBoard.getCells()[i][j].changeColor();
                        this.sudokuBoard.getCells()[i][j].setValue(sudoku[i][j]);
                    }
                }
            }
        }
    }

    private void handleBtnVisualizeClick(ActionEvent actionEvent) {
        this.sudokuBoard.disableAll();
        Backtracking.steps(this.sudokuBoard, SudokuHelper.to2DArray(sudokuBoard.getCells()));
        btnVisualize.requestFocus();
    }

    private void handleBtnClearClick(ActionEvent actionEvent) {
        this.sudokuBoard.clear();
        // this.sudokuBoard.setValuesNoZeros(presetThree);
    }

    public void disableControlButtons(boolean disabled) {
        this.btnSolve.setDisable(disabled);
        this.btnVisualize.setDisable(disabled);
        this.btnClear.setDisable(disabled);
    }
}
