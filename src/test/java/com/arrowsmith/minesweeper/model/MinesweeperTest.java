package com.arrowsmith.minesweeper.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MinesweeperTest {



    @Test
    @DisplayName("Generate grid makes grid of correct size")
    void generateGridOfCertainSize() {

        Minesweeper ms = new Minesweeper();

        MinesweeperOptions options = new MinesweeperOptions();

        options.setNumberOfRows(5);
        options.setNumberOfColumns(11);
        options.setNumberOfMines(2);

        ms.setOptions(options);

        ms.generateGrid();

        assertEquals(5, ms.getGridRowCount());
        assertEquals(11, ms.getGridColumnCount());
    }

    @Test
    @DisplayName("Flag Square")
    void flagSquare() {

        final Minesweeper ms = new Minesweeper();
        ms.generateGrid();

        final Coordinate coordinate = new Coordinate(0, 0);
        final Square square = ms.getSquareAtCoordinate(coordinate);

        final boolean flaggedBefore = square.isFlagged();

        ms.flagSquare(coordinate);

        final boolean flaggedAfter = square.isFlagged();

        assertFalse(flaggedBefore);
        assertTrue(flaggedAfter);
    }

    @Test
    @DisplayName("Select Square reveals square")
    void selectSquareRevealsSquare() {
        final Minesweeper ms = new Minesweeper();
        ms.generateGrid();

        final Square square = ms.getSquareAtCoordinate(new Coordinate(0, 0));

        final boolean isRevealedBefore = square.isRevealed();

        square.select();

        final boolean isRevealedAfter = square.isRevealed();

        assertFalse(isRevealedBefore);
        assertTrue(isRevealedAfter);

    }
}