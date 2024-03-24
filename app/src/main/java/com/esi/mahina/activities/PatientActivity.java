package com.esi.mahina.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.esi.mahina.Notifications.NotificationSender;
import com.esi.mahina.R;
import com.esi.mahina.Settings.GeneralSettings;
import com.esi.mahina.calculations.DatesHelper;
import com.esi.mahina.databinding.ActivityPatientBinding;
import com.esi.mahina.dates.ultraSoundAndDoctorVisitSchedule.Dates;
import com.esi.mahina.databinding.ActivityMainBinding;
import com.esi.mahina.dates.USGDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatientActivity extends AppCompatActivity {
    private DatePicker datePicker;
    ActivityPatientBinding binding;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final String PREFS_NAME = "MyPrefs";

    private Dates dates;

    private GeneralSettings generalSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Buttons Click Listeners
        Button buttonNavigate = findViewById(R.id.btnUltraSound);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientActivity.this, UltraSound.class);
                startActivity(intent);
            }
        });

        Button buttonNavigateDoctorAppointment = findViewById(R.id.btnDoctorAppointment);
        buttonNavigateDoctorAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientActivity.this, DoctorAppointment.class);
                startActivity(intent);
            }
        });

        this.datePicker=findViewById(R.id.datePicker);


        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                saveDate();
            }
        });




        //This is your saved date
//        LocalDate savedDate = loadDate();
//        dates=new Dates(savedDate);
//        dates.setUsgDates(savedDate);
//        dates.setAppointmentDates(savedDate);


//        Log.d("MyLogger ::", dates.toString());
//        setPogAndEDD(savedDate);

        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
//        textViewSavedLMPInfo.setText("Your LMP is set to \n"+savedDate.format(formatter));

        //Loading date on start is working fine.

        datePicker.init(
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Update LocalDate whenever the date changes in date picker
                    LocalDate selectedDate = DatesHelper.captureLocalDateFromDatePicker(datePicker);
//                    dates.setNlmp(selectedDate);
                    setPogAndEDD(selectedDate);
                }
        );

//        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
//        Map<String, ?> allEntries = preferences.getAll();
//        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
//            Log.d("preferences", entry.getKey() + ": " + entry.getValue().toString());
//        }
//
//        try {
//            Log.d("USG1 Static Date:", USGDates.getUsg1Date().toString());
//            Log.d("USG2 Static Date:", USGDates.getUsg2Date().toString());
//            Log.d("USG3 Static Date:", USGDates.getUsg3Date().toString());
//            Log.d("USG4 Static Date:", USGDates.getUsg4Date().toString());
//        }catch (NullPointerException e){
////            Log.d("NPE-Anu",e.getMessage());
//            textViewSavedLMPInfo.setText("There is no saved Date. Save LMP first and enable Notifications");
//
//        }

//        Log.d("NPE After", "Out of NPE");

        //        Testing switch state.
        Switch enableDisableSwitch = findViewById(R.id.enableDisableNotification);
        generalSettings = new GeneralSettings(this);
        generalSettings.initialize(enableDisableSwitch);



        if(generalSettings.isNotificationAllowed()){
            //code to send notification..
            Log.d("2Initial NF:: state", "NF="+generalSettings.isNotificationAllowed());
            //        Send USG Notification
            NotificationSender usgNotificationSender = new NotificationSender(getApplicationContext());

            boolean status = usgNotificationSender.pushUSGNotifications(getApplicationContext());

            Log.d("Trying Harder", " status = "+status);
            if(!status){
                textViewSavedLMPInfo.setText("You must save the date before enabling notifications");
            }
        }else {
            Log.d("NO Noti", "NOtification not enabled");
        }

    }

    private void setPogAndEDD(LocalDate date) {

        String pog= DatesHelper.getPeriodOfGestation.apply(date);
        TextView textViewPog = findViewById(R.id.twPog);
        textViewPog.setText(pog);

        String edd= DatesHelper.getExpectedDateOfDelivery.apply(date);
        TextView textViewEdd = findViewById(R.id.twEdd);
        textViewEdd.setText(edd);
    }


//    private void saveDate() {
//
////        setUSGDates(lastMenstrualCycle.getLMP());
//        SharedPreferences preferences = getSharedPreferences(PatientActivity.PREFS_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//
//
//        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
////        String savedLmpDate = isoFormatter.format(dates.getNlmp());
//        editor.putString("savedLmpDate", savedLmpDate);
//
//        List<String> usgDates = dates.getUsgDates();
//        editor.putString("savedUSG1DateRange", usgDates.get(0));
//
//
//        String savedUSG2Date = isoFormatter.format(USGDates.usg2Date);
//        editor.putString("savedUSG2DateRange", usgDates.get(1));
//
//        String savedUSG3Date = isoFormatter.format(USGDates.usg3Date);
//        editor.putString("savedUSG3DateRange", usgDates.get(2));
//
//        String savedUSG4Date = isoFormatter.format(USGDates.usg4Date);
//        editor.putString("savedUSG4DateRange", usgDates.get(3));
//        editor.apply();
//
//
//        String dateToShow =dates.getNlmp().format(formatter);
//
//        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
//        textViewSavedLMPInfo.setText("Your LMP is set to\n "+dateToShow);
//        Toast.makeText(this, "Data is Saved", Toast.LENGTH_SHORT).show();
//
//    }

//    private void setUSGDates(LocalDate lmp) {
//        USGDates.usg1Date = (DatesHelper.getUSG1Date.apply(lmp));
//        USGDates.usg2Date = (DatesHelper.getUSG2Date.apply(lmp));
//        USGDates.usg3Date = (DatesHelper.getUSG3Date.apply(lmp));
//        USGDates.usg4Date = (DatesHelper.getUSG4Date.apply(lmp));
//    }


//    private LocalDate loadDate(){
//        SharedPreferences preferences = getSharedPreferences(PatientActivity.PREFS_NAME, MODE_PRIVATE);
//
//        String date = preferences.getString("savedLmpDate","");
//
//        Log.d("Logger", "Loading data");
//
//
//
//
//        if(!date.isEmpty()){
//
//            LocalDate parsedDate = LocalDate.parse(date);
//
//
////            String usg1Date = preferences.getString("savedUSG1Date", "");
////            String usg2Date = preferences.getString("savedUSG2Date", "");
////            String usg3Date = preferences.getString("savedUSG3Date", "");
////            String usg4Date = preferences.getString("savedUSG4Date", "");
////
//
////            lastMenstrualCycle.setLMP(LocalDate.parse(date));
//
////            try {
////
////                LocalDate ld1 = LocalDate.parse(usg1Date);
////                LocalDate ld2 = LocalDate.parse(usg2Date);
////                LocalDate ld3 = LocalDate.parse(usg3Date);
////                LocalDate ld4 = LocalDate.parse(usg4Date);
////                USGDates.usg1Date=ld1;
////                USGDates.usg2Date = ld2;
////                USGDates.usg3Date =  ld3;
////                USGDates.usg4Date = ld4;
////            }catch (NullPointerException e){
////                Log.d("Logger", "Data is not being parsed ");
////
////            }
//
////            dates.setNlmp(parsedDate);
////            dates.setUsgDates(parsedDate);
//
//        }else{
//
//            LocalDate todayDate  = LocalDate.now();
//            Log.d("ILog::", todayDate.toString());
//
//            dates.setNlmp(todayDate);
//
//            Log.d("ILog::", dates.getNlmp().toString());
//
////            dates.setUsgDates(todayDate.minusDays(1));
//        }
//
//        return dates.getNlmp();
//    }

}