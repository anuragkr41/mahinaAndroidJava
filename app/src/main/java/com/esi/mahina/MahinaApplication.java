package com.esi.mahina;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.esi.mahina.data.Constants;

/**
 * Application class for Mahina app.
 * Handles app-wide initialization like notification channels.
 */
public class MahinaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    /**
     * Create notification channels for Android 8.0+.
     * Channels are required for showing notifications on API 26+.
     */
    private void createNotificationChannels() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager == null) {
            return;
        }

        // USG Reminders Channel
        NotificationChannel usgChannel = new NotificationChannel(
                Constants.CHANNEL_USG_REMINDERS,
                Constants.CHANNEL_USG_REMINDERS_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        usgChannel.setDescription(Constants.CHANNEL_USG_REMINDERS_DESC);
        usgChannel.enableVibration(true);
        notificationManager.createNotificationChannel(usgChannel);

        // Vaccination Reminders Channel
        NotificationChannel vaccinationChannel = new NotificationChannel(
                Constants.CHANNEL_VACCINATION_REMINDERS,
                Constants.CHANNEL_VACCINATION_REMINDERS_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        vaccinationChannel.setDescription(Constants.CHANNEL_VACCINATION_REMINDERS_DESC);
        vaccinationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(vaccinationChannel);
    }
}
