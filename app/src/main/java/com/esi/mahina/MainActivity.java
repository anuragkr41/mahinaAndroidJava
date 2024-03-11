package com.esi.mahina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
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
import com.esi.mahina.calculations.LastMenstrualPeriod;
import com.esi.mahina.databinding.ActivityMainBinding;
import com.esi.mahina.Notifications.NotificationScheduler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

//         Testing notificaiton for 10 Seconds
        Context appContext = getApplicationContext();

        // Call the scheduleNotification method with the application context

        LocalDate date= LocalDate.now();
        NotificationScheduler.scheduleNotification(appContext, date, "Anurag", "This is Testing");




        //        NotificationScheduler.scheduleNotification(context);

//        Send USG Notification
        NotificationSender usgNotificationSender = new NotificationSender();
        usgNotificationSender.pushUSGNotifications();

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

        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("savedLmpDate", savedLmpDate);
        editor.apply();

        String dateToShow =lastMenstrualCycle.getLMP().format(formatter);

        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
        textViewSavedLMPInfo.setText("Your LMP is set to\n "+dateToShow);
        Toast.makeText(this, "Data is Saved", Toast.LENGTH_SHORT).show();

    }
    private LocalDate loadDate(){
        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String date = preferences.getString("savedLmpDate","");
        if(!date.isEmpty()){
            lastMenstrualCycle.setLMP(LocalDate.parse(date));
        }
        else {
            lastMenstrualCycle.setLMP(LocalDate.now());
        }
        return lastMenstrualCycle.getLMP();
    }

}