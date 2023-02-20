package com.mobilecomputing.core_database.dao

import androidx.room.*
import com.mobilecomputing.core_database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE reminderId LIKE :reminderId")
    fun findOne(reminderId: Long): Flow<ReminderEntity>

    @Query("SELECT * from reminders")
    suspend fun findAllReminders(): List<ReminderEntity>

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)
}