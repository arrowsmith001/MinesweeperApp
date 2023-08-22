package com.arrowsmith.minesweeper.model;

public class Coordinate {
    public int row;
    public int column;

    public Coordinate(int row, int col) {
        this.row = row;
        this.column = col;
    }

    @Override
    public String toString() {
        return "row: " + row + ", column: " + column;
    }
}