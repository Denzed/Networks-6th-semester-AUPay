package network.requests

import network.http.HttpMethod
import network.http.HttpRequest

open class AuthorizedHttpRequest(
    method: HttpMethod,
    path: String,
    authToken: String
) : HttpRequest(method, path, mapOf(Pair("Cookie", "AuthToken=$authToken")))
