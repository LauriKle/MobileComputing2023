package com.mobilecomputing.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobilecomputing.core_database.dao.PaymentDao
import com.mobilecomputing.core_database.entity.PaymentEntity

@Database(
    entities = [PaymentEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun paymentDao(): PaymentDao
}