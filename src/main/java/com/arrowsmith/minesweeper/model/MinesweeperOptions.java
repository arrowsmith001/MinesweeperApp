package com.arrowsmith.minesweeper.model;

// TODO: Figure out fully flexible difficulty settings
public class MinesweeperOptions {
    static int DEFAULT_ROW_COUNT = 5;
    static int DEFAULT_COLUMN_COUNT = 10;
    static int DEFAULT_MINE_COUNT = 12;
    public Difficulty difficulty;
    public BoardSize boardSize;

    public MinesweeperOptions(){
        setOptions(Difficulty.Medium, BoardSize.Medium);
    }

    public MinesweeperOptions(Difficulty difficulty, BoardSize boardSize) {
        setOptions(difficulty, boardSize);
    }

    private void setOptions(Difficulty difficulty, BoardSize boardSize) {
        this.difficulty = difficulty;
        this.boardSize = boardSize;

        setNumberOfRowsAndColumns(boardSize);

        int numberOfSquares = numberOfColumns * numberOfRows;

        setNumberOfMines(numberOfSquares, difficulty);
    }

    private void setNumberOfMines(int numberOfSquares, Difficulty difficulty) {
        switch (difficulty)
        {
            case Infantile -> {
                numberOfMines = (int) (numberOfSquares * 0.05);
            }
            case Easy -> {
                numberOfMines = (int) (numberOfSquares * 0.1);
            }
            case Medium -> {
                numberOfMines = (int) (numberOfSquares * 0.2);
            }
            case Hard -> {
                numberOfMines = (int) (numberOfSquares * 0.3);
            }
            case Impossible -> {
                numberOfMines = (int) (numberOfSquares * 0.4);
            }
        }

        numberOfMines = Math.max(numberOfMines, 2);
    }

    private void setNumberOfRowsAndColumns(BoardSize boardSize) {
        switch (boardSize)
        {
            case Small -> {
                this.numberOfRows = 5;
                this.numberOfColumns = 10;
            }
            case Medium -> {
                this.numberOfRows = 8;
                this.numberOfColumns = 12;
            }
            case Large -> {
                this.numberOfRows = 20;
                this.numberOfColumns = 48;
            }
        }

    }

    public MinesweeperOptions(int numberOfRows, int numberOfColumns, int numberOfMines) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.numberOfMines = numberOfMines;
    }

    private int numberOfRows;
    private int numberOfColumns;
    private int numberOfMines;


    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }


    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }
    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(BoardSize boardSize) {
        this.boardSize = boardSize;
    }
}
