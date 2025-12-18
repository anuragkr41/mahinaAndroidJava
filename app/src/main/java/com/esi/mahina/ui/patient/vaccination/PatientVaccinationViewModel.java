package com.esi.mahina.ui.patient.vaccination;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.esi.mahina.data.model.VaccinationData;
import com.esi.mahina.data.model.VaccinationItem;
import com.esi.mahina.data.repository.VaccinationRepository;
import com.esi.mahina.domain.calculator.VaccinationCalculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ViewModel for PatientVaccinationActivity.
 * Manages baby DOB and vaccination schedule state.
 */
public class PatientVaccinationViewModel extends AndroidViewModel {

    private final VaccinationRepository repository;

    // LiveData for UI state
    private final MutableLiveData<LocalDate> babyDob = new MutableLiveData<>();
    private final MutableLiveData<VaccinationData> vaccinationData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasDataSaved = new MutableLiveData<>(false);
    private final MutableLiveData<String> babyAge = new MutableLiveData<>();
    private final MutableLiveData<List<VaccinationItem>> vaccinationSchedule = new MutableLiveData<>();
    private final MutableLiveData<List<VaccinationItem>> coreVaccinations = new MutableLiveData<>();

    // Pending date before confirmation
    private LocalDate pendingDob;

    public PatientVaccinationViewModel(@NonNull Application application) {
        super(application);
        repository = new VaccinationRepository(application);
        loadSavedData();
    }

    /**
     * Load saved DOB data from repository.
     */
    public void loadSavedData() {
        LocalDate savedDob = repository.getBabyDob();
        if (savedDob != null) {
            babyDob.setValue(savedDob);
            hasDataSaved.setValue(true);
            calculateAndUpdateData(savedDob);
        }
    }

    /**
     * Set the pending DOB (before user confirms).
     */
    public void setPendingDob(LocalDate date) {
        this.pendingDob = date;
    }

    /**
     * Get the pending DOB.
     */
    public LocalDate getPendingDob() {
        return pendingDob;
    }

    /**
     * Confirm and save the pending DOB.
     */
    public void confirmDob() {
        if (pendingDob != null) {
            repository.saveBabyDob(pendingDob);
            babyDob.setValue(pendingDob);
            hasDataSaved.setValue(true);
            calculateAndUpdateData(pendingDob);
            pendingDob = null;
        }
    }

    /**
     * Calculate from a given DOB without saving (for doctor mode).
     */
    public void calculateFromDob(LocalDate dob) {
        babyDob.setValue(dob);
        calculateAndUpdateData(dob);
    }

    /**
     * Clear all vaccination data.
     */
    public void clearData() {
        repository.clearBabyDob();
        babyDob.setValue(null);
        vaccinationData.setValue(null);
        hasDataSaved.setValue(false);
        babyAge.setValue(null);
        vaccinationSchedule.setValue(null);
        coreVaccinations.setValue(null);
    }

    /**
     * Calculate all vaccination data from DOB and update LiveData.
     */
    private void calculateAndUpdateData(LocalDate dob) {
        if (dob == null) return;

        VaccinationData data = VaccinationCalculator.calculate(dob);
        vaccinationData.setValue(data);

        // Update individual fields
        LocalDate today = LocalDate.now();
        babyAge.setValue(data.getBabyAgeString(today));

        vaccinationSchedule.setValue(data.getVaccinationSchedule());
        coreVaccinations.setValue(VaccinationCalculator.getCoreVaccinations(dob));
    }

    /**
     * Refresh the baby age (call when screen is resumed).
     */
    public void refreshBabyAge() {
        LocalDate dob = babyDob.getValue();
        if (dob != null) {
            VaccinationData data = vaccinationData.getValue();
            if (data != null) {
                babyAge.setValue(data.getBabyAgeString(LocalDate.now()));
            }
        }
    }

    /**
     * Check if notifications are enabled.
     */
    public boolean areNotificationsEnabled() {
        return repository.areNotificationsEnabled();
    }

    /**
     * Get upcoming vaccinations within days.
     */
    public List<VaccinationItem> getUpcomingVaccinations(int withinDays) {
        LocalDate dob = babyDob.getValue();
        if (dob == null) {
            return null;
        }
        return VaccinationCalculator.getUpcomingVaccinations(dob, LocalDate.now(), withinDays);
    }

    // LiveData getters

    public LiveData<LocalDate> getBabyDob() {
        return babyDob;
    }

    public LiveData<VaccinationData> getVaccinationData() {
        return vaccinationData;
    }

    public LiveData<Boolean> getHasDataSaved() {
        return hasDataSaved;
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
