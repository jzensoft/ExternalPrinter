package com.smartfinder.pax.data.datasource

import io.reactivex.rxjava3.core.Completable

interface PrinterDatasource {
    fun print(scale: Int, path: String): Completable
}