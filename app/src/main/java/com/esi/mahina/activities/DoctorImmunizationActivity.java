package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class DoctorImmunizationActivity extends BaseActivity {

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

    // Vaccination cards for animation
    private MaterialCardView cardBirth;
    private MaterialCardView card6Weeks;
    private MaterialCardView card10Weeks;
    private MaterialCardView card14Weeks;

    private LocalDate selectedDob;
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_immunization);
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
        vaccineCardsContainer = findViewById(R.id.vaccineCardsContainer);

        tvBabyAge = findViewById(R.id.tvBabyAge);
        tvBirthDate = findViewById(R.id.tvBirthDate);
        tv6WeeksDate = findViewById(R.id.tv6WeeksDate);
        tv10WeeksDate = findViewById(R.id.tv10WeeksDate);
        tv14WeeksDate = findViewById(R.id.tv14WeeksDate);

        // Vaccination cards
        cardBirth = findViewById(R.id.cardBirth);
        card6Weeks = findViewById(R.id.card6Weeks);
        card10Weeks = findViewById(R.id.card10Weeks);
        card14Weeks = findViewById(R.id.card14Weeks);
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

        if (selectedDob != null) {
            calendar.set(Calendar.YEAR, selectedDob.getYear());
            calendar.set(Calendar.MONTH, selectedDob.getMonthValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, selectedDob.getDayOfMonth());
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDob = LocalDate.of(year, month + 1, dayOfMonth);
                    isFirstLoad = false;
                    updateUI();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Prevent selecting future dates
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void updateUI() {
        if (selectedDob == null) return;

        // Update selected date display
        tvSelectedDate.setText(selectedDob.format(displayFormatter));

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

        // Animate vaccination cards with stagger
        animateVaccineCards();
    }

    private void animateVaccineCards() {
        if (cardBirth != null) {
            AnimationUtils.scaleInBounce(cardBirth, 0);
        }
        if (card6Weeks != null) {
            AnimationUtils.scaleInBounce(card6Weeks, 100);
        }
        if (card10Weeks != null) {
            AnimationUtils.scaleInBounce(card10Weeks, 200);
        }
        if (card14Weeks != null) {
            AnimationUtils.scaleInBounce(card14Weeks, 300);
        }
    }
}
