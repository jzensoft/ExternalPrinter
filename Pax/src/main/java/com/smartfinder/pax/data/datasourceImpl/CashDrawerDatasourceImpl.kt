package com.smartfinder.pax.data.datasourceImpl

import com.pax.dal.IDAL
import com.pax.dal.exceptions.PrinterDevException
import com.smartfinder.pax.data.datasource.CashDrawerDatasource

class CashDrawerDatasourceImpl(
    private val dal: IDAL?
) : CashDrawerDatasource {
    override fun open(): Boolean {
        if (dal == null) return false
        try {
            val cashDrawer = dal.cashDrawer
            cashDrawer.open()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}