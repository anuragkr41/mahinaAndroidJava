package com.esi.mahina.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.esi.mahina.data.Constants;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * BroadcastReceiver for scheduling and receiving notification alarms.
 * Handles scheduling notifications for USG and vaccination reminders.
 */
public class NotificationScheduler extends BroadcastReceiver {

    private static final String TAG = "NotificationScheduler";

    private static final String EXTRA_TITLE = "notification_title";
    private static final String EXTRA_MESSAGE = "notification_message";
    private static final String EXTRA_CHANNEL_ID = "notification_channel_id";

    /**
     * Schedule a notification for a specific date.
     * The notification will be delivered at the configured notification time (default 9 AM).
     *
     * @param context   The context
     * @param date      The date to schedule the notification for
     * @param title     Notification title
     * @param message   Notification message
     * @param channelId The notification channel ID (USG or Vaccination)
     */
    public static void scheduleNotification(Context context, LocalDate date, String title,
                                            String message, String channelId) {
        // Set the notification time (default 9 AM on the given date)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, Constants.NOTIFICATION_HOUR);
        calendar.set(Calendar.MINUTE, Constants.NOTIFICATION_MINUTE);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long triggerTime = calendar.getTimeInMillis();

        // Don't schedule notifications in the past
        if (triggerTime <= System.currentTimeMillis()) {
            Log.d(TAG, "Skipping notification in the past: " + title + " for " + date);
            return;
        }

        // Create intent with notification data
        Intent notificationIntent = new Intent(context, NotificationScheduler.class);
        notificationIntent.putExtra(EXTRA_TITLE, title);
        notificationIntent.putExtra(EXTRA_MESSAGE, message);
        notificationIntent.putExtra(EXTRA_CHANNEL_ID, channelId);

        // Generate unique request code from date and title
        int requestCode = generateRequestCode(date, title);

        // Create PendingIntent with appropriate flags
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, notificationIntent, flags);

        // Schedule the alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                }
                Log.d(TAG, "Scheduled notification: " + title + " for " + date +
                        " (requestCode: " + requestCode + ")");
            } catch (SecurityException e) {
                // Fallback if exact alarm permission not granted
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                Log.w(TAG, "Using inexact alarm due to permission: " + e.getMessage());
            }
        }
    }

    /**
     * Schedule a notification using the default channel.
     */
    public static void scheduleNotification(Context context, LocalDate date,
                                            String title, String message) {
        scheduleNotification(context, date, title, message, "default");
    }

    /**
     * Cancel a scheduled notification.
     *
     * @param context The context
     * @param date    The date of the notification
     * @param title   The title of the notification
     */
    public static void cancelNotification(Context context, LocalDate date, String title) {
        Intent notificationIntent = new Intent(context, NotificationScheduler.class);
        int requestCode = generateRequestCode(date, title);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, notificationIntent, flags);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.d(TAG, "Cancelled notification: " + title + " for " + date);
        }
    }

    /**
     * Generate a unique request code from date and title.
     * This ensures each notification has a unique PendingIntent.
     */
    private static int generateRequestCode(LocalDate date, String title) {
        String combined = date.toString() + (title != null ? title : "");
        return combined.hashCode();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(EXTRA_TITLE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String channelId = intent.getStringExtra(EXTRA_CHANNEL_ID);

        Log.d(TAG, "Received notification alarm: " + title);

        // Default to the default channel if not specified
        if (channelId == null || channelId.isEmpty()) {
            channelId = "default";
        }

        // Show the notification
        NotificationUtils.showNotification(context, title, message, channelId);
    }
}
