package com.arrowsmith.minesweeper.model;


// TODO: Generate board after first input
public class Minesweeper {

    private Grid grid;

    private MinesweeperOptions options = new MinesweeperOptions();
    public MinesweeperOptions getOptions() {
        return options;
    }
    public void setOptions(MinesweeperOptions options){
        this.options = options;
    }

    public int getGridRowCount(){
        return grid.rowCount;
    }
    public int getGridColumnCount(){
        return grid.columnCount;
    }

    public void generateGrid() {

        grid = new Grid(options);
    }

    public void flagSquare(Coordinate coordinate)
    {
        grid.flagSquare(coordinate);
    }
    public void selectSquare(Coordinate coordinate)
    {
            grid.selectSquare(coordinate);
    }

    public WinState getWinState() {

        boolean thereExistUnrevealedSafeSquares = false;

        for (int i = 0; i < grid.rowCount; i++) {
            for (int j = 0; j < grid.columnCount; j++) {

                final Square square = grid.getSquareAtCoordinate(new Coordinate(i, j));

                if(square.isMined() && square.isRevealed())
                {
                    return WinState.LOST;
                }

                if(!square.isMined() && !square.isRevealed())
                {
                    thereExistUnrevealedSafeSquares = true;
                }
            }
        }
        return thereExistUnrevealedSafeSquares ? WinState.PLAYING : WinState.WON;
    }

    public Square getSquareAtCoordinate(Coordinate coordinate) {
        return grid.getSquareAtCoordinate(coordinate);
    }

    public void revealAllMines() {
        grid.revealAllMines();
    }
}

enum Difficulty {
    INFANTILE, EASY, MEDIUM, HARD, IMPOSSIBLE, CUSTOM
}
enum BoardSize {
    SMALL, MEDIUM, LARGE, CUSTOM
}

