# Mahina App - Simplified UX Design (v2)

## Core Philosophy
**Simple. No login. No database. Just useful.**

---

## Key Requirements

### What This App IS
- A quick reference tool for doctors
- A personal tracker with notifications for patients
- Offline-first (SharedPreferences only)
- Single-purpose screens

### What This App IS NOT
- No user accounts/login
- No cloud database
- No multi-patient management
- No complex features

---

## Data Storage Strategy

| Data | Storage | Persistence |
|------|---------|-------------|
| User Role (Doctor/Patient) | SharedPreferences | Permanent until changed |
| Patient's LMP | SharedPreferences | Saved, triggers notifications |
| Patient's Baby DOB | SharedPreferences | Saved, triggers notifications |
| Doctor's LMP input | None | Not saved (calculator only) |
| Doctor's Baby DOB input | None | Not saved (calculator only) |

---

## User Flows

### First Launch Flow
```
┌─────────────────┐
│   Splash (1s)   │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│  Role Selection │
│                 │
│  [👨‍⚕️ Doctor]   │
│  [🤰 Patient]   │
│                 │
│ (Saved forever) │
└────────┬────────┘
         │
    ┌────┴────┐
    ▼         ▼
Doctor      Patient
 Home        Home
```

### Doctor Flow
```
┌─────────────────────────────────────┐
│           DOCTOR HOME               │
│                                     │
│   ┌─────────────────────────────┐   │
│   │  📅 View USG Dates          │   │
│   │     Calculate pregnancy     │   │
│   │     ultrasound schedule     │   │
│   └─────────────────────────────┘   │
│                                     │
│   ┌─────────────────────────────┐   │
│   │  💉 View Immunization       │   │
│   │     Schedule                │   │
│   │     Baby vaccination dates  │   │
│   └─────────────────────────────┘   │
│                                     │
│   ─────────────────────────────────│
│   [⚙️ Change to Patient Mode]      │
└─────────────────────────────────────┘
         │                │
         ▼                ▼
    ┌─────────┐     ┌─────────────┐
    │ USG     │     │ Immunization│
    │ Screen  │     │ Screen      │
    └─────────┘     └─────────────┘
```

### Doctor USG Screen
```
┌─────────────────────────────────────┐
│ ←  USG Dates Calculator             │
├─────────────────────────────────────┤
│                                     │
│   Select LMP Date                   │
│   ┌─────────────────────────────┐   │
│   │  📅  [Calendar Picker]      │   │
│   └─────────────────────────────┘   │
│                                     │
│   ▼ Results (after date selected)   │
│                                     │
│   POG: 24 weeks, 3 days             │
│   EDD: 24 Sep 2025                  │
│                                     │
│   ┌─────────────────────────────┐   │
│   │ USG 1 - Dating Scan         │   │
│   │ 6-8 weeks                   │   │
│   │ 28 Jan - 11 Feb 2025        │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ USG 2 - NT Scan             │   │
│   │ 11-13 weeks + 6 days        │   │
│   │ 4 Mar - 25 Mar 2025         │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ USG 3 - Anomaly Scan        │   │
│   │ 18-20 weeks                 │   │
│   │ 29 Apr - 13 May 2025        │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ USG 4 - Growth Scan         │   │
│   │ 30-32 weeks                 │   │
│   │ 12 Aug - 26 Aug 2025        │   │
│   └─────────────────────────────┘   │
│                                     │
│   [📤 Share Results]                │
│                                     │
│   ⚠️ Data not saved (calculator)   │
└─────────────────────────────────────┘
```

### Doctor Immunization Screen
```
┌─────────────────────────────────────┐
│ ←  Immunization Schedule            │
├─────────────────────────────────────┤
│                                     │
│   Select Baby's Date of Birth       │
│   ┌─────────────────────────────┐   │
│   │  📅  [Calendar Picker]      │   │
│   └─────────────────────────────┘   │
│                                     │
│   ▼ Vaccination Schedule            │
│                                     │
│   ┌─────────────────────────────┐   │
│   │ At Birth                    │   │
│   │ BCG, OPV-0, Hep B-1         │   │
│   │ Date: 15 Dec 2024           │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ 6 Weeks                     │   │
│   │ DTwP/DTaP-1, IPV-1, Hep B-2,│   │
│   │ Hib-1, Rotavirus-1, PCV-1   │   │
│   │ Date: 26 Jan 2025           │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ 10 Weeks                    │   │
│   │ DTwP/DTaP-2, IPV-2, Hib-2,  │   │
│   │ Rotavirus-2, PCV-2          │   │
│   │ Date: 23 Feb 2025           │   │
│   └─────────────────────────────┘   │
│   ... (more vaccinations)           │
│                                     │
│   [📤 Share Schedule]               │
│                                     │
│   ⚠️ Data not saved (calculator)   │
└─────────────────────────────────────┘
```

### Patient Flow
```
┌─────────────────────────────────────┐
│           PATIENT HOME              │
│                                     │
│   ┌─────────────────────────────┐   │
│   │  🤰 My Pregnancy            │   │
│   │     USG Schedule            │   │
│   │     (with notifications)    │   │
│   └─────────────────────────────┘   │
│                                     │
│   ┌─────────────────────────────┐   │
│   │  👶 Baby Vaccination        │   │
│   │     Schedule                │   │
│   │     (with notifications)    │   │
│   └─────────────────────────────┘   │
│                                     │
│   ─────────────────────────────────│
│   [⚙️ Change to Doctor Mode]       │
└─────────────────────────────────────┘
```

