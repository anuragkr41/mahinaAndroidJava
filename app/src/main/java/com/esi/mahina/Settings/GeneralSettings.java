package com.esi.mahina.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Switch;


public class GeneralSettings implements Notifications, Language {
    private static final String SHARED_PREF_NAME = "MyAppPreferences";
    private static final String NOTIFICATION_STATUS_KEY = "notificationStatus";
    private SharedPreferences preferences;
    private Switch enableDisableNotification;

    public GeneralSettings(Context context) {
        // Initialize the SharedPreferences with the provided context
        preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // The rest of your initialization code remains unchanged
    }

    public void initialize(Switch swithcView) {
        enableDisableNotification = swithcView;

        boolean notificationStatus = isNotificationAllowed();
        enableDisableNotification.setChecked(notificationStatus);

        // Set listener for the switch
        enableDisableNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the new notification status to SharedPreferences
            saveNotificationStatus(isChecked);
        });
    }

    @Override
    public boolean isNotificationAllowed() {
        // Get the current notification status from the switch
//        return enableDisableNotification.isChecked();
        return preferences.getBoolean(NOTIFICATION_STATUS_KEY, false) || enableDisableNotification != null && enableDisableNotification.isChecked();
    }

    //    public boolean getNotificationStatus() {
//        // Retrieve the notification status from SharedPreferences
//        return preferences.getBoolean(NOTIFICATION_STATUS_KEY, false);
//    }
    public void saveNotificationStatus(boolean isEnabled) {
        // Save the notification status to SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NOTIFICATION_STATUS_KEY, isEnabled);
        editor.apply();
    }

    @Override
    public String preferredLanguage(String lang) {
        return null;
    }
}
