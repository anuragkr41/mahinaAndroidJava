package com.esi.mahina.Notifications;

import android.content.Context;
import android.util.Log;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.esi.mahina.Settings.GeneralSettings;
import com.esi.mahina.dates.USGDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotificationSender extends AppCompatActivity {
    private GeneralSettings generalSettings;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");


    public void pushUSGNotifications(){
        if(generalSettings.isNotificationAllowed()){
            Log.d("AK Pushing NOTI", "Notigfication is allowed");
            Context appContext = getApplicationContext();

            String usg1NotificationTitle="Reminder: 1st Usg date due";
            String usg1NotificationText="Your first Usg date is due between "
                                        + USGDates.getUsg1Date().format(formatter)+" to "
                                        +(USGDates.getUsg1Date()).plusDays(7).format(formatter);

            NotificationScheduler.scheduleNotification(appContext, USGDates.getUsg1Date(), usg1NotificationTitle, usg1NotificationText);

            String usg2NotificationTitle="Reminder: 2nd Usg date due";
            String usg2NotificationText="Your second 2nd Usg date is due between "
                    + USGDates.getUsg2Date().format(formatter)+" to "
                    +(USGDates.getUsg2Date()).plusDays(7).format(formatter);

            NotificationScheduler.scheduleNotification(appContext, USGDates.getUsg2Date(), usg2NotificationTitle, usg2NotificationText);

            String usg3NotificationTitle="Reminder: 3rd Usg date due";
            String usg3NotificationText="Your third Usg date is due between "
                    + USGDates.getUsg3Date().format(formatter)+" to "
                    +(USGDates.getUsg3Date()).plusDays(7).format(formatter);

            NotificationScheduler.scheduleNotification(appContext, USGDates.getUsg3Date(), usg3NotificationTitle, usg3NotificationText);

            String usg4NotificationTitle="Reminder: 4th Usg date due";
            String usg4NotificationText="Your fourth Usg date is due between "
                    + USGDates.getUsg4Date().format(formatter)+" to "
                    +(USGDates.getUsg4Date()).plusDays(7).format(formatter);

            NotificationScheduler.scheduleNotification(appContext, USGDates.getUsg4Date(), usg4NotificationTitle, usg4NotificationText);



        }else {
            Log.d("AK Pushing NOTI", "Notification is not enabled");

        }

    }
    public static void pushDoctorAppointmentNotifications(){


    }
}