package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class DoctorUSGActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialCardView cardDatePicker;
    private TextView tvSelectedDate;
    private LinearLayout resultsContainer;

    // Hero card TextViews
    private TextView tvPogWeeks;
    private TextView tvPogDays;
    private ProgressBar progressPregnancy;
    private TextView tvEdd;

    // Result TextViews
    private TextView tvUsg1Dates;
    private TextView tvUsg2Dates;
    private TextView tvUsg3Dates;
    private TextView tvUsg4Dates;

    private LocalDate selectedLmpDate;
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_usg);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardDatePicker = findViewById(R.id.cardDatePicker);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        resultsContainer = findViewById(R.id.resultsContainer);

        tvPogWeeks = findViewById(R.id.tvPogWeeks);
        tvPogDays = findViewById(R.id.tvPogDays);
        progressPregnancy = findViewById(R.id.progressPregnancy);
        tvEdd = findViewById(R.id.tvEdd);

        tvUsg1Dates = findViewById(R.id.tvUsg1Dates);
        tvUsg2Dates = findViewById(R.id.tvUsg2Dates);
        tvUsg3Dates = findViewById(R.id.tvUsg3Dates);
        tvUsg4Dates = findViewById(R.id.tvUsg4Dates);
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
                        selectedLmpDate = LocalDate.of(year, month + 1, dayOfMonth);
                        updateUI();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set max date to today
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void updateUI() {
        if (selectedLmpDate == null) return;

        // Update selected date display
        tvSelectedDate.setText(selectedLmpDate.format(displayFormatter));

        // Show results section
        resultsContainer.setVisibility(View.VISIBLE);

        // Calculate POG
        long totalDays = ChronoUnit.DAYS.between(selectedLmpDate, LocalDate.now());
        long weeks = totalDays / 7;
        long days = totalDays % 7;

        if (totalDays >= 0) {
            tvPogWeeks.setText(String.valueOf(weeks));
            tvPogDays.setText(", Day " + days);
            progressPregnancy.setProgress((int) weeks);
        } else {
            tvPogWeeks.setText("--");
            tvPogDays.setText("");
            progressPregnancy.setProgress(0);
        }

        // Calculate EDD
        LocalDate edd = selectedLmpDate.plusMonths(9).plusDays(7);
        tvEdd.setText("EDD: " + edd.format(displayFormatter));

        // Update USG dates
        tvUsg1Dates.setText(DatesHelper.getUSG1DateRange.apply(selectedLmpDate));
        tvUsg2Dates.setText(DatesHelper.getUSG2DateRange.apply(selectedLmpDate));
        tvUsg3Dates.setText(DatesHelper.getUSG3DateRange.apply(selectedLmpDate));
        tvUsg4Dates.setText(DatesHelper.getUSG4DateRange.apply(selectedLmpDate));
    }
}
