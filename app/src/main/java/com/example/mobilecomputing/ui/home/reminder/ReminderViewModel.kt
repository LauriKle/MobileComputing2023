package com.example.mobilecomputing.ui.home.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecomputing.core_domain.entity.Reminder
import com.mobilecomputing.core_domain.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderRepository : ReminderRepository
): ViewModel(){

    private val _viewState = MutableStateFlow<ReminderViewState>(ReminderViewState.Loading)
    val uiState: StateFlow<ReminderViewState> = _viewState


    fun saveReminder(reminder: Reminder){
        viewModelScope.launch {
            reminderRepository.addReminder(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminder)
        }
    }

    fun loadReminders() {
        viewModelScope.launch {
            reminderRepository.loadReminders().collect {
                _viewState.value = ReminderViewState.Success(it)
                println("viewstate" + _viewState.value)
            }
        }
    }

}