import com.itvdn.checkers.CheckerGameState
import com.itvdn.checkers.SerializedBoard
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p
import react.useEffectOnce
import react.useState

/**
 * A coroutine scope that is tied to the lifecycle of the component.
 * This allows to launch coroutines and not block the rendering of the component.
 *
 * This means that when the component is unmounted, the coroutine scope will be cancelled.
 * This is useful for cancelling any pending network requests.
 *
 */
private val scope = MainScope()

/**
 * App Component
 */
val App = FC<Props> {
    /// State
    // Current board state
    var board : SerializedBoard? by useState(null)

    /// Effects
    useEffectOnce {
        scope.launch {
            board = getBoard()
        }
    }

    /// Render
    h1 {
        +"Full-Stack Checker Board"
    }
    if (board == null) {
        +"Loading..."
    } else {
        // Find out if the game is over
        val someoneWon = board?.state == CheckerGameState.BLACK_WON || board?.state == CheckerGameState.WHITE_WON
        if (someoneWon) {
            h2 {
                +"Game finished"
            }
            p {
                +"Winner: ${board?.state?.player}"
            }

            // Offer to restart the game
            form {
                onSubmit = {
                    it.preventDefault()
                    scope.launch {
                        restartGame()
                        board = getBoard()
                    }
                }
                button {
                    +"Play again"
                }
            }
        } else {
            // The game is still going
            h2 {
                +"Game Status: Player ${board!!.state.player} move"
            }
            // Render the board
            checkerBoardComponent {
                this.board = board!!
            }

            br {}
            br {}
            br {}

            // Render the move form
            formComponent {
                onSubmit = { move ->
                    // Print the move to the console
                    console.log("Move: $move")
                    // Run a request to make the move in a coroutine to not block the rendering
                    scope.launch {
                        try {
                            makeMove(move)
                        } catch (e: Exception) {
                            // On error, alert the user
                            window.alert("Error: ${e.message}")
                        }
                        // After the move, get the new board state
                        board = getBoard()
                    }
                }
            }
        }
    }
}