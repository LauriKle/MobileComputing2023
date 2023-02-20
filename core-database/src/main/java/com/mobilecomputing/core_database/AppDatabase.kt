package com.mobilecomputing.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobilecomputing.core_database.dao.ReminderDao
import com.mobilecomputing.core_database.entity.ReminderEntity

@Database(
    entities = [ReminderEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun reminderDao(): ReminderDao
}