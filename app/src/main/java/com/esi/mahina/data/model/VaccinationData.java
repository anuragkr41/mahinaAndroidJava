package com.esi.mahina.data.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Represents all vaccination-related data for a baby.
 * Contains baby's DOB, age, and vaccination schedule.
 */
public class VaccinationData {

    private final LocalDate babyDob;
    private final List<VaccinationItem> vaccinationSchedule;

    public VaccinationData(LocalDate babyDob, List<VaccinationItem> vaccinationSchedule) {
        this.babyDob = babyDob;
        this.vaccinationSchedule = vaccinationSchedule;
    }

    public LocalDate getBabyDob() {
        return babyDob;
    }

    public List<VaccinationItem> getVaccinationSchedule() {
        return vaccinationSchedule;
    }

    /**
     * Get formatted baby DOB.
     */
    public String getFormattedDob(java.time.format.DateTimeFormatter formatter) {
        return babyDob.format(formatter);
    }

    /**
     * Get baby's age in months and days.
     * @param today Current date
     * @return Array with [months, days]
     */
    public int[] getBabyAge(LocalDate today) {
        long totalDays = ChronoUnit.DAYS.between(babyDob, today);
        int months = (int) (totalDays / 30);
        int days = (int) (totalDays % 30);
        return new int[]{months, days};
    }

    /**
     * Get baby's age as a formatted string.
     */
    public String getBabyAgeString(LocalDate today) {
        long totalDays = ChronoUnit.DAYS.between(babyDob, today);

        if (totalDays < 0) {
            return "Not born yet";
        }

        int months = (int) (totalDays / 30);
        int days = (int) (totalDays % 30);

        if (months > 0) {
            return months + " month" + (months > 1 ? "s" : "") + ", " +
                   days + " day" + (days != 1 ? "s" : "");
        } else {
            return days + " day" + (days != 1 ? "s" : "") + " old";
        }
    }

    /**
     * Get the next upcoming vaccination.
     * @param today Current date
     * @return The next vaccination that hasn't been given, or null if all done
     */
    public VaccinationItem getNextUpcomingVaccination(LocalDate today) {
        if (vaccinationSchedule == null) return null;

        for (VaccinationItem vaccine : vaccinationSchedule) {
            if (!vaccine.isCompleted(today)) {
                return vaccine;
            }
        }
        return null;
    }

    /**
     * Get the count of completed vaccinations.
     */
    public int getCompletedVaccinationCount(LocalDate today) {
        if (vaccinationSchedule == null) return 0;

        int count = 0;
        for (VaccinationItem vaccine : vaccinationSchedule) {
            if (vaccine.isCompleted(today)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get vaccinations that are upcoming soon (within given days).
     */
    public List<VaccinationItem> getUpcomingVaccinations(LocalDate today, int withinDays) {
        if (vaccinationSchedule == null) return java.util.Collections.emptyList();

        java.util.List<VaccinationItem> upcoming = new java.util.ArrayList<>();
        for (VaccinationItem vaccine : vaccinationSchedule) {
            long daysUntil = vaccine.getDaysUntil(today);
            if (daysUntil >= 0 && daysUntil <= withinDays) {
                upcoming.add(vaccine);
            }
        }
        return upcoming;
    }

    /**
     * Check if this is valid vaccination data.
     */
    public boolean isValid() {
        return babyDob != null && !babyDob.isAfter(LocalDate.now());
    }
}
