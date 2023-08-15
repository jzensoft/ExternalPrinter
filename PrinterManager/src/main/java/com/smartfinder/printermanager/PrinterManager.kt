package com.smartfinder.printermanager

import android.content.Context
import com.smartfinder.printermanager.data.ExternalPrinterRepositoryImpl
import com.smartfinder.printermanager.data.datasource.EpsonCommandDatasource
import com.smartfinder.printermanager.data.datasourceImpl.ESCPOSDatasourceImpl
import com.smartfinder.printermanager.data.datasourceImpl.EpsonCommandDatasourceImpl
import com.smartfinder.printermanager.data.datasourceImpl.EpsonDatasourceImpl
import com.smartfinder.printermanager.data.datasourceImpl.StarDatasourceImpl

class PrinterManager {
    private lateinit var application: Context

    companion object {
        val instance: PrinterManager by lazy {
            PrinterManager()
        }
    }

    fun init(context: Context) {
        application = context
    }

    fun print(
        application: Context,
        printerBrand: String,
        ipAddress: String,
        model: String,
        series: String,
        scale: Int,
        path: String,
        port: Int,
        isRedColor: Boolean
    ) = ExternalPrinterRepositoryImpl(
        EpsonCommandDatasourceImpl(),
        EpsonDatasourceImpl(),
        ESCPOSDatasourceImpl(),
        StarDatasourceImpl()
    ).print(
        application,
        printerBrand,
        ipAddress,
        model,
        series,
        scale,
        path,
        port,
        isRedColor
    )
}