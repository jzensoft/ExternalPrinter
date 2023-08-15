package com.smartfinder.printermanager.domain

import android.content.Context
import androidx.core.util.Pair
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ExternalPrinterRepository {
    fun print(
        context: Context,
        printerBrand: String,
        ipAddress: String,
        model: String,
        series: String,
        scale: Int,
        path: String,
        port: Int,
        isRedColor: Boolean
    ): Completable
}