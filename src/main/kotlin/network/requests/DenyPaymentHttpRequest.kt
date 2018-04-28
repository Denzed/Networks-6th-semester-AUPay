package network.requests

import network.http.HttpMethod

class DenyPaymentHttpRequest(
    paymentId: Int,
    authToken: String
) : AuthorizedHttpRequest(HttpMethod.POST, "/deny/$paymentId", authToken)
