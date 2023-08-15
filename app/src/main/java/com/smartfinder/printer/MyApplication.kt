package com.smartfinder.printer

import android.app.Application
import android.util.Log
import com.smartfinder.printermanager.PrinterManager
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class MyApplication : Application() {

    private val TAG = MyApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        PrinterManager.instance.init(this)
//        PaxSDKManager.instance.init(this)
//        NewlandSDKManager.instance.init(this, true)
//        NEXGOSdkManager.instance.init(this)

        RxJavaPlugins.setErrorHandler { throwable: Throwable ->
            if (throwable is UndeliverableException) {
                Log.e(TAG, throwable.message ?: "")
            }
        }
    }
}