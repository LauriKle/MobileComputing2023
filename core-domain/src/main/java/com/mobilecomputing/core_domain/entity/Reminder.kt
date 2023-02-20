package com.mobilecomputing.core_domain.entity

import java.time.LocalDateTime

data class Reminder(
    val reminderId: Long = 0,
    val message: String,
    val location_x: String,
    val location_y: String,
    val reminder_time: String,
    val creation_time: String,
    val creator_id: String,
    val reminder_seen: Boolean,
    val emoji: String
)
