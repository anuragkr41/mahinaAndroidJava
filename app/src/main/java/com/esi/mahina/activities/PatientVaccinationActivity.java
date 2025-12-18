package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.esi.mahina.Notifications.NotificationScheduler;
import com.esi.mahina.R;
import com.esi.mahina.data.Constants;
import com.esi.mahina.data.model.VaccinationItem;
import com.esi.mahina.ui.patient.vaccination.PatientVaccinationViewModel;
import com.esi.mahina.utils.AnimationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * Patient Vaccination Activity - displays vaccination schedule for baby.
 * Uses PatientVaccinationViewModel for data management.
 */
public class PatientVaccinationActivity extends BaseActivity {

    private PatientVaccinationViewModel viewModel;

    private ImageButton btnBack;
    private MaterialCardView cardDatePicker;
    private TextView tvSelectedDate;
    private TextView tvSavedStatus;
    private LinearLayout resultsContainer;

    // Baby age
    private TextView tvBabyAge;

    // Vaccination date TextViews
    private TextView tvBirthDate;
    private TextView tv6WeeksDate;
    private TextView tv6WeeksStatus;
    private TextView tv10WeeksDate;
    private TextView tv14WeeksDate;

    // Cards
    private MaterialCardView cardBirth;
    private MaterialCardView card6Weeks;
    private MaterialCardView card10Weeks;
    private MaterialCardView card14Weeks;

    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_vaccination);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PatientVaccinationViewModel.class);

        initViews();
        setupClickListeners();
        setupObservers();
        playEntranceAnimations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh baby age when returning to screen
        viewModel.refreshBabyAge();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardDatePicker = findViewById(R.id.cardDatePicker);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSavedStatus = findViewById(R.id.tvSavedStatus);
        resultsContainer = findViewById(R.id.resultsContainer);

        // Baby age
        tvBabyAge = findViewById(R.id.tvBabyAge);

        // Vaccination dates
        tvBirthDate = findViewById(R.id.tvBirthDate);
        tv6WeeksDate = findViewById(R.id.tv6WeeksDate);
        tv6WeeksStatus = findViewById(R.id.tv6WeeksStatus);
        tv10WeeksDate = findViewById(R.id.tv10WeeksDate);
        tv14WeeksDate = findViewById(R.id.tv14WeeksDate);

        // Cards
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

    private void setupObservers() {
        // Observe baby DOB changes
        viewModel.getBabyDob().observe(this, dob -> {
            if (dob != null) {
                tvSelectedDate.setText(dob.format(viewModel.getDisplayFormatter()));
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

        // Observe baby age
        viewModel.getBabyAge().observe(this, age -> {
            if (age != null) {
                tvBabyAge.setText(age);
            }
        });

        // Observe core vaccinations (first 4)
        viewModel.getCoreVaccinations().observe(this, vaccinations -> {
            if (vaccinations != null && !vaccinations.isEmpty()) {
                updateVaccinationUI(vaccinations);
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

        LocalDate currentDob = viewModel.getBabyDob().getValue();
        if (currentDob != null) {
            calendar.set(Calendar.YEAR, currentDob.getYear());
            calendar.set(Calendar.MONTH, currentDob.getMonthValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, currentDob.getDayOfMonth());
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                    viewModel.setPendingDob(selectedDate);
                    showConfirmationDialog();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Prevent selecting future dates
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void showConfirmationDialog() {
        LocalDate pendingDate = viewModel.getPendingDob();
        if (pendingDate == null) return;

        DateTimeFormatter formatter = viewModel.getDisplayFormatter();

        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirm Baby's Date of Birth")
                .setMessage("Save " + pendingDate.format(formatter) + " as your baby's date of birth?\n\nThis will be used to calculate the vaccination schedule and set up reminders.")
                .setPositiveButton("Save", (dialog, which) -> {
                    isFirstLoad = false;
                    viewModel.confirmDob();
                    scheduleNotifications();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void updateVaccinationUI(List<VaccinationItem> vaccinations) {
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

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = viewModel.getScheduleFormatter();

        if (vaccinations.size() >= 4) {
            // Birth vaccination
            VaccinationItem birth = vaccinations.get(0);
            String birthStatus = birth.isCompleted(today) ? " - Completed" : " - Upcoming";
            tvBirthDate.setText(birth.getFormattedDate(formatter) + birthStatus);
            updateVaccineCardColor(cardBirth, birth, today);

            // 6 Weeks vaccination
            VaccinationItem week6 = vaccinations.get(1);
            tv6WeeksDate.setText(week6.getFormattedDate(formatter));
            updateVaccineCardAnimated(card6Weeks, tv6WeeksStatus, week6, today, 0);

            // 10 Weeks vaccination
            VaccinationItem week10 = vaccinations.get(2);
            tv10WeeksDate.setText(week10.getFormattedDate(formatter));
            updateVaccineCardSimpleAnimated(card10Weeks, week10, today, 100);

            // 14 Weeks vaccination
            VaccinationItem week14 = vaccinations.get(3);
            tv14WeeksDate.setText(week14.getFormattedDate(formatter));
            updateVaccineCardSimpleAnimated(card14Weeks, week14, today, 200);

            // Animate birth card
            if (cardBirth != null && !isFirstLoad) {
                AnimationUtils.scaleInBounce(cardBirth, 0);
            }
        }
    }

    private void updateVaccineCardColor(MaterialCardView card, VaccinationItem vaccine, LocalDate today) {
        if (vaccine.isCompleted(today)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
        } else {
            card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
        }
    }

    private void updateVaccineCardAnimated(MaterialCardView card, TextView statusView,
                                            VaccinationItem vaccine, LocalDate today, long delay) {
        card.postDelayed(() -> {
            updateVaccineCard(card, statusView, vaccine, today);
            AnimationUtils.scaleInBounce(card, 0);
        }, delay);
    }

    private void updateVaccineCardSimpleAnimated(MaterialCardView card,
                                                  VaccinationItem vaccine, LocalDate today, long delay) {
        card.postDelayed(() -> {
            updateVaccineCardSimple(card, vaccine, today);
            AnimationUtils.scaleInBounce(card, 0);
        }, delay);
    }

    private void updateVaccineCard(MaterialCardView card, TextView statusView,
                                   VaccinationItem vaccine, LocalDate today) {
        VaccinationItem.Status status = vaccine.getStatus(today);

        switch (status) {
            case COMPLETED:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
                if (statusView != null) {
                    statusView.setText("Completed");
                    statusView.setTextColor(getResources().getColor(R.color.success, getTheme()));
                }
                break;

            case TODAY:
                card.setCardBackgroundColor(getResources().getColor(R.color.warning_light, getTheme()));
                if (statusView != null) {
                    statusView.setText("Today!");
                    statusView.setTextColor(getResources().getColor(R.color.warning, getTheme()));
                    AnimationUtils.startContinuousPulse(statusView);
                }
                break;

            case TOMORROW:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
                if (statusView != null) {
                    statusView.setText("Tomorrow!");
                    statusView.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
                    AnimationUtils.startContinuousPulse(statusView);
                }
                break;

            case UPCOMING_SOON:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
                if (statusView != null) {
                    statusView.setText("In " + vaccine.getDaysUntil(today) + " days");
                    statusView.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
                    AnimationUtils.startContinuousPulse(statusView);
                }
                break;

            case UPCOMING:
            default:
                card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
                if (statusView != null) {
                    statusView.setText("In " + vaccine.getDaysUntil(today) + " days");
                    statusView.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
                }
                break;
        }
    }

    private void updateVaccineCardSimple(MaterialCardView card, VaccinationItem vaccine, LocalDate today) {
        VaccinationItem.Status status = vaccine.getStatus(today);

        switch (status) {
            case COMPLETED:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
                break;

            case TODAY:
            case TOMORROW:
            case UPCOMING_SOON:
                card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
                break;

            case UPCOMING:
            default:
                card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
                break;
        }
    }

    private void scheduleNotifications() {
        if (!viewModel.areNotificationsEnabled()) return;

        LocalDate babyDob = viewModel.getBabyDob().getValue();
        if (babyDob == null) return;

        List<VaccinationItem> schedule = viewModel.getVaccinationSchedule().getValue();
        if (schedule == null) return;

        LocalDate today = LocalDate.now();

        for (VaccinationItem vaccine : schedule) {
            LocalDate vaccineDate = vaccine.getDueDate();
            String vaccineName = vaccine.getAgeName() + " Vaccination";

            // Only schedule for future dates
            if (vaccineDate.isAfter(today)) {
                // 3 days before
                LocalDate threeDaysBefore = vaccineDate.minusDays(3);
                if (threeDaysBefore.isAfter(today)) {
                    NotificationScheduler.scheduleNotification(this, threeDaysBefore,
                            "Upcoming: " + vaccineName,
                            "Your baby's " + vaccine.getAgeName() + " vaccination is in 3 days!",
                            Constants.CHANNEL_VACCINATION_REMINDERS);
                }

                // 2 days before
                LocalDate twoDaysBefore = vaccineDate.minusDays(2);
                if (twoDaysBefore.isAfter(today)) {
                    NotificationScheduler.scheduleNotification(this, twoDaysBefore,
                            "Reminder: " + vaccineName,
                            "Your baby's " + vaccine.getAgeName() + " vaccination is in 2 days!",
                            Constants.CHANNEL_VACCINATION_REMINDERS);
                }

                // 1 day before
                LocalDate oneDayBefore = vaccineDate.minusDays(1);
                if (oneDayBefore.isAfter(today)) {
                    NotificationScheduler.scheduleNotification(this, oneDayBefore,
                            "Tomorrow: " + vaccineName,
                            "Your baby's " + vaccine.getAgeName() + " vaccination is tomorrow!",
                            Constants.CHANNEL_VACCINATION_REMINDERS);
                }

                // On the day
                NotificationScheduler.scheduleNotification(this, vaccineDate,
                        "Today: " + vaccineName,
                        "Your baby's " + vaccine.getAgeName() + " vaccination is scheduled for today!",
                        Constants.CHANNEL_VACCINATION_REMINDERS);
            }
        }
    }
}
