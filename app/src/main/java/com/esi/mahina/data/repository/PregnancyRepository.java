package com.esi.mahina.data.repository;

import android.content.Context;

import com.esi.mahina.data.model.PregnancyData;
import com.esi.mahina.data.model.USGScheduleItem;
import com.esi.mahina.domain.calculator.PregnancyCalculator;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for pregnancy-related data.
 * Combines data access (SharedPreferences) with calculations.
 */
public class PregnancyRepository {

    private final UserPreferencesRepository prefsRepository;

    public PregnancyRepository(Context context) {
        this.prefsRepository = new UserPreferencesRepository(context);
    }

    /**
     * Get the stored LMP date.
     */
    public LocalDate getLmpDate() {
        return prefsRepository.getLmpDate();
    }

    /**
     * Save the LMP date.
     */
    public void saveLmpDate(LocalDate lmpDate) {
        prefsRepository.setLmpDate(lmpDate);
    }

    /**
     * Check if LMP is saved.
     */
    public boolean hasLmpDate() {
        return prefsRepository.hasLmpDate();
    }

    /**
     * Clear the LMP date.
     */
    public void clearLmpDate() {
        prefsRepository.clearLmpDate();
    }

    /**
     * Get complete pregnancy data calculated from stored LMP.
     * Returns null if no LMP is stored.
     */
    public PregnancyData getPregnancyData() {
        LocalDate lmpDate = getLmpDate();
        if (lmpDate == null) {
            return null;
        }
        return PregnancyCalculator.calculate(lmpDate);
    }

    /**
     * Calculate pregnancy data from a given LMP (without saving).
     */
    public PregnancyData calculatePregnancyData(LocalDate lmpDate) {
        return PregnancyCalculator.calculate(lmpDate);
    }

    /**
     * Get the USG schedule from stored LMP.
     */
    public List<USGScheduleItem> getUSGSchedule() {
        LocalDate lmpDate = getLmpDate();
        if (lmpDate == null) {
            return null;
        }
        return PregnancyCalculator.calculateUSGSchedule(lmpDate);
    }

    /**
     * Get the period of gestation string.
     */
    public String getPeriodOfGestation() {
        LocalDate lmpDate = getLmpDate();
        return PregnancyCalculator.calculatePOGString(lmpDate);
    }

    /**
     * Get the Expected Delivery Date.
     */
    public LocalDate getExpectedDeliveryDate() {
        LocalDate lmpDate = getLmpDate();
        return PregnancyCalculator.calculateEDD(lmpDate);
    }

    /**
     * Get the EDD as a formatted string.
     */
    public String getExpectedDeliveryDateString() {
        LocalDate lmpDate = getLmpDate();
        return PregnancyCalculator.calculateEDDString(lmpDate);
    }

    /**
     * Get a specific USG date range.
     * @param usgNumber 1-4 for USG 1-4
     */
    public String getUSGDateRange(int usgNumber) {
        LocalDate lmpDate = getLmpDate();
        return PregnancyCalculator.getUSGDateRangeString(
                lmpDate, usgNumber, PregnancyCalculator.getFormatter());
    }

    /**
     * Get the next upcoming USG.
     */
    public USGScheduleItem getNextUpcomingUSG() {
        PregnancyData data = getPregnancyData();
        if (data == null) {
            return null;
        }
        return data.getNextUpcomingUSG(LocalDate.now());
    }

    /**
     * Check if notifications are enabled.
     */
    public boolean areNotificationsEnabled() {
        return prefsRepository.areNotificationsEnabled();
    }
}
