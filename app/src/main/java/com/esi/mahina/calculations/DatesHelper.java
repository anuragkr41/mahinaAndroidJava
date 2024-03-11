package com.esi.mahina.calculations;

import android.widget.DatePicker;

import com.esi.mahina.Settings.GeneralSettings;
import com.esi.mahina.dates.USGDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class DatesHelper {
    private static LocalDate todayDate = LocalDate.now();
    LocalDate lmp = LastMenstrualPeriod.getInstance().getLMP();



    public static LocalDate captureLocalDateFromDatePicker(DatePicker datePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();

        // Create a LocalDate object from the selected year, month, and day
        return LocalDate.of(year, month + 1, dayOfMonth); // month is zero-based in DatePicker
    }

//    Function to create the Period of Gestation
    public static Function<LocalDate, String> getPeriodOfGestation = (lmp)->{

        long numberOfWeeks = ChronoUnit.WEEKS.between(lmp, todayDate);
        long numberOfDays = ChronoUnit.DAYS.between(lmp, todayDate) % 7;

        if(numberOfDays<0 || numberOfWeeks<0){
            return "Future date!!";
        }
        return numberOfWeeks+" Weeks "+numberOfDays+" Days";
    };

//    Function to generate the expected Date of Delivery
    public static Function<LocalDate, String> getExpectedDateOfDelivery = (lmp)->{

        lmp = lmp.plusMonths(9);
        lmp = lmp.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        return lmp.format(formatter);

    };

//    *************UltraSound Dates**************


    public static Function<LocalDate, String> getUSG1DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(6);
        LocalDate endDate=lmp.plusWeeks(8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        USGDates.setUsg1Date(beginDate);



        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getUSG2DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(11);
        LocalDate endDate=lmp.plusWeeks(13);
        endDate=endDate.plusDays(6);

        USGDates.setUsg2Date(beginDate);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getUSG3DateRange = (lmp)->{

        LocalDate beginDate=lmp.plusWeeks(18);
        LocalDate endDate=lmp.plusWeeks(20);

        USGDates.setUsg3Date(beginDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);

    };
    public static Function<LocalDate, String> getUSG4DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(30);
        LocalDate endDate=lmp.plusWeeks(32);

        USGDates.setUsg4Date(beginDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };

}
