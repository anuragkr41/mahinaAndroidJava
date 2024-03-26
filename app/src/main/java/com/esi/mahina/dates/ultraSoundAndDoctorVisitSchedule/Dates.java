package com.esi.mahina.dates.ultraSoundAndDoctorVisitSchedule;

import com.esi.mahina.calculations.AppointmentsHelper;
import com.esi.mahina.calculations.DatesHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Dates {
    private LocalDate lastMenstrualPeriodDate;
    private List<String> usgDates;
    private List<String> appointmentDates;


    public Dates(LocalDate lastMenstrualPeriodDate) {
        this.lastMenstrualPeriodDate = lastMenstrualPeriodDate;
        usgDates = new ArrayList<>();
        appointmentDates = new ArrayList<>();
        //Logic to populate the USG Dates and Appointment dates is to be done here only
        //USG
        usgDates.add(DatesHelper.getUSG1DateRange.apply(this.lastMenstrualPeriodDate));
        usgDates.add(DatesHelper.getUSG2DateRange.apply(this.lastMenstrualPeriodDate));
        usgDates.add(DatesHelper.getUSG3DateRange.apply(this.lastMenstrualPeriodDate));
        usgDates.add(DatesHelper.getUSG4DateRange.apply(this.lastMenstrualPeriodDate));

        //AppointmentDates
        appointmentDates.add(AppointmentsHelper.getVisit1DateRange.apply(this.lastMenstrualPeriodDate));
        appointmentDates.add(AppointmentsHelper.getVisit2To8DateRange.apply(this.lastMenstrualPeriodDate, 20));
        appointmentDates.add(AppointmentsHelper.getVisit2To8DateRange.apply(this.lastMenstrualPeriodDate, 26));
        appointmentDates.add(AppointmentsHelper.getVisit2To8DateRange.apply(this.lastMenstrualPeriodDate, 30));
        appointmentDates.add(AppointmentsHelper.getVisit2To8DateRange.apply(this.lastMenstrualPeriodDate, 34));
        appointmentDates.add(AppointmentsHelper.getVisit2To8DateRange.apply(this.lastMenstrualPeriodDate, 36));
        appointmentDates.add(AppointmentsHelper.getVisit2To8DateRange.apply(this.lastMenstrualPeriodDate, 38));
        appointmentDates.add(AppointmentsHelper.getVisit2To8DateRange.apply(this.lastMenstrualPeriodDate, 40));
    }


    public LocalDate getLastMenstrualPeriodDate() {
        return lastMenstrualPeriodDate;
    }

    public void setLastMenstrualPeriodDate(LocalDate lastMenstrualPeriodDate) {
        this.lastMenstrualPeriodDate = lastMenstrualPeriodDate;
    }

    public List<String> getUsgDates() {
        return usgDates;
    }

    public List<String> getAppointmentDates() {
        return appointmentDates;
    }

    @Override
    public String toString() {
        return "Dates{" +
                "lastMenstrualPeriodDate=" + lastMenstrualPeriodDate +
                ", usgDates=" + usgDates +
                ", appointmentDates=" + appointmentDates +
                '}';
    }
}
