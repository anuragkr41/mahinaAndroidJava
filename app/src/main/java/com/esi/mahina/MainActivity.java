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
import android.widget.TextView;
import android.widget.Toast;

import com.esi.mahina.calculations.DatesHelper;
import com.esi.mahina.calculations.LastMenstrualPeriod;
import com.esi.mahina.databinding.ActivityMainBinding;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private LastMenstrualPeriod lastMenstrualCycle = LastMenstrualPeriod.getInstance() ;


    ActivityMainBinding binding;



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
//        binding.datePicker.setMinDate(binding.datePicker.getMinDate());
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();
            }
        });

        loadDate();
        Log.d("Saved Variable", lastMenstrualCycle.getLMP().toString());


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

                    lastMenstrualCycle.setLMP(selectedDate);

                    String pog= DatesHelper.getPeriodOfGestation.apply(lastMenstrualCycle.getLMP());
//
                    TextView textViewPog = findViewById(R.id.twPog);
//
                    textViewPog.setText(pog);


                    String edd= DatesHelper.getExpectedDateOfDelivery.apply(lastMenstrualCycle.getLMP());
//
                    TextView textViewEdd = findViewById(R.id.twEdd);
//
                    textViewEdd.setText(edd);

//                    Log.d("MainActivity", "EDD Date: " + DatesHelper.getExpectedDateOfDelivery.apply(lmp));

                }
        );

     }

    private void saveDate() {
        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String lmpDate =  lastMenstrualCycle.getLMP().toString();
        editor.putString("lmpDate", lmpDate);
        editor.apply();
        Toast.makeText(this, "Data is Saved", Toast.LENGTH_SHORT).show();
    }
    private void loadDate(){
        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String date = preferences.getString("lmpDate","");
        if(!date.isEmpty()){
            lastMenstrualCycle.setLMP(LocalDate.parse(date));
        }
        else {
            lastMenstrualCycle.setLMP(LocalDate.now());
        }
    }

}