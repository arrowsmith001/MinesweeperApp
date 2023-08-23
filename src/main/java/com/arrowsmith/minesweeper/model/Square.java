package com.arrowsmith.minesweeper.model;

import com.arrowsmith.minesweeperapp.SquareState;

import java.util.LinkedList;
import java.util.List;

public class Square
{

    public SquareState getState() {
        if(!isRevealed)
        {
            if(isFlagged)
            {
                return SquareState.FLAGGED;
            }
            else
            {
                return SquareState.HIDDEN;
            }
        }
        else if(isMined)
        {
            return SquareState.MINED;
        }
        else
        {
            final int neighborCount = getNeighborMineCount();
            if(neighborCount > 0)
            {
                return SquareState.NUMBERED;
            }
            else return SquareState.EMPTY;
        }
    }

    public boolean setIsFlagged;

    public Square(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    final Coordinate coordinate;

    final List<Square> neighbors = new LinkedList<>();

    public void addNeighbor(Square squareToAdd) {
        neighbors.add(squareToAdd);
        if(squareToAdd.isMined) neighborMineCount++;
    }



    public boolean isFlagged() {
        return isFlagged;
    }

    private boolean isMined;

    private boolean isFlagged;
    private boolean isRevealed;


    public void setMined(boolean mined) {
        this.isMined = mined;
    }

    public boolean isMined() {
        return isMined;
    }
    private int neighborMineCount = 0;

    public int getNeighborMineCount() {
        return neighborMineCount;
    }

    public void select()  {

        // If this square is flagged, ignore
        if(isFlagged)
        {
            return;
        }

        // Any selected square will reveal itself
        isRevealed = true;

        // If this square has a mine, the game ends
        if(isMined)
        {
            return;
        }

        // If this square has any neighboring mines, selection process stops
        if(hasNeighboringMines()) return;

        // For all neighbors, select them if:
        //      - They have no mine
        //      - They aren't already revealed - this prevents infinite loops
        for (Square neighbor : neighbors)
        {
            if(!neighbor.isMined && !neighbor.isRevealed)
            {
                neighbor.select();
            }
        }
    }



    protected boolean hasNeighboringMines() {
        return getNeighborMineCount() > 0;
    }

    public void toggleFlagged() {
        isFlagged = !isFlagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
}

