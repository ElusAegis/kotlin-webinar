package com.itvdn.checkers

/**
 * An extension function to convert a CellPositionKt from a Cross Platform Class
 * Into a Java Class representation
 * The only reason we need is that we use a Java Class inside the CheckerBoard
 * And we must make sure that the CheckerBoard also handles the Cross Platform Move Submissions
 * Another and better solution would be to remove Java CellPosition and use the Kotlin one
 */
fun CellPositionKt.toJava() = CellPosition(row, col)
