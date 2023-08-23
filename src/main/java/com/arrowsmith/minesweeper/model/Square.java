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
        else if(hasMine)
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

    final List<Square> neighbors = new LinkedList<Square>();

    public void addNeighbor(Square squareToAdd) {
        neighbors.add(squareToAdd);
        if(squareToAdd.hasMine) neighborMineCount++;
    }


    public boolean hasMine;
    public boolean isFlagged;
    public boolean isRevealed;

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean getHasMine() {
        return hasMine;
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
        if(hasMine)
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
            if(!neighbor.hasMine && !neighbor.isRevealed)
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

    public boolean getIsRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
}

