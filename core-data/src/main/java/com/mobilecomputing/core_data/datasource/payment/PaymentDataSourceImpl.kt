package com.mobilecomputing.core_data.datasource.payment

import com.mobilecomputing.core_database.dao.PaymentDao
import com.mobilecomputing.core_database.entity.PaymentEntity
import com.mobilecomputing.core_domain.entity.Payment

class PaymentDataSourceImpl(
    private val paymentDao: PaymentDao
) : PaymentDatasource{
    override suspend fun addPayment(payment: Payment) {
        paymentDao.insertOrUpdate(payment.toEntity())
    }

    private fun Payment.toEntity() = PaymentEntity(
        paymentId = this.paymentId,
        categoryId = this.categoryId,
        title = this.title,
        amount = this.amount,
        date = this.date
    )

    private fun PaymentEntity.fromEntity() = Payment(
        paymentId = this.paymentId,
        categoryId = this.categoryId,
        title = this.title,
        amount = this.amount,
        date = this.date
    )
}