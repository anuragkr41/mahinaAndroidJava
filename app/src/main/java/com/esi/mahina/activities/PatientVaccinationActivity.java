package com.esi.mahina.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class PatientVaccinationActivity extends AppCompatActivity {

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

    private LocalDate selectedDob;
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    // Vaccination schedule data (IAP India recommendations)
    private static final String[][] VACCINATION_SCHEDULE = {
            {"Birth", "BCG, OPV-0, Hepatitis B-1"},
            {"6 Weeks", "DTwP/DTaP-1, IPV-1, Hep B-2, Hib-1, Rotavirus-1, PCV-1"},
            {"10 Weeks", "DTwP/DTaP-2, IPV-2, Hib-2, Rotavirus-2, PCV-2"},
            {"14 Weeks", "DTwP/DTaP-3, IPV-3, Hib-3, Rotavirus-3, PCV-3"},
            {"6 Months", "OPV-1, Hepatitis B-3"},
            {"9 Months", "MMR-1, OPV-2"},
            {"12 Months", "Hepatitis A-1, Japanese Encephalitis-1"},
            {"15 Months", "MMR-2, Varicella-1, PCV Booster"},
            {"16-18 Months", "DTwP/DTaP Booster-1, IPV Booster-1, Hib Booster"},
            {"18 Months", "Hepatitis A-2, Japanese Encephalitis-2"},
            {"4-6 Years", "DTwP/DTaP Booster-2, OPV-3, Varicella-2, MMR-3"},
            {"10-12 Years", "Tdap/Td, HPV (for girls)"}
    };

    // Days to add for each schedule item
    private static final int[] SCHEDULE_DAYS = {
            0,      // Birth
            42,     // 6 Weeks
            70,     // 10 Weeks
            98,     // 14 Weeks
            180,    // 6 Months
            270,    // 9 Months
            365,    // 12 Months
            456,    // 15 Months
            487,    // 16-18 Months (using 16 months)
            548,    // 18 Months
            1461,   // 4 Years
            3652    // 10 Years
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_vaccination);
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
        String dobStr = prefs.getString(PatientHomeActivity.KEY_BABY_DOB, null);

        if (dobStr != null) {
            try {
                selectedDob = LocalDate.parse(dobStr);
                updateUI();
            } catch (Exception e) {
                // Keep default state
            }
        }
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
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDob = LocalDate.of(year, month + 1, dayOfMonth);
                        saveDob();
                        updateUI();
                        scheduleNotifications();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void saveDob() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(PatientHomeActivity.KEY_BABY_DOB, selectedDob.toString()).apply();
    }

    private void updateUI() {
        if (selectedDob == null) return;

        // Update selected date display
        tvSelectedDate.setText(selectedDob.format(displayFormatter));
        tvSavedStatus.setVisibility(View.VISIBLE);

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
        if (today.isAfter(birthDate) || today.isEqual(birthDate)) {
            tvBirthDate.setText(birthDate.format(DatesHelper.formatter) + " - Completed");
            cardBirth.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
        } else {
            tvBirthDate.setText(birthDate.format(DatesHelper.formatter) + " - Upcoming");
            cardBirth.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
        }

        // 6 Weeks vaccination
        tv6WeeksDate.setText(week6Date.format(DatesHelper.formatter));
        updateVaccineCard(card6Weeks, tv6WeeksStatus, today, week6Date);

        // 10 Weeks vaccination
        tv10WeeksDate.setText(week10Date.format(DatesHelper.formatter));
        updateVaccineCardSimple(card10Weeks, today, week10Date);

        // 14 Weeks vaccination
        tv14WeeksDate.setText(week14Date.format(DatesHelper.formatter));
        updateVaccineCardSimple(card14Weeks, today, week14Date);
    }

    private void updateVaccineCard(MaterialCardView card, TextView statusView, LocalDate today, LocalDate vaccineDate) {
        long daysUntil = ChronoUnit.DAYS.between(today, vaccineDate);

        if (daysUntil < 0) {
            // Past due
            card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
            if (statusView != null) {
                statusView.setText("Completed");
                statusView.setTextColor(getResources().getColor(R.color.success, getTheme()));
            }
        } else if (daysUntil == 0) {
            // Today
            card.setCardBackgroundColor(getResources().getColor(R.color.warning_light, getTheme()));
            if (statusView != null) {
                statusView.setText("Today!");
                statusView.setTextColor(getResources().getColor(R.color.warning, getTheme()));
            }
        } else if (daysUntil <= 3) {
            // Upcoming soon
            card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
            if (statusView != null) {
                if (daysUntil == 1) {
                    statusView.setText("Tomorrow!");
                } else {
                    statusView.setText("In " + daysUntil + " days");
                }
                statusView.setTextColor(getResources().getColor(R.color.primary_rose, getTheme()));
            }
        } else {
            // Future
            card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
            if (statusView != null) {
                statusView.setText("In " + daysUntil + " days");
                statusView.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
            }
        }
    }

    private void updateVaccineCardSimple(MaterialCardView card, LocalDate today, LocalDate vaccineDate) {
        long daysUntil = ChronoUnit.DAYS.between(today, vaccineDate);

        if (daysUntil < 0) {
            card.setCardBackgroundColor(getResources().getColor(R.color.card_completed_bg, getTheme()));
        } else if (daysUntil <= 3) {
            card.setCardBackgroundColor(getResources().getColor(R.color.card_usg_bg, getTheme()));
        } else {
            card.setCardBackgroundColor(getResources().getColor(R.color.background_card, getTheme()));
        }
    }

    private void scheduleNotifications() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        boolean notificationsEnabled = prefs.getBoolean(PatientHomeActivity.KEY_NOTIFICATIONS_ENABLED, true);

        if (!notificationsEnabled || selectedDob == null) return;

        LocalDate today = LocalDate.now();

        for (int i = 0; i < VACCINATION_SCHEDULE.length; i++) {
            LocalDate vaccineDate = selectedDob.plusDays(SCHEDULE_DAYS[i]);
            String vaccineName = VACCINATION_SCHEDULE[i][0] + " Vaccination";

            // Only schedule for future dates
            if (vaccineDate.isAfter(today)) {
                // 72 hours before (3 days)
                LocalDate threeDaysBefore = vaccineDate.minusDays(3);
                if (threeDaysBefore.isAfter(today)) {
                    NotificationScheduler.scheduleNotification(this, threeDaysBefore,
                            "Upcoming: " + vaccineName,
                            "Your baby's " + VACCINATION_SCHEDULE[i][0] + " vaccination is in 3 days!");
                }

                // 48 hours before (2 days)
                LocalDate twoDaysBefore = vaccineDate.minusDays(2);
                if (twoDaysBefore.isAfter(today)) {
                    NotificationScheduler.scheduleNotification(this, twoDaysBefore,
                            "Reminder: " + vaccineName,
                            "Your baby's " + VACCINATION_SCHEDULE[i][0] + " vaccination is in 2 days!");
                }

                // 24 hours before (1 day)
                LocalDate oneDayBefore = vaccineDate.minusDays(1);
                if (oneDayBefore.isAfter(today)) {
                    NotificationScheduler.scheduleNotification(this, oneDayBefore,
                            "Tomorrow: " + vaccineName,
                            "Your baby's " + VACCINATION_SCHEDULE[i][0] + " vaccination is tomorrow!");
                }

                // On the day
                NotificationScheduler.scheduleNotification(this, vaccineDate,
                        "Today: " + vaccineName,
                        "Your baby's " + VACCINATION_SCHEDULE[i][0] + " vaccination is scheduled for today!");
            }
        }
    }
}
