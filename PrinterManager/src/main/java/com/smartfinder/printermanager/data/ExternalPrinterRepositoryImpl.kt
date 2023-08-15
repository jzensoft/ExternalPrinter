package com.smartfinder.printermanager.data

import android.content.Context
import com.smartfinder.printermanager.data.datasource.ESCPOSDatasource
import com.smartfinder.printermanager.data.datasource.EpsonCommandDatasource
import com.smartfinder.printermanager.data.datasource.EpsonDatasource
import com.smartfinder.printermanager.data.datasource.StarDatasource
import com.smartfinder.printermanager.domain.ExternalPrinterRepository
import io.reactivex.rxjava3.core.Completable

class ExternalPrinterRepositoryImpl(
    private val epsonCommandDatasource: EpsonCommandDatasource,
    private val epsonDatasource: EpsonDatasource,
    private val escposDatasource: ESCPOSDatasource,
    private val starDatasource: StarDatasource
) : ExternalPrinterRepository {
    override fun print(
        context: Context,
        printerBrand: String,
        ipAddress: String,
        model: String,
        series: String,
        scale: Int,
        path: String,
        port: Int,
        isRedColor: Boolean
    ): Completable {
        return when (printerBrand.uppercase()) {
            "EPSON" -> epsonDatasource.print(context, ipAddress, model, series, scale, path)
//            "EPSON" -> epsonCommandDatasource.print(context, ipAddress, model, series, scale, path)
            "STAR" -> starDatasource.print(context, ipAddress, scale, path, isRedColor)
            else -> escposDatasource.print(context, ipAddress, port, scale, path)
        }
    }
}