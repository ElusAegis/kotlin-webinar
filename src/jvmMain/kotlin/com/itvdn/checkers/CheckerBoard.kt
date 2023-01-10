package com.itvdn.checkers

/**
 * This is the main class for the game. It is responsible for keeping track of the state of the game
 * and the board. It is also responsible for making moves and checking for a win.
 *
 * @param boardDimensions The dimensions of the board. The board will be a square with this many rows and columns.
 * @property board The board is represented as a list of cell states. The list is ordered by row and then column.
 * @property gameState The current state of the game. The setter is private to prevent invalid state transitions.
 */
class CheckerBoard(private val boardDimensions: Int = 8) : ICheckerBoard {

    // We have used Kotlin notation to declare the getter as public and the setter as private
    var gameState: CheckerGameState
        private set

    // Create a board list with boardDimensions * boardDimensions elements and initialize it with CheckerCellState.NONE
    private val board = MutableList(boardDimensions * boardDimensions) { Player.NONE }


    /**
     * A simple getter for the board. It returns a copy of the board so that it cannot be modified.
     */
    private fun getCell(position: CellPosition): Player {
        return board[position.row * boardDimensions + position.col]
    }

    /**
     * A simple setter for the board. It sets the value of the board at the given position.
     */
    private fun updateCell(position: CellPosition, state: Player) {
        board[position.row * boardDimensions + position.col] = state
    }


    // This code is run when the class is instantiated. It initializes the board and sets the game state.
    init {

        // Set the White pieces
        for (column in 0 until boardDimensions) {
            updateCell(CellPosition(0, column), Player.WHITE)
        }

        // Set the Black pieces
        for (column in 0 until boardDimensions) {
            updateCell(CellPosition(boardDimensions - 1, column), Player.BLACK)
        }

        // Set the game state to White to move
        gameState = CheckerGameState.WHITE_TO_MOVE
    }

    /**
     * Prints the board to the console.
     */
    override fun printBoard() {
        // Iterate over the rows
        for (row in 0 until boardDimensions) {
            // Iterate over the columns
            for (column in 0 until boardDimensions) {
                // Get the cell state at the current position
                val position = CellPosition(boardDimensions - row - 1, column)
                val cellStr = when (getCell(position)) {
                    Player.NONE -> " "
                    Player.WHITE -> "○"
                    Player.BLACK -> "●"
                }
                // Print the cell state
                print("| $cellStr ")
            }
            // Print the delimiter
            println("|")
            println(" ---".repeat(boardDimensions) + " ")
        }
    }

    /**
     * Converts the board to a Cross Platform class [SerializedBoard]
     */
    fun serialize(): SerializedBoard {
        return SerializedBoard(gameState, board, boardDimensions)
    }

    /**
     * Makes a move on the board. The move is validated before it is made.
     * @param move The move to make.
     * @throws Exception If the move is invalid.
     * @return Unit if the transition has been successful
     */
    fun makeMove(move: Move) = makeMove(move.from.toJava(), move.to.toJava())

    /**
     * Makes a move on the board. The move is validated before it is made.
     * @param from The position of the piece to move.
     * @param to The position to move the piece to.
     * @throws Exception If the move is invalid.
     * @return Unit if the transition has been successful
     */
    override fun makeMove(from: CellPosition, to: CellPosition) {
        // Get the cell state at the from position
        val fromState = getCell(from)

        // Check that there is a piece at the from position
        if (fromState == Player.NONE) {
            throw Exception("No checker at position $from")
        }

        // Check that the player is moving their own piece
        if (fromState != gameState.player) {
            throw Exception("Moving not your own piece")
        }

        // Check that the move is valid
        val moveIsLegal = (to.col == from.col + 1 || to.col == from.col - 1 || to.col == from.col) && when (fromState) {
            Player.WHITE -> {
                to.row == from.row + 1
            }
            Player.BLACK -> {
                to.row == from.row - 1
            }
            else -> {
                false
            }
        }

        // If the move is not valid, throw an exception
        if (!moveIsLegal) {
            throw Exception("Illegal checker move from $from to $to")
        }

        // Otherwise, make the move
        updateCell(from, Player.NONE)
        updateCell(to, fromState)
        // Update the game state and check for a win
        updateState()
    }

    /**
     * Updates the game state. If the game is over, the game state is set to WIN of particular colour.
     * This function mutates the game state.
     * The game is over if there are no more pieces of one color or if a piece has reached the opposite end of the board.
     */
    private fun updateState() {
        // Store the next state of the game, initially set to null
        var nextState: CheckerGameState? = null

        // Count the number of pieces of each color
        var whiteCount = 0
        var blackCount = 0

        board.forEach {
            when (it) {
                Player.WHITE -> whiteCount++
                Player.BLACK -> blackCount++
                else -> {}
            }
        }

        // If there are no pieces of one color, the game is over and the other player wins
        // If someone has won, set the next state to WIN with the winner
        if (whiteCount == 0)
            nextState = CheckerGameState.BLACK_WON
        else if (blackCount == 0)
            nextState = CheckerGameState.WHITE_WON
        else {

            // Check if any players have reached the other side
            for (column in 0 until boardDimensions) {
                if (getCell(CellPosition(boardDimensions - 1, column)) == Player.WHITE) {
                    // If a white piece has reached the other side, the game is over and white wins
                    // We can stop checking the rest of the board
                    nextState = CheckerGameState.WHITE_WON
                    break
                }
                if (getCell(CellPosition(0, column)) == Player.BLACK) {
                    nextState = CheckerGameState.BLACK_WON
                    break
                }
            }
        }

        // Check if the nextState has already been set, if not, set it to the next player to move
        gameState = nextState ?: gameState.switchToNextPlayerMove()
    }
}

