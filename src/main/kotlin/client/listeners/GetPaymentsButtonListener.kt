package client.listeners

import client.ClientWindow
import network.data.PaymentStatus
import network.http.HttpResponse
import network.requests.GetPaymentsHttpRequest
import java.awt.GridLayout
import java.awt.Point
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.table.AbstractTableModel
import javax.swing.table.TableModel

class GetPaymentsButtonListener(
    clientWindow: ClientWindow
) : ClientWindowButtonListener(clientWindow) {

    override fun actionPerformed(e: ActionEvent?) {
        val request = GetPaymentsHttpRequest(clientWindow.authToken!!)
        executeRequestInsideClientWindow(request) { response ->
            val tableModel = buildModelFromResponse(response)
            val table = JTable(tableModel)
            val panel = JPanel()
            panel.layout = GridLayout()
            panel.add(JScrollPane(table))

            val dialog = JDialog(clientWindow, true)
            dialog.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            dialog.contentPane = panel
            dialog.pack()
            dialog.setLocationRelativeTo(clientWindow)
            dialog.location =
                Point(
                    clientWindow.width / 2 - dialog.width / 2,
                    clientWindow.height / 2 - dialog.height / 2)
            dialog.isVisible = true
        }
    }

    companion object {
        private fun buildModelFromResponse(response: HttpResponse): TableModel {
            val payments = response.body.getJSONArray("payments")
            return object : AbstractTableModel() {
                override fun getRowCount(): Int = payments.length()
                override fun getColumnCount(): Int = 5
                override fun getColumnName(column: Int): String = when (column) {
                    0 -> "PaymentId"
                    1 -> "FromAccountId"
                    2 -> "ToAccountId"
                    3 -> "Amount"
                    4 -> "Status"
                    else -> ""
                }
                override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
                    val payment = payments.getJSONObject(rowIndex)
                    return when (columnIndex) {
                        0 -> payment.getInt("paymentId")
                        1 -> payment.getInt("fromAccountId")
                        2 -> payment.getInt("toAccountId")
                        3 -> payment.getInt("amount")
                        4 -> PaymentStatus.valueOf(payment.getString("status"))
                        else -> ""
                    }
                }
            }
        }
    }
}