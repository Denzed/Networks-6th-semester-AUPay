package network.requests

import network.http.HttpMethod

class GetPaymentsHttpRequest(
    authToken: String
) : AuthorizedHttpRequest(HttpMethod.GET, "/payments", authToken)
