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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.esi.mahina.Notifications.NotificationScheduler;
import com.esi.mahina.R;
import com.esi.mahina.calculations.DatesHelper;
import com.esi.mahina.utils.AnimationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class PatientUSGActivity extends BaseActivity {

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
    private LocalDate pendingLmpDate; // Temporary storage before confirmation
    private boolean isFirstLoad = true;

    private DateTimeFormatter getDisplayFormatter() {
        return DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale.getDefault());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_usg);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
        playEntranceAnimations();
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
                (view, year, month, dayOfMonth) -> {
                    pendingLmpDate = LocalDate.of(year, month + 1, dayOfMonth);
                    showConfirmationDialog();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void showConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirm LMP Date")
                .setMessage("Save " + pendingLmpDate.format(getDisplayFormatter()) + " as your Last Menstrual Period date?\n\nThis will be used to calculate your pregnancy timeline and schedule reminders.")
                .setPositiveButton("Save", (dialog, which) -> {
                    selectedLmpDate = pendingLmpDate;
                    saveLmpDate();
                    isFirstLoad = false;
                    updateUI();
                    scheduleNotifications();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    pendingLmpDate = null;
                    dialog.dismiss();
                })
                .show();
    }

    private void saveLmpDate() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(PatientHomeActivity.KEY_LMP_DATE, selectedLmpDate.toString()).apply();
    }

    private void updateUI() {
        if (selectedLmpDate == null) return;

        // Update selected date display
        tvSelectedDate.setText(selectedLmpDate.format(getDisplayFormatter()));

        // Animate saved status badge
        if (tvSavedStatus.getVisibility() != View.VISIBLE) {
            tvSavedStatus.setAlpha(0f);
            tvSavedStatus.setVisibility(View.VISIBLE);
            tvSavedStatus.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .start();
        }

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

        // Update USG status indicators with staggered animations
        updateUSGStatusAnimated();
    }

    private void updateUSGStatusAnimated() {
        LocalDate today = LocalDate.now();

        // USG 1: 6-8 weeks
        LocalDate usg1Start = selectedLmpDate.plusWeeks(6);
        LocalDate usg1End = selectedLmpDate.plusWeeks(8);
        updateUSGCardAnimated(cardUsg1, tvUsg1Status, tvUsg1StatusText, today, usg1Start, usg1End, 0);

        // USG 2: 11-13+6 weeks
        LocalDate usg2Start = selectedLmpDate.plusWeeks(11);
        LocalDate usg2End = selectedLmpDate.plusWeeks(13).plusDays(6);
        updateUSGCardAnimated(cardUsg2, tvUsg2Status, tvUsg2StatusText, today, usg2Start, usg2End, 100);

        // USG 3: 18-20 weeks
        LocalDate usg3Start = selectedLmpDate.plusWeeks(18);
        LocalDate usg3End = selectedLmpDate.plusWeeks(20);
        updateUSGCardSimpleAnimated(cardUsg3, tvUsg3Status, today, usg3Start, usg3End, 200);

        // USG 4: 30-32 weeks
        LocalDate usg4Start = selectedLmpDate.plusWeeks(30);
        LocalDate usg4End = selectedLmpDate.plusWeeks(32);
        updateUSGCardSimpleAnimated(cardUsg4, tvUsg4Status, today, usg4Start, usg4End, 300);
    }

    private void updateUSGCardAnimated(MaterialCardView card, TextView statusIcon, TextView statusText,
                                        LocalDate today, LocalDate start, LocalDate end, long delay) {
        card.postDelayed(() -> {
            updateUSGCard(card, statusIcon, statusText, today, start, end);
            AnimationUtils.scaleInBounce(card, 0);
        }, delay);
    }

    private void updateUSGCardSimpleAnimated(MaterialCardView card, TextView statusIcon,
                                              LocalDate today, LocalDate start, LocalDate end, long delay) {
        card.postDelayed(() -> {
            updateUSGCardSimple(card, statusIcon, today, start, end);
            AnimationUtils.scaleInBounce(card, 0);
        }, delay);
    }

    private void updateUSGCard(MaterialCardView card, TextView statusIcon, TextView statusText,
                               LocalDate today, LocalDate start, LocalDate end) {
        if (today.isAfter(end)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
            statusIcon.setText("✓");
            statusIcon.setTextColor(getResources().getColor(R.color.success, getTheme()));
            if (statusText != null) {
                statusText.setText("Completed");
                statusText.setTextColor(getResources().getColor(R.color.success, getTheme()));
            }
        } else if (!today.isBefore(start) && !today.isAfter(end)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
            statusIcon.setText("●");
            statusIcon.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
            if (statusText != null) {
                statusText.setText("Due now!");
                statusText.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
            }
            // Add pulsing effect for current USG
            AnimationUtils.startContinuousPulse(statusIcon);
        } else {
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
            AnimationUtils.startContinuousPulse(statusIcon);
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

        scheduleUSGNotifications("USG 1 (Dating Scan)", selectedLmpDate.plusWeeks(6));
        scheduleUSGNotifications("USG 2 (NT Scan)", selectedLmpDate.plusWeeks(11));
        scheduleUSGNotifications("USG 3 (Anomaly Scan)", selectedLmpDate.plusWeeks(18));
        scheduleUSGNotifications("USG 4 (Growth Scan)", selectedLmpDate.plusWeeks(30));

        LocalDate edd = selectedLmpDate.plusMonths(9).plusDays(7);
        scheduleUSGNotifications("Expected Delivery Date", edd);
    }

    private void scheduleUSGNotifications(String eventName, LocalDate eventDate) {
        LocalDate today = LocalDate.now();

        LocalDate threeDaysBefore = eventDate.minusDays(3);
        if (threeDaysBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, threeDaysBefore,
                    "Upcoming: " + eventName,
                    "Your " + eventName + " is in 3 days!");
        }

        LocalDate twoDaysBefore = eventDate.minusDays(2);
        if (twoDaysBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, twoDaysBefore,
                    "Reminder: " + eventName,
                    "Your " + eventName + " is in 2 days!");
        }

        LocalDate oneDayBefore = eventDate.minusDays(1);
        if (oneDayBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, oneDayBefore,
                    "Tomorrow: " + eventName,
                    "Your " + eventName + " is tomorrow!");
        }

        if (!eventDate.isBefore(today)) {
            NotificationScheduler.scheduleNotification(this, eventDate,
                    "Today: " + eventName,
                    "Your " + eventName + " is scheduled for today!");
        }
    }
}
