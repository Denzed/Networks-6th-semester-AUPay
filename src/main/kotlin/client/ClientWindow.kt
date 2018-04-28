package client

import network.SERVER_HOSTNAME
import network.SERVER_PORT
import network.http.HttpMethod
import network.http.HttpRequest
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import kotlin.concurrent.thread

class ClientWindow : JFrame() {
    private val httpClient: HttpClient = HttpClient(SERVER_HOSTNAME, SERVER_PORT)
    private val connectButton: JButton = JButton("Connect")

    init {
        setBounds(100, 100, 720, 480)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = BoxLayout(contentPane, BoxLayout.X_AXIS)
        add(connectButton)
        connectButton.addActionListener { _ ->
            thread {
                try {
                    val request =
                        HttpRequest(
                            HttpMethod.POST,
                            "/register",
                            mapOf(Pair("Cookie", "SessionID=anonymous")))
                    val response = httpClient.executeRequest(request)
                    JOptionPane.showMessageDialog(this, response.toString())
                } catch (e: Exception) {
                    JOptionPane.showMessageDialog(this, "Exception: ${e.message}")
                }
            }
        }
    }
}
