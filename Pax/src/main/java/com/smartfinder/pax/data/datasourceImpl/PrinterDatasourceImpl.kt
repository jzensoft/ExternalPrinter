package com.smartfinder.pax.data.datasourceImpl

import android.content.Context
import com.bumptech.glide.Glide
import com.pax.dal.IDAL
import com.pax.dal.exceptions.PrinterDevException
import com.smartfinder.pax.data.datasource.PrinterDatasource
import io.reactivex.rxjava3.core.Completable

class PrinterDatasourceImpl(
    private val context: Context,
    private val dal: IDAL?
) : PrinterDatasource {
    override fun print(scale: Int, path: String): Completable {
        return Completable.create {
            if (dal == null) {
                it.tryOnError(Throwable("Not support build-in printer."))
            }
            try {
                val pathDemo =
                    "https://gsastorages.blob.core.windows.net/gsa/upload/260/print/viewcheck/638267540499205517.png"
                val bitmap = Glide.with(context).asBitmap().load(pathDemo).submit().get()
                val printer = dal!!.printer
                printer.init()
                printer.invert(true)
                printer.printBitmap(bitmap)
                printer.invert(false)
                printer.start()
                var cutMote = printer.cutMode
                when (cutMote) {
                    -1 -> {
                        printer.init()
                        printer.printStr("\n\n\n\n\n\n", "")
                        printer.start()
                    }

                    0, 1, 2 -> {
                        if (cutMote == 2) cutMote = 0
                        printer.cutPaper(cutMote)
                    }
                }
                it.onComplete()
            } catch (e: PrinterDevException) {
                it.tryOnError(Throwable(e.errMsg))
            } catch (e: Exception) {
                it.tryOnError(e)
            }
        }
    }
}