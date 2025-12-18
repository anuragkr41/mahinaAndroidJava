package com.esi.mahina.ui.doctor.immunization;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esi.mahina.data.model.VaccinationData;
import com.esi.mahina.data.model.VaccinationItem;
import com.esi.mahina.domain.calculator.VaccinationCalculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ViewModel for DoctorImmunizationActivity.
 * Calculator-only mode - no data persistence.
 * Used by healthcare professionals for quick vaccination schedule calculations.
 */
public class DoctorImmunizationViewModel extends ViewModel {

    // LiveData for UI state
    private final MutableLiveData<LocalDate> babyDob = new MutableLiveData<>();
    private final MutableLiveData<VaccinationData> vaccinationData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasCalculated = new MutableLiveData<>(false);
    private final MutableLiveData<String> babyAge = new MutableLiveData<>();
    private final MutableLiveData<List<VaccinationItem>> vaccinationSchedule = new MutableLiveData<>();
    private final MutableLiveData<List<VaccinationItem>> coreVaccinations = new MutableLiveData<>();

    /**
     * Calculate vaccination schedule from a given DOB.
     * Does not persist the data.
     *
     * @param dob The baby's date of birth
     */
    public void calculateFromDob(LocalDate dob) {
        if (dob == null) {
            clearResults();
            return;
        }

        babyDob.setValue(dob);
        hasCalculated.setValue(true);

        VaccinationData data = VaccinationCalculator.calculate(dob);
        vaccinationData.setValue(data);

        // Update individual fields
        LocalDate today = LocalDate.now();
        babyAge.setValue(data.getBabyAgeString(today));

        vaccinationSchedule.setValue(data.getVaccinationSchedule());
        coreVaccinations.setValue(VaccinationCalculator.getCoreVaccinations(dob));
    }

    /**
     * Clear all calculation results.
     */
    public void clearResults() {
        babyDob.setValue(null);
        vaccinationData.setValue(null);
        hasCalculated.setValue(false);
        babyAge.setValue(null);
        vaccinationSchedule.setValue(null);
        coreVaccinations.setValue(null);
    }

    /**
     * Refresh the baby age (call when needed).
     */
    public void refreshBabyAge() {
        VaccinationData data = vaccinationData.getValue();
        if (data != null) {
            babyAge.setValue(data.getBabyAgeString(LocalDate.now()));
        }
    }

    // LiveData getters

    public LiveData<LocalDate> getBabyDob() {
        return babyDob;
    }

    public LiveData<VaccinationData> getVaccinationData() {
        return vaccinationData;
    }

    public LiveData<Boolean> getHasCalculated() {
        return hasCalculated;
    }

    public LiveData<String> getBabyAge() {
        return babyAge;
    }

    public LiveData<List<VaccinationItem>> getVaccinationSchedule() {
        return vaccinationSchedule;
    }

    public LiveData<List<VaccinationItem>> getCoreVaccinations() {
        return coreVaccinations;
    }

    /**
     * Get date formatter for display.
     */
    public DateTimeFormatter getDisplayFormatter() {
        return VaccinationCalculator.getDisplayFormatter();
    }

    /**
     * Get date formatter for schedule cards.
     */
    public DateTimeFormatter getScheduleFormatter() {
        return VaccinationCalculator.getFormatter();
    }
}
