package com.smartfinder.printermanager.data.datasource

import android.content.Context
import androidx.core.util.Pair
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ESCPOSDatasource {
    fun print(
        context: Context,
        ipAddress: String,
        port: Int,
        scale: Int,
        path: String
    ): Completable
}