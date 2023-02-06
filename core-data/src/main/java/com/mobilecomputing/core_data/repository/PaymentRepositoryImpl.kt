package com.mobilecomputing.core_data.repository

import com.mobilecomputing.core_data.datasource.payment.PaymentDatasource
import com.mobilecomputing.core_domain.entity.Payment
import com.mobilecomputing.core_domain.repository.PaymentRepository

class PaymentRepositoryImpl(
    private val paymentDataSource: PaymentDatasource
) : PaymentRepository{
    override suspend fun addPayment(payment: Payment) {
        paymentDataSource.addPayment(payment)
    }

}