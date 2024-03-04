package com.esi.mahina.calculations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class AppointmentsHelper {
    public static Function<LocalDate, String> getVisit1DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusDays(30);
        LocalDate endDate=(lmp.plusWeeks(13)).plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getVisit2DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(20);
        LocalDate endDate=beginDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getVisit3DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(26);
        LocalDate endDate=beginDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getVisit4DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(30);
        LocalDate endDate=beginDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getVisit5DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(34);
        LocalDate endDate=beginDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getVisit6DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusDays(36);
        LocalDate endDate=(lmp.plusWeeks(13)).plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getVisit7DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(38);
        LocalDate endDate=beginDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static Function<LocalDate, String> getVisit8DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusWeeks(40);
        LocalDate endDate=beginDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };


}
