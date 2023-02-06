package com.mobilecomputing.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobilecomputing.core_database.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(payment: PaymentEntity)

    @Query("SELECT * FROM payments WHERE paymentId LIKE :paymentId")
    fun findOne(paymentId: Long): Flow<PaymentEntity>

    suspend fun delete(payment: PaymentEntity)
}