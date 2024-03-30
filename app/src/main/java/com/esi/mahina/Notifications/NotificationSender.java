package com.esi.mahina.Notifications;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.esi.mahina.Settings.GeneralSettings;

import java.time.format.DateTimeFormatter;

public class NotificationSender extends AppCompatActivity {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private Context context;
    public NotificationSender(Context context) {
        this.context = context;
    }
    public static void pushDoctorAppointmentNotifications() {

    }

    public boolean pushUSGNotifications(Context appContext) {

        boolean isSuccess = false;
        appContext = this.context;


        GeneralSettings generalSettings = new GeneralSettings(appContext);

//        Log.d("AK 11 Pushing NOTI", "Notification is allowed"+generalSettings.getNotificationStatus());
//        Log.d("AK 22 Pushing NOTI", "Notification is "+generalSettings.isNotificationAllowed());

        if (generalSettings.isNotificationAllowed()) {

            try {
                String usg1NotificationTitle = "Reminder: 1st Usg date due";
//                String usg1NotificationText = "Your first Usg date is due between "
//                        + (USGDates.usg1Date).format(formatter) + " to "
//                        + ((USGDates.usg1Date).plusDays(7)).format(formatter);

//                Log.d("HAHA", usg1NotificationText);
//
//                NotificationScheduler.scheduleNotification(context, USGDates.usg1Date, usg1NotificationTitle, usg1NotificationText);
//
//                String usg2NotificationTitle = "Reminder: 2nd Usg date due";
//                String usg2NotificationText = "Your second 2nd Usg date is due between "
//                        + USGDates.usg2Date.format(formatter) + " to "
//                        + (USGDates.usg2Date).plusDays(7).format(formatter);
//
//                NotificationScheduler.scheduleNotification(context, USGDates.usg2Date, usg2NotificationTitle, usg2NotificationText);
//
//                String usg3NotificationTitle = "Reminder: 3rd Usg date due";
//                String usg3NotificationText = "Your third Usg date is due between "
//                        + USGDates.usg3Date.format(formatter) + " to "
//                        + (USGDates.usg3Date).plusDays(7).format(formatter);
//
//                NotificationScheduler.scheduleNotification(context, USGDates.usg3Date, usg3NotificationTitle, usg3NotificationText);
//
//                String usg4NotificationTitle = "Reminder: 4th Usg date due";
//                String usg4NotificationText = "Your fourth Usg date is due between "
//                        + USGDates.usg4Date.format(formatter) + " to "
//                        + (USGDates.usg4Date).plusDays(7).format(formatter);
//
//                NotificationScheduler.scheduleNotification(context, USGDates.usg4Date, usg4NotificationTitle, usg4NotificationText);
//                isSuccess = true;

            } catch (NullPointerException e) {
                Log.d("HAHAHAH", "NPE" + e.getMessage());
            }

        } else {
            Log.d("AK Pushing NOTI", "Notification is not enabled");

        }
        return isSuccess;
    }
}
