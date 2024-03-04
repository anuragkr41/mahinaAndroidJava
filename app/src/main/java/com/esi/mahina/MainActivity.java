package com.esi.mahina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.esi.mahina.calculations.DatesHelper;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;

    public static LocalDate lmp = LocalDate.now();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



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

        datePicker.init(
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Update LocalDate whenever the date changes
                    LocalDate selectedDate = DatesHelper.captureLocalDateFromDatePicker(datePicker);
                    // Now, you have the updated LocalDate, you can use it as needed
                    // Example: Log the selected date
//                    Log.d("MainActivity", "Selected Date: " + selectedDate);
                    lmp=selectedDate;

                    String pog= DatesHelper.getPeriodOfGestation.apply(lmp);
//
                    TextView textViewPog = findViewById(R.id.twPog);
//
                    textViewPog.setText(pog);


                    String edd= DatesHelper.getExpectedDateOfDelivery.apply(lmp);
//
                    TextView textViewEdd = findViewById(R.id.twEdd);
//
                    textViewEdd.setText(edd);

//                    Log.d("MainActivity", "EDD Date: " + DatesHelper.getExpectedDateOfDelivery.apply(lmp));

                }
        );







     }

}