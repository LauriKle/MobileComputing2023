package com.mobilecomputing.core_data.datasource.payment

import com.mobilecomputing.core_domain.entity.Payment


interface PaymentDatasource {
    suspend fun addPayment(payment: Payment)
}