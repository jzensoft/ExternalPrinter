package com.smartfinder.nexgo.data.datasourceImpl

import androidx.core.util.Pair
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.SdkResult
import com.nexgo.oaf.apiv3.device.printer.AlignEnum
import com.smartfinder.nexgo.data.datasource.PrinterDatasource
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Completable

class PrinterDatasourceImpl(
    private val deviceEngine: DeviceEngine?
) : PrinterDatasource {
    override fun print(scale: Int, path: String): Completable {
        return Completable.create {
            if (deviceEngine == null) {
                it.tryOnError(Throwable("Not support NEXGO SDK."))
            }
            try {
                val printer = deviceEngine!!.printer
                printer.initPrinter()
                when (printer.status) {
                    SdkResult.Success -> {
                        val pathDemo =
                            "https://gsastorages.blob.core.windows.net/gsa/upload/260/print/viewcheck/638267540499205517.png"
                        val bitmap = Picasso.get().load(pathDemo).get()
                        printer.appendImage(bitmap, AlignEnum.CENTER)
                        printer.startPrint(true) { result ->
                            when (result) {
                                SdkResult.Success -> {
                                    printer.cutPaper()
                                    it.onComplete()
                                }

                                SdkResult.Printer_Print_Fail -> it.tryOnError(Throwable("Printer Failed"))
                                SdkResult.Printer_Busy -> it.tryOnError(Throwable("Printer is Busy"))
                                SdkResult.Printer_PaperLack -> it.tryOnError(Throwable("Printer is out of paper"))
                                SdkResult.Printer_Fault -> it.tryOnError(Throwable("Printer fault"))
                                SdkResult.Printer_TooHot -> it.tryOnError(Throwable("Printer temperature is too hot"))
                                SdkResult.Printer_UnFinished -> it.tryOnError(Throwable("Printer job is unfinished"))
                                SdkResult.Printer_Other_Error -> it.tryOnError(Throwable("Printer Other Error $result"))
                                else -> it.tryOnError(Throwable("Generic Fail Error"))
                            }
                        }
                    }

                    else -> {
                        it.tryOnError(Throwable("Initial printer failed ${printer.status}"))
                    }
                }
            } catch (e: Exception) {
                it.tryOnError(e)
            }
        }
    }
}