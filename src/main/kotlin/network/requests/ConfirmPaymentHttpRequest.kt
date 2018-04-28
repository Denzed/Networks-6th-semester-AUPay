package network.requests

import network.http.HttpMethod

class ConfirmPaymentHttpRequest(
    paymentId: Int,
    authToken: String
) : AuthorizedHttpRequest(HttpMethod.POST, "/confirm/$paymentId", authToken)
