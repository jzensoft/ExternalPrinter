package com.smartfinder.newland.data.datasourceImpl

import com.newland.sdk.ModuleManage
import com.newland.sdk.module.rfcard.RFCardModule
import com.newland.sdk.module.rfcard.RFCardType
import com.newland.sdk.module.rfcard.RFKeyMode
import com.newland.sdk.utils.ISOUtils
import com.smartfinder.newland.data.datasource.NFCDatasource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter

class NFCDatasourceImpl(
    private val newlandSdk: ModuleManage?
) : NFCDatasource {
    override fun getData(): Single<String> {
        return Single.create {
            try {
                if (newlandSdk == null) {
                    it.tryOnError(Throwable("Not support NEWLAND SDK."))
                }
                val rfCardModule = newlandSdk!!.rfCardModule
                val rfCardType = arrayOf(RFCardType.M1CARD)
                val timeout = 10
                try {
                    val result = rfCardModule.powerOn(rfCardType, timeout, null)
                    if (result.snr == null) {
                        it.tryOnError(Throwable("SN is null"))
                    } else {
                        val id = ISOUtils.hexString(result.snr)
                        it.onSuccess(id)
                    }
                } catch (e: Exception) {
                    it.tryOnError(e)
                }
            } catch (e: Exception) {
                it.tryOnError(e)
            }
        }
    }

    private fun m1Authenticate(
        rfCardModule: RFCardModule,
        sn: ByteArray,
        emitter: SingleEmitter<String>
    ) {
        val block = 0
        val password = ISOUtils.hex2byte("ffffffffffff")
        val isSuccess = rfCardModule.m1Authenticate(RFKeyMode.KEYB_0X01, sn, block, password)
        if (isSuccess) {
            val output = rfCardModule.m1ReadBlockData(block)
            emitter.onSuccess(ISOUtils.hexString(output))
        } else {
            emitter.tryOnError(Throwable("Can't read block data."))
        }
    }
}