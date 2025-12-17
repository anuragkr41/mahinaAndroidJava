# Patient USG Tracker Screen

## Purpose
Personal pregnancy USG tracker with:
- **Saved LMP date** (persists in SharedPreferences)
- **Notifications** (72h, 48h, 24h, on-date for each USG)
- **Progress visualization**

---

## Layout - First Time (No LMP Saved)

```
┌─────────────────────────────────────┐
│ ←  My Pregnancy USG                 │
├─────────────────────────────────────┤
│                                     │
│  Set Your LMP Date                  │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │    📅  Select Date          │    │
│  │                             │    │
│  │    Tap to choose LMP        │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│         ┌───────────────┐           │
│         │               │           │
│         │      🤰       │           │
│         │               │           │
│         └───────────────┘           │
│                                     │
│    Set your Last Menstrual Period   │
│    to start tracking your           │
│    pregnancy journey                │
│                                     │
│    🔔 You'll receive reminders      │
│    72h, 48h, 24h before each USG    │
│    and on the scheduled date        │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

---

## Layout - After LMP Saved

```
┌─────────────────────────────────────┐
│ ←  My Pregnancy USG                 │
├─────────────────────────────────────┤
│                                     │
│  My LMP Date                        │
│  ┌─────────────────────────────┐    │
│  │  📅  17 December 2024       │    │
│  │      ✓ Saved                │    │
│  │      [Tap to change]        │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │      Week 24, Day 3         │    │
│  │                             │    │
│  │   ━━━━━━━━━━━●━━━━━━━━━━   │    │
│  │   0         24          40  │    │
│  │                             │    │
│  │   EDD: 24 September 2025    │    │
│  │   112 days to go            │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│  🔔 Reminders                       │
│  ┌─────────────────────────────┐    │
│  │  Notifications: ON          │    │
│  │  72h, 48h, 24h, and on date │    │
│  └─────────────────────────────┘    │
│                                     │
│  USG Schedule                       │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ✓ USG 1 - Dating Scan      │    │
│  │    6-8 weeks                │    │
│  │    28 Jan - 11 Feb 2025     │    │
│  │    ─────────────────────    │    │
│  │    Completed                │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ✓ USG 2 - NT Scan          │    │
│  │    11-13 weeks + 6 days     │    │
│  │    4 Mar - 25 Mar 2025      │    │
│  │    ─────────────────────    │    │
│  │    Completed                │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ● USG 3 - Anomaly Scan     │    │
│  │    18-20 weeks              │    │
│  │    29 Apr - 13 May 2025     │    │
│  │    ─────────────────────    │    │
│  │    🔔 Starts in 14 days     │    │
│  │    UPCOMING                 │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ○ USG 4 - Growth Scan      │    │
│  │    30-32 weeks              │    │
│  │    12 Aug - 26 Aug 2025     │    │
│  │    ─────────────────────    │    │
│  │    In 16 weeks              │    │
│  └─────────────────────────────┘    │
│                                     │
└─────────────────────────────────────┘
```

---

## Status States

### Completed (Past)
```
┌─────────────────────────────────────┐
│  ✓ USG 1 - Dating Scan              │
│    6-8 weeks                        │
│    28 Jan - 11 Feb 2025             │
│    ─────────────────────            │
│    Completed                        │
└─────────────────────────────────────┘

Colors:
- Left border: #7AB89B (sage green)
- Checkmark: #7AB89B
- Status text: #7AB89B
- Background: #F0F9F4 (light green tint)
```

### Current/Upcoming (Within Window or Next)
```
┌─────────────────────────────────────┐
│  ● USG 3 - Anomaly Scan             │
│    18-20 weeks                      │
│    29 Apr - 13 May 2025             │
│    ─────────────────────            │
│    🔔 Starts in 14 days             │
│    UPCOMING                         │
└─────────────────────────────────────┘

Colors:
- Left border: #E8A5B8 (rose)
- Status dot: #E8A5B8
- Status text: #E8A5B8
- Background: #FDF2F5 (light rose tint)
- Bell icon: #E8A5B8
```

### Future
```
┌─────────────────────────────────────┐
│  ○ USG 4 - Growth Scan              │
│    30-32 weeks                      │
│    12 Aug - 26 Aug 2025             │
│    ─────────────────────            │
│    In 16 weeks                      │
└─────────────────────────────────────┘

Colors:
- Left border: #D1D5DB (grey)
- Status circle: #D1D5DB (empty)
- Status text: #9CA3AF
- Background: #FFFFFF
```

---

## Design Specs

### App Bar
- Title: "My Pregnancy USG"
- Back navigation to Patient Home
- Primary color: #E8A5B8

### Date Card with Saved Indicator
- Background: #FFFFFF
- Border: 2dp solid #7AB89B (green = saved)
- Checkmark badge: "✓ Saved"
- Badge color: #7AB89B

### Progress Card (Hero)
- Background: Gradient #E8A5B8 → #CC76A1
- Week display: 36sp, White, Bold
- Progress bar: White track, Gold fill
- EDD: 16sp, White
- Countdown: 14sp, rgba(255,255,255,0.8)

### Notification Settings Card
- Background: #E8F5E9 (light green)
- Icon: Bell, #7AB89B
- Text: 14sp, #3D4852
- Toggle: Material Switch (optional)

### USG Cards
- Same structure as Doctor screen
- Added: Status indicator (✓ ● ○)
- Added: Status text (Completed, Upcoming, In X weeks)
- Added: Notification badge for upcoming

---

## Notification Logic

### When Notifications Fire (for each USG start date)

```java
// For USG 3 starting on April 29, 2025
LocalDate usgStartDate = LocalDate.of(2025, 4, 29);

// Schedule 4 notifications:
scheduleNotification(usgStartDate.minusDays(3), "USG 3 (Anomaly Scan) in 3 days");
scheduleNotification(usgStartDate.minusDays(2), "USG 3 (Anomaly Scan) in 2 days");
scheduleNotification(usgStartDate.minusDays(1), "USG 3 (Anomaly Scan) tomorrow!");
scheduleNotification(usgStartDate, "USG 3 (Anomaly Scan) is due today");
```

### Notification Content
```
Title: Mahina - USG Reminder
Body: USG 3 (Anomaly Scan) is due tomorrow!
      Scheduled window: 29 Apr - 13 May

Action: Opens Patient USG Tracker screen
```

### Rescheduling
When patient changes LMP:
1. Cancel all existing USG notifications
2. Recalculate all USG dates
3. Schedule new notifications for future USGs
4. Don't schedule for past dates

---

## SharedPreferences

```java
// Save LMP
SharedPreferences prefs = getSharedPreferences("MahinaPrefs", MODE_PRIVATE);
prefs.edit()
    .putString("patient_lmp_date", lmpDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
    .apply();

// Schedule notifications
NotificationScheduler.scheduleUSGReminders(context, lmpDate);
```

---

## Auto-Status Calculation

```java
public USGStatus getUSGStatus(LocalDate usgStart, LocalDate usgEnd, LocalDate today) {
    if (today.isAfter(usgEnd)) {
        return USGStatus.COMPLETED;
    } else if (today.isAfter(usgStart.minusDays(14))) {
        // Within 2 weeks of start or in window
        return USGStatus.UPCOMING;
    } else {
        return USGStatus.FUTURE;
    }
}
```

---

## Interaction: Marking Complete

For simplicity in v1, we can auto-calculate status based on current date.

**Future enhancement**: Allow patient to tap and manually mark as complete.
