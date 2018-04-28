Types of requests:

1. Registration:

    ```http request
    POST /register HTTP/1.1
    ```

    ```http request
    HTTP/1.1 200 OK
    Set-Cookie: AuthToken=46c000b6-3717-4de7-a530-193d34e5b760

    {"accountId":153}
    ```

2.  Requesting all accounts:

    ```http request
    GET /accounts HTTP/1.1
    ```

    ```http request
    HTTP/1.1 200 OK

    {"accounts":[{"id":153},{"id":289}, {"id":793}]}
    ```

3.  Requesting the account balance associated with the given AuthToken:

    ```http request
    GET /balance HTTP/1.1
    Cookie: AuthToken=46c000b6-3717-4de7-a530-193d34e5b760
    ```

    ```http request
    HTTP/1.1 200 OK

    {"balance":15000}
    ```

4. Asking to place an order of payment:

    ```http request
    POST /pay/value/fromId/toId HTTP/1.1
    Cookie: AuthToken=46c000b6-3717-4de7-a530-193d34e5b760
    ```

    ```http request
    HTTP/1.1 200 OK

    {"paymentId":147982}

5. Get all payments from or to the user associated with the given AuthToken:

    ```http request
    GET /payments
    Cookie: AuthToken=46c000b6-3717-4de7-a530-193d34e5b760
    ```

    ```http request
    HTTP/1.1 200 OK

    {"payments":[{"fromAccountId":4,"paymentId":153,"toAccountId":10,"value":500,"status":"PLACED"},{"fromAccountId":18,"paymentId":154,"toAccountId":10,"value":50,"status":"SUCCESSFUL"}]}
    ```

    PaymentStatus can be one of the following:
    *   PLACED
    *   CONFIRMED
    *   DENIED
    *   SUCCESSFUL

6. Confirming an incoming payment request:

    ```http request
    POST /payment/paymentId/confirm HTTP/1.1
    Cookie: AuthToken=46c000b6-3717-4de7-a530-193d34e5b760
    ```

    ```http request
    HTTP/1.1 200 OK
    
    ```

7. Denying an incoming payment request:
    
    ```http request
    POST /payment/paymentId/deny HTTP/1.1
    Cookie: AuthToken=46c000b6-3717-4de7-a530-193d34e5b760
    ```

    ```http request
    HTTP/1.1 200 OK
    
    ```    

