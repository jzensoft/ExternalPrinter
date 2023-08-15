package com.smartfinder.newland

import android.content.Context
import com.newland.sdk.ModuleManage
import com.smartfinder.newland.data.NFCRepositoryImpl
import com.smartfinder.newland.data.PrinterRepositoryImpl
import com.smartfinder.newland.data.SNRepositoryImpl
import com.smartfinder.newland.data.datasourceImpl.NFCDatasourceImpl
import com.smartfinder.newland.data.datasourceImpl.PrinterDatasourceImpl
import com.smartfinder.newland.data.datasourceImpl.SNDatasourceImpl

class NewlandSDKManager {
    private lateinit var context: Context
    private var newlandSdk: ModuleManage? = null

    companion object {
        val instance: NewlandSDKManager by lazy {
            NewlandSDKManager()
        }
    }

    fun init(context: Context, isDebugMode: Boolean) {
        this.context = context
        initSDK(isDebugMode)
    }

    private fun initSDK(isDebugMode: Boolean) {
        try {
            val moduleManage = ModuleManage.getInstance()
            moduleManage.setDebugMode(isDebugMode)
            val isConnected = moduleManage.init(context)
            if (isConnected) {
                newlandSdk = moduleManage
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSN() = SNRepositoryImpl(SNDatasourceImpl(newlandSdk)).getData()
    fun getNFC() = NFCRepositoryImpl(NFCDatasourceImpl(newlandSdk)).getData()
    fun print(scale: Int, path: String) =
        PrinterRepositoryImpl(PrinterDatasourceImpl(newlandSdk)).print(scale, path)
}