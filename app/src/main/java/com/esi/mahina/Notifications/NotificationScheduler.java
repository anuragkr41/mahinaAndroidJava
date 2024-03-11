package com.esi.mahina.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class NotificationScheduler extends BroadcastReceiver {
    private static String title;
    private static String message;
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = intent.getStringExtra("title");
        String notificationText = intent.getStringExtra("message");
        NotificationUtils.showNotification(context, notificationTitle, notificationText);
    }
    public static void scheduleNotification(Context context, LocalDate date, String title, String message) {

        // Set the time for testing purposes (10 seconds from now)
//        long triggerTime = getNextNotificationTime();
        // Create an intent for the BroadcastReceiver

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);

        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonthValue()-1);
        calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());

        long triggerTime = calendar.getTimeInMillis();


        Intent notificationIntent = new Intent(context, NotificationScheduler.class);
        notificationIntent.putExtra("message", message);
        notificationIntent.putExtra("title", title);

        PendingIntent pendingIntent;

        // Check Android version and choose the appropriate flag for PendingIntent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        // Schedule the notification using AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }

    private void initializeTitle(String title, String message) {
        this.title=title;
        this.message=message;

    }

    private static long getNextNotificationTime() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        // Calculate the time after 10 seconds
        LocalDateTime notificationTime = now.plus(3, ChronoUnit.SECONDS);
        // Convert LocalDateTime to milliseconds
        return notificationTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


}
