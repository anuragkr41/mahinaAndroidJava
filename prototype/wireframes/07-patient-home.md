# Patient Home Screen

## Purpose
Main menu for expecting mothers with 2 tracking options (both with notifications).

---

## Layout

```
┌─────────────────────────────────────┐
│                                     │
│  ┌───┐                        ⚙️   │
│  │🌙│  Mahina                       │
│  └───┘  Your Pregnancy Companion    │
│                                     │
├─────────────────────────────────────┤
│                                     │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │    ┌─────┐                  │    │
│  │    │ 🤰  │                  │    │
│  │    └─────┘                  │    │
│  │                             │    │
│  │    My Pregnancy             │    │
│  │    USG Schedule             │    │
│  │                             │    │
│  │    Track your ultrasound    │    │
│  │    appointments with        │    │
│  │    reminders                │    │
│  │                             │    │
│  │    🔔 Notifications ON      │    │
│  │                        →    │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │    ┌─────┐                  │    │
│  │    │ 👶  │                  │    │
│  │    └─────┘                  │    │
│  │                             │    │
│  │    Baby Vaccination         │    │
│  │    Schedule                 │    │
│  │                             │    │
│  │    Track your baby's        │    │
│  │    immunization dates       │    │
│  │    with reminders           │    │
│  │                             │    │
│  │    🔔 Notifications ON      │    │
│  │                        →    │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specs

### Header Bar
- Height: 64dp
- Logo: 32dp moon icon
- App name: 24sp, Lexend Bold, #E8A5B8 (rose - patient color)
- Subtitle: 14sp, Lexend Regular, #6B7280
- Subtitle text: "Your Pregnancy Companion"
- Settings icon: 24dp, #6B7280, right aligned
- Background: #FDF8F5 (warm white)

### Option Cards
- Width: match_parent - 32dp (16dp margins)
- Height: ~180dp (wrap_content)
- Corner radius: 16dp
- Elevation: 2dp
- Background: #FFFFFF
- Left accent: 4dp, #E8A5B8 (rose)
- Padding: 20dp

### Card Icon Container
- Size: 48dp x 48dp
- Background: #FDF2F5 (light rose)
- Corner radius: 12dp
- Icon: 28dp emoji

### Card Text
- Title: 20sp, Lexend Medium, #3D4852
- Description: 14sp, Lexend Regular, #6B7280
- Arrow: 20dp chevron-right, #9CA3AF

### Notification Badge
- Background: #E8F5E9 (light green)
- Corner radius: 4dp
- Padding: 4dp 8dp
- Icon: 14dp bell, #7AB89B
- Text: 12sp, Lexend Medium, #7AB89B
- Text: "Notifications ON"

### Spacing
- Cards gap: 16dp
- First card margin top: 24dp

---

## Color Theme (Patient)

```
Primary: #E8A5B8 (Warm Rose)
Primary Light: #FDF2F5
Accent border: #E8A5B8
Icon backgrounds: #FDF2F5
Notification badge: #E8F5E9 (green for active)
```

---

## Interactions

### Card Tap
1. Ripple effect on card
2. Navigate to respective tracker screen
3. Transition: Slide left

### Settings Tap
1. Navigate to Settings screen
2. Transition: Slide up or fade

---

## Navigation

```
Patient Home
    │
    ├── [Pregnancy Card] → Patient USG Tracker
    │
    ├── [Vaccination Card] → Patient Vaccination Tracker
    │
    └── [Settings ⚙️] → Settings Screen
```

---

## State Indicators on Cards

The cards can show saved data status:

### No Data Saved Yet
```
┌─────────────────────────────┐
│  🤰 My Pregnancy            │
│     USG Schedule            │
│                             │
│  ⚠️ Set your LMP to start   │
│     tracking                │
│                             │
│  🔔 Notifications ready     │
└─────────────────────────────┘
```

### Data Saved
```
┌─────────────────────────────┐
│  🤰 My Pregnancy            │
│     USG Schedule            │
│                             │
│  Week 24, Day 3             │
│  Next: USG 3 in 14 days     │
│                             │
│  🔔 Notifications ON        │
└─────────────────────────────┘
```

This gives the patient a quick glance at their status without opening the detail screen.

---

## Design Difference from Doctor Home

| Element | Doctor | Patient |
|---------|--------|---------|
| Primary color | Blue #5B7DB1 | Rose #E8A5B8 |
| Subtitle | "For Healthcare Professionals" | "Your Pregnancy Companion" |
| Card accent | Blue | Rose |
| Icon background | Light blue | Light rose |
| Notification badge | Not shown | Always shown |
| Status preview | Not shown | Shows week/next event |
| Footer info | "Data not saved" | Not shown |
