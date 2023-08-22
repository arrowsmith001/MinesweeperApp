package com.arrowsmith.minesweeper.model;


import com.arrowsmith.minesweeper.exceptions.MineUncoveredException;

import java.util.*;
import java.util.stream.IntStream;

public class Grid
{
    public Grid(MinesweeperOptions options)
    {

        rows = options.getNumberOfRows();
        columns = options.getNumberOfColumns();
        mines = options.getNumberOfMines();

        generateBoard();
    }


    public final int rows;
    public final int columns;
    final int mines;

    private Square[][] squaresArray;

    public Square getSquareAtCoordinate(Coordinate coordinate)
    {
        try
        {
            return squaresArray[coordinate.row][coordinate.column];
        }catch(ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }


    private void generateBoard() {

        addSquares();

        addMines();

        addNeighborsToSquares();

    }


    private void addSquares() {

        // 2D Array - for instant querying
        squaresArray = new Square[rows][columns];

        for (int i = 0; i < rows; i++) {

            final List<Square> rowList = new LinkedList<>();

            for (int j = 0; j < columns; j++)
            {
                final Coordinate coordinate = new Coordinate(i, j);
                final Square square = new Square(coordinate);

                squaresArray[i][j] = square;
                rowList.add(square);
            }
        }


    }

    private void addNeighborsToSquares() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                final Square square = getSquareAtCoordinate(new Coordinate(i, j));
                addNeighbors(square);
            }
        }

    }

    private void addNeighbors(Square square)
    {
        final Coordinate coordinate = square.coordinate;
        final List<Coordinate> neighborCoordinates = new ArrayList<>()
        {
            {
                add(new Coordinate(coordinate.row - 1, coordinate.column + 1));
                add(new Coordinate(coordinate.row - 1, coordinate.column));
                add(new Coordinate(coordinate.row - 1, coordinate.column - 1));
                add(new Coordinate(coordinate.row, coordinate.column + 1));
                add(new Coordinate(coordinate.row, coordinate.column - 1));
                add(new Coordinate(coordinate.row + 1, coordinate.column + 1));
                add(new Coordinate(coordinate.row + 1, coordinate.column));
                add(new Coordinate(coordinate.row + 1, coordinate.column - 1));
            }
        };

        neighborCoordinates.removeIf(c -> !isValidCoordinate(c));

        neighborCoordinates.forEach(c ->
        {
            final Square neighbor = getSquareAtCoordinate(c);
            if(neighbor != null) square.addNeighbor(neighbor);
        });
    }

    private boolean isValidCoordinate(Coordinate c) {
        final boolean isNonNegative = c.column >= 0 && c.row >=0;
        final boolean isInBounds = c.column < columns && c.row < rows;
        return isNonNegative && isInBounds;
    }

    private Coordinate indexToCoordinate(int i) {
        return new Coordinate(i / columns, i % columns);
    }

    private void addMines()
    {
        // New array from 0 to end of grid
        final List<Integer> indexes = new ArrayList<>(IntStream.range(0, rows * columns).boxed().toList());

        // Get a random subset of these indices
        Collections.shuffle(indexes);
        final Set<Integer> randomIndices = new HashSet<Integer>(indexes.subList(0, mines - 1));

        // Add mines at those indices
        for(int index : randomIndices)
        {
            final Coordinate coordinate = indexToCoordinate(index);
            final Square square = getSquareAtCoordinate(coordinate);

            square.setHasMine(true);

        }
    }


    public void selectSquare(Coordinate coordinate) {
        System.out.println("Selecting " + coordinate);
        final Square square = getSquareAtCoordinate(coordinate);
        square.select();
    }

    public void reset() {
        generateBoard();
    }

    public void flagSquare(Coordinate coordinate) {

        System.out.println("Flagging " + coordinate);
        final Square square = getSquareAtCoordinate(coordinate);
        if(square.getIsRevealed())
        {
            System.out.println("Cannot flag a revealed square");
        }
        else square.toggleFlagged();
    }
}
