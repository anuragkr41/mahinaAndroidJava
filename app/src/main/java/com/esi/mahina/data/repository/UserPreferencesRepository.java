package com.esi.mahina.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.esi.mahina.data.Constants;
import com.esi.mahina.data.model.UserRole;

import java.time.LocalDate;

/**
 * Repository for managing user preferences.
 * Provides a single source of truth for all SharedPreferences access.
 */
public class UserPreferencesRepository {

    private final SharedPreferences prefs;

    public UserPreferencesRepository(Context context) {
        this.prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    // ============================================
    // User Role
    // ============================================

    /**
     * Get the current user role.
     * @return UserRole or null if not set
     */
    public UserRole getUserRole() {
        String roleValue = prefs.getString(Constants.KEY_USER_ROLE, null);
        return UserRole.fromValue(roleValue);
    }

    /**
     * Set the user role.
     * @param role The role to set
     */
    public void setUserRole(UserRole role) {
        prefs.edit().putString(Constants.KEY_USER_ROLE, role.getValue()).apply();
    }

    /**
     * Check if a user role has been selected.
     */
    public boolean hasUserRole() {
        return getUserRole() != null;
    }

    /**
     * Clear the user role (used when switching modes).
     */
    public void clearUserRole() {
        prefs.edit().remove(Constants.KEY_USER_ROLE).apply();
    }

    // ============================================
    // Language
    // ============================================

    /**
     * Get the current language code.
     * @return Language code (e.g., "en", "hi") or default
     */
    public String getLanguage() {
        return prefs.getString(Constants.KEY_LANGUAGE, Constants.DEFAULT_LANGUAGE);
    }

    /**
     * Set the language code.
     * @param languageCode The language code to set
     */
    public void setLanguage(String languageCode) {
        prefs.edit().putString(Constants.KEY_LANGUAGE, languageCode).apply();
    }

    // ============================================
    // Notifications
    // ============================================

    /**
     * Check if notifications are enabled.
     */
    public boolean areNotificationsEnabled() {
        return prefs.getBoolean(Constants.KEY_NOTIFICATIONS_ENABLED, Constants.DEFAULT_NOTIFICATIONS_ENABLED);
    }

    /**
     * Set notifications enabled state.
     */
    public void setNotificationsEnabled(boolean enabled) {
        prefs.edit().putBoolean(Constants.KEY_NOTIFICATIONS_ENABLED, enabled).apply();
    }

    // ============================================
    // Pregnancy Data (LMP)
    // ============================================

    /**
     * Get the saved LMP (Last Menstrual Period) date.
     * @return LocalDate or null if not set
     */
    public LocalDate getLmpDate() {
        String dateStr = prefs.getString(Constants.KEY_LMP_DATE, null);
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Save the LMP date.
     * @param lmpDate The LMP date to save
     */
    public void setLmpDate(LocalDate lmpDate) {
        if (lmpDate == null) {
            prefs.edit().remove(Constants.KEY_LMP_DATE).apply();
        } else {
            prefs.edit().putString(Constants.KEY_LMP_DATE, lmpDate.toString()).apply();
        }
    }

    /**
     * Check if LMP date is set.
     */
    public boolean hasLmpDate() {
        return getLmpDate() != null;
    }

    /**
     * Clear the LMP date.
     */
    public void clearLmpDate() {
        prefs.edit().remove(Constants.KEY_LMP_DATE).apply();
    }

    // ============================================
    // Vaccination Data (Baby DOB)
    // ============================================

    /**
     * Get the saved baby's date of birth.
     * @return LocalDate or null if not set
     */
    public LocalDate getBabyDob() {
        String dateStr = prefs.getString(Constants.KEY_BABY_DOB, null);
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Save the baby's date of birth.
     * @param dob The date of birth to save
     */
    public void setBabyDob(LocalDate dob) {
        if (dob == null) {
            prefs.edit().remove(Constants.KEY_BABY_DOB).apply();
        } else {
            prefs.edit().putString(Constants.KEY_BABY_DOB, dob.toString()).apply();
        }
    }

    /**
     * Check if baby DOB is set.
     */
    public boolean hasBabyDob() {
        return getBabyDob() != null;
    }

    /**
     * Clear the baby DOB.
     */
    public void clearBabyDob() {
        prefs.edit().remove(Constants.KEY_BABY_DOB).apply();
    }

    // ============================================
    // Utility Methods
    // ============================================

    /**
     * Clear all patient data (LMP, DOB) but keep user preferences (role, language).
     */
    public void clearPatientData() {
        prefs.edit()
                .remove(Constants.KEY_LMP_DATE)
                .remove(Constants.KEY_BABY_DOB)
                .apply();
    }

    /**
     * Clear all preferences.
     */
    public void clearAll() {
        prefs.edit().clear().apply();
    }
}
