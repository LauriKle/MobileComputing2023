package com.mobilecomputing.core_database.entity

import androidx.room.Index
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "payments",
    indices = [
        Index("paymentId", unique = true)
    ]
)

data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Long = 0,
    val title: String,
    val categoryId: Long,
    val amount: Double,
    val date: LocalDateTime
)
