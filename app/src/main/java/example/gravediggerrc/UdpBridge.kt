package com.example.gravediggerrc

import android.webkit.JavascriptInterface
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.Executors

class UdpBridge(
    private val host: String = "192.168.4.1",
    private val port: Int = 4210
) {
    private val executor = Executors.newSingleThreadExecutor()
    private val socket = DatagramSocket()
    private val address: InetAddress = InetAddress.getByName(host)

    @JavascriptInterface
    fun sendUdp(message: String) {
        executor.execute {
            try {
                val bytes = message.toByteArray(Charsets.UTF_8)
                val packet = DatagramPacket(bytes, bytes.size, address, port)
                socket.send(packet)
            } catch (_: Exception) {
                // Ignore les erreurs ponctuelles si le Wi-Fi n'est pas encore pret
            }
        }
    }
}