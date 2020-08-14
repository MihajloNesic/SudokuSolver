package io.gitlab.mihajlonesic.sudoku;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Mihajlo Nesic (https://mihajlonesic.gitlab.io/)
 * @since Nov 24, 2018
 */
public class SudokuSolver extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller(primaryStage);
        startApp(primaryStage);
    }

    private void startApp(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sudoku_solver.fxml"));
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();

        controller.init();

        stage.setTitle("Sudoku Solver");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        root.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
