package client

import client.listeners.RegisterButtonListener
import network.SERVER_HOSTNAME
import network.SERVER_PORT
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane

class ClientWindow : JFrame() {
    @Volatile var authToken: String? = null
    @Volatile var accountId: Int? = null

    val httpClient: HttpClient = HttpClient(SERVER_HOSTNAME, SERVER_PORT)

    init {
        setBounds(100, 100, 720, 480)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        val registerButton = JButton("Register")
        add(registerButton)
        registerButton.addActionListener(RegisterButtonListener(this, registerButton))
    }

    fun showUnsuccessfulRequest(statusCode: Int, statusMessage: String) {
        JOptionPane.showMessageDialog(this, "$statusCode: $statusMessage")
    }

    fun showMessageDialog(message: String) {
        JOptionPane.showMessageDialog(this, message)
    }
}
