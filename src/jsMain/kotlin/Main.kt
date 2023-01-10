import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

/**
 * This is the entry point for the browser.
 */
fun main() {
    // Create the root element for the React app.
    // Notice that the `index.html` file has a div with id `root`.
    val container = document.getElementById("root") ?: error("Couldn't find container!")
    // Create the React app and render it in the container.
    createRoot(container).render(App.create())
}