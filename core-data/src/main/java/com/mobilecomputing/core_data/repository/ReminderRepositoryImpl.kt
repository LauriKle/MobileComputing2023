package com.mobilecomputing.core_data.repository

import com.mobilecomputing.core_data.datasource.reminder.ReminderDatasource
import com.mobilecomputing.core_domain.entity.Reminder
import com.mobilecomputing.core_domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDataSource: ReminderDatasource
) : ReminderRepository{
    override suspend fun addReminder(reminder: Reminder) {
        reminderDataSource.addReminder(reminder)
    }
    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDataSource.deleteReminder(reminder)
    }
    override suspend fun loadReminders(): Flow<List<Reminder>> {
        println("PASKASAATANA")
        reminderDataSource.loadReminders().collect {
            println("Contents of the list: $it")
        }
        return reminderDataSource.loadReminders()
    }

}