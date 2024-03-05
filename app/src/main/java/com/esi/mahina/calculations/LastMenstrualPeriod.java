package com.esi.mahina.calculations;

import java.time.LocalDate;

// Create a singleton class for the LastMenstrualPeriod

public class LastMenstrualPeriod {
    private static LastMenstrualPeriod instance;
    private LastMenstrualPeriod() {
    }
    public static LastMenstrualPeriod getInstance() {
        if (instance == null) {
            instance = new LastMenstrualPeriod();
        }
        return instance;
    }

    // Field to store the last menstrual period date
    private LocalDate lmp;

//    Getters and Setters
    public void setLMP(LocalDate lmp) {
        this.lmp = lmp;
    }

    public LocalDate getLMP() {
        return lmp;
    }
}