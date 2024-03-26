package com.esi.mahina.calculations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AppointmentsHelper {
    public static Function<LocalDate, String> getVisit1DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusDays(30);
        LocalDate endDate = (lmp.plusWeeks(13)).plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        AppointmentDates.setVisit1Date(beginDate);
        return beginDate.format(formatter) + " to " + endDate.format(formatter);
    };
    public static BiFunction<LocalDate, Integer, String> getVisit2To8DateRange = (lmp, weeeks) -> {
        LocalDate beginDate = lmp.plusWeeks(weeeks);
        LocalDate endDate = beginDate.plusDays(6);

        switch (weeeks) {
            case 20:
                AppointmentDates.setVisit2Date(beginDate);
                break;
            case 30:
                AppointmentDates.setVisit3Date(beginDate);
                break;
            case 34:
                AppointmentDates.setVisit4Date(beginDate);
                break;
            case 36:
                AppointmentDates.setVisit5Date(beginDate);
                break;
            case 38:
                AppointmentDates.setVisit6Date(beginDate);
                break;
            case 40:
                AppointmentDates.setVisit7Date(beginDate);
                break;
            default:
                break;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return beginDate.format(formatter) + " to " + endDate.format(formatter);
    };

}
