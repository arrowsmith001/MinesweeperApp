package com.arrowsmith.minesweeperapp;

import com.arrowsmith.minesweeper.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MinesweeperController {

    final double d = 45.0;
    public GridPane gridPane;
    public Text infoText;
    public Button playButton;

    @FXML
    SquareViewModel[][] vms;

    final Minesweeper ms = new Minesweeper();


    public void start(){
        startNewGame();

    }

    private void startNewGame() {

        infoText.setText("");

        ms.generateGrid();
        final Grid grid = ms.grid;

        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        for (int i = 0; i < ms.grid.columns; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(d));
        }
        for (int j = 0; j < ms.grid.rows; j++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(d));
        }


        vms = new SquareViewModel[grid.rows][grid.columns];

        for (int i = 0; i < grid.rows; i++) {
            for (int j = 0; j < grid.columns; j++) {

                final Square square = grid.getSquareAtCoordinate(new Coordinate(i, j));
                final SquareViewModel vm = new SquareViewModel(square);

                final Node squareView = vm.getView(d);
                squareView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onTileClicked);

                vms[i][j] = vm;
                gridPane.add(squareView, i, j);
            }
        }


        gridIsClickable = true;
    }

    boolean gridIsClickable = true;

    private void onTileClicked(MouseEvent event) {

        if(!gridIsClickable) return;

        Node node = (Node) event.getSource();
        MouseButton mouseButton = event.getButton();

        // Swapped for some reason...
        int column = GridPane.getRowIndex(node);
        int row = GridPane.getColumnIndex(node);

        final Square square = ms.grid.getSquareAtCoordinate(new Coordinate(row, column));

        if(square != null)
        {
            if(mouseButton == MouseButton.SECONDARY)
            {
                square.toggleFlagged();
            }
            else
            {
                square.select();

                final WinState winState = ms.getWinState();

                if(winState == WinState.lost)
                {
                    onGameLost();
                }
                if(winState == WinState.won)
                {
                    onGameWon();
                }
            }
        }


        updateGridLabels();

    }

    private void onGameWon() {
        gridIsClickable = false;
        infoText.setText("You win!");
    }

    private void onGameLost() {
        gridIsClickable = false;
        ms.grid.revealAllMines();
        infoText.setText("You lose!");
    }

    private void updateGridLabels() {
        for (int i = 0; i < ms.grid.rows; i++) {
            for (int j = 0; j < ms.grid.columns; j++) {
                vms[i][j].updateLabel();
            }
        }
    }

    @FXML
    protected void onPlayButtonClicked(){
        startNewGame();
    }

}


class SquareViewModel
{
    private final Label label = new Label();

    private final Square square;

    public SquareViewModel(Square square) {
        this.square = square;

        label.textProperty().bind(textProperty);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(24));

        updateLabel();
    }

    StringProperty textProperty = new SimpleStringProperty();

    public final String getText() {
        return textProperty.get();
    }

    public final void updateLabel() {
        textProperty.set(generateLabelText());
        label.setTextFill(generateLabelColor());
    }


    private Paint generateLabelColor() {

        if(square.getState() != SquareState.numbered) return Color.BLACK;

        switch (square.getNeighborMineCount())
        {
            case 1: return Color.rgb(0, 0, 255);
            case 2: return Color.rgb(0, 122, 255);
            case 3: return Color.rgb(255, 200, 0);
            case 4: return Color.rgb(255, 150, 0);
            case 5: return Color.rgb(255, 100, 0);
            case 6: return Color.rgb(255, 66, 0);
            case 7: return Color.rgb(255, 33, 0);
            case 8: return Color.rgb(255, 0, 0);
        }

        return Color.BLACK;
    }


    private String generateLabelText()
    {
        switch (square.getState())
        {
            case hidden -> {
                return "?";
            }
            case flagged -> {
                return "#";
            }
            case empty -> {
                return "";
            }
            case numbered -> {
                return Integer.toString(square.getNeighborMineCount());
            }
            case mined -> {
                return "X";
            }
        }

        return null;
    }


    public Node getView(double size) {

        final BorderPane tile = new BorderPane(label);
        tile.setPrefSize(size, size);
        tile.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return tile;
    }
}
