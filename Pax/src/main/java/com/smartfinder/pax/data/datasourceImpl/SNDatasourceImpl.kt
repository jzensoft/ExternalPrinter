package com.smartfinder.pax.data.datasourceImpl

import com.pax.dal.IDAL
import com.pax.dal.entity.ETermInfoKey
import com.smartfinder.pax.data.datasource.SNDatasource

class SNDatasourceImpl(
    private val dal: IDAL?
) : SNDatasource {
    override fun getData(): String {
        if (dal == null) return ""
        return try {
            dal.sys.termInfo[ETermInfoKey.SN] ?: ""
        } catch (e: NoClassDefFoundError) {
            e.message ?: ""
        } catch (e: Exception) {
            e.message ?: ""
        }
    }
}