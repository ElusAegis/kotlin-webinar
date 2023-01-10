package com.itvdn.checkers

/**
 * A simple terminal application for the game.
 * One player can submit moves, first for white, then for black.
 * The game is played until a player wins.
 * The game state is printed after each move.
 */
class Terminal {
    // We use a companion object to define a static method
    companion object {

        /**
         * The entry point for the application.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            println("Hello, world of checkers!")

            // Create a new game with a 3x3 board
            val checkerBoard = CheckerBoard(3)

            // Loop until the game is over
            while (true) {

                // Get the current game state
                val gameState = checkerBoard.gameState

                // If the game is over, print the winner and exit
                if (gameState == CheckerGameState.WHITE_WON || gameState == CheckerGameState.BLACK_WON) {
                    println("Player ${gameState.player} has won!")
                    break
                }


                // Otherwise, print the current game state
                checkerBoard.printBoard()

                // Then get the next move from the player
                println("Enter a move for ${gameState.player} in the format 'fromRow,fromCol,toRow,toCol'")
                val input = readLine() ?: ""
                val inputSplit = input.split(",")
                if (inputSplit.size != 4) {
                    println("Invalid input format")
                    continue
                }
                // We make the player input a move as a string, but we need to convert it to a Move object
                // We also deduct 1 from the row and column values, as the board is 0-indexed
                // But we want the user to input 1-indexed values
                val move = try {
                    val fromRow = inputSplit[0].toInt() - 1
                    val fromCol = inputSplit[1].toInt() - 1
                    val toRow = inputSplit[2].toInt() - 1
                    val toCol = inputSplit[3].toInt() - 1
                    Move(CellPositionKt(fromRow, fromCol), CellPositionKt(toRow, toCol))
                } catch (e: NumberFormatException) {
                    println("Invalid input - must be integers")
                    continue
                }

                try {
                    // We try to make the move
                    checkerBoard.makeMove(move)
                } catch (e: Exception) {
                    // If the move is invalid, we catch the exception and print the error message
                    println("Invalid move $move: ${e.message})" )
                }
            }

        }
    }
}
