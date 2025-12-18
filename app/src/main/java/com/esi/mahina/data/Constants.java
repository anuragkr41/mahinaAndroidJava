package com.esi.mahina.data;

/**
 * Centralized constants for the Mahina app.
 * Contains all SharedPreferences keys, notification channels, and other app-wide constants.
 */
public final class Constants {

    // Prevent instantiation
    private Constants() {}

    // ============================================
    // SharedPreferences
    // ============================================

    /** SharedPreferences file name */
    public static final String PREFS_NAME = "MahinaPrefs";

    // User Preferences Keys
    public static final String KEY_USER_ROLE = "user_role";
    public static final String KEY_LANGUAGE = "app_language";
    public static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";

    // Pregnancy Data Keys
    public static final String KEY_LMP_DATE = "lmp_date";

    // Vaccination Data Keys
    public static final String KEY_BABY_DOB = "baby_dob";

    // ============================================
    // User Role Values
    // ============================================

    public static final String ROLE_DOCTOR = "doctor";
    public static final String ROLE_PATIENT = "patient";

    // ============================================
    // Notification Channels
    // ============================================

    public static final String CHANNEL_USG_REMINDERS = "usg_reminders";
    public static final String CHANNEL_USG_REMINDERS_NAME = "USG Reminders";
    public static final String CHANNEL_USG_REMINDERS_DESC = "Notifications for upcoming ultrasound appointments";

    public static final String CHANNEL_VACCINATION_REMINDERS = "vaccination_reminders";
    public static final String CHANNEL_VACCINATION_REMINDERS_NAME = "Vaccination Reminders";
    public static final String CHANNEL_VACCINATION_REMINDERS_DESC = "Notifications for upcoming vaccinations";

    // ============================================
    // Default Values
    // ============================================

    public static final String DEFAULT_LANGUAGE = "en";
    public static final boolean DEFAULT_NOTIFICATIONS_ENABLED = true;

    // ============================================
    // Supported Languages
    // ============================================

    public static final String[] LANGUAGE_CODES = {"en", "hi", "bn", "ta", "te", "mr", "gu"};
    public static final String[] LANGUAGE_NAMES = {"English", "हिंदी", "বাংলা", "தமிழ்", "తెలుగు", "मराठी", "ગુજરાતી"};

    // ============================================
    // Notification Settings
    // ============================================

    /** Default hour for notification delivery (9 AM) */
    public static final int NOTIFICATION_HOUR = 9;
    /** Default minute for notification delivery */
    public static final int NOTIFICATION_MINUTE = 0;

    // ============================================
    // USG Schedule (weeks from LMP)
    // ============================================

    public static final int USG1_START_WEEK = 6;
    public static final int USG1_END_WEEK = 8;
    public static final int USG2_START_WEEK = 11;
    public static final int USG2_END_WEEK = 14;  // 13 weeks + 6 days
    public static final int USG3_START_WEEK = 18;
    public static final int USG3_END_WEEK = 20;
    public static final int USG4_START_WEEK = 30;
    public static final int USG4_END_WEEK = 32;

    // ============================================
    // Vaccination Schedule (days from DOB)
    // ============================================

    public static final int[] VACCINATION_DAYS = {
            0,      // Birth
            42,     // 6 Weeks
            70,     // 10 Weeks
            98,     // 14 Weeks
            270,    // 9 Months
            365,    // 12 Months
            456,    // 15 Months
            548,    // 18 Months
            730,    // 24 Months (2 years)
            1460,   // 4 Years
            1825,   // 5 Years
            3650    // 10 Years
    };
}
