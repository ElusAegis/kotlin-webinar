package com.itvdn.checkers

/**
 * The information summary of the current state of the game.
 * We created this as the `CheckerBoard` class was not Cross Platform suitable
 * As it used java classes.
 */
@kotlinx.serialization.Serializable
data class SerializedBoard(
    val state: CheckerGameState,
    val board: List<Player> = emptyList(),
    val boardDimensions: Int
)


