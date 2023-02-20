package com.mobilecomputing.core_database.entity

import androidx.room.Index
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "reminders",
    indices = [
        Index("reminderId", unique = true)
    ]
)

data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Long = 0,
    val message: String,
    val location_x: String,
    val location_y: String,
    val reminder_time: String,
    val creation_time: String,
    val creator_id: String,
    val reminder_seen: Boolean,
    val emoji: String,
)
