package com.mobilecomputing.core_data.datasource.reminder

import com.mobilecomputing.core_database.dao.ReminderDao
import com.mobilecomputing.core_database.entity.ReminderEntity
import com.mobilecomputing.core_domain.entity.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReminderDataSourceImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderDatasource{
    override suspend fun addReminder(reminder: Reminder) {
        println("jjooo")
        reminderDao.insertOrUpdate(reminder.toEntity())
    }
    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder.toEntity())
    }
    override suspend fun loadReminders(): Flow<List<Reminder>> = flow {
        println("datasourceimpl load ")
        emit(
            reminderDao.findAllReminders().map {
                it.fromEntity()
            }
        )
    }

    private fun Reminder.toEntity() = ReminderEntity(
        reminderId = this.reminderId,
        message = this.message,
        location_x = this.location_x,
        location_y = this.location_y,
        reminder_time = this.reminder_time,
        creation_time = this.creation_time,
        creator_id = this.creator_id,
        reminder_seen = this.reminder_seen,
        emoji = this.emoji
    )

    private fun ReminderEntity.fromEntity() = Reminder(
        reminderId = this.reminderId,
        message = this.message,
        location_x = this.location_x,
        location_y = this.location_y,
        reminder_time = this.reminder_time,
        creation_time = this.creation_time,
        creator_id = this.creator_id,
        reminder_seen = this.reminder_seen,
        emoji = this.emoji
    )

}