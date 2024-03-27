package com.esi.mahina.calculations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AppointmentsHelper {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public static Function<LocalDate, String> getVisit1DateRange = (lmp) -> {
        LocalDate beginDate = lmp.plusDays(30);
        LocalDate endDate = (lmp.plusWeeks(13)).plusDays(6);
        return beginDate.format(formatter) + " to " + endDate.format(formatter);
    };
    public static BiFunction<LocalDate, Integer, String> getVisit2To8DateRange = (lmp, weeks) -> {
        LocalDate beginDate = lmp.plusWeeks(weeks);
        LocalDate endDate = beginDate.plusDays(6);

        switch (weeks) {
            case 20, 30, 34, 36, 38, 40:

                beginDate = lmp.plusWeeks(weeks);
                endDate = beginDate.plusDays(6);
                return beginDate.format(formatter) + " to " + endDate.format(formatter);

        }
        return beginDate.format(formatter) + " to " + endDate.format(formatter);
    };

}
