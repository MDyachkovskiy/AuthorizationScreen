package com.test.application.core.domain

data class Payment(
    val id: Int,
    val title: String,
    val amount: Double,
    val created: Int?
)
