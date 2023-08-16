package com.smartfinder.newland.data.datasourceImpl

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.newland.sdk.ModuleManage
import com.newland.sdk.module.printer.ErrorCode
import com.newland.sdk.module.printer.PrintListener
import com.newland.sdk.module.printer.PrinterStatus
import com.smartfinder.newland.data.datasource.PrinterDatasource
import io.reactivex.rxjava3.core.Completable
import kotlin.math.roundToInt

class PrinterDatasourceImpl(
    private val context: Context,
    private val newlandSdk: ModuleManage?
) : PrinterDatasource {
    override fun print(scale: Int, path: String): Completable {
        return Completable.create {
            if (newlandSdk == null) {
                it.tryOnError(Throwable("Not support NEWLAND device."))
            }
            try {
                val pathDemo =
                    "https://gsastorages.blob.core.windows.net/gsa/upload/260/print/viewcheck/638267540499205517.png"
                val bitmap = Glide.with(context).asBitmap().load(pathDemo).submit().get()
                val printer = newlandSdk!!.printerModule
                if (printer.status == PrinterStatus.NORMAL) {
                    val script = StringBuffer()
                    val data = hashMapOf<String, Bitmap>()
                    val bmp0 = "bmp0"
                    val width = 750
                    val height = (bitmap.height / 1.5).roundToInt()
                    data[bmp0] = bitmap
                    script.append("*image c $width*$height path:$bmp0\n")
                    printer.print(script.toString(), data, object : PrintListener {
                        override fun onSuccess() {
                            printer.paperFeed(5)
                            printer.paperCut()
                            printer.cancelStatusListener()
                            it.onComplete()
                        }

                        override fun onError(p0: ErrorCode?, p1: String?) {
                            it.tryOnError(Throwable(p1))
                        }
                    })
                } else {
                    it.tryOnError(Throwable("Init NEWLAND Printer failed."))
                }
            } catch (e: Exception) {
                it.tryOnError(e)
            }
        }
    }
}