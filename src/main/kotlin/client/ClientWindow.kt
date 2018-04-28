package client

import com.google.common.base.Preconditions
import network.SERVER_HOSTNAME
import network.SERVER_PORT
import network.http.HttpResponse
import network.requests.RegisterHttpRequest
import javax.swing.*
import kotlin.concurrent.thread

class ClientWindow : JFrame() {
    private val httpClient: HttpClient = HttpClient(SERVER_HOSTNAME, SERVER_PORT)
    @Volatile private var authToken: String? = null
    @Volatile private var accountId: Int? = null

    init {
        setBounds(100, 100, 720, 480)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = BoxLayout(contentPane, BoxLayout.X_AXIS)
        val registerButton = JButton("Register")
        add(registerButton)
        registerButton.addActionListener { _ ->
            thread {
                try {
                    val request = RegisterHttpRequest()
                    val response = httpClient.executeRequest(request)
                    if (response.statusCode != HttpResponse.OK_STATUS_CODE) {
                        showUnsuccessfulStatusCode(response.statusCode)
                        return@thread
                    }
                    val cookie = response.headers["Set-Cookie"]
                    Preconditions.checkArgument(cookie != null)
                    val cookieParts = cookie!!.split("=")
                    Preconditions.checkArgument(cookieParts[0] == "AuthToken")
                    authToken = cookieParts[1]
                    accountId = response.body.getInt("accountId")
                    showMessageDialog("Registration successful!")
                    SwingUtilities.invokeLater {
                        registerButton.isEnabled = false
                        val accountLabel = JLabel("Your account id is $accountId")
                        add(accountLabel)
                        revalidate()
                    }
                } catch (e: Exception) {
                    showMessageDialog("Exception: ${e.message}")
                }
            }
        }
    }

    private fun showUnsuccessfulStatusCode(statusCode: Int) {
        JOptionPane.showMessageDialog(this, "Returned status code: $statusCode")
    }

    fun showMessageDialog(message: String) {
        JOptionPane.showMessageDialog(this, message)
    }
}
