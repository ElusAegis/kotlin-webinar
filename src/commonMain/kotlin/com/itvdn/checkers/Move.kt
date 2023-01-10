package com.itvdn.checkers

/**
 * Cross Platform data type containing information about how a player made a move
 * @param from - players original position
 * @param to - players new position
 */
@kotlinx.serialization.Serializable
data class Move(val from: CellPositionKt, val to: CellPositionKt)
