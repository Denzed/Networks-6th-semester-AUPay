package network.requests

import network.http.HttpMethod
import network.http.HttpRequest

class RegisterHttpRequest : HttpRequest(HttpMethod.POST, "/register")
