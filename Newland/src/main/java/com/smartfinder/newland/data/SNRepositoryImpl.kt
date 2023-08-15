package com.smartfinder.newland.data

import com.smartfinder.newland.data.datasource.SNDatasource
import com.smartfinder.newland.domain.SNRepository

class SNRepositoryImpl(
    private val snDatasource: SNDatasource
) : SNRepository {
    override fun getData() = snDatasource.getData()
}