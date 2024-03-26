package com.esi.mahina.dates;

import java.time.LocalDate;

// Create a singleton class for the LastMenstrualPeriod
// A better design could have been taking Dates and static variable as LMP and arrays of Dates
// as USG1, Appointment, then a Dates helper function to populate visits. functions to calcul.
public class LastMenstrualPeriod {
    private static LastMenstrualPeriod instance;

    // Field to store the last menstrual period date
    private LocalDate lmp;

    private LastMenstrualPeriod() {
    }

    public static LastMenstrualPeriod getInstance() {
        if (instance == null) {
            instance = new LastMenstrualPeriod();
        }
        return instance;
    }

    public LocalDate getLMP() {
        return lmp;
    }

    //    Getters and Setters
    public void setLMP(LocalDate lmp) {
        this.lmp = lmp;
    }
}