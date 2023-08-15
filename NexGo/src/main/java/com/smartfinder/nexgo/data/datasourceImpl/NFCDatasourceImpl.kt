package com.smartfinder.nexgo.data.datasourceImpl

import android.util.Log
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum
import com.nexgo.oaf.apiv3.device.reader.RfCardTypeEnum
import com.smartfinder.nexgo.data.datasource.NFCDatasource
import io.reactivex.rxjava3.core.Single

class NFCDatasourceImpl(
    private val deviceEngine: DeviceEngine?
) : NFCDatasource {

    private val TAG = NFCDatasourceImpl::class.java.simpleName

    override fun getData(): Single<String> {
        return Single.create {
            if (deviceEngine == null) {
                it.tryOnError(Throwable("Not support NEXGO SDk."))
            }
            try {
                val cardReader = deviceEngine!!.cardReader
                //cardReader.stopSearch()
                cardReader.close(CardSlotTypeEnum.RF)
                cardReader.open(CardSlotTypeEnum.RF)
                var rfCardType: RfCardTypeEnum?
                val startTime: Long = System.currentTimeMillis()
                var endTime: Long?
                while (true) {
                    rfCardType = cardReader.getRfCardType(CardSlotTypeEnum.RF)
                    if (rfCardType != null) {
                        Log.d(TAG, "Find card....")
                        break
                    }
                    endTime = System.currentTimeMillis()
                    if ((endTime - startTime) > (10 * 1000)) {
                        cardReader.close(CardSlotTypeEnum.RF)
                        Log.wtf(TAG, "Search card timeout")
                        it.tryOnError(Throwable("Search card timeout"))
                        break
                    }
                }
                val m1CardHandler = deviceEngine.m1CardHandler
                val id = m1CardHandler.readUid()
                cardReader.stopSearch()
                cardReader.close(CardSlotTypeEnum.RF)
                deviceEngine.beeper.beep(10)
                it.onSuccess(id)
            } catch (e: Exception) {
                it.tryOnError(e)
            }
        }
    }
}