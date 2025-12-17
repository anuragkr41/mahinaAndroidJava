package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.esi.mahina.R;
import com.esi.mahina.calculations.DatesHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class DoctorImmunizationActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialCardView cardDatePicker;
    private TextView tvSelectedDate;
    private LinearLayout resultsContainer;
    private LinearLayout vaccineCardsContainer;

    // Baby age
    private TextView tvBabyAge;

    // Vaccination date TextViews
    private TextView tvBirthDate;
    private TextView tv6WeeksDate;
    private TextView tv10WeeksDate;
    private TextView tv14WeeksDate;

    private LocalDate selectedDob;
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_immunization);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardDatePicker = findViewById(R.id.cardDatePicker);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        resultsContainer = findViewById(R.id.resultsContainer);
        vaccineCardsContainer = findViewById(R.id.vaccineCardsContainer);

        tvBabyAge = findViewById(R.id.tvBabyAge);
        tvBirthDate = findViewById(R.id.tvBirthDate);
        tv6WeeksDate = findViewById(R.id.tv6WeeksDate);
        tv10WeeksDate = findViewById(R.id.tv10WeeksDate);
        tv14WeeksDate = findViewById(R.id.tv14WeeksDate);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cardDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDob = LocalDate.of(year, month + 1, dayOfMonth);
                        updateUI();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateUI() {
        if (selectedDob == null) return;

        // Update selected date display
        tvSelectedDate.setText(selectedDob.format(displayFormatter));

        // Show results section
        resultsContainer.setVisibility(View.VISIBLE);

        // Calculate baby age
        LocalDate today = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(selectedDob, today);

        if (totalDays >= 0) {
            long months = totalDays / 30;
            long days = totalDays % 30;

            if (months > 0) {
                tvBabyAge.setText(months + " month" + (months > 1 ? "s" : "") + ", " + days + " day" + (days != 1 ? "s" : ""));
            } else {
                tvBabyAge.setText(days + " day" + (days != 1 ? "s" : "") + " old");
            }
        } else {
            tvBabyAge.setText("Not born yet");
        }

        // Update vaccination dates
        LocalDate birthDate = selectedDob;
        LocalDate week6Date = selectedDob.plusDays(42);
        LocalDate week10Date = selectedDob.plusDays(70);
        LocalDate week14Date = selectedDob.plusDays(98);

        // Birth vaccination
        tvBirthDate.setText(birthDate.format(DatesHelper.formatter));

        // 6 Weeks vaccination
        tv6WeeksDate.setText(week6Date.format(DatesHelper.formatter));

        // 10 Weeks vaccination
        tv10WeeksDate.setText(week10Date.format(DatesHelper.formatter));

        // 14 Weeks vaccination
        tv14WeeksDate.setText(week14Date.format(DatesHelper.formatter));
    }
}
