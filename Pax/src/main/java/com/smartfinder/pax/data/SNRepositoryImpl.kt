package com.smartfinder.pax.data

import com.smartfinder.pax.data.datasource.SNDatasource
import com.smartfinder.pax.domain.SNRepository

class SNRepositoryImpl(
    private val snDatasource: SNDatasource
) : SNRepository {
    override fun getData() = snDatasource.getData()
}