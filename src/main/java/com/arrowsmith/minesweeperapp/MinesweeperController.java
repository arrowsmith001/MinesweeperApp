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

    final static double d = 45.0;

    @FXML
    protected GridPane gridPane;
    @FXML
    protected Text infoText;
    @FXML
    protected Button playButton;

    @FXML
    SquareViewModel[][] vms;

    final Minesweeper ms = new Minesweeper();


    public void start(){
        startNewGame();

    }

    private void startNewGame() {

        infoText.setText("");

        ms.generateGrid();

        setRowConstraints();

        final int rowCount = ms.getGridRowCount();
        final int columnCount = ms.getGridColumnCount();

        vms = new SquareViewModel[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {

                final Square square = ms.getSquareAtCoordinate(new Coordinate(i, j));
                final SquareViewModel vm = new SquareViewModel(square);

                final Node squareView = vm.getView(d);
                squareView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onTileClicked);

                vms[i][j] = vm;
                gridPane.add(squareView, i, j);
            }
        }

        gridPane.setCenterShape(true);
        gridIsClickable = true;
    }

    private void setRowConstraints() {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        for (int i = 0; i < ms.getGridRowCount(); i++) {
            gridPane.getRowConstraints().add(new RowConstraints(d));
        }
        for (int j = 0; j < ms.getGridColumnCount(); j++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(d));
        }
    }

    boolean gridIsClickable = true;

    private void onTileClicked(MouseEvent event) {

        if(!gridIsClickable) return;

        Node node = (Node) event.getSource();
        MouseButton mouseButton = event.getButton();

        // Swapped for some reason...
        int column = GridPane.getRowIndex(node);
        int row = GridPane.getColumnIndex(node);

        final Square square = ms.getSquareAtCoordinate(new Coordinate(row, column));

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

                if(winState == WinState.LOST)
                {
                    onGameLost();
                }
                if(winState == WinState.WON)
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
        ms.revealAllMines();
        infoText.setText("You lose!");
    }

    private void updateGridLabels() {
        for (int i = 0; i < ms.getGridRowCount(); i++) {
            for (int j = 0; j < ms.getGridColumnCount(); j++) {
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

        if(square.getState() != SquareState.NUMBERED) return Color.BLACK;

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
            default: return Color.BLACK;
        }


    }


    private String generateLabelText()
    {
        switch (square.getState())
        {
            case HIDDEN -> {
                return "?";
            }
            case FLAGGED -> {
                return "#";
            }
            case EMPTY -> {
                return "";
            }
            case NUMBERED -> {
                return Integer.toString(square.getNeighborMineCount());
            }
            case MINED -> {
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
