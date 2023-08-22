package com.arrowsmith.minesweeperapp;

import com.arrowsmith.minesweeper.model.Coordinate;
import com.arrowsmith.minesweeper.model.Grid;
import com.arrowsmith.minesweeper.model.Minesweeper;
import com.arrowsmith.minesweeper.model.Square;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class MinesweeperController {
    public GridPane gridPane;


    @FXML
    SquareViewModel[][] vms;

    final Minesweeper ms = new Minesweeper();

    public void start(){

        final double d = 30.0;

        ms.generateGrid();
        final Grid grid = ms.grid;

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


                final Label label = (Label) vm.getLabel();
                label.setPrefSize(d, d);
                label.setAlignment(Pos.CENTER);

                label.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onTileClicked);

                vms[i][j] = vm;
                gridPane.add(label, i, j);
            }
        }

    }

    private void onTileClicked(MouseEvent event) {

        System.out.println(event);

        Node node = (Node) event.getSource();

        int column = GridPane.getRowIndex(node);
        int row = GridPane.getColumnIndex(node);

        System.out.println("addEventHandler: " + row + " " + column);

        Square square = ms.grid.getSquareAtCoordinate(new Coordinate(row, column));

        if(square != null)
        {
            square.select();
        }

        for (int i = 0; i < ms.grid.rows; i++) {
            for (int j = 0; j < ms.grid.columns; j++) {
                vms[i][j].setText();
            }
        }

    }

}


class SquareViewModel
{
    private final Label label = new Label();

    private final Square square;

    public SquareViewModel(Square square) {
        label.textProperty().bind(textProperty);
        this.square = square;
        setText();
    }

    StringProperty textProperty = new SimpleStringProperty();

    public final String getText() {
        return textProperty.get();
    }
    public final void setText() {
        final String text = stateToLabelText(square.getState());
        textProperty.set(text);
    }



    public String stateToLabelText(SquareState state)
    {
        switch (state)
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


    public Node getLabel() {
        return label;
    }
}