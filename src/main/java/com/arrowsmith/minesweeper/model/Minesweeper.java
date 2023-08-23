package com.arrowsmith.minesweeper.model;


import com.arrowsmith.minesweeper.exceptions.MineUncoveredException;

import java.util.Scanner;

// TODO: Generate board after first input
public class Minesweeper {

    public Minesweeper() {}

    public Grid grid;

    private MinesweeperOptions options = new MinesweeperOptions();
    public MinesweeperOptions getOptions() {
        return options;
    }
    public void setOptions(MinesweeperOptions options){
        this.options = options;
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

        for (int i = 0; i < grid.rows; i++) {
            for (int j = 0; j < grid.columns; j++) {

                final Square square = grid.getSquareAtCoordinate(new Coordinate(i, j));

                if(square.hasMine && square.isRevealed)
                {
                    return WinState.lost;
                }

                if(!square.hasMine && !square.isRevealed)
                {
                    thereExistUnrevealedSafeSquares = true;
                }
            }
        }
        return thereExistUnrevealedSafeSquares ? WinState.playing : WinState.won;
    }

}

enum Difficulty {
    Infantile, Easy, Medium, Hard, Impossible, Custom
}
enum BoardSize {
    Small, Medium, Large, Custom
}

