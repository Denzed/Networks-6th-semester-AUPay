package network.requests

import network.http.HttpMethod
import network.http.HttpRequest

class BalanceHttpRequest(
    authToken: String
) : HttpRequest(HttpMethod.GET, "/balance", mapOf(Pair("Cookie", "AuthToken=$authToken")))
