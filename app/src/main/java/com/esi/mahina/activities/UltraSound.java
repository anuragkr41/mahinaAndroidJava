package com.esi.mahina.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.esi.mahina.R;
import com.esi.mahina.Settings.GeneralSettings;
import com.esi.mahina.calculations.DatesHelper;
import com.esi.mahina.dates.LastMenstrualPeriod;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class UltraSound extends AppCompatActivity {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private GeneralSettings generalSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultrasound);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        LocalDate lmp = LastMenstrualPeriod.getInstance().getLMP();
        TextView tvLMP = findViewById(R.id.lmpForUSGCalc);
        tvLMP.setText(lmp.format(formatter));


        Switch notificationSwitch;
        generalSettings=new GeneralSettings(getApplicationContext());
//        generalSettings.initialize();

        Log.d("Initial Notification state", "NF="+generalSettings.isNotificationAllowed());



        String usg1Range = DatesHelper.getUSG1DateRange.apply(lmp);
        TextView textViewUsg1 = findViewById(R.id.usg1Dates);
        textViewUsg1.setText(usg1Range);

        String usg2Range = DatesHelper.getUSG2DateRange.apply(lmp);
        TextView textViewUsg2 = findViewById(R.id.usg2Dates);
        textViewUsg2.setText(usg2Range);

        String usg3Range = DatesHelper.getUSG3DateRange.apply(lmp);
        TextView textViewUsg3 = findViewById(R.id.usg3Dates);
        textViewUsg3.setText(usg3Range);

        String usg4Range = DatesHelper.getUSG4DateRange.apply(lmp);
        TextView textViewUsg4 = findViewById(R.id.usg4Dates);
        textViewUsg4.setText(usg4Range);


    }

}
