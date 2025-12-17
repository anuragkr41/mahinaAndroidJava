# Patient Vaccination Tracker Screen

## Purpose
Personal baby vaccination tracker with:
- **Saved Baby DOB** (persists in SharedPreferences)
- **Notifications** (72h, 48h, 24h, on-date for each vaccination)
- **Progress visualization**

---

## Layout - First Time (No DOB Saved)

```
┌─────────────────────────────────────┐
│ ←  Baby Vaccination Schedule        │
├─────────────────────────────────────┤
│                                     │
│  Set Baby's Date of Birth           │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │    📅  Select Date          │    │
│  │                             │    │
│  │    Tap to choose DOB        │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│         ┌───────────────┐           │
│         │               │           │
│         │      👶       │           │
│         │               │           │
│         └───────────────┘           │
│                                     │
│    Set your baby's date of birth    │
│    to start tracking vaccinations   │
│                                     │
│    🔔 You'll receive reminders      │
│    72h, 48h, 24h before each        │
│    vaccination and on the date      │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

---

## Layout - After DOB Saved

```
┌─────────────────────────────────────┐
│ ←  Baby Vaccination Schedule        │
├─────────────────────────────────────┤
│                                     │
│  Baby's Date of Birth               │
│  ┌─────────────────────────────┐    │
│  │  📅  15 December 2024       │    │
│  │      ✓ Saved                │    │
│  │      [Tap to change]        │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │   Baby's Age                │    │
│  │   1 month, 15 days          │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│  🔔 Reminders                       │
│  ┌─────────────────────────────┐    │
│  │  Notifications: ON          │    │
│  │  72h, 48h, 24h, and on date │    │
│  └─────────────────────────────┘    │
│                                     │
│  Vaccination Schedule               │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ✓ At Birth                 │    │
│  │    ─────────────────────    │    │
│  │    BCG                      │    │
│  │    OPV-0                    │    │
│  │    Hepatitis B - Birth dose │    │
│  │    ─────────────────────    │    │
│  │    📅 15 Dec 2024           │    │
│  │    Completed                │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ● 6 Weeks                  │    │
│  │    ─────────────────────    │    │
│  │    DTwP/DTaP - 1            │    │
│  │    IPV - 1                  │    │
│  │    Hepatitis B - 2          │    │
│  │    Hib - 1                  │    │
│  │    Rotavirus - 1            │    │
│  │    PCV - 1                  │    │
│  │    ─────────────────────    │    │
│  │    📅 26 Jan 2025           │    │
│  │    🔔 In 3 days!            │    │
│  │    UPCOMING                 │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ○ 10 Weeks                 │    │
│  │    ─────────────────────    │    │
│  │    DTwP/DTaP - 2            │    │
│  │    IPV - 2                  │    │
│  │    Hib - 2                  │    │
│  │    Rotavirus - 2            │    │
│  │    PCV - 2                  │    │
│  │    ─────────────────────    │    │
│  │    📅 23 Feb 2025           │    │
│  │    In 7 weeks               │    │
│  └─────────────────────────────┘    │
│                                     │
│  ... (more vaccinations below)      │
│                                     │
└─────────────────────────────────────┘
```

---

## Status States

### Completed
```
┌─────────────────────────────────────┐
│  ✓ At Birth                         │
│    ─────────────────────            │
│    BCG, OPV-0, Hepatitis B          │
│    ─────────────────────            │
│    📅 15 Dec 2024                   │
│    Completed                        │
└─────────────────────────────────────┘

Colors:
- Left border: #7AB89B (sage green)
- Checkmark: #7AB89B
- Status text: #7AB89B
- Background: #F0F9F4
```

### Upcoming (Next due)
```
┌─────────────────────────────────────┐
│  ● 6 Weeks                          │
│    ─────────────────────            │
│    DTwP/DTaP-1, IPV-1, Hep B-2...   │
│    ─────────────────────            │
│    📅 26 Jan 2025                   │
│    🔔 In 3 days!                    │
│    UPCOMING                         │
└─────────────────────────────────────┘

Colors:
- Left border: #E8A5B8 (rose)
- Status dot: #E8A5B8 filled
- Bell + countdown: #E8A5B8
- Background: #FDF2F5
```

### Overdue (Past due date, not marked complete)
```
┌─────────────────────────────────────┐
│  ⚠️ 6 Weeks                         │
│    ─────────────────────            │
│    DTwP/DTaP-1, IPV-1, Hep B-2...   │
│    ─────────────────────            │
│    📅 26 Jan 2025                   │
│    OVERDUE by 5 days                │
└─────────────────────────────────────┘

