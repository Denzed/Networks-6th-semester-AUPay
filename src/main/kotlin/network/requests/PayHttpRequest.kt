package network.requests

import network.http.HttpMethod

class PayHttpRequest(
    amount: Int,
    fromId: Int,
    toId: Int,
    authToken: String
) : AuthorizedHttpRequest(HttpMethod.POST, "/pay/$amount/$fromId/$toId", authToken)
