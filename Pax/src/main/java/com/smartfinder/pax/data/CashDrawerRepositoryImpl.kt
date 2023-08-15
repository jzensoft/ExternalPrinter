package com.smartfinder.pax.data

import com.smartfinder.pax.data.datasource.CashDrawerDatasource
import com.smartfinder.pax.domain.CashDrawerRepository

class CashDrawerRepositoryImpl(
    private val cashDrawerDatasource: CashDrawerDatasource
) : CashDrawerRepository {
    override fun open() = cashDrawerDatasource.open()
}