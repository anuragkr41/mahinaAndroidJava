package com.esi.mahina.calculations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AppointmentsHelper {
    public static Function<LocalDate, String> getVisit1DateRange = (lmp)->{
        LocalDate beginDate=lmp.plusDays(30);
        LocalDate endDate=(lmp.plusWeeks(13)).plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };
    public static BiFunction<LocalDate, Integer, String> getVisit2To8DateRange = (lmp, weeeks)->{
        LocalDate beginDate=lmp.plusWeeks(weeeks);
        LocalDate endDate=beginDate.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  beginDate.format(formatter) + " to "  + endDate.format(formatter);
    };

}
