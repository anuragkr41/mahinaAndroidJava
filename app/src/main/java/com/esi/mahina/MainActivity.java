package com.esi.mahina;

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
import com.esi.mahina.Settings.GeneralSettings;
import com.esi.mahina.calculations.DatesHelper;
import com.esi.mahina.dates.LastMenstrualPeriod;
import com.esi.mahina.databinding.ActivityMainBinding;
import com.esi.mahina.dates.USGDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private LastMenstrualPeriod lastMenstrualCycle = LastMenstrualPeriod.getInstance() ;
    ActivityMainBinding binding;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final String PREFS_NAME = "MyPrefs";
    private static final String SWITCH_STATE = "switchState";
    private Switch mySwitch;

    private GeneralSettings generalSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Buttons Click Listeners
        Button buttonNavigate = findViewById(R.id.btnUltraSound);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UltraSound.class);
                startActivity(intent);
            }
        });

        Button buttonNavigateDoctorAppointment = findViewById(R.id.btnDoctorAppointment);
        buttonNavigateDoctorAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoctorAppointment.class);
                startActivity(intent);
            }
        });

        this.datePicker=findViewById(R.id.datePicker);
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();
            }
        });

//        Testing switch state.
            Switch enableDisableSwitch = findViewById(R.id.enableDisableNotification);
            generalSettings = new GeneralSettings(this);
            generalSettings.initialize(enableDisableSwitch);

            Log.d("Initial Notification state", "NF="+generalSettings.isNotificationAllowed());

            if(generalSettings.isNotificationAllowed()){
                //code to send notification..
            }


//         Testing notificaiton for 10 Seconds
//        Context appContext = getApplicationContext();

        // Call the scheduleNotification method with the application context

//        LocalDate date= LocalDate.now();
//        NotificationScheduler.scheduleNotification(appContext, date, "Anurag", "This is Testing");

//                NotificationScheduler.scheduleNotification(getApplicationContext());



        //This is your saved date
        LocalDate savedDate = loadDate();
        setPogAndEDD(savedDate);

        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
        textViewSavedLMPInfo.setText("Your LMP is set to \n"+savedDate.format(formatter));

        //Loading date on start is working fine.

        datePicker.init(
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Update LocalDate whenever the date changes in date picker
                    LocalDate selectedDate = DatesHelper.captureLocalDateFromDatePicker(datePicker);
                    lastMenstrualCycle.setLMP(selectedDate);
                    setPogAndEDD(selectedDate);

                }
        );

        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("preferences", entry.getKey() + ": " + entry.getValue().toString());
        }

        try {
            Log.d("USG1 Static Date:", USGDates.getUsg1Date().toString());
            Log.d("USG2 Static Date:", USGDates.getUsg2Date().toString());
            Log.d("USG3 Static Date:", USGDates.getUsg3Date().toString());
            Log.d("USG4 Static Date:", USGDates.getUsg4Date().toString());
        }catch (NullPointerException e){
            e.printStackTrace();
        }




        //        Send USG Notification
        NotificationSender usgNotificationSender = new NotificationSender(getApplicationContext());

//        usgNotificationSender.pushUSGNotifications();
    }

    private void setPogAndEDD(LocalDate date) {

        String pog= DatesHelper.getPeriodOfGestation.apply(date);
        TextView textViewPog = findViewById(R.id.twPog);
        textViewPog.setText(pog);

        String edd= DatesHelper.getExpectedDateOfDelivery.apply(date);
        TextView textViewEdd = findViewById(R.id.twEdd);
        textViewEdd.setText(edd);
    }


    private void saveDate() {
        String savedLmpDate =  lastMenstrualCycle.getLMP().toString();
        setUSGDates(lastMenstrualCycle.getLMP());
        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("savedLmpDate", savedLmpDate);
        editor.putString("savedUSG1Date", (USGDates.getUsg1Date()).toString());
        editor.putString("savedUSG2Date", (USGDates.getUsg2Date()).toString());
        editor.putString("savedUSG3Date", (USGDates.getUsg3Date()).toString());
        editor.putString("savedUSG4Date", (USGDates.getUsg4Date()).toString());
        editor.apply();


        String dateToShow =lastMenstrualCycle.getLMP().format(formatter);

        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
        textViewSavedLMPInfo.setText("Your LMP is set to\n "+dateToShow);
        Toast.makeText(this, "Data is Saved", Toast.LENGTH_SHORT).show();

    }

    private void setUSGDates(LocalDate lmp) {
        USGDates.setUsg1Date(DatesHelper.getUSG1Date.apply(lmp));
        USGDates.setUsg2Date(DatesHelper.getUSG2Date.apply(lmp));
        USGDates.setUsg3Date(DatesHelper.getUSG3Date.apply(lmp));
        USGDates.setUsg4Date(DatesHelper.getUSG4Date.apply(lmp));
    }


    private LocalDate loadDate(){
        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String date = preferences.getString("savedLmpDate","");
        String usg1Date = preferences.getString("savedUSG1Date", "");
        String usg2Date = preferences.getString("savedUSG2Date", "");
        String usg3Date = preferences.getString("savedUSG3Date", "");
        String usg4Date = preferences.getString("savedUSG4Date", "");

        if(!date.isEmpty()){
            lastMenstrualCycle.setLMP(LocalDate.parse(date));
            USGDates.setUsg1Date(LocalDate.parse(usg1Date));
            USGDates.setUsg2Date(LocalDate.parse(usg2Date));
            USGDates.setUsg3Date(LocalDate.parse(usg3Date));
            USGDates.setUsg4Date(LocalDate.parse(usg4Date));
//            Log.d("Load USG1", USGDates.getUsg1Date().toString());
//            Log.d("Load USG2", USGDates.getUsg2Date().toString());
//            Log.d("Load USG3", USGDates.getUsg3Date().toString());

//            USGDates.setUsg2Date(LocalDate.parse(usg2Date));
//            USGDates.setUsg3Date(LocalDate.parse(usg3Date));
//            USGDates.setUsg4Date(LocalDate.parse(usg4Date));
        }

        else {
            lastMenstrualCycle.setLMP(LocalDate.now());
//            USGDates.setUsg1Date(LocalDate.now());
//            USGDates.setUsg2Date(LocalDate.now());
//            USGDates.setUsg3Date(LocalDate.now());
//            USGDates.setUsg4Date(LocalDate.now());
        }
        return lastMenstrualCycle.getLMP();
    }

}