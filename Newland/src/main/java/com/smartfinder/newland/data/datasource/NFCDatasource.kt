package com.smartfinder.newland.data.datasource

import io.reactivex.rxjava3.core.Single

interface NFCDatasource {
    fun getData(): Single<String>
}