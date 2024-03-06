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
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private LastMenstrualPeriod lastMenstrualCycle = LastMenstrualPeriod.getInstance() ;

    ActivityMainBinding binding;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");




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


//        We first have to check if there is a saved LMP already


        //This is your saved date
        LocalDate savedDate = loadDate();
        setPogAndEDD(savedDate);

        TextView textViewSavedLMPInfo = findViewById(R.id.savedLmp);
        textViewSavedLMPInfo.setText("Your LMP is set to \n"+savedDate.format(formatter));

//        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
//        String date = preferences.getString("savedLmpDate","");
//        Log.d("--Date Variable--", date);
//        Log.d("--LMP Variable--", lastMenstrualCycle.getLMP().toString());

        //Loading data on start is working fine.

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
        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String savedLmpDate =  lastMenstrualCycle.getLMP().toString();

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