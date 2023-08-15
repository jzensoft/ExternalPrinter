package com.smartfinder.printermanager.data.datasource

import android.content.Context
import androidx.core.util.Pair
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface EpsonDatasource {
    fun print(
        context: Context,
        ipAddress: String,
        model: String,
        series: String,
        scale: Int,
        path: String
    ): Completable
}