package com.esi.mahina.activities.patientActivities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.esi.mahina.Notifications.NotificationSender;
import com.esi.mahina.R;
import com.esi.mahina.Settings.GeneralSettings;
import com.esi.mahina.calculations.DatesHelper;
import com.esi.mahina.databinding.ActivityPatientBinding;
import com.esi.mahina.dates.ultraSoundAndDoctorVisitSchedule.Dates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class PatientActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    private DatePicker datePicker;
    private Dates dates;

    ActivityPatientBinding binding;


    private GeneralSettings generalSettings;

    private boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.datePicker = findViewById(R.id.datePicker);
        binding.save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            saveDate();
                        }
                    });

        LocalDate savedDate = loadDate();
        Log.d("MyLoadedDate:", savedDate.toString());
        setPogAndEDD(savedDate);
        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
        textViewSavedLMPInfo.setText("Your LMP is set to \n"+savedDate.format(DatesHelper.formatter));

        datePicker.init(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    LocalDate selectedDate = DatesHelper.captureLocalDateFromDatePicker(datePicker);
//                    setPogAndEDD(selectedDate);

                    Log.d("dfd:", selectedDate.toString());
                    this.dates = new Dates(selectedDate);

                    if (this.dates != null) {

                        Log.d("Timppo", this.dates.toString());
                    }

                });
//
//



//

//          This is your saved date


//        dates.setUsgDates(savedDate);
//        dates.setAppointmentDates(savedDate);
//
//
//        setPogAndEDD(savedDate);
//
//        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
//        textViewSavedLMPInfo.setText("Your LMP is set to \n"+savedDate.format(formatter));
//
//        //Loading date on start is working fine.
//
//        datePicker.init(
//                datePicker.getYear(),
//                datePicker.getMonth(),
//                datePicker.getDayOfMonth(),
//                (view, year, monthOfYear, dayOfMonth) -> {
//                    // Update LocalDate whenever the date changes in date picker
//                    LocalDate selectedDate = DatesHelper.captureLocalDateFromDatePicker(datePicker);
////                    dates.setNlmp(selectedDate);
//                    setPogAndEDD(selectedDate);
//                }
//        );
//
////        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
////        Map<String, ?> allEntries = preferences.getAll();
////        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
////            Log.d("preferences", entry.getKey() + ": " + entry.getValue().toString());
////        }
////
////        try {
////            Log.d("USG1 Static Date:", USGDates.getUsg1Date().toString());
////            Log.d("USG2 Static Date:", USGDates.getUsg2Date().toString());
////            Log.d("USG3 Static Date:", USGDates.getUsg3Date().toString());
////            Log.d("USG4 Static Date:", USGDates.getUsg4Date().toString());
////        }catch (NullPointerException e){
//////            Log.d("NPE-Anu",e.getMessage());
////            textViewSavedLMPInfo.setText("There is no saved Date. Save LMP first and enable Notifications");
////
////        }
//
////        Log.d("NPE After", "Out of NPE");
//
//        //        Testing switch state.
//        Switch enableDisableSwitch = findViewById(R.id.enableDisableNotification);
//        generalSettings = new GeneralSettings(this);
//        generalSettings.initialize(enableDisableSwitch);
//
//
//
//        if(generalSettings.isNotificationAllowed()){
//            //code to send notification..
//            Log.d("2Initial NF:: state", "NF="+generalSettings.isNotificationAllowed());
//            //        Send USG Notification
//            NotificationSender usgNotificationSender = new NotificationSender(getApplicationContext());
//
//            boolean status = usgNotificationSender.pushUSGNotifications(getApplicationContext());
//
//            Log.d("Trying Harder", " status = "+status);
//            if(!status){
////                textViewSavedLMPInfo.setText("You must save the date before enabling notifications");
//            }
//        }else {
//            Log.d("NO Noti", "NOtification not enabled");
//        }
//
//    }


    }

    private void setPogAndEDD(LocalDate date) {

        String pog = DatesHelper.getPeriodOfGestation.apply(date);
        TextView textViewPog = findViewById(R.id.twPog);
        textViewPog.setText(pog);

        String edd = DatesHelper.getExpectedDateOfDelivery.apply(date);
        TextView textViewEdd = findViewById(R.id.twEdd);
        textViewEdd.setText(edd);

    }


    private void saveDate() {

        SharedPreferences preferences = getSharedPreferences(PatientActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String savedLmpDate = isoFormatter.format(this.dates.getLastMenstrualPeriodDate());
        editor.putString("savedLmpDate", savedLmpDate);
        editor.apply();
        String dateToShow = dates
                .getLastMenstrualPeriodDate()
                .format(DatesHelper.formatter);
        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
        textViewSavedLMPInfo.setText("Your LMP is set to\n " + dateToShow);
        Toast.makeText(this, "Date is Saved", Toast.LENGTH_SHORT).show();
    }

    private LocalDate loadDate() {
        SharedPreferences preferences = getSharedPreferences(PatientActivity.PREFS_NAME, MODE_PRIVATE);
        String date = preferences.getString("savedLmpDate", "");
        if (!date.isEmpty()) {
            this.dates = new Dates(LocalDate.parse(date));
        }
        else this.dates = new Dates(LocalDate.now());

        return this.dates.getLastMenstrualPeriodDate();
    }
}