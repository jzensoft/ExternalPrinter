package com.smartfinder.pax.data.datasourceImpl

import android.content.Context
import androidx.core.util.Pair
import com.pax.dal.IDAL
import com.pax.dal.exceptions.PrinterDevException
import com.smartfinder.pax.data.datasource.PrinterDatasource
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

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
                val bitmap = Picasso.get().load(pathDemo).get()
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