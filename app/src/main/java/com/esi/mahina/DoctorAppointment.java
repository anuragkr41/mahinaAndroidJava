package com.esi.mahina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.esi.mahina.calculations.AppointmentsHelper;
import com.esi.mahina.calculations.LastMenstrualPeriod;

import java.time.LocalDate;

public class DoctorAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalDate lmp = LastMenstrualPeriod.getInstance().getLMP();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        String visit1Range = AppointmentsHelper.getVisit1DateRange.apply(lmp);
         String visit2Range = AppointmentsHelper.getVisit2To8DateRange.apply(lmp, 20);
         String visit3Range = AppointmentsHelper.getVisit2To8DateRange.apply(lmp, 26);
         String visit4Range = AppointmentsHelper.getVisit2To8DateRange.apply(lmp, 30);
         String visit5Range = AppointmentsHelper.getVisit2To8DateRange.apply(lmp,34);
         String visit6Range = AppointmentsHelper.getVisit2To8DateRange.apply(lmp,36);
         String visit7Range = AppointmentsHelper.getVisit2To8DateRange.apply(lmp,38);
         String visit8Range = AppointmentsHelper.getVisit2To8DateRange.apply(lmp,40);


        TextView textViewVisit1 = findViewById(R.id.visit1Dates);
        textViewVisit1.setText(visit1Range);
           
        TextView textViewVisit2 = findViewById(R.id.visit2Dates);
        textViewVisit2.setText(visit2Range);
           
        TextView textViewVisit3 = findViewById(R.id.visit3Dates);
        textViewVisit3.setText(visit3Range);
        
           
        TextView textViewVisit4 = findViewById(R.id.visit4Dates);
        textViewVisit4.setText(visit4Range);
           
        TextView textViewVisit5 = findViewById(R.id.visit5Dates);
        textViewVisit5.setText(visit5Range);
           
        TextView textViewVisit6 = findViewById(R.id.visit6Dates);
        textViewVisit6.setText(visit6Range);
           
        TextView textViewVisit7 = findViewById(R.id.visit7Dates);
        textViewVisit7.setText(visit7Range);

        TextView textViewVisit8 = findViewById(R.id.visit8Dates);
        textViewVisit8.setText(visit8Range);

        


    }
}