package com.smartfinder.nexgo.data

import com.smartfinder.nexgo.data.datasource.PrinterDatasource
import com.smartfinder.nexgo.domain.PrinterRepository

class PrinterRepositoryImpl(
    private val printerDatasource: PrinterDatasource
) : PrinterRepository {
    override fun print(scale: Int, path: String) = printerDatasource.print(scale, path)
}