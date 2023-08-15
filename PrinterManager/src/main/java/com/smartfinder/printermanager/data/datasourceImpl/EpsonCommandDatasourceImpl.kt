package com.smartfinder.printermanager.data.datasourceImpl

import android.content.Context
import com.epson.eposprint.Builder
import com.epson.eposprint.EposException
import com.epson.eposprint.Print
import com.smartfinder.printermanager.data.datasource.EpsonCommandDatasource
import io.reactivex.rxjava3.core.Completable

class EpsonCommandDatasourceImpl : EpsonCommandDatasource {

    override fun print(
        context: Context,
        ipAddress: String,
        model: String,
        series: String,
        scale: Int,
        path: String
    ): Completable {
        var printer: Print?
        val status = IntArray(1) { 0 }
        val battery = IntArray(1) { 0 }

        return Completable.create {
            try {
                printer = Print(context)
                printer?.openPrinter(
                    Print.DEVTYPE_TCP,
                    ipAddress,
                    Print.TRUE,
                    Print.PARAM_DEFAULT,
                    Print.PARAM_DEFAULT
                )
                printer?.getStatus(status, battery)
                if (status[0] == Print.ST_NO_RESPONSE) {

                }
                if (status[0] == Print.ST_OFF_LINE) {

                }
                if ((battery[0] == 0xFF) && (battery[0] == 0x36)) {

                }
                val builder = Builder("TM-T88V", Builder.MODEL_CHINESE)
                builder.addText("Hi")
                printer?.sendData(builder, 10000, status, battery)
                printer?.closePrinter()
            } catch (e: EposException) {
                it.onError(e)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}