package com.example.mobilecomputing.ui.home.reminder

import com.mobilecomputing.core_domain.entity.Reminder

sealed interface ReminderViewState {
    object Loading: ReminderViewState
    data class Success(
        val data: List<Reminder>
    ): ReminderViewState
}