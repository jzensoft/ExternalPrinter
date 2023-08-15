package com.smartfinder.newland.data.datasourceImpl

import com.newland.sdk.ModuleManage
import com.smartfinder.newland.data.datasource.SNDatasource

class SNDatasourceImpl(
    private val newlandSdk: ModuleManage?
) : SNDatasource {
    override fun getData(): String {
        if (newlandSdk == null) return ""
        try {
            return newlandSdk.deviceBasicModule.deviceInfo.sn ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}