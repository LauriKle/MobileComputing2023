package com.mobilecomputing.core_data

import com.mobilecomputing.core_data.datasource.reminder.ReminderDataSourceImpl
import com.mobilecomputing.core_data.datasource.reminder.ReminderDatasource
import com.mobilecomputing.core_data.repository.ReminderRepositoryImpl
import com.mobilecomputing.core_domain.repository.ReminderRepository
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindReminderDataSource(
        reminderDataSource: ReminderDataSourceImpl
    ): ReminderDatasource

    @Singleton
    @Binds
    fun bindReminderRepository(
        reminderRepository: ReminderRepositoryImpl
    ): ReminderRepository

}