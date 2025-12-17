# Mahina App - Complete Flow Diagram

## Visual Flow

```
                    ┌─────────────┐
                    │   SPLASH    │
                    │    (1s)     │
                    └──────┬──────┘
                           │
              ┌────────────┴────────────┐
              │    First Launch?        │
              └────────────┬────────────┘
                     YES   │   NO
              ┌────────────┴────────────┐
              ▼                         ▼
     ┌─────────────────┐      ┌─────────────────┐
     │ ROLE SELECTION  │      │  Check Saved    │
     │                 │      │     Role        │
     │ [👨‍⚕️ Doctor]    │      └────────┬────────┘
     │ [🤰 Patient]    │               │
     └────────┬────────┘      ┌────────┴────────┐
              │               ▼                 ▼
              │        ┌───────────┐     ┌───────────┐
              └───────►│  DOCTOR   │     │  PATIENT  │
                       │   HOME    │     │   HOME    │
                       └─────┬─────┘     └─────┬─────┘
                             │                 │
              ┌──────────────┼──────────────┐  │
              ▼              ▼              │  │
     ┌─────────────┐  ┌─────────────┐       │  │
     │ USG Dates   │  │Immunization │       │  │
     │ Calculator  │  │  Schedule   │       │  │
     │             │  │             │       │  │
     │ • Select LMP│  │• Select DOB │       │  │
     │ • View dates│  │• View dates │       │  │
     │ • Share     │  │• Share      │       │  │
     │             │  │             │       │  │
     │ ⚠️ NO SAVE  │  │ ⚠️ NO SAVE  │       │  │
     └─────────────┘  └─────────────┘       │  │
                                            │  │
                       ┌────────────────────┘  │
                       │                       │
              ┌────────┴───────────────────────┴────────┐
              ▼                                        ▼
     ┌─────────────────┐                    ┌─────────────────┐
     │  My Pregnancy   │                    │ Baby Vaccination│
     │   USG Tracker   │                    │    Schedule     │
     │                 │                    │                 │
     │ • Select LMP    │                    │ • Select DOB    │
     │ • ✓ AUTO SAVE   │                    │ • ✓ AUTO SAVE   │
     │ • View timeline │                    │ • View timeline │
     │ • 🔔 Notifs ON  │                    │ • 🔔 Notifs ON  │
     │                 │                    │                 │
     │ Notifications:  │                    │ Notifications:  │
     │ 72h, 48h, 24h,  │                    │ 72h, 48h, 24h,  │
     │ on date         │                    │ on date         │
     └─────────────────┘                    └─────────────────┘
```

---

## Screen Details

### 1. Splash Screen
- Duration: 1 second
- Shows: Logo + App name + Tagline
- Next: Check if role exists in SharedPreferences

### 2. Role Selection (First Launch Only)
- Shows only if no role saved
- Two large cards: Doctor / Patient
- Selection saves to SharedPreferences permanently
- Can be changed later from Home screen

### 3. Doctor Home
```
┌─────────────────────────────────────┐
│  Mahina                    ⚙️       │
│  For Healthcare Professionals       │
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────┐    │
│  │     📅                      │    │
│  │                             │    │
│  │   View USG Dates            │    │
│  │                             │    │
│  │   Calculate pregnancy       │    │
│  │   ultrasound schedule       │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │     💉                      │    │
│  │                             │    │
│  │   View Immunization         │    │
│  │   Schedule                  │    │
│  │                             │    │
│  │   Baby vaccination          │    │
│  │   dates calculator          │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│                                     │
├─────────────────────────────────────┤
│  ℹ️ Data is not saved in doctor    │
│     mode (calculator only)          │
└─────────────────────────────────────┘
```

### 4. Patient Home
```
┌─────────────────────────────────────┐
│  Mahina                    ⚙️       │
│  Your Pregnancy Companion           │
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────┐    │
│  │     🤰                      │    │
│  │                             │    │
│  │   My Pregnancy              │    │
│  │   USG Schedule              │    │
│  │                             │    │
│  │   Track your ultrasound     │    │
│  │   appointments              │    │
│  │                             │    │
│  │   🔔 Reminders enabled      │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │     👶                      │    │
│  │                             │    │
│  │   Baby Vaccination          │    │
│  │   Schedule                  │    │
│  │                             │    │
│  │   Track your baby's         │    │
│  │   immunization dates        │    │
│  │                             │    │
│  │   🔔 Reminders enabled      │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

### 5. Settings (Accessed from ⚙️)
```
┌─────────────────────────────────────┐
│ ←  Settings                         │
├─────────────────────────────────────┤
│                                     │
│  Current Mode                       │
│  ┌─────────────────────────────┐    │
│  │  👨‍⚕️ Doctor Mode            │    │
│  │      [Switch to Patient]    │    │
│  └─────────────────────────────┘    │
│                                     │
│  About                              │
│  ┌─────────────────────────────┐    │
│  │  Designed & Developed by    │    │
│  │  Dr. Neetu and Anurag       │    │
│  │                             │    │
│  │  ESI Govind Puri Haridwar   │    │
│  │                             │    │
│  │  Version 1.0                │    │
│  └─────────────────────────────┘    │
│                                     │
└─────────────────────────────────────┘
```

---

## Key Differences: Doctor vs Patient

| Feature | Doctor | Patient |
|---------|--------|---------|
| Data saved | ❌ No | ✅ Yes |
| Notifications | ❌ No | ✅ Yes (72h, 48h, 24h, 0h) |
| Screen title | "Calculator" | "My..." |
| Share button | ✅ Yes | Optional |
| Status indicators | ❌ No | ✅ Yes (✓ completed, ● upcoming) |
| Progress tracking | ❌ No | ✅ Yes |

---

## Navigation Pattern

- **Back button**: Always returns to Home
- **Settings gear**: Opens settings overlay/screen
- **No bottom navigation** (only 2 options, cards are sufficient)
- **No hamburger menu** (keep it simple)

---

## Color Coding by Mode

### Doctor Mode
- Primary: `#5B7DB1` (Professional Blue)
- Cards: White with blue accents
- Header: "For Healthcare Professionals"

### Patient Mode
- Primary: `#E8A5B8` (Warm Rose)
- Cards: White with rose accents
- Header: "Your Pregnancy Companion"
