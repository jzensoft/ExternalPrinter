package com.smartfinder.nexgo

import android.content.Context
import com.nexgo.oaf.apiv3.APIProxy
import com.nexgo.oaf.apiv3.DeviceEngine
import com.smartfinder.nexgo.data.NFCRepositoryImpl
import com.smartfinder.nexgo.data.PrinterRepositoryImpl
import com.smartfinder.nexgo.data.SNRepositoryImpl
import com.smartfinder.nexgo.data.datasourceImpl.NFCDatasourceImpl
import com.smartfinder.nexgo.data.datasourceImpl.PrinterDatasourceImpl
import com.smartfinder.nexgo.data.datasourceImpl.SNDatasourceImpl

class NEXGOSdkManager {
    private lateinit var context: Context
    private var nexgoSdk: DeviceEngine? = null

    companion object {
        val instance: NEXGOSdkManager by lazy {
            NEXGOSdkManager()
        }
    }

    fun init(context: Context) {
        this.context = context
        initSDK()
    }

    private fun initSDK() {
        try {
            nexgoSdk = APIProxy.getDeviceEngine(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSN() = SNRepositoryImpl(SNDatasourceImpl(nexgoSdk)).getData()
    fun getNFC() = NFCRepositoryImpl(NFCDatasourceImpl(nexgoSdk)).getData()
    fun print(scale: Int, path: String) =
        PrinterRepositoryImpl(PrinterDatasourceImpl(nexgoSdk)).print(scale, path)
}