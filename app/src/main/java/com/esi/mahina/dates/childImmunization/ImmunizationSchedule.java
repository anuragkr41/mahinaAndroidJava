package com.esi.mahina.dates.childImmunization;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ImmunizationSchedule {
    private static ImmunizationSchedule instance;
    private LocalDate childBirthDate;
    private List<VaccinationDate> vaccinationDates;

    private ImmunizationSchedule(LocalDate childBirthDate) {
        this.childBirthDate = childBirthDate;
        this.vaccinationDates = new ArrayList<>();
    }

    // Public static method to get the instance of ImmunizationSchedule
    public static ImmunizationSchedule getInstance(LocalDate childBirthDate) {
        if (instance == null) {
            instance = new ImmunizationSchedule(childBirthDate);
        }
        return instance;
    }

    public LocalDate getChildBirthDate() {
        return childBirthDate;
    }

    public void setChildBirthDate(LocalDate childBirthDate) {
        this.childBirthDate = childBirthDate;
    }

    public List<VaccinationDate> getVaccinationDates() {
        return vaccinationDates;
    }

    public void addVaccinationDate(VaccinationDate vaccinationDate) {
        this.vaccinationDates.add(vaccinationDate);
    }


}
