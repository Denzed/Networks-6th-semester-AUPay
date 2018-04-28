package network.requests

import network.http.HttpMethod

class PayHttpRequest(
    value: Int,
    fromId: Int,
    toId: Int,
    authToken: String
) : AuthorizedHttpRequest(HttpMethod.POST, "/pay/$value/$fromId/$toId", authToken)
