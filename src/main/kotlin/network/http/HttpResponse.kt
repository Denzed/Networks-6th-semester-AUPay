package network.http

import org.json.JSONObject

data class HttpResponse(
    val statusCode: Int,
    val statusMessage: String,
    val headers: Map<String, String>,
    val body: JSONObject
)
