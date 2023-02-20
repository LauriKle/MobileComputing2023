package com.mobilecomputing.core_domain.repository

import com.mobilecomputing.core_domain.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    suspend fun addReminder(reminder: Reminder)
    suspend fun deleteReminder(reminder: Reminder)
    suspend fun loadReminders(): Flow<List<Reminder>>
}