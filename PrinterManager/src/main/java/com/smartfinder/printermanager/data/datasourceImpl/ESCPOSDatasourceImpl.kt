package com.smartfinder.printermanager.data.datasourceImpl

import android.content.Context
import com.bumptech.glide.Glide
import com.dantsu.escposprinter.EscPosPrinterCommands
import com.dantsu.escposprinter.connection.tcp.TcpConnection
import com.smartfinder.printermanager.data.datasource.ESCPOSDatasource
import com.smartfinder.printermanager.utils.ExternalPrinterUtils
import io.reactivex.rxjava3.core.Completable

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

                    val bitmap = Glide.with(context).asBitmap().load(pathDemo).submit().get()
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