### Patient USG Screen
```
┌─────────────────────────────────────┐
│ ←  My Pregnancy USG                 │
├─────────────────────────────────────┤
│                                     │
│   My LMP Date                       │
│   ┌─────────────────────────────┐   │
│   │  📅  17 December 2024       │   │
│   │      [Tap to change]        │   │
│   │      ✓ Saved                │   │
│   └─────────────────────────────┘   │
│                                     │
│   🔔 Notifications: ON              │
│   (72h, 48h, 24h, and on date)      │
│                                     │
│   ┌─────────────────────────────┐   │
│   │ Week 24, Day 3              │   │
│   │ EDD: 24 Sep 2025            │   │
│   │ ━━━━━━━━━━●━━━━━━━━        │   │
│   └─────────────────────────────┘   │
│                                     │
│   ┌─────────────────────────────┐   │
│   │ ✓ USG 1 - Dating Scan       │   │
│   │   Completed                 │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ ● USG 2 - NT Scan           │   │
│   │   🔔 In 5 days              │   │
│   │   4 Mar - 25 Mar 2025       │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ ○ USG 3 - Anomaly Scan      │   │
│   │   29 Apr - 13 May 2025      │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ ○ USG 4 - Growth Scan       │   │
│   │   12 Aug - 26 Aug 2025      │   │
│   └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

### Patient Vaccination Screen
```
┌─────────────────────────────────────┐
│ ←  Baby Vaccination Schedule        │
├─────────────────────────────────────┤
│                                     │
│   Baby's Date of Birth              │
│   ┌─────────────────────────────┐   │
│   │  📅  15 December 2024       │   │
│   │      [Tap to change]        │   │
│   │      ✓ Saved                │   │
│   └─────────────────────────────┘   │
│                                     │
│   🔔 Notifications: ON              │
│   (72h, 48h, 24h, and on date)      │
│                                     │
│   ┌─────────────────────────────┐   │
│   │ ✓ At Birth                  │   │
│   │   BCG, OPV-0, Hep B-1       │   │
│   │   Completed                 │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ ● 6 Weeks                   │   │
│   │   DTwP/DTaP-1, IPV-1...     │   │
│   │   🔔 Tomorrow!              │   │
│   │   Date: 26 Jan 2025         │   │
│   └─────────────────────────────┘   │
│   ┌─────────────────────────────┐   │
│   │ ○ 10 Weeks                  │   │
│   │   DTwP/DTaP-2, IPV-2...     │   │
│   │   Date: 23 Feb 2025         │   │
│   └─────────────────────────────┘   │
│   ... (more vaccinations)           │
│                                     │
└─────────────────────────────────────┘
```

---

## Notification Strategy (Patient Only)

### When Notifications Fire
For each USG date and each Vaccination date:
1. **72 hours before** - "Reminder: USG 2 (NT Scan) in 3 days"
2. **48 hours before** - "Reminder: USG 2 (NT Scan) in 2 days"
3. **24 hours before** - "Reminder: USG 2 (NT Scan) tomorrow!"
4. **On the date** - "Today: USG 2 (NT Scan) is due"

### Notification Content
```
Title: Mahina Reminder
Body: [Event name] is [in X days / tomorrow / today]
      Tap to view details.
```

---

## Screen Inventory

| # | Screen | Doctor | Patient |
|---|--------|--------|---------|
| 1 | Splash | ✓ | ✓ |
| 2 | Role Selection (first launch) | ✓ | ✓ |
| 3 | Home | ✓ (2 options) | ✓ (2 options) |
| 4 | USG Calculator/Tracker | ✓ (no save) | ✓ (save + notify) |
| 5 | Immunization/Vaccination | ✓ (no save) | ✓ (save + notify) |

**Total: 5 unique screens** (with mode variations)

---

## SharedPreferences Keys

```java
// User role
"user_role" → "doctor" | "patient"

// Patient data (only used in patient mode)
"patient_lmp_date" → "2024-12-17" (ISO format)
"patient_baby_dob" → "2024-12-15" (ISO format)

// Notification settings
"notifications_enabled" → true | false
```

---

## Immunization Schedule Data (IAP India)

| Age | Vaccines |
|-----|----------|
| Birth | BCG, OPV-0, Hepatitis B-1 |
| 6 weeks | DTwP/DTaP-1, IPV-1, Hep B-2, Hib-1, Rotavirus-1, PCV-1 |
| 10 weeks | DTwP/DTaP-2, IPV-2, Hib-2, Rotavirus-2, PCV-2 |
| 14 weeks | DTwP/DTaP-3, IPV-3, Hib-3, Rotavirus-3, PCV-3 |
| 6 months | OPV-1, Hepatitis B-3 |
| 9 months | MMR-1, OPV-2 |
| 9-12 months | Typhoid Conjugate Vaccine |
| 12 months | Hepatitis A-1 |
| 15 months | MMR-2, Varicella-1, PCV Booster |
| 16-18 months | DTwP/DTaP B1, IPV B1, Hib B1 |
| 18 months | Hepatitis A-2 |
| 4-6 years | DTwP/DTaP B2, OPV-3, Varicella-2, MMR-3 |
| 10-12 years | Tdap, HPV (girls) |

---

## Design Principles

1. **One screen, one purpose** - No multi-tasking screens
2. **Clear mode distinction** - Doctor sees "Calculator", Patient sees "My..."
3. **Obvious save status** - Patient always knows data is saved
4. **Notification transparency** - Show when notifications will fire
5. **Easy mode switch** - Can change role from home screen settings

---

## Next Steps

1. Create wireframes for each screen
2. Define color coding (Doctor = Professional Blue, Patient = Warm Rose)
3. Design notification templates
4. Create XML layouts
