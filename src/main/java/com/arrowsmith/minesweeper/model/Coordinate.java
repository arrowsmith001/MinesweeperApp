package com.arrowsmith.minesweeper.model;

public class Coordinate {
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    private int row;
    private int column;

    public Coordinate(int row, int col) {
        this.row = row;
        this.column = col;
    }

    @Override
    public String toString() {
        return "row: " + row + ", column: " + column;
    }
}