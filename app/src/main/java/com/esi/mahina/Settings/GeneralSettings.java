package com.esi.mahina.Settings;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.esi.mahina.R;

public class GeneralSettings implements Notifications, Language{
    private SharedPreferences preferences;
    private Switch enableDisableNotification;
    private static final String SHARED_PREF_NAME = "MyAppPreferences";
    private static final String NOTIFICATION_STATUS_KEY = "notificationStatus";
    public GeneralSettings(Context context) {
        // Initialize the SharedPreferences with the provided context
        preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // The rest of your initialization code remains unchanged
    }
    public  void initialize(Switch swithcView) {
        enableDisableNotification = swithcView;

        boolean notificationStatus = getNotificationStatus();
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
        return enableDisableNotification.isChecked();
    }
    private boolean getNotificationStatus() {
        // Retrieve the notification status from SharedPreferences
        return preferences.getBoolean(NOTIFICATION_STATUS_KEY, true);
    }
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
