package com.esi.mahina.data.model;

import com.esi.mahina.data.Constants;

/**
 * Enum representing the user's role in the application.
 * The app behaves differently based on whether the user is a Doctor or Patient.
 */
public enum UserRole {
    /**
     * Doctor mode - Calculator only, no data persistence or notifications.
     * Used for healthcare professionals to quickly calculate dates.
     */
    DOCTOR(Constants.ROLE_DOCTOR),

    /**
     * Patient mode - Full features with data persistence and notifications.
     * Used for pregnant women and parents to track their pregnancy/baby.
     */
    PATIENT(Constants.ROLE_PATIENT);

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    /**
     * Get the string value used for SharedPreferences storage.
     */
    public String getValue() {
        return value;
    }

    /**
     * Convert a string value from SharedPreferences to UserRole enum.
     * @param value The stored string value
     * @return The corresponding UserRole, or null if not found
     */
    public static UserRole fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (UserRole role : values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        return null;
    }

    /**
     * Check if this role is Doctor mode.
     */
    public boolean isDoctor() {
        return this == DOCTOR;
    }

    /**
     * Check if this role is Patient mode.
     */
    public boolean isPatient() {
        return this == PATIENT;
    }
}
