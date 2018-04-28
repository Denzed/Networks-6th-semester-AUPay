package client

import network.http.HttpRequest
import network.http.HttpResponse
import java.io.IOException
import java.net.Socket

class HttpClient(private val serverHostname: String, private val serverPort: Int) {
    fun executeRequest(request: HttpRequest): HttpResponse {
        Socket(serverHostname, serverPort).use { socket ->
            request.writeToOutputStream(socket.getOutputStream())
        }
        throw IOException("Server does not respond")
    }
}