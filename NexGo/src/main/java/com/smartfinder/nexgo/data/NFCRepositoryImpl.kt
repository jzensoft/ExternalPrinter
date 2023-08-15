package com.smartfinder.nexgo.data

import com.smartfinder.nexgo.data.datasource.NFCDatasource
import com.smartfinder.nexgo.domain.NFCRepository

class NFCRepositoryImpl(
    private val nfcDatasource: NFCDatasource
) : NFCRepository {
    override fun getData() = nfcDatasource.getData()
}