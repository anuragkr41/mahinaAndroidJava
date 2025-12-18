package com.esi.mahina.data.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a single vaccination in the baby's immunization schedule.
 */
public class VaccinationItem {

    /**
     * Status of a vaccination.
     */
    public enum Status {
        COMPLETED,      // Vaccination date has passed
        TODAY,          // Vaccination is due today
        TOMORROW,       // Vaccination is due tomorrow
        UPCOMING_SOON,  // Vaccination is coming up within 7 days
        UPCOMING        // Vaccination is in the future
    }

    private final int index;
    private final String ageName;          // e.g., "Birth", "6 Weeks", "10 Weeks"
    private final String vaccines;         // e.g., "BCG, OPV-0, Hepatitis B-1"
    private final LocalDate dueDate;
    private final int daysFromBirth;

    public VaccinationItem(int index, String ageName, String vaccines, LocalDate dueDate, int daysFromBirth) {
        this.index = index;
        this.ageName = ageName;
        this.vaccines = vaccines;
        this.dueDate = dueDate;
        this.daysFromBirth = daysFromBirth;
    }

    public int getIndex() {
        return index;
    }

    public String getAgeName() {
        return ageName;
    }

    public String getVaccines() {
        return vaccines;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getDaysFromBirth() {
        return daysFromBirth;
    }

    /**
     * Get formatted due date.
     */
    public String getFormattedDate(java.time.format.DateTimeFormatter formatter) {
        return dueDate.format(formatter);
    }

    /**
     * Calculate the current status of this vaccination.
     */
    public Status getStatus(LocalDate today) {
        long daysUntil = ChronoUnit.DAYS.between(today, dueDate);

        if (daysUntil < 0) {
            return Status.COMPLETED;
        } else if (daysUntil == 0) {
            return Status.TODAY;
        } else if (daysUntil == 1) {
            return Status.TOMORROW;
        } else if (daysUntil <= 7) {
            return Status.UPCOMING_SOON;
        } else {
            return Status.UPCOMING;
        }
    }

    /**
     * Get days until the vaccination is due.
     * @return Positive if in future, negative if past, 0 if today
     */
    public long getDaysUntil(LocalDate today) {
        return ChronoUnit.DAYS.between(today, dueDate);
    }

    /**
     * Get a human-readable status message.
     */
    public String getStatusMessage(LocalDate today) {
        long daysUntil = getDaysUntil(today);

        if (daysUntil < 0) {
            return "Completed";
        } else if (daysUntil == 0) {
            return "Today!";
        } else if (daysUntil == 1) {
            return "Tomorrow!";
        } else {
            return "In " + daysUntil + " days";
        }
    }

    /**
     * Check if vaccination date has passed.
     */
    public boolean isCompleted(LocalDate today) {
        return today.isAfter(dueDate);
    }

    /**
     * Check if vaccination is due today.
     */
    public boolean isDueToday(LocalDate today) {
        return today.isEqual(dueDate);
    }

    /**
     * Check if vaccination is upcoming soon (within 3 days).
     */
    public boolean isUpcomingSoon(LocalDate today) {
        long daysUntil = getDaysUntil(today);
        return daysUntil > 0 && daysUntil <= 3;
    }
}
