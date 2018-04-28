package network.requests

import network.http.HttpMethod
import network.http.HttpRequest

class AccountsHttpRequest : HttpRequest(HttpMethod.GET, "/accounts")
