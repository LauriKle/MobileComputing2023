package com.mobilecomputing.core_domain.repository

import com.mobilecomputing.core_domain.entity.Payment

interface PaymentRepository {
    suspend fun addPayment(payment: Payment)
}