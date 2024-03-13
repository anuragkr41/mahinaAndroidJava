package com.esi.mahina.Notifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.esi.mahina.MainActivity;
import com.esi.mahina.R;

public class NotificationUtils {
    private static final String CHANNEL_ID = "default";
    private static final int NOTIFICATION_ID = 1;

    public static void showNotification(Context context,String title,  String message) {
        // Check if the notification channel needs to be created
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
        }

        // Check if the app has the POST_NOTIFICATIONS permission
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // If the permission is not granted, request it
            // This will typically happen on devices running Android 8.0 (Oreo) or above
            ActivityCompat.requestPermissions((MainActivity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 123);
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.mahinalogo) // Use your logo drawable as the small icon
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);
        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    // Create the notification channel
    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Default Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
