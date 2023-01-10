import com.itvdn.checkers.SerializedBoard
import com.itvdn.checkers.Player
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.p

/**
 * Parameters for the [checkerBoardComponent].
 * @property board - the board to display
 */
external interface CheckerBoardProps : Props {
    var board: SerializedBoard
}

/**
 * Component to render the checkerboard
 * Has a parameter of type [SerializedBoard] which is the current state of the game
 */
val checkerBoardComponent = FC<CheckerBoardProps> { it ->
    // Get the board from the props
    val board = it.board
    // Render the board
    ReactHTML.div {
        // Iterate over the board one row at a time in reverse order
        for (rowInv in 0 until board.boardDimensions) {
            val row = board.boardDimensions - rowInv - 1
            p {
                // Print the row number
                + "${row + 1}⠀"
                for (column in 0 until board.boardDimensions) {
                    // Get the cell at this position
                    val cell = board.board[row * board.boardDimensions + column]
                    val cellStr = when (cell) {
                        Player.NONE -> "⠀"
                        Player.WHITE -> "○"
                        Player.BLACK -> "●"
                    }
                    +"|⠀$cellStr⠀"
                }
                +"|"
            }
        }

        p {
            // Print the column numbers
            + "⠀⠀"
            +((List(board.boardDimensions) { pos -> "⠀${pos + 1}⠀" }).joinToString(" "))
        }
    }
}