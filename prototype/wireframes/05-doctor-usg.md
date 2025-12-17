# Doctor USG Calculator Screen

## Purpose
Quick calculator for doctors to determine USG dates based on patient's LMP.
**No data saved. No notifications.**

---

## Layout - Initial State (No Date Selected)

```
┌─────────────────────────────────────┐
│ ←  USG Dates Calculator             │
├─────────────────────────────────────┤
│                                     │
│  Select Patient's LMP               │
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
│                                     │
│         ┌───────────────┐           │
│         │               │           │
│         │  [Calendar    │           │
│         │   illustration│           │
│         │   or empty    │           │
│         │   state icon] │           │
│         │               │           │
│         └───────────────┘           │
│                                     │
│    Select Last Menstrual Period     │
│    to calculate USG schedule        │
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

## Layout - After Date Selected

```
┌─────────────────────────────────────┐
│ ←  USG Dates Calculator             │
├─────────────────────────────────────┤
│                                     │
│  Patient's LMP                      │
│  ┌─────────────────────────────┐    │
│  │  📅  17 December 2024       │    │
│  │      Tap to change          │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │   POG: 24 weeks, 3 days     │    │
│  │   EDD: 24 September 2025    │    │
│  │                             │    │
│  │   ━━━━━━━━━━●━━━━━━━━━━    │    │
│  │   0        24          40   │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│  USG Schedule                       │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  USG 1 - Dating Scan        │    │
│  │  6-8 weeks                  │    │
│  │  📅 28 Jan - 11 Feb 2025    │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  USG 2 - NT Scan            │    │
│  │  11 weeks - 13 weeks + 6d   │    │
│  │  📅 4 Mar - 25 Mar 2025     │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  USG 3 - Anomaly Scan       │    │
│  │  18-20 weeks                │    │
│  │  📅 29 Apr - 13 May 2025    │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  USG 4 - Growth Scan        │    │
│  │  30-32 weeks                │    │
│  │  📅 12 Aug - 26 Aug 2025    │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │     📤  Share Results       │    │
│  └─────────────────────────────┘    │
│                                     │
│  ⚠️ Data not saved (calculator)    │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specs

### App Bar
- Height: 56dp
- Back arrow: 24dp, #3D4852
- Title: 20sp, Lexend Medium, #3D4852
- Background: #FDF8F5

### Date Picker Card
- Background: #FFFFFF
- Corner radius: 12dp
- Elevation: 1dp
- Padding: 16dp
- Border: 1dp solid #E5E7EB (normal), #5B7DB1 (selected)

### Date Display
- Icon: 24dp calendar, #5B7DB1
- Date: 18sp, Lexend Medium, #3D4852
- Hint: 14sp, Lexend Regular, #9CA3AF

### POG/EDD Card
- Background: Linear gradient #5B7DB1 → #3A5A8C
- Corner radius: 16dp
- Padding: 20dp
- Text color: White
- POG: 24sp, Lexend Bold
- EDD: 16sp, Lexend Regular

### Progress Bar
- Height: 8dp
- Track: rgba(255,255,255,0.3)
- Progress: White
- Corner radius: 4dp

### USG Cards
- Background: #FFFFFF
- Corner radius: 12dp
- Elevation: 1dp
- Left border: 4dp, #E8A5B8 (rose)
- Padding: 16dp

### USG Card Content
- Title: 16sp, Lexend Medium, #3D4852
- Timing: 14sp, Lexend Regular, #6B7280
- Date: 14sp, Lexend Medium, #5B7DB1
- Icon: 16dp calendar, #5B7DB1

### Share Button
- Style: Outlined
- Height: 48dp
- Corner radius: 24dp
- Border: 1dp #5B7DB1
- Text: 14sp, Lexend Medium, #5B7DB1
- Icon: 20dp share

### Warning Footer
- Icon: 16dp warning, #F5A962
- Text: 12sp, #6B7280
- Margin top: 16dp

---

## Interactions

### Date Picker
1. Tap date card
2. Show Material DatePicker dialog
3. On date selected → Calculate all dates
4. Update UI immediately

### Share Button
1. Generate shareable text:
```
Pregnancy USG Schedule
LMP: 17 December 2024
POG: 24 weeks, 3 days
EDD: 24 September 2025

USG 1 - Dating Scan (6-8 weeks)
28 Jan - 11 Feb 2025

USG 2 - NT Scan (11-13w+6d)
4 Mar - 25 Mar 2025

USG 3 - Anomaly Scan (18-20 weeks)
29 Apr - 13 May 2025

USG 4 - Growth Scan (30-32 weeks)
12 Aug - 26 Aug 2025

Generated by Mahina App
```
2. Open system share sheet

### Back Button
- Return to Doctor Home
- **Do NOT save any data**

---

## USG Data Reference

| USG | Name | Timing | Purpose |
|-----|------|--------|---------|
| 1 | Dating Scan | 6-8 weeks | Confirm pregnancy, dating |
| 2 | NT Scan | 11-13 weeks + 6 days | Nuchal translucency |
| 3 | Anomaly Scan | 18-20 weeks | Detailed anatomy |
| 4 | Growth Scan | 30-32 weeks | Growth assessment |

---

## Date Calculation

```java
// From LMP date
LocalDate lmp = selectedDate;

// USG 1: LMP + 6 weeks to LMP + 8 weeks
LocalDate usg1Start = lmp.plusWeeks(6);
LocalDate usg1End = lmp.plusWeeks(8);

// USG 2: LMP + 11 weeks to LMP + 13 weeks + 6 days
LocalDate usg2Start = lmp.plusWeeks(11);
LocalDate usg2End = lmp.plusWeeks(13).plusDays(6);

// USG 3: LMP + 18 weeks to LMP + 20 weeks
LocalDate usg3Start = lmp.plusWeeks(18);
LocalDate usg3End = lmp.plusWeeks(20);

// USG 4: LMP + 30 weeks to LMP + 32 weeks
LocalDate usg4Start = lmp.plusWeeks(30);
LocalDate usg4End = lmp.plusWeeks(32);

// EDD: LMP + 40 weeks (or LMP + 9 months + 7 days)
LocalDate edd = lmp.plusWeeks(40);

// POG: Days between LMP and today
long totalDays = ChronoUnit.DAYS.between(lmp, LocalDate.now());
int weeks = (int) totalDays / 7;
int days = (int) totalDays % 7;
```
