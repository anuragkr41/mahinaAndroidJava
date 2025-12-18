package com.esi.mahina.calculations;

import android.widget.DatePicker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.function.Function;

public class DatesHelper {

    public static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.getDefault());
    }

    // Keep this for backward compatibility but make it use locale
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.getDefault());

    public static Function<LocalDate, String> getPeriodOfGestation = (lmp) -> {

        long numberOfWeeks = ChronoUnit.WEEKS.between(lmp, LocalDate.now());
        long numberOfDays = ChronoUnit.DAYS.between(lmp, LocalDate.now()) % 7;

        if (numberOfDays < 0 || numberOfWeeks < 0) {
            return "Future date!!";
        }
        return numberOfWeeks + " Weeks " + numberOfDays + " Days";
    };

    public static Function<LocalDate, String> getExpectedDateOfDelivery = (lmp) -> {
        lmp = lmp.plusMonths(9);
        lmp = lmp.plusDays(7);

        return lmp.format(getFormatter());

    };

    public static Function<LocalDate, String> getUSG1DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusWeeks(6);
        LocalDate endDate = lmp.plusWeeks(8);

        return beginDate.format(getFormatter()) + " to " + endDate.format(getFormatter());
    };


    public static Function<LocalDate, String> getUSG2DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusWeeks(11);
        LocalDate endDate = lmp.plusWeeks(13);
        endDate = endDate.plusDays(6);
        return beginDate.format(getFormatter()) + " to " + endDate.format(getFormatter());
    };


    public static Function<LocalDate, String> getUSG3DateRange = (lmp) -> {

        LocalDate beginDate = lmp.plusWeeks(18);
        LocalDate endDate = lmp.plusWeeks(20);
        return beginDate.format(getFormatter()) + " to " + endDate.format(getFormatter());

    };
    public static Function<LocalDate, String> getUSG4DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusWeeks(30);
        LocalDate endDate = lmp.plusWeeks(32);
        return beginDate.format(getFormatter()) + " to " + endDate.format(getFormatter());
    };

    public static LocalDate captureLocalDateFromDatePicker(DatePicker datePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();

        // Create a LocalDate object from the selected year, month, and day
        return LocalDate.of(year, month + 1, dayOfMonth); // month is zero-based in DatePicker
    }


}
