package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.esi.mahina.Notifications.NotificationScheduler;
import com.esi.mahina.R;
import com.esi.mahina.data.Constants;
import com.esi.mahina.data.model.PregnancyData;
import com.esi.mahina.data.model.USGScheduleItem;
import com.esi.mahina.ui.patient.usg.PatientUSGViewModel;
import com.esi.mahina.utils.AnimationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

/**
 * Patient USG Activity - displays pregnancy timeline and USG schedule.
 * Uses PatientUSGViewModel for data management.
 */
public class PatientUSGActivity extends BaseActivity {

    private PatientUSGViewModel viewModel;

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

    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_usg);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PatientUSGViewModel.class);

        initViews();
        setupClickListeners();
        setupObservers();
        playEntranceAnimations();
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

    private void setupObservers() {
        // Observe LMP date changes
        viewModel.getLmpDate().observe(this, lmpDate -> {
            if (lmpDate != null) {
                tvSelectedDate.setText(lmpDate.format(viewModel.getDisplayFormatter()));
            }
        });

        // Observe saved status
        viewModel.getHasDataSaved().observe(this, hasSaved -> {
            if (hasSaved && tvSavedStatus.getVisibility() != View.VISIBLE) {
                tvSavedStatus.setAlpha(0f);
                tvSavedStatus.setVisibility(View.VISIBLE);
                tvSavedStatus.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .start();
            }
        });

        // Observe pregnancy data changes
        viewModel.getPregnancyData().observe(this, pregnancyData -> {
            if (pregnancyData != null) {
                updateUIWithPregnancyData(pregnancyData);
            }
        });

        // Observe USG schedule
        viewModel.getUsgSchedule().observe(this, schedule -> {
            if (schedule != null && !schedule.isEmpty()) {
                updateUSGScheduleUI(schedule);
            }
        });
    }

    private void playEntranceAnimations() {
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

        LocalDate currentLmp = viewModel.getLmpDate().getValue();
        if (currentLmp != null) {
            calendar.set(Calendar.YEAR, currentLmp.getYear());
            calendar.set(Calendar.MONTH, currentLmp.getMonthValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, currentLmp.getDayOfMonth());
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                    viewModel.setPendingLmpDate(selectedDate);
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
        LocalDate pendingDate = viewModel.getPendingLmpDate();
        if (pendingDate == null) return;

        DateTimeFormatter formatter = viewModel.getDisplayFormatter();

        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirm LMP Date")
                .setMessage("Save " + pendingDate.format(formatter) + " as your Last Menstrual Period date?\n\nThis will be used to calculate your pregnancy timeline and schedule reminders.")
                .setPositiveButton("Save", (dialog, which) -> {
                    isFirstLoad = false;
                    viewModel.confirmLmpDate();
                    scheduleNotifications();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void updateUIWithPregnancyData(PregnancyData data) {
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

        // Update POG
        int weeks = data.getGestationWeeks();
        int days = data.getGestationDays();

        if (weeks >= 0 && days >= 0) {
            if (!isFirstLoad) {
                AnimationUtils.countUp(tvPogWeeks, 0, weeks, 800, "");
            } else {
                tvPogWeeks.setText(String.valueOf(weeks));
            }
            tvPogDays.setText(", Day " + days);
            AnimationUtils.animateProgress(progressPregnancy, weeks, 1000);
        } else {
            tvPogWeeks.setText("--");
            tvPogDays.setText("");
            progressPregnancy.setProgress(0);
        }

        // Update EDD
        DateTimeFormatter formatter = viewModel.getDisplayFormatter();
        LocalDate edd = data.getExpectedDeliveryDate();
        tvEdd.setText("EDD: " + edd.format(formatter));

        long daysToEdd = ChronoUnit.DAYS.between(LocalDate.now(), edd);
        if (daysToEdd > 0) {
            tvDaysRemaining.setText(daysToEdd + " days to go");
        } else if (daysToEdd == 0) {
            tvDaysRemaining.setText("Today is the day!");
        } else {
            tvDaysRemaining.setText(Math.abs(daysToEdd) + " days overdue");
        }
    }

    private void updateUSGScheduleUI(List<USGScheduleItem> schedule) {
        DateTimeFormatter formatter = viewModel.getScheduleFormatter();
        LocalDate today = LocalDate.now();

        if (schedule.size() >= 4) {
            // Update date ranges
            tvUsg1Dates.setText(schedule.get(0).getFormattedDateRange(formatter));
            tvUsg2Dates.setText(schedule.get(1).getFormattedDateRange(formatter));
            tvUsg3Dates.setText(schedule.get(2).getFormattedDateRange(formatter));
            tvUsg4Dates.setText(schedule.get(3).getFormattedDateRange(formatter));

            // Update status with animations
            updateUSGCardAnimated(cardUsg1, tvUsg1Status, tvUsg1StatusText, schedule.get(0), today, 0);
            updateUSGCardAnimated(cardUsg2, tvUsg2Status, tvUsg2StatusText, schedule.get(1), today, 100);
            updateUSGCardSimpleAnimated(cardUsg3, tvUsg3Status, schedule.get(2), today, 200);
            updateUSGCardSimpleAnimated(cardUsg4, tvUsg4Status, schedule.get(3), today, 300);
        }
    }

    private void updateUSGCardAnimated(MaterialCardView card, TextView statusIcon, TextView statusText,
                                        USGScheduleItem usg, LocalDate today, long delay) {
        card.postDelayed(() -> {
            updateUSGCard(card, statusIcon, statusText, usg, today);
            AnimationUtils.scaleInBounce(card, 0);
        }, delay);
    }

    private void updateUSGCardSimpleAnimated(MaterialCardView card, TextView statusIcon,
                                              USGScheduleItem usg, LocalDate today, long delay) {
        card.postDelayed(() -> {
            updateUSGCardSimple(card, statusIcon, usg, today);
            AnimationUtils.scaleInBounce(card, 0);
        }, delay);
    }

    private void updateUSGCard(MaterialCardView card, TextView statusIcon, TextView statusText,
                               USGScheduleItem usg, LocalDate today) {
        USGScheduleItem.Status status = usg.getStatus(today);

        switch (status) {
            case COMPLETED:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
                statusIcon.setText("✓");
                statusIcon.setTextColor(getResources().getColor(R.color.success, getTheme()));
                if (statusText != null) {
                    statusText.setText("Completed");
                    statusText.setTextColor(getResources().getColor(R.color.success, getTheme()));
                }
                break;

            case TODAY:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
                statusIcon.setText("●");
                statusIcon.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
                if (statusText != null) {
                    statusText.setText("Due now!");
                    statusText.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
                }
                AnimationUtils.startContinuousPulse(statusIcon);
                break;

            case UPCOMING_SOON:
            case UPCOMING:
            default:
                card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
                statusIcon.setText("○");
                statusIcon.setTextColor(getResources().getColor(R.color.text_tertiary, getTheme()));
                if (statusText != null) {
                    long daysUntil = usg.getDaysUntilStart(today);
                    statusText.setText("In " + daysUntil + " days");
                    statusText.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
                }
                break;
        }
    }

    private void updateUSGCardSimple(MaterialCardView card, TextView statusIcon,
                                      USGScheduleItem usg, LocalDate today) {
        USGScheduleItem.Status status = usg.getStatus(today);

        switch (status) {
            case COMPLETED:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
                statusIcon.setText("✓");
                statusIcon.setTextColor(getResources().getColor(R.color.success, getTheme()));
                break;

            case TODAY:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
                statusIcon.setText("●");
                statusIcon.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
                AnimationUtils.startContinuousPulse(statusIcon);
                break;

            case UPCOMING_SOON:
            case UPCOMING:
            default:
                card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
                statusIcon.setText("○");
                statusIcon.setTextColor(getResources().getColor(R.color.text_tertiary, getTheme()));
                break;
        }
    }

    private void scheduleNotifications() {
        if (!viewModel.areNotificationsEnabled()) return;

        LocalDate lmpDate = viewModel.getLmpDate().getValue();
        if (lmpDate == null) return;

        // Schedule USG reminders
        scheduleUSGNotifications("USG 1 (Dating Scan)", lmpDate.plusWeeks(Constants.USG1_START_WEEK));
        scheduleUSGNotifications("USG 2 (NT Scan)", lmpDate.plusWeeks(Constants.USG2_START_WEEK));
        scheduleUSGNotifications("USG 3 (Anomaly Scan)", lmpDate.plusWeeks(Constants.USG3_START_WEEK));
        scheduleUSGNotifications("USG 4 (Growth Scan)", lmpDate.plusWeeks(Constants.USG4_START_WEEK));

        // Schedule EDD reminder
        LocalDate edd = lmpDate.plusMonths(9).plusDays(7);
        scheduleUSGNotifications("Expected Delivery Date", edd);
    }

    private void scheduleUSGNotifications(String eventName, LocalDate eventDate) {
        LocalDate today = LocalDate.now();

        // 3 days before
        LocalDate threeDaysBefore = eventDate.minusDays(3);
        if (threeDaysBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, threeDaysBefore,
                    "Upcoming: " + eventName,
                    "Your " + eventName + " is in 3 days!",
                    Constants.CHANNEL_USG_REMINDERS);
        }

        // 2 days before
        LocalDate twoDaysBefore = eventDate.minusDays(2);
        if (twoDaysBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, twoDaysBefore,
                    "Reminder: " + eventName,
                    "Your " + eventName + " is in 2 days!",
                    Constants.CHANNEL_USG_REMINDERS);
        }

        // 1 day before
        LocalDate oneDayBefore = eventDate.minusDays(1);
        if (oneDayBefore.isAfter(today)) {
            NotificationScheduler.scheduleNotification(this, oneDayBefore,
                    "Tomorrow: " + eventName,
                    "Your " + eventName + " is tomorrow!",
                    Constants.CHANNEL_USG_REMINDERS);
        }

        // On the day
        if (!eventDate.isBefore(today)) {
            NotificationScheduler.scheduleNotification(this, eventDate,
                    "Today: " + eventName,
                    "Your " + eventName + " is scheduled for today!",
                    Constants.CHANNEL_USG_REMINDERS);
        }
    }
}
