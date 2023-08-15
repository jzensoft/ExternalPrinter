package com.smartfinder.pax.data

import com.smartfinder.pax.data.datasource.PrinterDatasource
import com.smartfinder.pax.domain.PrinterRepository

class PrinterRepositoryImpl(
    private val printerDatasource: PrinterDatasource
) : PrinterRepository {
    override fun print(scale: Int, path: String) = printerDatasource.print(scale, path)
}