package com.smartfinder.printermanager.data.datasourceImpl

import android.content.Context
import androidx.core.util.Pair
import com.dantsu.escposprinter.EscPosPrinterCommands
import com.dantsu.escposprinter.connection.tcp.TcpConnection
import com.smartfinder.printermanager.data.datasource.ESCPOSDatasource
import com.smartfinder.printermanager.utils.ExternalPrinterUtils
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ESCPOSDatasourceImpl : ESCPOSDatasource {
    override fun print(
        context: Context,
        ipAddress: String,
        port: Int,
        scale: Int,
        path: String
    ): Completable {
        return Completable.create {
            try {
                val tcpConnect = TcpConnection(ipAddress, port, 120)
                tcpConnect.connect()
                if (tcpConnect.isConnected) {
                    val pathDemo =
                        "https://gsastorages.blob.core.windows.net/gsa/upload/260/print/viewcheck/638267540499205517.png"

                    val bitmap = Picasso.get().load(pathDemo).get()
                    val bitmap1 = ExternalPrinterUtils.convertBitmapToGrayscale(bitmap)

                    val printerCommand = EscPosPrinterCommands(tcpConnect)
                    printerCommand.connect()
                    printerCommand.reset()
                    printerCommand.printImage(EscPosPrinterCommands.bitmapToBytes(bitmap1, false))
                    printerCommand.newLine()
                    printerCommand.newLine()
                    printerCommand.newLine()
                    printerCommand.cutPaper()

                    if (tcpConnect.isConnected) {
                        printerCommand.disconnect()
                        tcpConnect.disconnect()
                    }
                }
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}