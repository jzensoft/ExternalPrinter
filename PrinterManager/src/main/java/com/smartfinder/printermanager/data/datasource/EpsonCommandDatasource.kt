package com.smartfinder.printermanager.data.datasource

import android.content.Context
import io.reactivex.rxjava3.core.Completable

interface EpsonCommandDatasource {
    fun print(
        context: Context,
        ipAddress: String,
        model: String,
        series: String,
        scale: Int,
        path: String
    ): Completable
}