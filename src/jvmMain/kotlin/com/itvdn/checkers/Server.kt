package com.itvdn.checkers

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * This is a simple server that serves the static files in the resources' folder
 * It also has a set of endpoints that can be used by the client to get the current state of the game
 * And to make moves as well as reset the game
 *
 * We use Ktor as the server framework
 */
class Server {
    companion object {
        /**
         * This is the main entry point for the server
         */
        @JvmStatic
        fun main(args: Array<String>) {

            // Set the dimensions of the board
            val dimensions = 4

            // Create a new game, we set it to `var` to reset the game if needed
            var board = CheckerBoard(dimensions)

            // Create a new server on port 9090
            embeddedServer(Netty, 9090) {

                // Define how to send and receive data
                install(ContentNegotiation) {
                    json()
                }
                // Enable CORS to prevent issues with the client
                install(CORS) {
                    allowMethod(HttpMethod.Get)
                    allowMethod(HttpMethod.Post)
                    allowMethod(HttpMethod.Delete)
                    anyHost()
                }
                // Enable compression to reduce the size of the data sent
                install(Compression) {
                    gzip()
                }

                // Define the endpoints for the server
                routing {
                    // Serve the static files from the resources folder
                    get("/") {
                        call.respondText(
                            this::class.java.classLoader.getResource("index.html")!!.readText(),
                            ContentType.Text.Html
                        )
                    }
                    static("/") {
                        resources("")
                    }

                    // Get the current state of the game
                    // We return it as [BoardSerialized] which is a data class that can be serialized
                    get("/board") {
                        call.respond(board.serialize())
                    }

                    // Make a move
                    // If the move is invalid, we return a 400 error
                    // Otherwise we respond with the 200 OK status
                    post("/board/move") {
                        val move = call.receive<Move>()
                        try {
                            board.makeMove(move)
                            call.respond(HttpStatusCode(200, "OK"))
                        } catch (e: Exception) {
                            call.respond(HttpStatusCode(400, e.message ?: "Bad Request"))
                        }
                    }

                    // Reset the game
                    // If successful, we respond with the 200 OK status
                    post("/board/restart") {
                        board = CheckerBoard(dimensions)
                        call.respond(HttpStatusCode(200, "OK"))
                    }


                }
            }.start(wait = true)
        }
    }
}

