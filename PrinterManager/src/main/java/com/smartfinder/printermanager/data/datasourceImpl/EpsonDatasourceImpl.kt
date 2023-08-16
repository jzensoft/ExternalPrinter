package com.smartfinder.printermanager.data.datasourceImpl

import android.content.Context
import com.bumptech.glide.Glide
import com.epson.epos2.Epos2Exception
import com.epson.epos2.printer.Printer
import com.epson.epos2.printer.PrinterStatusInfo
import com.smartfinder.printermanager.data.datasource.EpsonDatasource
import com.smartfinder.printermanager.utils.EpsonErrorMsg
import com.smartfinder.printermanager.utils.EpsonModel
import com.smartfinder.printermanager.utils.EpsonSeries
import com.smartfinder.printermanager.utils.ExternalPrinterUtils
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableEmitter

class EpsonDatasourceImpl : EpsonDatasource {
    private var printer: Printer? = null
    override fun print(
        context: Context,
        ipAddress: String,
        model: String,
        series: String,
        scale: Int,
        path: String
    ): Completable {
        return Completable.create {
            try {
                val m = EpsonModel.getModel(model)
                val s = EpsonSeries.getSeries(series)
                val isDotMatrix = s == Printer.TM_U220

                printer = Printer(s, m, context)
                printer?.clearCommandBuffer()
                printer?.setReceiveEventListener(null)

                val pathDemo =
                    "https://gsastorages.blob.core.windows.net/gsa/upload/260/print/viewcheck/638267540499205517.png"
//                val pathDemo =
//                    "https://gsastorages.blob.core.windows.net/gsa/upload/260/print/history-closeshift/638273421314103555.png"

                val bitmap = Glide.with(context).asBitmap().load(pathDemo).submit().get()
                val bitmap1 = ExternalPrinterUtils.resizeBitmap(bitmap, scale)

                printer?.addImage(
                    bitmap1,
                    0,
                    0,
                    bitmap1.width,
                    bitmap1.height,
                    if (isDotMatrix) Printer.COLOR_NONE else Printer.COLOR_1,
                    Printer.MODE_MONO,
                    Printer.HALFTONE_THRESHOLD,
                    Printer.PARAM_DEFAULT.toDouble(),
                    if (isDotMatrix) Printer.COMPRESS_AUTO else Printer.COMPRESS_AUTO
                )

                if (isDotMatrix) {
                    printer?.addFeedLine(10)
                } else {
                    printer?.addFeedLine(5)
                }

                printer?.addCut(Printer.CUT_NO_FEED)
                printer?.connect("TCP:$ipAddress", Printer.PARAM_DEFAULT)
                printer?.sendData(Printer.PARAM_DEFAULT)
                printer?.setReceiveEventListener { _, _, status, _ ->
                    disconnectPrinter(status, isDotMatrix, it)
                }
            } catch (e: Epos2Exception) {
                val errorMsg = EpsonErrorMsg.makeErrorMsg(printer?.status)
                printer?.setReceiveEventListener(null)
                printer?.clearCommandBuffer()
                printer = null
                if (errorMsg.isNotEmpty()) {
                    it.tryOnError(Throwable(errorMsg))
                } else {
                    it.tryOnError(e)
                }
            } catch (e: Exception) {
                printer = null
                it.tryOnError(e)
            }
        }
    }

    private fun disconnectPrinter(
        status: PrinterStatusInfo?,
        isDotMatrix: Boolean,
        emitter: CompletableEmitter
    ) {
        while (true) {
            try {
                printer?.setReceiveEventListener(null)
                printer?.disconnect()
                break
            } catch (e: Epos2Exception) {
                //Note: If printer is processing such as printing and so on, the disconnect API returns ERR_PROCESSING.
                if (e.errorStatus == Epos2Exception.ERR_PROCESSING) {
                    try {
                        Thread.sleep(500)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                } else {
                    e.printStackTrace()
                    break
                }
            } catch (e: Exception) {
                e.printStackTrace()
                break
            }
        }
        printer?.clearCommandBuffer()
        val isConnected = printer?.status?.connection
        printer = null
        val errorMsg = EpsonErrorMsg.makeErrorMsg(status)
        if (errorMsg.isEmpty() || isDotMatrix) {
            if (isDotMatrix && isConnected == 0) {
                emitter.onComplete()
            } else {
                if (errorMsg.isEmpty()) {
                    emitter.onComplete()
                } else {
                    emitter.onError(Throwable(errorMsg))
                }
            }
        } else {
            emitter.onError(Throwable(errorMsg))
        }
    }
}