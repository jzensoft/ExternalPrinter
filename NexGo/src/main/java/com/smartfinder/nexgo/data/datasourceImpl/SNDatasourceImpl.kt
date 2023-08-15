package com.smartfinder.nexgo.data.datasourceImpl

import com.nexgo.oaf.apiv3.DeviceEngine
import com.smartfinder.nexgo.data.datasource.SNDatasource

class SNDatasourceImpl(
    private val deviceEngine: DeviceEngine?
) : SNDatasource {
    override fun getData(): String {
        if (deviceEngine == null) return ""
        try {
            return deviceEngine.deviceInfo.sn ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}