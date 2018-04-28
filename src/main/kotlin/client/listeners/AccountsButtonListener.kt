package client.listeners

import client.ClientWindow
import network.requests.AccountsHttpRequest
import java.awt.event.ActionEvent

class AccountsButtonListener(
    clientWindow: ClientWindow
) : ClientWindowButtonListener(clientWindow) {

    override fun actionPerformed(e: ActionEvent?) {
        val request = AccountsHttpRequest()
        executeRequestInsideClientWindow(request) { response ->
            val accounts = response.body.getJSONArray("accounts")
            val accountIds = mutableListOf<Int>()
            for (index in 0 until accounts.length()) {
                val account = accounts.getJSONObject(index)
                val accountID = account.getInt("id")
                accountIds.add(accountID)
            }
            clientWindow.showMessageDialog(
                accountIds.joinToString(",", "Accounts: ") { it.toString() })
        }
    }
}
