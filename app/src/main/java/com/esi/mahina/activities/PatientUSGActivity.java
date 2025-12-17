package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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
import com.esi.mahina.Notifications.NotificationScheduler;
import com.esi.mahina.R;
import com.esi.mahina.calculations.DatesHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class PatientUSGActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialCardView cardDatePicker;
    private TextView tvSelectedDate;
    private TextView tvSavedStatus;
    private LinearLayout resultsContainer;

    // Hero card TextViews
    private TextView tvPogWeeks;
    private TextView tvPogDays;
    private ProgressBar progressPregnancy;
    private TextView tvEdd;
    private TextView tvDaysRemaining;

    // USG Date TextViews
    private TextView tvUsg1Dates;
    private TextView tvUsg2Dates;
    private TextView tvUsg3Dates;
    private TextView tvUsg4Dates;

    // USG Status TextViews and Cards
    private TextView tvUsg1Status;
    private TextView tvUsg1StatusText;
    private TextView tvUsg2Status;
    private TextView tvUsg2StatusText;
    private TextView tvUsg3Status;
    private TextView tvUsg4Status;
    private MaterialCardView cardUsg1;
    private MaterialCardView cardUsg2;
    private MaterialCardView cardUsg3;
    private MaterialCardView cardUsg4;

    private LocalDate selectedLmpDate;
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_usg);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
        loadSavedData();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardDatePicker = findViewById(R.id.cardDatePicker);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSavedStatus = findViewById(R.id.tvSavedStatus);
        resultsContainer = findViewById(R.id.resultsContainer);

        // Hero card
        tvPogWeeks = findViewById(R.id.tvPogWeeks);
        tvPogDays = findViewById(R.id.tvPogDays);
        progressPregnancy = findViewById(R.id.progressPregnancy);
        tvEdd = findViewById(R.id.tvEdd);
        tvDaysRemaining = findViewById(R.id.tvDaysRemaining);

        // USG dates
        tvUsg1Dates = findViewById(R.id.tvUsg1Dates);
        tvUsg2Dates = findViewById(R.id.tvUsg2Dates);
        tvUsg3Dates = findViewById(R.id.tvUsg3Dates);
        tvUsg4Dates = findViewById(R.id.tvUsg4Dates);

        // USG status
        tvUsg1Status = findViewById(R.id.tvUsg1Status);
        tvUsg1StatusText = findViewById(R.id.tvUsg1StatusText);
        tvUsg2Status = findViewById(R.id.tvUsg2Status);
        tvUsg2StatusText = findViewById(R.id.tvUsg2StatusText);
        tvUsg3Status = findViewById(R.id.tvUsg3Status);
        tvUsg4Status = findViewById(R.id.tvUsg4Status);

        // USG cards
        cardUsg1 = findViewById(R.id.cardUsg1);
        cardUsg2 = findViewById(R.id.cardUsg2);
        cardUsg3 = findViewById(R.id.cardUsg3);
        cardUsg4 = findViewById(R.id.cardUsg4);
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

    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String lmpDateStr = prefs.getString(PatientHomeActivity.KEY_LMP_DATE, null);

        if (lmpDateStr != null) {
            try {
                selectedLmpDate = LocalDate.parse(lmpDateStr);
                updateUI();
            } catch (Exception e) {
                // Keep default state
            }
        }
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
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedLmpDate = LocalDate.of(year, month + 1, dayOfMonth);
                        saveLmpDate();
                        updateUI();
                        scheduleNotifications();
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

    private void saveLmpDate() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(PatientHomeActivity.KEY_LMP_DATE, selectedLmpDate.toString()).apply();
    }

    private void updateUI() {
        if (selectedLmpDate == null) return;

        // Update selected date display
        tvSelectedDate.setText(selectedLmpDate.format(displayFormatter));
        tvSavedStatus.setVisibility(View.VISIBLE);

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

        long daysToEdd = ChronoUnit.DAYS.between(LocalDate.now(), edd);
        if (daysToEdd > 0) {
            tvDaysRemaining.setText(daysToEdd + " days to go");
        } else if (daysToEdd == 0) {
            tvDaysRemaining.setText("Today is the day!");
        } else {
            tvDaysRemaining.setText(Math.abs(daysToEdd) + " days overdue");
        }

        // Update USG dates
        tvUsg1Dates.setText(DatesHelper.getUSG1DateRange.apply(selectedLmpDate));
        tvUsg2Dates.setText(DatesHelper.getUSG2DateRange.apply(selectedLmpDate));
        tvUsg3Dates.setText(DatesHelper.getUSG3DateRange.apply(selectedLmpDate));
        tvUsg4Dates.setText(DatesHelper.getUSG4DateRange.apply(selectedLmpDate));

        // Update USG status indicators
        updateUSGStatus();
    }

    private void updateUSGStatus() {
        LocalDate today = LocalDate.now();

        // USG 1: 6-8 weeks
        LocalDate usg1Start = selectedLmpDate.plusWeeks(6);
        LocalDate usg1End = selectedLmpDate.plusWeeks(8);
        updateUSGCard(cardUsg1, tvUsg1Status, tvUsg1StatusText, today, usg1Start, usg1End);

        // USG 2: 11-13+6 weeks
        LocalDate usg2Start = selectedLmpDate.plusWeeks(11);
        LocalDate usg2End = selectedLmpDate.plusWeeks(13).plusDays(6);
        updateUSGCard(cardUsg2, tvUsg2Status, tvUsg2StatusText, today, usg2Start, usg2End);

        // USG 3: 18-20 weeks
        LocalDate usg3Start = selectedLmpDate.plusWeeks(18);
        LocalDate usg3End = selectedLmpDate.plusWeeks(20);
        updateUSGCardSimple(cardUsg3, tvUsg3Status, today, usg3Start, usg3End);

        // USG 4: 30-32 weeks
        LocalDate usg4Start = selectedLmpDate.plusWeeks(30);
        LocalDate usg4End = selectedLmpDate.plusWeeks(32);
        updateUSGCardSimple(cardUsg4, tvUsg4Status, today, usg4Start, usg4End);
    }

    private void updateUSGCard(MaterialCardView card, TextView statusIcon, TextView statusText,
                               LocalDate today, LocalDate start, LocalDate end) {
        if (today.isAfter(end)) {
            // Completed
            card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
            statusIcon.setText("✓");
            statusIcon.setTextColor(getResources().getColor(R.color.success, getTheme()));
            if (statusText != null) {
                statusText.setText("Completed");
                statusText.setTextColor(getResources().getColor(R.color.success, getTheme()));
            }
        } else if (!today.isBefore(start) && !today.isAfter(end)) {
            // Current window
            card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
            statusIcon.setText("●");
            statusIcon.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
            if (statusText != null) {
                statusText.setText("Due now!");
                statusText.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
            }
        } else {
            // Upcoming
            card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
            statusIcon.setText("○");
            statusIcon.setTextColor(getResources().getColor(R.color.text_tertiary, getTheme()));
            if (statusText != null) {
                long daysUntil = ChronoUnit.DAYS.between(today, start);
                statusText.setText("In " + daysUntil + " days");
                statusText.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
            }
        }
    }

    private void updateUSGCardSimple(MaterialCardView card, TextView statusIcon,
                                      LocalDate today, LocalDate start, LocalDate end) {
        if (today.isAfter(end)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
            statusIcon.setText("✓");
            statusIcon.setTextColor(getResources().getColor(R.color.success, getTheme()));
        } else if (!today.isBefore(start) && !today.isAfter(end)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
            statusIcon.setText("●");
            statusIcon.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
        } else {
            card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
            statusIcon.setText("○");
            statusIcon.setTextColor(getResources().getColor(R.color.text_tertiary, getTheme()));
        }
    }

    private void scheduleNotifications() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        boolean notificationsEnabled = prefs.getBoolean(PatientHomeActivity.KEY_NOTIFICATIONS_ENABLED, true);

        if (!notificationsEnabled || selectedLmpDate == null) return;

        // Schedule notifications for each USG
        scheduleUSGNotifications("USG 1 (Dating Scan)", selectedLmpDate.plusWeeks(6));
        scheduleUSGNotifications("USG 2 (NT Scan)", selectedLmpDate.plusWeeks(11));
        scheduleUSGNotifications("USG 3 (Anomaly Scan)", selectedLmpDate.plusWeeks(18));
        scheduleUSGNotifications("USG 4 (Growth Scan)", selectedLmpDate.plusWeeks(30));

        // Schedule EDD notification
        LocalDate edd = selectedLmpDate.plusMonths(9).plusDays(7);
        scheduleUSGNotifications("Expected Delivery Date", edd);
    }

    private void scheduleUSGNotifications(String eventName, LocalDate eventDate) {
        LocalDate today = LocalDate.now();

        // 72 hours before (3 days)
        LocalDate threeDaysBefore = eventDate.minusDays(3);
        if (threeDaysBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, threeDaysBefore,
                    "Upcoming: " + eventName,
                    "Your " + eventName + " is in 3 days!");
        }

        // 48 hours before (2 days)
        LocalDate twoDaysBefore = eventDate.minusDays(2);
        if (twoDaysBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, twoDaysBefore,
                    "Reminder: " + eventName,
                    "Your " + eventName + " is in 2 days!");
        }

        // 24 hours before (1 day)
        LocalDate oneDayBefore = eventDate.minusDays(1);
        if (oneDayBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, oneDayBefore,
                    "Tomorrow: " + eventName,
                    "Your " + eventName + " is tomorrow!");
        }

        // On the day
        if (!eventDate.isBefore(today)) {
            NotificationScheduler.scheduleNotification(this, eventDate,
                    "Today: " + eventName,
                    "Your " + eventName + " is scheduled for today!");
        }
    }
}
