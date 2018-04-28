package network.http

import com.google.common.base.Preconditions
import com.google.common.io.ByteStreams
import com.google.common.primitives.Bytes
import network.HTTP_ENCODING
import network.HTTP_SEPARATOR
import network.HTTP_VERSION
import java.io.InputStream
import java.io.OutputStream
import java.util.*

data class HttpRequest(
    val method: HttpMethod,
    val path: String,
    val headers: Map<String, String>
) {
    fun writeToOutputStream(outputStream: OutputStream) {
        outputStream.use {
            outputStream.write("$method $path $HTTP_VERSION".toByteArray(HTTP_ENCODING))
            outputStream.write(HTTP_SEPARATOR)
            headers.forEach { header ->
                outputStream.write("${header.key}: ${header.value}".toByteArray(HTTP_ENCODING))
                outputStream.write(HTTP_SEPARATOR)
            }
            outputStream.write(HTTP_SEPARATOR)
        }
    }

    companion object {
        fun parseFromInputStream(inputStream: InputStream): HttpRequest {
            val bytes = ByteStreams.toByteArray(inputStream)
            val requestLineEnd = Bytes.indexOf(bytes, HTTP_SEPARATOR)
            Preconditions.checkArgument(requestLineEnd != -1)
            val requestLineBytes = Arrays.copyOf(bytes, requestLineEnd)
            val requestLine = String(requestLineBytes, HTTP_ENCODING)
            val parts = requestLine.split(" ")
            Preconditions.checkArgument(parts.size == 3)
            val method = HttpMethod.valueOf(parts[0])
            val path = parts[1]
            Preconditions.checkArgument(parts[2] == HTTP_VERSION)

            var startIndex = requestLineEnd + HTTP_SEPARATOR.size
            val headers = mutableMapOf<String, String>()
            while (true) {
                val bytesLeft = bytes.sliceArray(startIndex until bytes.size)
                val separatorIndex = Bytes.indexOf(bytesLeft, HTTP_SEPARATOR)
                Preconditions.checkArgument(separatorIndex != -1)
                if (separatorIndex == 0) {
                    break
                }
                val headerBytes = bytesLeft.sliceArray(0 until separatorIndex)
                val headerLine = String(headerBytes, HTTP_ENCODING)
                val headerColumn = headerLine.indexOf(':')
                Preconditions.checkArgument(headerColumn != -1)
                val headerName = headerLine.substring(0, headerColumn)
                val headerValue = headerLine.substring(headerColumn + 2)
                headers[headerName] = headerValue

                startIndex += separatorIndex + HTTP_SEPARATOR.size
            }

            return HttpRequest(method, path, headers)
        }
    }
}
