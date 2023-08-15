package com.smartfinder.nexgo.data

import com.smartfinder.nexgo.data.datasource.SNDatasource
import com.smartfinder.nexgo.domain.SNRepository

class SNRepositoryImpl(
    private val snDatasource: SNDatasource
) : SNRepository {
    override fun getData() = snDatasource.getData()
}