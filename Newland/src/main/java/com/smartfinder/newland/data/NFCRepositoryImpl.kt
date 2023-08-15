package com.smartfinder.newland.data

import com.smartfinder.newland.data.datasource.NFCDatasource
import com.smartfinder.newland.domain.NFCRepository

class NFCRepositoryImpl(
    private val nfcDatasource: NFCDatasource
) : NFCRepository {
    override fun getData() = nfcDatasource.getData()
}