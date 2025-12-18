package com.esi.mahina.domain.calculator;

import com.esi.mahina.data.model.VaccinationData;
import com.esi.mahina.data.model.VaccinationItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Calculator for all vaccination-related calculations.
 * Calculates vaccination schedule based on baby's date of birth.
 * Follows IAP (Indian Academy of Pediatrics) recommendations.
 */
public class VaccinationCalculator {

    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.getDefault());

    /**
     * Vaccination schedule data based on IAP India recommendations.
     * Each entry: {Age Name, Vaccines}
     */
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

    /**
     * Days from birth for each vaccination.
     */
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

    /**
     * Calculate all vaccination data from baby's DOB.
     *
     * @param babyDob Baby's date of birth
     * @return VaccinationData containing schedule and age info
     */
    public static VaccinationData calculate(LocalDate babyDob) {
        if (babyDob == null) {
            return null;
        }

        List<VaccinationItem> schedule = calculateSchedule(babyDob);
        return new VaccinationData(babyDob, schedule);
    }

    /**
     * Calculate the vaccination schedule from DOB.
     *
     * @param babyDob Baby's date of birth
     * @return List of vaccination items
     */
    public static List<VaccinationItem> calculateSchedule(LocalDate babyDob) {
        List<VaccinationItem> schedule = new ArrayList<>();

        for (int i = 0; i < VACCINATION_SCHEDULE.length; i++) {
            LocalDate dueDate = babyDob.plusDays(SCHEDULE_DAYS[i]);
            schedule.add(new VaccinationItem(
                    i,
                    VACCINATION_SCHEDULE[i][0],
                    VACCINATION_SCHEDULE[i][1],
                    dueDate,
                    SCHEDULE_DAYS[i]
            ));
        }

        return schedule;
    }

    /**
     * Get just the first 4 core vaccinations (commonly displayed).
     *
     * @param babyDob Baby's date of birth
     * @return List of first 4 vaccinations (Birth, 6w, 10w, 14w)
     */
    public static List<VaccinationItem> getCoreVaccinations(LocalDate babyDob) {
        List<VaccinationItem> all = calculateSchedule(babyDob);
        return all.subList(0, Math.min(4, all.size()));
    }

    /**
     * Get vaccination date for a specific age milestone.
     *
     * @param babyDob     Baby's date of birth
     * @param vaccinationIndex Index of vaccination (0-11)
     * @return Due date for the vaccination
     */
    public static LocalDate getVaccinationDate(LocalDate babyDob, int vaccinationIndex) {
        if (babyDob == null || vaccinationIndex < 0 || vaccinationIndex >= SCHEDULE_DAYS.length) {
            return null;
        }
        return babyDob.plusDays(SCHEDULE_DAYS[vaccinationIndex]);
    }

    /**
     * Get formatted vaccination date.
     */
    public static String getFormattedVaccinationDate(LocalDate babyDob, int vaccinationIndex,
                                                      DateTimeFormatter formatter) {
        LocalDate date = getVaccinationDate(babyDob, vaccinationIndex);
        if (date == null) {
            return "N/A";
        }
        return date.format(formatter != null ? formatter : DEFAULT_FORMATTER);
    }

    /**
     * Get the name of a vaccination by index.
     */
    public static String getVaccinationName(int index) {
        if (index < 0 || index >= VACCINATION_SCHEDULE.length) {
            return "Unknown";
        }
        return VACCINATION_SCHEDULE[index][0];
    }

    /**
     * Get the vaccines for a given age.
     */
    public static String getVaccines(int index) {
        if (index < 0 || index >= VACCINATION_SCHEDULE.length) {
            return "";
        }
        return VACCINATION_SCHEDULE[index][1];
    }

    /**
     * Get the total number of vaccination milestones.
     */
    public static int getTotalVaccinationCount() {
        return VACCINATION_SCHEDULE.length;
    }

    /**
     * Get upcoming vaccinations within a given number of days.
     *
     * @param babyDob    Baby's date of birth
     * @param today      Current date
     * @param withinDays Number of days to look ahead
     * @return List of upcoming vaccinations
     */
    public static List<VaccinationItem> getUpcomingVaccinations(LocalDate babyDob,
                                                                 LocalDate today,
                                                                 int withinDays) {
        List<VaccinationItem> all = calculateSchedule(babyDob);
        List<VaccinationItem> upcoming = new ArrayList<>();

        for (VaccinationItem vaccine : all) {
            long daysUntil = vaccine.getDaysUntil(today);
            if (daysUntil >= 0 && daysUntil <= withinDays) {
                upcoming.add(vaccine);
            }
        }

        return upcoming;
    }

    /**
     * Get the next vaccination that hasn't passed yet.
     *
     * @param babyDob Baby's DOB
     * @param today   Current date
     * @return Next upcoming vaccination or null if all completed
     */
    public static VaccinationItem getNextVaccination(LocalDate babyDob, LocalDate today) {
        List<VaccinationItem> all = calculateSchedule(babyDob);

        for (VaccinationItem vaccine : all) {
            if (!vaccine.isCompleted(today)) {
                return vaccine;
            }
        }
        return null;
    }

    /**
     * Get the default date formatter.
     */
    public static DateTimeFormatter getDefaultFormatter() {
        return DEFAULT_FORMATTER;
    }

    /**
     * Create a locale-aware date formatter.
     */
    public static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.getDefault());
    }

    /**
     * Get a display formatter for full date display.
     */
    public static DateTimeFormatter getDisplayFormatter() {
        return DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault());
    }
}
