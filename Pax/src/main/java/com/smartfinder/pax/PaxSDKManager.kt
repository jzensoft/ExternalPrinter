package com.smartfinder.pax

import android.content.Context
import com.pax.dal.IDAL
import com.pax.neptunelite.api.NeptuneLiteUser
import com.smartfinder.pax.data.CashDrawerRepositoryImpl
import com.smartfinder.pax.data.NFCRepositoryImpl
import com.smartfinder.pax.data.PrinterRepositoryImpl
import com.smartfinder.pax.data.SNRepositoryImpl
import com.smartfinder.pax.data.datasourceImpl.CashDrawerDatasourceImpl
import com.smartfinder.pax.data.datasourceImpl.NFCDatasourceImpl
import com.smartfinder.pax.data.datasourceImpl.PrinterDatasourceImpl
import com.smartfinder.pax.data.datasourceImpl.SNDatasourceImpl

class PaxSDKManager {
    private lateinit var context: Context

    companion object {
        val instance: PaxSDKManager by lazy {
            PaxSDKManager()
        }
    }

    fun init(context: Context) {
        this.context = context
    }

    private fun getDal(): IDAL? {
        try {
            return NeptuneLiteUser.getInstance().getDal(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getSN() = SNRepositoryImpl(SNDatasourceImpl(getDal())).getData()
    fun openCashDrawer() = CashDrawerRepositoryImpl(CashDrawerDatasourceImpl(getDal())).open()
    fun getNFCData() = NFCRepositoryImpl(NFCDatasourceImpl(getDal())).getData()
    fun print(scale: Int, path: String) =
        PrinterRepositoryImpl(PrinterDatasourceImpl(context, getDal())).print(scale, path)
}