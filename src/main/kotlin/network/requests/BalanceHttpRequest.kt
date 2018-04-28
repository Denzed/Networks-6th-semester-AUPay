package network.requests

import network.http.HttpMethod

class BalanceHttpRequest(
    authToken: String
) : AuthorizedHttpRequest(HttpMethod.GET, "/balance", authToken)
