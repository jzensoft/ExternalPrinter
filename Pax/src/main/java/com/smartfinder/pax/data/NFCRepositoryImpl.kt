package com.smartfinder.pax.data

import com.smartfinder.pax.data.datasource.NFCDatasource
import com.smartfinder.pax.domain.NFCRepository

class NFCRepositoryImpl(
    private val nfcDatasource: NFCDatasource
) : NFCRepository {
    override fun getData() = nfcDatasource.getData()
}