package com.esi.mahina.data.model;

import java.time.LocalDate;

/**
 * Represents a single USG (ultrasound) scan in the pregnancy schedule.
 * Contains the scan number, date range, and status information.
 */
public class USGScheduleItem {

    /**
     * Status of a USG scan.
     */
    public enum Status {
        COMPLETED,      // Scan date has passed
        TODAY,          // Scan is due today (within the range)
        UPCOMING_SOON,  // Scan is coming up within 7 days
        UPCOMING        // Scan is in the future
    }

    private final int scanNumber;
    private final String scanName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int startWeek;
    private final int endWeek;

    public USGScheduleItem(int scanNumber, String scanName, LocalDate startDate, LocalDate endDate,
                           int startWeek, int endWeek) {
        this.scanNumber = scanNumber;
        this.scanName = scanName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    public int getScanNumber() {
        return scanNumber;
    }

    public String getScanName() {
        return scanName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    /**
     * Get the formatted date range string.
     * @param formatter The formatter to use
     * @return Date range as "startDate to endDate"
     */
    public String getFormattedDateRange(java.time.format.DateTimeFormatter formatter) {
        return startDate.format(formatter) + " to " + endDate.format(formatter);
    }

    /**
     * Get the week range string.
     * @return Week range as "startWeek - endWeek weeks"
     */
    public String getWeekRange() {
        return startWeek + " - " + endWeek + " weeks";
    }

    /**
     * Calculate the current status of this USG scan.
     * @param today The current date
     * @return The status of the scan
     */
    public Status getStatus(LocalDate today) {
        if (today.isAfter(endDate)) {
            return Status.COMPLETED;
        } else if (!today.isBefore(startDate) && !today.isAfter(endDate)) {
            return Status.TODAY;
        } else if (startDate.minusDays(7).isBefore(today)) {
            return Status.UPCOMING_SOON;
        } else {
            return Status.UPCOMING;
        }
    }

    /**
     * Get days until the start of the scan window.
     * @param today The current date
     * @return Days until start, or negative if past
     */
    public long getDaysUntilStart(LocalDate today) {
        return java.time.temporal.ChronoUnit.DAYS.between(today, startDate);
    }

    /**
     * Check if the scan window has passed.
     */
    public boolean isCompleted(LocalDate today) {
        return today.isAfter(endDate);
    }

    /**
     * Check if today is within the scan window.
     */
    public boolean isDueNow(LocalDate today) {
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }
}
