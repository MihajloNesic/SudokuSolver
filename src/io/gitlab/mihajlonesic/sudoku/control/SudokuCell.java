package io.gitlab.mihajlonesic.sudoku.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SudokuCell extends TextField {

    private final int limit = 1;

    public SudokuCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sudoku_cell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
            verify();
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
            verify();
        }
    }

    /**
     * Replace text to fit <i>limit</i>
     */
    private void verify() {
        if (getText().length() == limit) return;

        if (getText().length() > limit) {
            setText(getText().substring(0, limit));
        }
    }

    /**
     * Does String contain only numbers
     *
     * @param text String to test
     * @return true - only numbers
     */
    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }

    /**
     * Set number as textbox text
     *
     * @param n Number to set
     */
    public void setValue(int n) {
        this.setText(n + "");
    }

    /**
     * Return a number from text
     *
     * @return Number in textbox
     */
    public int getValue() {
        if (this.isEmpty()) return 0;
        return Integer.parseInt(this.getText());
    }

    /**
     * Checks if the textbox is empty
     *
     * @return true - empty<br>false - not empty
     */
    public boolean isEmpty() {
        return this.getText().length() == 0;
    }

    /**
     * Checks if textbox value is 0
     *
     * @return true - 0<br>false - not 0
     */
    public boolean isZero() {
        return this.getValue() == 0;
    }

    /**
     * Changes textbox background to gray
     */
    public void changeColor() {
        this.setStyle("-fx-control-inner-background: #E2E2E2; -fx-text-inner-color: black;");
    }

    /*
     * Sets textbox background to default white
     */
    public void resetColor() {
        this.setStyle("-fx-control-inner-background: white; -fx-text-inner-color: black;");
    }

    /**
     * Changes textbox background to gray
     */
    public void changeColorRed() {
        this.setStyle("-fx-control-inner-background: #ff8080; -fx-text-inner-color: #ff8080;");
    }

    /**
     * Disables input
     */
    public void disable() {
        this.setEditable(false);
    }

    /**
     * Clears the text, resets the color and enables input
     */
    @Override
    public void clear() {
        super.clear();
        this.resetColor();
        this.setEditable(true);
    }
}