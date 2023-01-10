package com.itvdn.checkers;

import kotlinx.serialization.Serializable;

/**
 * Class to store the position information of a board cell
 * Defines a row and a column, with values from 0 to (board dimensions - 1)
 */
@Serializable
public class CellPosition {
    private final int row;
    private final int col;

    public CellPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
