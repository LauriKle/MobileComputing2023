package com.mobilecomputing.core_domain.entity

import java.time.LocalDateTime

data class Payment(
    val paymentId: Long = 0,
    val title: String,
    val categoryId: Long,
    val amount: Double,
    val date: LocalDateTime
)
