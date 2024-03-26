package com.esi.mahina.calculations;

import android.widget.DatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class DatesHelper {
    //    LocalDate lmp = LastMenstrualPeriod.getInstance().getLMP();
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    //    Function to generate the expected Date of Delivery
    public static Function<LocalDate, String> getExpectedDateOfDelivery = (lmp) -> {

        lmp = lmp.plusMonths(9);
        lmp = lmp.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        return lmp.format(formatter);

    };
    public static Function<LocalDate, String> getUSG1DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusWeeks(6);
        LocalDate endDate = lmp.plusWeeks(8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        USGDates.usg1Date = (beginDate);

        return beginDate.format(formatter) + " to " + endDate.format(formatter);
    };
    public static UnaryOperator<LocalDate> getUSG1Date = lmp -> lmp.plusWeeks(6);
    public static Function<LocalDate, String> getUSG2DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusWeeks(11);
        LocalDate endDate = lmp.plusWeeks(13);
        endDate = endDate.plusDays(6);

        USGDates.usg2Date = (beginDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return beginDate.format(formatter) + " to " + endDate.format(formatter);
    };

//    *************UltraSound Dates**************
    public static UnaryOperator<LocalDate> getUSG2Date = lmp -> lmp.plusWeeks(11);
    public static Function<LocalDate, String> getUSG3DateRange = (lmp) -> {

        LocalDate beginDate = lmp.plusWeeks(18);
        LocalDate endDate = lmp.plusWeeks(20);

        USGDates.usg3Date = (beginDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return beginDate.format(formatter) + " to " + endDate.format(formatter);

    };
    public static UnaryOperator<LocalDate> getUSG3Date = lmp -> lmp.plusWeeks(18);
    public static Function<LocalDate, String> getUSG4DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusWeeks(30);
        LocalDate endDate = lmp.plusWeeks(32);

        USGDates.usg4Date = (beginDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return beginDate.format(formatter) + " to " + endDate.format(formatter);
    };
    public static UnaryOperator<LocalDate> getUSG4Date = lmp -> lmp.plusWeeks(30);
    private static LocalDate todayDate = LocalDate.now();
    //    Function to create the Period of Gestation
    public static Function<LocalDate, String> getPeriodOfGestation = (lmp) -> {

        long numberOfWeeks = ChronoUnit.WEEKS.between(lmp, todayDate);
        long numberOfDays = ChronoUnit.DAYS.between(lmp, todayDate) % 7;

        if (numberOfDays < 0 || numberOfWeeks < 0) {
            return "Future date!!";
        }
        return numberOfWeeks + " Weeks " + numberOfDays + " Days";
    };

    public static LocalDate captureLocalDateFromDatePicker(DatePicker datePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();

        // Create a LocalDate object from the selected year, month, and day
        return LocalDate.of(year, month + 1, dayOfMonth); // month is zero-based in DatePicker
    }


}
