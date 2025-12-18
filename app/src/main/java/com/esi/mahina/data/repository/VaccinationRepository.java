package com.esi.mahina.data.repository;

import android.content.Context;

import com.esi.mahina.data.model.VaccinationData;
import com.esi.mahina.data.model.VaccinationItem;
import com.esi.mahina.domain.calculator.VaccinationCalculator;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for vaccination-related data.
 * Combines data access (SharedPreferences) with calculations.
 */
public class VaccinationRepository {

    private final UserPreferencesRepository prefsRepository;

    public VaccinationRepository(Context context) {
        this.prefsRepository = new UserPreferencesRepository(context);
    }

    /**
     * Get the stored baby DOB.
     */
    public LocalDate getBabyDob() {
        return prefsRepository.getBabyDob();
    }

    /**
     * Save the baby's date of birth.
     */
    public void saveBabyDob(LocalDate dob) {
        prefsRepository.setBabyDob(dob);
    }

    /**
     * Check if baby DOB is saved.
     */
    public boolean hasBabyDob() {
        return prefsRepository.hasBabyDob();
    }

    /**
     * Clear the baby DOB.
     */
    public void clearBabyDob() {
        prefsRepository.clearBabyDob();
    }

    /**
     * Get complete vaccination data calculated from stored DOB.
     * Returns null if no DOB is stored.
     */
    public VaccinationData getVaccinationData() {
        LocalDate babyDob = getBabyDob();
        if (babyDob == null) {
            return null;
        }
        return VaccinationCalculator.calculate(babyDob);
    }

    /**
     * Calculate vaccination data from a given DOB (without saving).
     */
    public VaccinationData calculateVaccinationData(LocalDate babyDob) {
        return VaccinationCalculator.calculate(babyDob);
    }

    /**
     * Get the vaccination schedule from stored DOB.
     */
    public List<VaccinationItem> getVaccinationSchedule() {
        LocalDate babyDob = getBabyDob();
        if (babyDob == null) {
            return null;
        }
        return VaccinationCalculator.calculateSchedule(babyDob);
    }

    /**
     * Get just the core vaccinations (first 4).
     */
    public List<VaccinationItem> getCoreVaccinations() {
        LocalDate babyDob = getBabyDob();
        if (babyDob == null) {
            return null;
        }
        return VaccinationCalculator.getCoreVaccinations(babyDob);
    }

    /**
     * Get the baby's age string.
     */
    public String getBabyAgeString() {
        VaccinationData data = getVaccinationData();
        if (data == null) {
            return "N/A";
        }
        return data.getBabyAgeString(LocalDate.now());
    }

    /**
     * Get the next upcoming vaccination.
     */
    public VaccinationItem getNextUpcomingVaccination() {
        LocalDate babyDob = getBabyDob();
        if (babyDob == null) {
            return null;
        }
        return VaccinationCalculator.getNextVaccination(babyDob, LocalDate.now());
    }

    /**
     * Get upcoming vaccinations within a given number of days.
     */
    public List<VaccinationItem> getUpcomingVaccinations(int withinDays) {
        LocalDate babyDob = getBabyDob();
        if (babyDob == null) {
            return null;
        }
        return VaccinationCalculator.getUpcomingVaccinations(babyDob, LocalDate.now(), withinDays);
    }

    /**
     * Check if notifications are enabled.
     */
    public boolean areNotificationsEnabled() {
        return prefsRepository.areNotificationsEnabled();
    }
}
