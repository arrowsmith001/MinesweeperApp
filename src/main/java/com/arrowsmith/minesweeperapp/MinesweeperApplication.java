package com.arrowsmith.minesweeperapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MinesweeperApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 750);

        stage.setTitle("Minesweeper");
        stage.setScene(scene);

        MinesweeperController c = fxmlLoader.getController();
        c.start();

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}

