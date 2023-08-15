package com.smartfinder.pax.data.datasourceImpl

import com.pax.dal.IDAL
import com.pax.dal.entity.EBeepMode
import com.pax.dal.entity.EDetectMode
import com.pax.dal.entity.EPiccType
import com.pax.dal.exceptions.PiccDevException
import com.smartfinder.pax.data.datasource.NFCDatasource
import com.smartfinder.pax.utils.Convert
import io.reactivex.rxjava3.core.Single

class NFCDatasourceImpl(
    private val dal: IDAL?
) : NFCDatasource {
    override fun getData(): Single<String> {
        return Single.create {
            if (dal == null) {
                it.tryOnError(Throwable("Not support build-in NFC"))
            }
            try {
                var data = ""
                val picc = dal!!.getPicc(EPiccType.INTERNAL)
                picc.close()
                picc.open()
                while (data.isEmpty()) {
                    val cardInfo = picc.detect(EDetectMode.ONLY_M)
                    if (cardInfo != null) {
                        data = Convert.getInstance().bcdToStr(cardInfo.serialInfo)
                    }
                }
                dal.sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100)
                picc.close()
                it.onSuccess(data)
            } catch (e: PiccDevException) {
                dal?.sys?.beep(EBeepMode.FREQUENCE_LEVEL_6, 200)
                it.tryOnError(e)
            } catch (e: Exception) {
                dal?.sys?.beep(EBeepMode.FREQUENCE_LEVEL_6, 200)
                it.tryOnError(e)
            }
        }
    }
}