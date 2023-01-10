package com.itvdn.checkers;

/**
 * An interface for a checkerboard.
 */
public interface ICheckerBoard {
    /**
     * Print the checkerboard to the console.
     */
    void printBoard();

    /**
     * Change the state of the checkerboard in accordance with the move.
     * Also check that the move is valid.
     * This is a JVM-only method as it depends on the Java Class.
     * @param from The position of the piece to move from.
     * @param to The position to move the piece to.
     */
    void makeMove(CellPosition from, CellPosition to);
}
