package network.data

/**
 * Represents the status of the payment order:
 * -- PLACED means that the payment is in the system, but isn't confirmed or denied by fromAccountId
 * -- CONFIRMED means that the payment was confirmed by fromAccountId
 * -- DENIED means that either the payment was denied by fromAccountId or there is not enough
 *    money on the balance
 * -- SUCCESSFUL means that the payment was conducted by the system
 */
enum class PaymentStatus {
    PLACED,
    CONFIRMED,
    DENIED,
    SUCCESSFUL,
}
