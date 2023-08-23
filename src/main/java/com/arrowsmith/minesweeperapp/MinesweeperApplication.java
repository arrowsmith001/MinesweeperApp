package com.arrowsmith.minesweeperapp;

import com.arrowsmith.minesweeper.exceptions.MineUncoveredException;
import com.arrowsmith.minesweeper.model.*;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

// TODO: Databind

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