Colors:
- Left border: #E57373 (coral red)
- Warning icon: #E57373
- Status text: #E57373
- Background: #FEF2F2
```

### Future
```
┌─────────────────────────────────────┐
│  ○ 10 Weeks                         │
│    DTwP/DTaP-2, IPV-2...            │
│    ─────────────────────            │
│    📅 23 Feb 2025                   │
│    In 7 weeks                       │
└─────────────────────────────────────┘

Colors:
- Left border: #D1D5DB (grey)
- Status circle: empty
- Background: #FFFFFF
```

---

## Design Specs

### App Bar
- Title: "Baby Vaccination Schedule"
- Back navigation to Patient Home
- Primary color: #E8A5B8

### Date Card with Saved Indicator
- Same as Patient USG screen
- "✓ Saved" badge in green

### Age Display Card
- Background: Gradient #E8A5B8 → #CC76A1
- Age: 28sp, White, Bold
- Format: "X months, Y days" or "X years, Y months"

### Notification Settings Card
- Same as Patient USG screen

### Vaccination Cards
- Expandable/collapsible for vaccine list
- Status indicator (✓ ● ○ ⚠️)
- Date badge
- Countdown or overdue indicator

---

## Notification Logic

### When Notifications Fire

```java
// For 6-week vaccination on January 26, 2025
LocalDate vaccinationDate = dob.plusWeeks(6);

// Schedule 4 notifications:
scheduleNotification(
    vaccinationDate.minusDays(3),
    "6-week vaccination in 3 days",
    "DTwP/DTaP-1, IPV-1, Hepatitis B-2, Hib-1, Rotavirus-1, PCV-1"
);

scheduleNotification(
    vaccinationDate.minusDays(2),
    "6-week vaccination in 2 days",
    "..."
);

scheduleNotification(
    vaccinationDate.minusDays(1),
    "6-week vaccination tomorrow!",
    "..."
);

scheduleNotification(
    vaccinationDate,
    "6-week vaccination is due today!",
    "..."
);
```

### Notification Content
```
Title: Mahina - Vaccination Reminder
Body: 6-week vaccination is due tomorrow!
      Vaccines: DTwP/DTaP-1, IPV-1, Hep B-2, Hib-1, Rotavirus-1, PCV-1

Action: Opens Patient Vaccination screen
```

---

## Full Vaccination Schedule (IAP India)

| Age | Vaccines | Days from DOB |
|-----|----------|---------------|
| Birth | BCG, OPV-0, Hep B-1 | 0 |
| 6 Weeks | DTwP/DTaP-1, IPV-1, Hep B-2, Hib-1, Rotavirus-1, PCV-1 | 42 |
| 10 Weeks | DTwP/DTaP-2, IPV-2, Hib-2, Rotavirus-2, PCV-2 | 70 |
| 14 Weeks | DTwP/DTaP-3, IPV-3, Hib-3, Rotavirus-3, PCV-3 | 98 |
| 6 Months | OPV-1, Hep B-3 | 182 |
| 9 Months | MMR-1, OPV-2 | 274 |
| 9-12 Months | Typhoid Conjugate Vaccine | 274 |
| 12 Months | Hepatitis A-1 | 365 |
| 15 Months | MMR-2, Varicella-1, PCV Booster | 456 |
| 16-18 Months | DTwP/DTaP-B1, IPV-B1, Hib-B1 | 487 |
| 18 Months | Hepatitis A-2 | 548 |
| 4-6 Years | DTwP/DTaP-B2, OPV-3, Varicella-2, MMR-3 | 1461 |
| 10-12 Years | Tdap/Td, HPV (girls) | 3652 |

---

## SharedPreferences

```java
// Save Baby DOB
SharedPreferences prefs = getSharedPreferences("MahinaPrefs", MODE_PRIVATE);
prefs.edit()
    .putString("patient_baby_dob", dobDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
    .apply();

// Schedule notifications
NotificationScheduler.scheduleVaccinationReminders(context, dobDate);
```

---

## Auto-Status Calculation

```java
public VaccinationStatus getStatus(LocalDate vaccinationDate, LocalDate today) {
    long daysDiff = ChronoUnit.DAYS.between(vaccinationDate, today);

    if (daysDiff > 7) {
        // More than 7 days past due
        return VaccinationStatus.OVERDUE;
    } else if (daysDiff > 0) {
        // Past due but within grace period
        return VaccinationStatus.DUE_NOW;
    } else if (daysDiff > -14) {
        // Within 2 weeks
        return VaccinationStatus.UPCOMING;
    } else {
        return VaccinationStatus.FUTURE;
    }
}
```

---

## Future Enhancement

Allow patient to manually mark vaccinations as complete:
- Tap card → "Mark as Complete" button
- Save completion status to SharedPreferences
- Update UI to show ✓
- Cancel pending notifications for that vaccination
