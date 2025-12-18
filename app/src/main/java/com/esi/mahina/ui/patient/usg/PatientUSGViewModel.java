package com.esi.mahina.ui.patient.usg;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.esi.mahina.data.model.PregnancyData;
import com.esi.mahina.data.model.USGScheduleItem;
import com.esi.mahina.data.repository.PregnancyRepository;
import com.esi.mahina.domain.calculator.PregnancyCalculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ViewModel for PatientUSGActivity.
 * Manages pregnancy data and USG schedule state.
 */
public class PatientUSGViewModel extends AndroidViewModel {

    private final PregnancyRepository repository;

    // LiveData for UI state
    private final MutableLiveData<LocalDate> lmpDate = new MutableLiveData<>();
    private final MutableLiveData<PregnancyData> pregnancyData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasDataSaved = new MutableLiveData<>(false);
    private final MutableLiveData<String> periodOfGestation = new MutableLiveData<>();
    private final MutableLiveData<String> expectedDeliveryDate = new MutableLiveData<>();
    private final MutableLiveData<List<USGScheduleItem>> usgSchedule = new MutableLiveData<>();

    // Pending date before confirmation
    private LocalDate pendingLmpDate;

    public PatientUSGViewModel(@NonNull Application application) {
        super(application);
        repository = new PregnancyRepository(application);
        loadSavedData();
    }

    /**
     * Load saved LMP data from repository.
     */
    public void loadSavedData() {
        LocalDate savedLmp = repository.getLmpDate();
        if (savedLmp != null) {
            lmpDate.setValue(savedLmp);
            hasDataSaved.setValue(true);
            calculateAndUpdateData(savedLmp);
        }
    }

    /**
     * Set the pending LMP date (before user confirms).
     */
    public void setPendingLmpDate(LocalDate date) {
        this.pendingLmpDate = date;
    }

    /**
     * Get the pending LMP date.
     */
    public LocalDate getPendingLmpDate() {
        return pendingLmpDate;
    }

    /**
     * Confirm and save the pending LMP date.
     */
    public void confirmLmpDate() {
        if (pendingLmpDate != null) {
            repository.saveLmpDate(pendingLmpDate);
            lmpDate.setValue(pendingLmpDate);
            hasDataSaved.setValue(true);
            calculateAndUpdateData(pendingLmpDate);
            pendingLmpDate = null;
        }
    }

    /**
     * Calculate from a given LMP without saving (for doctor mode).
     */
    public void calculateFromLmp(LocalDate lmp) {
        lmpDate.setValue(lmp);
        calculateAndUpdateData(lmp);
    }

    /**
     * Clear all pregnancy data.
     */
    public void clearData() {
        repository.clearLmpDate();
        lmpDate.setValue(null);
        pregnancyData.setValue(null);
        hasDataSaved.setValue(false);
        periodOfGestation.setValue(null);
        expectedDeliveryDate.setValue(null);
        usgSchedule.setValue(null);
    }

    /**
     * Calculate all pregnancy data from LMP and update LiveData.
     */
    private void calculateAndUpdateData(LocalDate lmp) {
        if (lmp == null) return;

        PregnancyData data = PregnancyCalculator.calculate(lmp);
        pregnancyData.setValue(data);

        // Update individual fields
        periodOfGestation.setValue(data.getPeriodOfGestationString());

        DateTimeFormatter formatter = PregnancyCalculator.getDisplayFormatter();
        expectedDeliveryDate.setValue(data.getFormattedEdd(formatter));

        usgSchedule.setValue(data.getUsgSchedule());
    }

    /**
     * Check if notifications are enabled.
     */
    public boolean areNotificationsEnabled() {
        return repository.areNotificationsEnabled();
    }

    // LiveData getters

    public LiveData<LocalDate> getLmpDate() {
        return lmpDate;
    }

    public LiveData<PregnancyData> getPregnancyData() {
        return pregnancyData;
    }

    public LiveData<Boolean> getHasDataSaved() {
        return hasDataSaved;
    }

    public LiveData<String> getPeriodOfGestation() {
        return periodOfGestation;
    }

    public LiveData<String> getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public LiveData<List<USGScheduleItem>> getUsgSchedule() {
        return usgSchedule;
    }

    /**
     * Get date formatter for display.
     */
    public DateTimeFormatter getDisplayFormatter() {
        return PregnancyCalculator.getDisplayFormatter();
    }

    /**
     * Get date formatter for schedule cards.
     */
    public DateTimeFormatter getScheduleFormatter() {
        return PregnancyCalculator.getFormatter();
    }
}
