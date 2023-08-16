package com.smartfinder.printermanager.data.datasourceImpl

import android.content.Context
import com.bumptech.glide.Glide
import com.smartfinder.printermanager.data.datasource.StarDatasource
import com.smartfinder.printermanager.utils.ExternalPrinterUtils
import com.starmicronics.stario.StarIOPort
import com.starmicronics.stario.StarIOPortException
import com.starmicronics.starioextension.ICommandBuilder
import com.starmicronics.starioextension.StarIoExt
import io.reactivex.rxjava3.core.Completable

class StarDatasourceImpl : StarDatasource {
    private var port: StarIOPort? = null
    override fun print(
        context: Context,
        ipAddress: String,
        scale: Int,
        path: String,
        isRedColor: Boolean
    ): Completable {
        return Completable.create {
            try {
                port = StarIOPort.getPort(ipAddress, "", 10000, context)
                var status = port!!.beginCheckedBlock()
                if (status.offline) {
                    it.tryOnError(Throwable("A printer is offline. $ipAddress"))
                } else {
                    val pathDemo =
                        "https://gsastorages.blob.core.windows.net/gsa/upload/260/print/viewcheck/638267540499205517.png"

                    val bitmap = Glide.with(context).asBitmap().load(pathDemo).submit().get()
                    val bitmap1 = ExternalPrinterUtils.resizeBitmap(bitmap, scale)

                    val builder = StarIoExt.createCommandBuilder(StarIoExt.Emulation.StarDotImpact)
                    builder.beginDocument()
                    builder.appendInvert(isRedColor)
                    builder.appendBitmap(bitmap1, true, scale, true)
                    builder.appendInvert(false)
                    builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed)
                    builder.endDocument()

                    status = port!!.beginCheckedBlock()
                    if (status.offline) {
                        it.tryOnError(Throwable("A printer is offline. $ipAddress"))
                    } else if (status.coverOpen) {
                        it.tryOnError(Throwable("Printer cover is open $ipAddress"))
                    } else if (status.receiptPaperEmpty) {
                        it.tryOnError(Throwable("Receipt paper is empty"))
                    }
                    port?.writePort(builder.commands, 0, builder.commands.size)
                    port?.setEndCheckedBlockTimeoutMillis(30000)
                    port?.endCheckedBlock()
                }
                StarIOPort.releasePort(port)
                it.onComplete()
            } catch (e: StarIOPortException) {
                StarIOPort.releasePort(port)
                it.tryOnError(e)
            } catch (e: Exception) {
                StarIOPort.releasePort(port)
                it.tryOnError(e)
            }
        }
    }
}