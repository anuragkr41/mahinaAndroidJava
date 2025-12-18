package com.esi.mahina.domain.calculator;

import com.esi.mahina.data.Constants;
import com.esi.mahina.data.model.PregnancyData;
import com.esi.mahina.data.model.USGScheduleItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Calculator for all pregnancy-related calculations.
 * Calculates period of gestation, EDD, USG schedule, etc.
 */
public class PregnancyCalculator {

    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.getDefault());

    /**
     * Calculate all pregnancy data from LMP date.
     *
     * @param lmpDate The Last Menstrual Period date
     * @return PregnancyData containing all calculated values
     */
    public static PregnancyData calculate(LocalDate lmpDate) {
        if (lmpDate == null) {
            return null;
        }

        LocalDate today = LocalDate.now();

        // Calculate period of gestation
        long totalDays = ChronoUnit.DAYS.between(lmpDate, today);
        int gestationWeeks = (int) (totalDays / 7);
        int gestationDays = (int) (totalDays % 7);

        // Handle future dates
        if (totalDays < 0) {
            gestationWeeks = -1;
            gestationDays = -1;
        }

        // Calculate Expected Delivery Date (Naegele's rule: LMP + 9 months + 7 days)
        LocalDate edd = lmpDate.plusMonths(9).plusDays(7);

        // Calculate USG schedule
        List<USGScheduleItem> usgSchedule = calculateUSGSchedule(lmpDate);

        return new PregnancyData(lmpDate, edd, gestationWeeks, gestationDays, usgSchedule);
    }

    /**
     * Calculate the USG (ultrasound) schedule from LMP.
     *
     * @param lmpDate The LMP date
     * @return List of USG schedule items
     */
    public static List<USGScheduleItem> calculateUSGSchedule(LocalDate lmpDate) {
        List<USGScheduleItem> schedule = new ArrayList<>();

        // USG 1: 6-8 weeks (Early/Dating scan)
        schedule.add(new USGScheduleItem(
                1,
                "Dating Scan",
                lmpDate.plusWeeks(Constants.USG1_START_WEEK),
                lmpDate.plusWeeks(Constants.USG1_END_WEEK),
                Constants.USG1_START_WEEK,
                Constants.USG1_END_WEEK
        ));

        // USG 2: 11-14 weeks (NT Scan / First Trimester Screening)
        schedule.add(new USGScheduleItem(
                2,
                "NT Scan",
                lmpDate.plusWeeks(Constants.USG2_START_WEEK),
                lmpDate.plusWeeks(Constants.USG2_END_WEEK).minusDays(1), // 13w+6d
                Constants.USG2_START_WEEK,
                Constants.USG2_END_WEEK
        ));

        // USG 3: 18-20 weeks (Anomaly Scan)
        schedule.add(new USGScheduleItem(
                3,
                "Anomaly Scan",
                lmpDate.plusWeeks(Constants.USG3_START_WEEK),
                lmpDate.plusWeeks(Constants.USG3_END_WEEK),
                Constants.USG3_START_WEEK,
                Constants.USG3_END_WEEK
        ));

        // USG 4: 30-32 weeks (Growth Scan)
        schedule.add(new USGScheduleItem(
                4,
                "Growth Scan",
                lmpDate.plusWeeks(Constants.USG4_START_WEEK),
                lmpDate.plusWeeks(Constants.USG4_END_WEEK),
                Constants.USG4_START_WEEK,
                Constants.USG4_END_WEEK
        ));

        return schedule;
    }

    /**
     * Calculate period of gestation as a formatted string.
     *
     * @param lmpDate The LMP date
     * @return Formatted string like "12 Weeks 3 Days"
     */
    public static String calculatePOGString(LocalDate lmpDate) {
        if (lmpDate == null) {
            return "N/A";
        }

        LocalDate today = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(lmpDate, today);

        if (totalDays < 0) {
            return "Future date!";
        }

        int weeks = (int) (totalDays / 7);
        int days = (int) (totalDays % 7);

        return weeks + " Weeks " + days + " Days";
    }

    /**
     * Calculate Expected Delivery Date.
     *
     * @param lmpDate The LMP date
     * @return EDD as LocalDate
     */
    public static LocalDate calculateEDD(LocalDate lmpDate) {
        if (lmpDate == null) {
            return null;
        }
        return lmpDate.plusMonths(9).plusDays(7);
    }

    /**
     * Calculate EDD as a formatted string.
     *
     * @param lmpDate   The LMP date
     * @param formatter The date formatter to use
     * @return Formatted EDD string
     */
    public static String calculateEDDString(LocalDate lmpDate, DateTimeFormatter formatter) {
        LocalDate edd = calculateEDD(lmpDate);
        if (edd == null) {
            return "N/A";
        }
        return edd.format(formatter != null ? formatter : DEFAULT_FORMATTER);
    }

    /**
     * Calculate EDD as a formatted string using default formatter.
     */
    public static String calculateEDDString(LocalDate lmpDate) {
        return calculateEDDString(lmpDate, DEFAULT_FORMATTER);
    }

    /**
     * Get USG date range as formatted string.
     *
     * @param lmpDate   The LMP date
     * @param usgNumber The USG number (1-4)
     * @param formatter The date formatter
     * @return Formatted date range string
     */
    public static String getUSGDateRangeString(LocalDate lmpDate, int usgNumber,
                                                DateTimeFormatter formatter) {
        if (lmpDate == null || usgNumber < 1 || usgNumber > 4) {
            return "N/A";
        }

        DateTimeFormatter fmt = formatter != null ? formatter : DEFAULT_FORMATTER;
        List<USGScheduleItem> schedule = calculateUSGSchedule(lmpDate);

        if (usgNumber <= schedule.size()) {
            return schedule.get(usgNumber - 1).getFormattedDateRange(fmt);
        }
        return "N/A";
    }

    /**
     * Get the current trimester based on weeks.
     *
     * @param weeks Weeks of gestation
     * @return Trimester number (1, 2, or 3)
     */
    public static int getTrimester(int weeks) {
        if (weeks < 13) {
            return 1;
        } else if (weeks < 27) {
            return 2;
        } else {
            return 3;
        }
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
