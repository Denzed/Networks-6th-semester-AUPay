package network.requests

import network.http.HttpRequestMethod

class PayHttpRequest(
    amount: Int,
    fromId: Int,
    toId: Int,
    authToken: String
) : AuthorizedHttpRequest(HttpRequestMethod.POST, "/pay/$amount/$fromId/$toId", authToken)
