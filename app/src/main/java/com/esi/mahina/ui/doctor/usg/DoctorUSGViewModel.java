package com.esi.mahina.ui.doctor.usg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esi.mahina.data.model.PregnancyData;
import com.esi.mahina.data.model.USGScheduleItem;
import com.esi.mahina.domain.calculator.PregnancyCalculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ViewModel for DoctorUSGActivity.
 * Calculator-only mode - no data persistence.
 * Used by healthcare professionals for quick date calculations.
 */
public class DoctorUSGViewModel extends ViewModel {

    // LiveData for UI state
    private final MutableLiveData<LocalDate> lmpDate = new MutableLiveData<>();
    private final MutableLiveData<PregnancyData> pregnancyData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasCalculated = new MutableLiveData<>(false);
    private final MutableLiveData<String> periodOfGestation = new MutableLiveData<>();
    private final MutableLiveData<String> expectedDeliveryDate = new MutableLiveData<>();
    private final MutableLiveData<List<USGScheduleItem>> usgSchedule = new MutableLiveData<>();

    /**
     * Calculate pregnancy data from a given LMP.
     * Does not persist the data.
     *
     * @param lmp The Last Menstrual Period date
     */
    public void calculateFromLmp(LocalDate lmp) {
        if (lmp == null) {
            clearResults();
            return;
        }

        lmpDate.setValue(lmp);
        hasCalculated.setValue(true);

        PregnancyData data = PregnancyCalculator.calculate(lmp);
        pregnancyData.setValue(data);

        // Update individual fields
        periodOfGestation.setValue(data.getPeriodOfGestationString());

        DateTimeFormatter formatter = PregnancyCalculator.getDisplayFormatter();
        expectedDeliveryDate.setValue(data.getFormattedEdd(formatter));

        usgSchedule.setValue(data.getUsgSchedule());
    }

    /**
     * Clear all calculation results.
     */
    public void clearResults() {
        lmpDate.setValue(null);
        pregnancyData.setValue(null);
        hasCalculated.setValue(false);
        periodOfGestation.setValue(null);
        expectedDeliveryDate.setValue(null);
        usgSchedule.setValue(null);
    }

    // LiveData getters

    public LiveData<LocalDate> getLmpDate() {
        return lmpDate;
    }

    public LiveData<PregnancyData> getPregnancyData() {
        return pregnancyData;
    }

    public LiveData<Boolean> getHasCalculated() {
        return hasCalculated;
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
