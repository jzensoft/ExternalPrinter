package com.smartfinder.pax.domain

interface CashDrawerRepository {
    fun open(): Boolean
}