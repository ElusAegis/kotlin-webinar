package com.itvdn.checkers

// File that contains some common classes

/**
 * The state of the cell on the board
 * We also use this to represent the player
 * Where NONE is like a null player
 */
@kotlinx.serialization.Serializable
enum class Player {
    WHITE, BLACK, NONE
}


/**
 * The current state of the game
 * @param player The player who's turn it is or who won
 */
@kotlinx.serialization.Serializable
enum class CheckerGameState(val player: Player) {
    WHITE_WON(Player.WHITE), BLACK_WON(Player.BLACK), WHITE_TO_MOVE(Player.WHITE), BLACK_TO_MOVE(Player.BLACK);

    /**
     * Generates the next state of the game if a player has moved
     * If the game is over then it will return the same state
     *
     * Note: it does not detect when the game ends as a result of a move
     * There is a separate method for that
     */
    fun switchToNextPlayerMove(): CheckerGameState {
        return when (this) {
            WHITE_TO_MOVE -> BLACK_TO_MOVE
            BLACK_TO_MOVE -> WHITE_TO_MOVE
            else -> this
        }
    }
}