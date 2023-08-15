package com.smartfinder.newland.data

import com.smartfinder.newland.data.datasource.PrinterDatasource
import com.smartfinder.newland.domain.PrinterRepository

class PrinterRepositoryImpl(
    private val printerDatasource: PrinterDatasource
) : PrinterRepository {
    override fun print(scale: Int, path: String) = printerDatasource.print(scale, path)
}