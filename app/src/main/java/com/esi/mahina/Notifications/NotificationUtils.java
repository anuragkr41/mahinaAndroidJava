package com.esi.mahina.Notifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.esi.mahina.R;
import com.esi.mahina.data.Constants;

/**
 * Utility class for showing notifications.
 * Handles notification creation and channel management.
 */
public class NotificationUtils {

    private static final String TAG = "NotificationUtils";
    private static final String CHANNEL_ID = "default";

    /**
     * Show a notification with the given title and message.
     * Uses a unique notification ID based on content to allow multiple notifications.
     *
     * @param context The context
     * @param title   Notification title
     * @param message Notification message
     */
    public static void showNotification(Context context, String title, String message) {
        showNotification(context, title, message, CHANNEL_ID);
    }

    /**
     * Show a notification with the given title, message, and channel.
     *
     * @param context   The context
     * @param title     Notification title
     * @param message   Notification message
     * @param channelId The notification channel ID
     */
    public static void showNotification(Context context, String title, String message, String channelId) {
        // Ensure notification channel exists
        createNotificationChannel(context, channelId);

        // Check permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "POST_NOTIFICATIONS permission not granted, skipping notification");
                return;
            }
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.mahinalogo)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Generate unique notification ID from content
        int notificationId = generateNotificationId(title, message);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        try {
            notificationManager.notify(notificationId, builder.build());
            Log.d(TAG, "Notification shown: " + title + " (ID: " + notificationId + ")");
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException showing notification: " + e.getMessage());
        }
    }

    /**
     * Show a USG reminder notification.
     */
    public static void showUSGReminder(Context context, String title, String message) {
        showNotification(context, title, message, Constants.CHANNEL_USG_REMINDERS);
    }

    /**
     * Show a vaccination reminder notification.
     */
    public static void showVaccinationReminder(Context context, String title, String message) {
        showNotification(context, title, message, Constants.CHANNEL_VACCINATION_REMINDERS);
    }

    /**
     * Generate a unique notification ID from the title and message.
     * This allows multiple different notifications to be shown simultaneously.
     */
    public static int generateNotificationId(String title, String message) {
        String combined = (title != null ? title : "") + (message != null ? message : "");
        return combined.hashCode();
    }

    /**
     * Create a notification channel if it doesn't exist.
     */
    private static void createNotificationChannel(Context context, String channelId) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (notificationManager == null) {
            return;
        }

        // Check if channel already exists
        if (notificationManager.getNotificationChannel(channelId) != null) {
            return;
        }

        // Create appropriate channel based on ID
        String name;
        String description;

        if (Constants.CHANNEL_USG_REMINDERS.equals(channelId)) {
            name = Constants.CHANNEL_USG_REMINDERS_NAME;
            description = Constants.CHANNEL_USG_REMINDERS_DESC;
        } else if (Constants.CHANNEL_VACCINATION_REMINDERS.equals(channelId)) {
            name = Constants.CHANNEL_VACCINATION_REMINDERS_NAME;
            description = Constants.CHANNEL_VACCINATION_REMINDERS_DESC;
        } else {
            name = "Default Channel";
            description = "Default notification channel";
        }

        NotificationChannel channel = new NotificationChannel(
                channelId,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription(description);
        channel.enableVibration(true);

        notificationManager.createNotificationChannel(channel);
        Log.d(TAG, "Created notification channel: " + channelId);
    }

    /**
     * Cancel a specific notification by ID.
     */
    public static void cancelNotification(Context context, int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
    }

    /**
     * Cancel all notifications.
     */
    public static void cancelAllNotifications(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancelAll();
    }
}
