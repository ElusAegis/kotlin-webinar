import com.itvdn.checkers.CellPositionKt
import com.itvdn.checkers.Move
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.form
import react.useState

/**
 * Parameters for the [formComponent] component.
 * @property onSubmit Callback to be called when the form is submitted.
 */
external interface FormProps : Props {
    var onSubmit: (Move) -> Unit
}

/**
 * A form component that allows the user to enter a move.
 * @attribute props The properties for the component.
 */
val formComponent = FC <FormProps> { props ->

    /// State

    // The current value of the input with setter methods to update it.
    val (fromRow, setFromRow) = useState("")
    val (fromCol, setFromCol) = useState("")
    val (toRow, setToRow) = useState("")
    val (toCol, setToCol) = useState("")

    /// Render

    // The form is a collection of input fields and a submit button.
    form {
        // Function to do once the form is submitted.
        onSubmit = {
            it.preventDefault()
            // Convert the input values to integers.
            val fromPos = CellPositionKt(fromRow.toInt() - 1, fromCol.toInt() - 1)
            val toPos = CellPositionKt(toRow.toInt() - 1, toCol.toInt() - 1)
            // Call the callback with the move.
            props.onSubmit(Move(fromPos, toPos))
        }

        ReactHTML.h2 {
            +"Make a move"
        }
        // Group the input fields together to describe position From.
        ReactHTML.div {
            ReactHTML.p {
                +"From: "
            }
            ReactHTML.input {
                type = InputType.text
                onChange = {
                    // Update the state when the input changes.
                    setFromRow(it.target.value)
                }
                value = fromRow
                placeholder = "row number"
            }
            ReactHTML.input {
                type = InputType.text
                onChange = {
                    // Update the state when the input changes.
                    setFromCol(it.target.value)
                }
                value = fromCol
                placeholder = "col number"
            }
        }
        // Group the input fields together to describe position To.
        ReactHTML.div {
            ReactHTML.p {
                +"To: "
            }
            ReactHTML.input {
                type = InputType.text
                onChange = {
                    // Update the state when the input changes.
                    setToRow(it.target.value)
                }
                value = toRow
                placeholder = "row number"
            }
            ReactHTML.input {
                type = InputType.text
                onChange = {
                    // Update the state when the input changes.
                    setToCol(it.target.value)
                }
                value = toCol
                placeholder = "col number"
            }
        }

        ReactHTML.br {}

        // Submit button.
        ReactHTML.button {
            +"Make a Move"
        }
    }
}
