package com.esi.mahina;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.esi.mahina.calculations.DatesHelper;


public class UltraSound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultrasound);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        String usg1Range = DatesHelper.getUSG1DateRange.apply(MainActivity.lmp);
//        Log.d("UltraSound DR1", "Date Range: " + usg1Range);
        TextView textViewUsg1 = findViewById(R.id.usg1Dates);
        textViewUsg1.setText(usg1Range);

        String usg2Range = DatesHelper.getUSG2DateRange.apply(MainActivity.lmp);
        Log.d("UltraSound DR2", "Date Range: " + usg2Range);
        TextView textViewUsg2 = findViewById(R.id.usg2Dates);
        textViewUsg2.setText(usg2Range);

        String usg3Range = DatesHelper.getUSG3DateRange.apply(MainActivity.lmp);
        Log.d("UltraSound DR3", "Date Range: " + usg3Range);
        TextView textViewUsg3 = findViewById(R.id.usg3Dates);
        textViewUsg3.setText(usg3Range);

        String usg4Range = DatesHelper.getUSG4DateRange.apply(MainActivity.lmp);
        Log.d("UltraSound DR4", "Date Range: " + usg4Range);
        TextView textViewUsg4 = findViewById(R.id.usg4Dates);
        textViewUsg4.setText(usg4Range);


    }

}
