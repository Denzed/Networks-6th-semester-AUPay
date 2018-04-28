package server

import com.google.common.base.Preconditions
import network.SERVER_PORT
import network.data.PaymentStatus
import network.http.HttpRequest
import network.http.HttpResponse
import org.json.JSONArray
import org.json.JSONObject
import java.net.ServerSocket
import java.util.*

fun generateResponseForHttpRequest(request: HttpRequest): HttpResponse = try {
    val path = request.path
    when {
        path.startsWith("/register") -> {
            val clientToken = UUID.randomUUID().toString()
            HttpResponse(
                HttpResponse.OK_STATUS_CODE,
                "OK",
                mapOf(Pair("Set-Cookie", "AuthToken=$clientToken")),
                JSONObject().put("accountId", "153"))
        }
        path.startsWith("/accounts") -> {
            val accounts = JSONArray()
            accounts.put(JSONObject().put("id", 153))
            accounts.put(JSONObject().put("id", 289))
            accounts.put(JSONObject().put("id", 793))
            HttpResponse(
                HttpResponse.OK_STATUS_CODE,
                "OK",
                emptyMap(),
                JSONObject().put("accounts", accounts))
        }
        path.startsWith("/balance") -> {
            verifyAuthTokenIsPresent(request)
            HttpResponse(
                HttpResponse.OK_STATUS_CODE,
                "OK",
                emptyMap(),
                JSONObject().put("balance", 15000))
        }
        path.startsWith("/pay/") -> {
            verifyAuthTokenIsPresent(request)
            val pathParts = path.split(Regex.fromLiteral("/"))
            pathParts[2].toInt()
            pathParts[3].toInt()
            pathParts[4].toInt()
            val paymentId = Math.abs(Random().nextInt())
            HttpResponse(
                HttpResponse.OK_STATUS_CODE,
                "OK",
                emptyMap(),
                JSONObject().put("paymentId", paymentId))
        }
        path.startsWith("/payments") -> {
            verifyAuthTokenIsPresent(request)
            val payment1 = JSONObject()
            payment1.put("amount", 500)
            payment1.put("fromAccountId", 153)
            payment1.put("paymentId", 147982)
            payment1.put("toAccountId", 289)
            payment1.put("status", PaymentStatus.PLACED)
            val payment2 = JSONObject()
            payment2.put("amount", 50)
            payment2.put("fromAccountId", 793)
            payment2.put("paymentId", 213009)
            payment2.put("toAccountId", 153)
            payment2.put("status", PaymentStatus.SUCCESSFUL)
            HttpResponse(
                HttpResponse.OK_STATUS_CODE,
                "OK",
                emptyMap(),
                JSONObject().put("payments", JSONArray().put(payment1).put(payment2)))
        }
        else -> throw IllegalArgumentException()
    }
} catch (_: Exception) {
    HttpResponse(
        HttpResponse.BAD_REQUEST_STATUS_CODE,
        "Bad Request",
        emptyMap(),
        JSONObject())
}

fun verifyAuthTokenIsPresent(request: HttpRequest) {
    Preconditions.checkArgument(request.headers.containsKey("Cookie"))
    val cookie = request.headers["Cookie"]!!
    val equalsIndex = cookie.indexOf('=')
    Preconditions.checkArgument(equalsIndex != -1)
    val cookieName = cookie.substring(0, equalsIndex)
    Preconditions.checkArgument(cookieName == "AuthToken")
    val cookieValue = cookie.substring(equalsIndex + 1)
    Preconditions.checkArgument(cookieValue.isNotEmpty())
}

fun main(args: Array<String>) {
    ServerSocket(SERVER_PORT).use { serverSocket ->
        println("SERVER IS UP AND RUNNING")
        while (true) {
            try {
                serverSocket.accept().use { socket ->
                    println("NEW REQUEST")
                    val request = HttpRequest.parseFromInputStream(socket.getInputStream())
                    socket.shutdownInput()
                    println(request)
                    val response = generateResponseForHttpRequest(request)
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
