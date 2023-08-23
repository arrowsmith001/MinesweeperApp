package com.arrowsmith.minesweeper.model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Grid
{
    static Logger logger = Logger.getLogger(Grid.class.getName());

    public Grid(MinesweeperOptions options)
    {

        rowCount = options.getNumberOfRows();
        columnCount = options.getNumberOfColumns();
        mines = options.getNumberOfMines();

        generateBoard();
    }


    public final int rowCount;
    public final int columnCount;
    final int mines;

    private Square[][] squaresArray;

    public Square getSquareAtCoordinate(Coordinate coordinate)
    {
        try
        {
            return squaresArray[coordinate.getRow()][coordinate.getColumn()];
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
        squaresArray = new Square[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {

            final List<Square> rowList = new LinkedList<>();

            for (int j = 0; j < columnCount; j++)
            {
                final Coordinate coordinate = new Coordinate(i, j);
                final Square square = new Square(coordinate);

                squaresArray[i][j] = square;
                rowList.add(square);
            }
        }


    }

    private void addNeighborsToSquares() {

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {

                final Square square = getSquareAtCoordinate(new Coordinate(i, j));
                addNeighbors(square);
            }
        }

    }

    private void addNeighbors(Square square)
    {
        final Coordinate coordinate = square.coordinate;
        final List<Coordinate> neighborCoordinates = new ArrayList<>();

        neighborCoordinates.add(new Coordinate(coordinate.getRow() - 1, coordinate.getColumn() + 1));
        neighborCoordinates.add(new Coordinate(coordinate.getRow() - 1, coordinate.getColumn()));
        neighborCoordinates.add(new Coordinate(coordinate.getRow() - 1, coordinate.getColumn() - 1));
        neighborCoordinates.add(new Coordinate(coordinate.getRow(), coordinate.getColumn() + 1));
        neighborCoordinates.add(new Coordinate(coordinate.getRow(), coordinate.getColumn() - 1));
        neighborCoordinates.add(new Coordinate(coordinate.getRow() + 1, coordinate.getColumn() + 1));
        neighborCoordinates.add(new Coordinate(coordinate.getRow() + 1, coordinate.getColumn()));
        neighborCoordinates.add(new Coordinate(coordinate.getRow() + 1, coordinate.getColumn() - 1));

        neighborCoordinates.removeIf(c -> !isValidCoordinate(c));

        neighborCoordinates.forEach(c ->
        {
            final Square neighbor = getSquareAtCoordinate(c);
            if(neighbor != null) square.addNeighbor(neighbor);
        });
    }

    private boolean isValidCoordinate(Coordinate c) {
        final boolean isNonNegative = c.getColumn() >= 0 && c.getRow() >=0;
        final boolean isInBounds = c.getColumn() < columnCount && c.getRow() < rowCount;
        return isNonNegative && isInBounds;
    }

    private Coordinate indexToCoordinate(int i) {
        return new Coordinate(i / columnCount, i % columnCount);
    }

    private void addMines()
    {
        // New array from 0 to end of grid
        final List<Integer> indexes = new ArrayList<>(IntStream.range(0, rowCount * columnCount).boxed().toList());

        // Get a random subset of these indices
        Collections.shuffle(indexes);
        final Set<Integer> randomIndices = new HashSet<>(indexes.subList(0, mines - 1));


        for(int index : randomIndices)
        {
            final Coordinate coordinate = indexToCoordinate(index);
            final Square square = getSquareAtCoordinate(coordinate);

            square.setMined(true);

        }
    }



    public void selectSquare(Coordinate coordinate) {

        final Square square = getSquareAtCoordinate(coordinate);

        square.select();
    }

    public void reset() {
        generateBoard();
    }

    public void flagSquare(Coordinate coordinate) {

        logger.log(Level.INFO,"Flagging " + coordinate);
        final Square square = getSquareAtCoordinate(coordinate);
        if(square.isRevealed())
        {
            logger.log(Level.INFO,"Cannot flag a revealed square");
        }
        else square.toggleFlagged();
    }

    public void revealAllMines() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++)
            {
                final Coordinate coordinate = new Coordinate(i, j);
                final Square square = getSquareAtCoordinate(coordinate);

                if(square.isMined())
                {
                    square.setRevealed(true);
                }
            }
        }
    }
}
