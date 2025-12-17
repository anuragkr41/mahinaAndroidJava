# Settings Screen

## Purpose
Allow user to:
- Switch between Doctor/Patient mode
- View app information
- (Patient only) Toggle notifications

---

## Layout - Doctor Mode

```
┌─────────────────────────────────────┐
│ ←  Settings                         │
├─────────────────────────────────────┤
│                                     │
│  App Mode                           │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │  👨‍⚕️ Doctor Mode            │    │
│  │                             │    │
│  │  Calculator mode - data     │    │
│  │  is not saved               │    │
│  │                             │    │
│  │  ─────────────────────────  │    │
│  │                             │    │
│  │  [Switch to Patient Mode]   │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│                                     │
│  About                              │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │  🌙 Mahina                  │    │
│  │  Version 1.0.0              │    │
│  │                             │    │
│  │  ─────────────────────────  │    │
│  │                             │    │
│  │  Designed & Developed by    │    │
│  │  Dr. Neetu and Anurag       │    │
│  │                             │    │
│  │  ESI Govind Puri Haridwar   │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│                                     │
│                                     │
│                                     │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

---

## Layout - Patient Mode

```
┌─────────────────────────────────────┐
│ ←  Settings                         │
├─────────────────────────────────────┤
│                                     │
│  App Mode                           │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │  🤰 Patient Mode            │    │
│  │                             │    │
│  │  Your data is saved and     │    │
│  │  you receive reminders      │    │
│  │                             │    │
│  │  ─────────────────────────  │    │
│  │                             │    │
│  │  [Switch to Doctor Mode]    │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│  Notifications                      │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │  🔔 Enable Reminders   [ON] │    │
│  │                             │    │
│  │  Receive notifications 72h, │    │
│  │  48h, 24h before events and │    │
│  │  on the scheduled date      │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│  Saved Data                         │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │  LMP: 17 December 2024      │    │
│  │  Baby DOB: Not set          │    │
│  │                             │    │
│  │  ─────────────────────────  │    │
│  │                             │    │
│  │  [Clear All Data]           │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│  About                              │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  🌙 Mahina v1.0.0           │    │
│  │  Dr. Neetu and Anurag       │    │
│  │  ESI Govind Puri Haridwar   │    │
│  └─────────────────────────────┘    │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specs

### App Bar
- Title: "Settings"
- Back navigation
- No additional icons

### Section Headers
- Text: 14sp, Lexend Medium, #6B7280
- Margin: 24dp top, 8dp bottom
- All caps: No

### Setting Cards
- Background: #FFFFFF
- Corner radius: 12dp
- Elevation: 1dp
- Padding: 16dp

### Mode Card
- Icon: 32dp emoji
- Title: 18sp, Lexend Medium, #3D4852
- Description: 14sp, Lexend Regular, #6B7280
- Divider: 1dp, #E5E7EB
- Button: Outlined, full width

### Toggle Row
- Icon: 24dp, #6B7280
- Text: 16sp, Lexend Regular, #3D4852
- Toggle: Material Switch
- Description: 12sp, #9CA3AF

### About Card (Compact)
- Logo: 24dp
- App name + version: 16sp, #3D4852
- Credits: 14sp, #6B7280

---

## Switch Mode Confirmation

When user taps "Switch to [X] Mode":

```
┌─────────────────────────────────────┐
│                                     │
│         Switch to Doctor Mode?      │
│                                     │
│   In Doctor mode:                   │
│   • Data is not saved               │
│   • No notifications                │
│   • Calculator only                 │
│                                     │
│   Your saved data will be           │
│   preserved if you switch back.     │
│                                     │
│   ┌─────────┐     ┌─────────────┐   │
│   │ Cancel  │     │   Switch    │   │
│   └─────────┘     └─────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

---

## Clear Data Confirmation (Patient Only)

```
┌─────────────────────────────────────┐
│                                     │
│         Clear All Data?             │
│                                     │
│   This will remove:                 │
│   • Your saved LMP date             │
│   • Your baby's date of birth       │
│   • All scheduled notifications     │
│                                     │
│   This action cannot be undone.     │
│                                     │
│   ┌─────────┐     ┌─────────────┐   │
│   │ Cancel  │     │   Clear     │   │
│   └─────────┘     └─────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

---

## SharedPreferences Operations

### Switch Mode
```java
void switchMode(String newMode) {
    SharedPreferences prefs = getSharedPreferences("MahinaPrefs", MODE_PRIVATE);
    prefs.edit().putString("user_role", newMode).apply();

    // Restart to appropriate home screen
    Intent intent;
    if (newMode.equals("doctor")) {
        intent = new Intent(this, DoctorHomeActivity.class);
    } else {
        intent = new Intent(this, PatientHomeActivity.class);
    }
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
}
```

### Toggle Notifications (Patient Only)
```java
void toggleNotifications(boolean enabled) {
    SharedPreferences prefs = getSharedPreferences("MahinaPrefs", MODE_PRIVATE);
    prefs.edit().putBoolean("notifications_enabled", enabled).apply();

    if (enabled) {
        // Reschedule all notifications based on saved dates
        rescheduleAllNotifications();
    } else {
        // Cancel all pending notifications
        NotificationScheduler.cancelAll(context);
    }
}
```

### Clear All Data
```java
void clearAllData() {
    SharedPreferences prefs = getSharedPreferences("MahinaPrefs", MODE_PRIVATE);
    prefs.edit()
        .remove("patient_lmp_date")
        .remove("patient_baby_dob")
        .apply();

    // Cancel all notifications
    NotificationScheduler.cancelAll(context);

    // Refresh screen
    recreate();
}
```

---

## Navigation

```
Settings
    │
    ├── [Switch Mode] → Confirmation Dialog → Home Screen (new mode)
    │
    ├── [Notifications Toggle] → Enable/Disable (Patient only)
    │
    ├── [Clear Data] → Confirmation Dialog → Refresh (Patient only)
    │
    └── [Back] → Return to Home
```
