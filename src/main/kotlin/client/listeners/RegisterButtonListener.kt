package client.listeners

import client.ClientWindow
import com.google.common.base.Preconditions
import network.requests.RegisterHttpRequest
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.SwingUtilities

class RegisterButtonListener(
    clientWindow: ClientWindow,
    private val registerButton: JButton
) : ClientWindowButtonListener(clientWindow) {

    override fun actionPerformed(e: ActionEvent?) {
        val request = RegisterHttpRequest()
        executeRequestInsideClientWindow(request) { response ->
            val cookie = response.headers["Set-Cookie"]
            Preconditions.checkArgument(cookie != null)
            val cookieParts = cookie!!.split("=")
            Preconditions.checkArgument(cookieParts[0] == "AuthToken")
            val authToken = cookieParts[1]
            val accountId = response.body.getInt("accountId")
            clientWindow.authToken = authToken
            clientWindow.accountId = accountId
            clientWindow.showMessageDialog("Registration successful!")
            SwingUtilities.invokeLater {
                registerButton.isEnabled = false
                val accountLabel = JLabel("Your account id is $accountId")
                clientWindow.add(accountLabel)
                val balanceButton = JButton("Check balance")
                balanceButton.addActionListener(BalanceButtonListener(clientWindow))
                clientWindow.add(balanceButton)
                val accountsButton = JButton("Get accounts")
                accountsButton.addActionListener(AccountsButtonListener(clientWindow))
                clientWindow.add(accountsButton)
                clientWindow.revalidate()
            }
        }
    }
}
