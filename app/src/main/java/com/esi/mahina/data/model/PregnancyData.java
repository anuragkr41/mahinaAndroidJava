package com.esi.mahina.data.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents all pregnancy-related data for a patient.
 * Contains LMP date, calculated dates, and USG schedule.
 */
public class PregnancyData {

    private final LocalDate lmpDate;
    private final LocalDate expectedDeliveryDate;
    private final int gestationWeeks;
    private final int gestationDays;
    private final List<USGScheduleItem> usgSchedule;

    public PregnancyData(LocalDate lmpDate, LocalDate expectedDeliveryDate,
                         int gestationWeeks, int gestationDays,
                         List<USGScheduleItem> usgSchedule) {
        this.lmpDate = lmpDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.gestationWeeks = gestationWeeks;
        this.gestationDays = gestationDays;
        this.usgSchedule = usgSchedule;
    }

    public LocalDate getLmpDate() {
        return lmpDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public int getGestationWeeks() {
        return gestationWeeks;
    }

    public int getGestationDays() {
        return gestationDays;
    }

    public List<USGScheduleItem> getUsgSchedule() {
        return usgSchedule;
    }

    /**
     * Get the period of gestation as a formatted string.
     * @return String like "12 Weeks 3 Days"
     */
    public String getPeriodOfGestationString() {
        if (gestationWeeks < 0 || gestationDays < 0) {
            return "Future date!";
        }
        return gestationWeeks + " Weeks " + gestationDays + " Days";
    }

    /**
     * Get expected delivery date formatted.
     */
    public String getFormattedEdd(java.time.format.DateTimeFormatter formatter) {
        return expectedDeliveryDate.format(formatter);
    }

    /**
     * Get LMP date formatted.
     */
    public String getFormattedLmp(java.time.format.DateTimeFormatter formatter) {
        return lmpDate.format(formatter);
    }

    /**
     * Get the next upcoming USG scan.
     * @param today Current date
     * @return The next USG that hasn't been completed, or null if all completed
     */
    public USGScheduleItem getNextUpcomingUSG(LocalDate today) {
        if (usgSchedule == null) return null;

        for (USGScheduleItem usg : usgSchedule) {
            if (!usg.isCompleted(today)) {
                return usg;
            }
        }
        return null;
    }

    /**
     * Get the count of completed USG scans.
     */
    public int getCompletedUSGCount(LocalDate today) {
        if (usgSchedule == null) return 0;

        int count = 0;
        for (USGScheduleItem usg : usgSchedule) {
            if (usg.isCompleted(today)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check if this is a valid pregnancy (LMP is in the past and not too old).
     */
    public boolean isValid() {
        if (lmpDate == null) return false;
        LocalDate today = LocalDate.now();
        // LMP should be in the past and within 42 weeks (max pregnancy duration)
        return !lmpDate.isAfter(today) && gestationWeeks <= 42;
    }

    /**
     * Get the trimester based on weeks of gestation.
     * @return 1, 2, or 3 for first, second, or third trimester
     */
    public int getTrimester() {
        if (gestationWeeks < 13) {
            return 1;
        } else if (gestationWeeks < 27) {
            return 2;
        } else {
            return 3;
        }
    }
}
