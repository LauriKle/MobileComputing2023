package com.example.mobilecomputing.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.google.android.gms.location.LocationListener
import com.mobilecomputing.core_domain.entity.Reminder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class NotificationWorker(
    context: Context,
    params: WorkerParameters,
) : Worker(context, params) {

    private lateinit var locationManager: LocationManager
    private lateinit var reminderLocation: Location

    override fun doWork(): Result {
        val reminderId = inputData.getLong("reminderId", -1L)
        val reminderMessage = inputData.getString("reminderMessage")
        val reminderTime = inputData.getString("reminderTime")
        val reminderEmoji = inputData.getString("reminderEmoji")
        val lng = inputData.getString("reminderX")
        val lat = inputData.getString("reminderY")
        if (reminderId != null && reminderMessage != null && reminderTime != null && reminderEmoji != null) {
            createNotification(applicationContext, reminderMessage, reminderTime, reminderEmoji)
        }



        return Result.success()
    }
}

fun createWork(context: Context, reminder: Reminder) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val reminderTime = dateFormat.parse(reminder.reminder_time) ?: return
    val reminderCalendar = Calendar.getInstance().apply {
        time = reminderTime
    }
    val initialDelay = (reminderCalendar.timeInMillis - Calendar.getInstance().timeInMillis)
    println("Reminder Time: " + reminderTime + "initial DELAY: " + initialDelay)
    val reminderWorkerRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInputData(
            Data.Builder()
                .putLong("reminderId", reminder.reminderId)
                .putString("reminderMessage", reminder.message)
                .putString("reminderTime", reminder.reminder_time)
                .putString("reminderEmoji", reminder.emoji)
                .putString("reminderX", reminder.location_x)
                .putString("reminderY", reminder.location_y)
                .build()
        )
        .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
        .build()
    WorkManager.getInstance(context).enqueue(reminderWorkerRequest)
}

fun createNotification(context: Context, reminderMessage: String, reminderTime: String, emoji: String) {
    val importance = NotificationManager.IMPORTANCE_HIGH
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channel = NotificationChannel("channel_id", "channelName", importance).apply { }

    notificationManager.createNotificationChannel(channel)
    val notificationId = 1
    val builder = NotificationCompat.Builder(context, "channel_id")
        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
        .setContentTitle(reminderMessage)
        .setContentText(reminderTime)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setLargeIcon(getEmojiBitmap(context, emoji))
        .setStyle(NotificationCompat.BigTextStyle().bigText(reminderMessage).setBigContentTitle(reminderTime))

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}


fun getEmojiBitmap(context: Context, emoji: String): Bitmap {
    val drawable = ContextCompat.getDrawable(context, android.R.drawable.sym_def_app_icon)?.let {
            BitmapDrawable(context.resources, Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888))
    } ?: throw IllegalArgumentException("")
    val bitmap = drawable.bitmap
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bounds = Rect(0, 0, bitmap.width, bitmap.height)
    paint.textSize = 96F
    paint.color = ContextCompat.getColor(context, android.R.color.white)
    val textBounds = Rect()
    paint.getTextBounds(emoji, 0, 1, textBounds)
    canvas.drawText(emoji, bounds.centerX() - textBounds.width() / 2f, bounds.centerY() + textBounds.height() / 2f, paint)
    return bitmap
}


