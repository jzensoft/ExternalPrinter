package com.smartfinder.nexgo.domain

import io.reactivex.rxjava3.core.Completable

interface PrinterRepository {
    fun print(scale: Int, path: String): Completable
}