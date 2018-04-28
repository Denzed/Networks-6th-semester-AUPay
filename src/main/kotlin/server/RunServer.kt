package server

import network.SERVER_PORT
import network.http.HttpRequest
import network.http.HttpResponse
import org.json.JSONObject
import java.net.ServerSocket
import java.util.*

fun main(args: Array<String>) {
    ServerSocket(SERVER_PORT).use { serverSocket ->
        println("SERVER IS UP AND RUNNING")
        while (true) {
            try {
                serverSocket.accept().use { socket ->
                    println("NEW CONNECTION")
                    val request = HttpRequest.parseFromInputStream(socket.getInputStream())
                    socket.shutdownInput()
                    println(request)
                    val clientToken = UUID.randomUUID().toString()
                    val response =
                        HttpResponse(
                            200,
                            "OK",
                            mapOf(Pair("Set-Cookie", "AuthToken=$clientToken")),
                            JSONObject().put("accountId", "153"))
                    response.writeToOutputStream(socket.getOutputStream())
                    println(response)
                    socket.shutdownOutput()
                }
            } catch (e: Exception) {
                println("EXCEPTION DURING HANDLING: ${e.message}")
            }
        }
    }
}
