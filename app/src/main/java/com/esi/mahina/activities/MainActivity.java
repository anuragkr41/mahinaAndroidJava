package com.esi.mahina.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.esi.mahina.R;
import com.esi.mahina.activities.doctorActivities.DoctorOptions;

public class MainActivity extends AppCompatActivity {

    private final String DOCTOR = "doctor";
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        CardView cardNavigateDoctorScreen = findViewById(R.id.doctorCard);
        cardNavigateDoctorScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoctorOptions.class);
                startActivity(intent);
                userType = DOCTOR;
            }
        });

    }


}
