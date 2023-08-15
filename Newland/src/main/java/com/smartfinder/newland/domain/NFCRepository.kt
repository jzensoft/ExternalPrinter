package com.smartfinder.newland.domain

import io.reactivex.rxjava3.core.Single

interface NFCRepository {
    fun getData(): Single<String>
}