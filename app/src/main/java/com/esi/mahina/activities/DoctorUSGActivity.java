package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.esi.mahina.R;
import com.esi.mahina.calculations.DatesHelper;
import com.esi.mahina.utils.AnimationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class DoctorUSGActivity extends BaseActivity {

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

    // USG Cards for animation
    private MaterialCardView cardUsg1;
    private MaterialCardView cardUsg2;
    private MaterialCardView cardUsg3;
    private MaterialCardView cardUsg4;

    private LocalDate selectedLmpDate;
    private boolean isFirstLoad = true;

    private DateTimeFormatter getDisplayFormatter() {
        return DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale.getDefault());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_usg);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
        playEntranceAnimations();
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

        // USG cards
        cardUsg1 = findViewById(R.id.cardUsg1);
        cardUsg2 = findViewById(R.id.cardUsg2);
        cardUsg3 = findViewById(R.id.cardUsg3);
        cardUsg4 = findViewById(R.id.cardUsg4);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.slide_down_fade_out);
        });

        AnimationUtils.addTouchScaleEffect(cardDatePicker);
        cardDatePicker.setOnClickListener(v -> {
            AnimationUtils.pulse(v);
            showDatePicker();
        });
    }

    private void playEntranceAnimations() {
        // Animate date picker card
        cardDatePicker.setAlpha(0f);
        cardDatePicker.setTranslationY(40f);
        cardDatePicker.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(100)
                .setInterpolator(new android.view.animation.DecelerateInterpolator(2f))
                .start();
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        if (selectedLmpDate != null) {
            calendar.set(Calendar.YEAR, selectedLmpDate.getYear());
            calendar.set(Calendar.MONTH, selectedLmpDate.getMonthValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, selectedLmpDate.getDayOfMonth());
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedLmpDate = LocalDate.of(year, month + 1, dayOfMonth);
                    isFirstLoad = false;
                    updateUI();
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
        tvSelectedDate.setText(selectedLmpDate.format(getDisplayFormatter()));

        // Show results section with animation
        if (resultsContainer.getVisibility() != View.VISIBLE) {
            resultsContainer.setAlpha(0f);
            resultsContainer.setTranslationY(60f);
            resultsContainer.setVisibility(View.VISIBLE);
            resultsContainer.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(500)
                    .setStartDelay(100)
                    .setInterpolator(new android.view.animation.DecelerateInterpolator(2f))
                    .start();
        }

        // Calculate POG
        long totalDays = ChronoUnit.DAYS.between(selectedLmpDate, LocalDate.now());
        long weeks = totalDays / 7;
        long days = totalDays % 7;

        if (totalDays >= 0) {
            // Animate the week count
            if (!isFirstLoad) {
                AnimationUtils.countUp(tvPogWeeks, 0, (int) weeks, 800, "");
            } else {
                tvPogWeeks.setText(String.valueOf(weeks));
            }
            tvPogDays.setText(", Day " + days);

            // Animate progress bar
            AnimationUtils.animateProgress(progressPregnancy, (int) weeks, 1000);
        } else {
            tvPogWeeks.setText("--");
            tvPogDays.setText("");
            progressPregnancy.setProgress(0);
        }

        // Calculate EDD
        LocalDate edd = selectedLmpDate.plusMonths(9).plusDays(7);
        tvEdd.setText("EDD: " + edd.format(getDisplayFormatter()));

        // Update USG dates
        tvUsg1Dates.setText(DatesHelper.getUSG1DateRange.apply(selectedLmpDate));
        tvUsg2Dates.setText(DatesHelper.getUSG2DateRange.apply(selectedLmpDate));
        tvUsg3Dates.setText(DatesHelper.getUSG3DateRange.apply(selectedLmpDate));
        tvUsg4Dates.setText(DatesHelper.getUSG4DateRange.apply(selectedLmpDate));

        // Animate USG cards with stagger
        animateUSGCards();
    }

    private void animateUSGCards() {
        if (cardUsg1 != null) {
            AnimationUtils.scaleInBounce(cardUsg1, 0);
        }
        if (cardUsg2 != null) {
            AnimationUtils.scaleInBounce(cardUsg2, 100);
        }
        if (cardUsg3 != null) {
            AnimationUtils.scaleInBounce(cardUsg3, 200);
        }
        if (cardUsg4 != null) {
            AnimationUtils.scaleInBounce(cardUsg4, 300);
        }
    }
}
