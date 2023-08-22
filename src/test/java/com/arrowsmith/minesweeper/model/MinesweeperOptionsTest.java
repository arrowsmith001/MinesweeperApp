package com.arrowsmith.minesweeper.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperOptionsTest {

    @Test
    @DisplayName("Default Options")
    void defaultOptions() {
        final MinesweeperOptions options = new MinesweeperOptions();

        final boolean isMediumDifficulty = options.getDifficulty() == Difficulty.Medium;
        final boolean isMediumSize = options.getBoardSize() == BoardSize.Medium;

        assertAll(
                () -> assertTrue(isMediumDifficulty),
                () -> assertTrue(isMediumSize));
    }

//    @Test
//    @DisplayName("Higher Difficulty on Same Board Size has More Mines")
//    void moreMinesOnHigherDifficulty() {
//
//        final MinesweeperOptions options = new MinesweeperOptions();
//
//        options.setBoardSize(BoardSize.Medium);
//        options.setDifficulty(Difficulty.Easy);
//
//        final int easyNumberOfMines = options.getNumberOfMines();
//
//        options.setDifficulty(Difficulty.Hard);
//
//        final int hardNumberOfMines = options.getNumberOfMines();
//
//        assertTrue(easyNumberOfMines < hardNumberOfMines);
//    }


}