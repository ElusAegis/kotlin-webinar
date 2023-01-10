import com.itvdn.checkers.Move
import com.itvdn.checkers.SerializedBoard
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

/**
 * A Ktor client that talks to the API
 */
val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

/**
 * Get the current state of the game as a [SerializedBoard]
 * @throws [Exception] if the response is not successful
 */
suspend fun getBoard(): SerializedBoard {
    return try { jsonClient.get("/board").body() }
    catch (e: Exception) { throw Exception("Failed to get board: ${e.message}") }
}

/**
 * Make a move on the board
 * @param move The move to make
 * @throws [Exception] if the move is invalid
 */
suspend fun makeMove(move: Move) {
    val result = jsonClient.post("/board/move") {
        contentType(ContentType.Application.Json)
        setBody(move)
    }.call.response.status

    if (result != HttpStatusCode.OK) {
        throw Exception("Move $move failed: ${result.description}")
    }
}

/**
 * Asks the server to reset the board
 * @throws [Exception] if the reset fails
 */
suspend fun restartGame() {
    val result = jsonClient.post("/board/restart").call.response.status

    if (result != HttpStatusCode.OK) {
        throw Exception("Restart failed: ${result.description}")
    }
}
