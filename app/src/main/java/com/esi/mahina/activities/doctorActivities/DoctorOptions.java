package com.esi.mahina.activities.doctorActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.esi.mahina.R;


public class DoctorOptions extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_options);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        CardView cardNavigateUsgAndAppointmentCalculations = findViewById(R.id.usgDateAndAppointmentOption);
        cardNavigateUsgAndAppointmentCalculations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorOptions.this, DoctorActivity.class);
                startActivity(intent);

            }
        });
    }
}