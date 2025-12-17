# Mahina App - UX Redesign Brief

## Current State Analysis

### What Works Well
- Clear medical purpose (pregnancy tracking)
- WHO-compliant calculations
- Simple role-based entry (Doctor/Patient)
- Functional date calculations

### UX Pain Points Identified

1. **Information Overload**
   - 12 cards displayed at once (4 USG + 8 visits)
   - No visual hierarchy or grouping
   - All information shown regardless of relevance to current stage

2. **Navigation Friction**
   - 4 screens to reach main calculator (Splash → Main → Options → Doctor)
   - Redundant "Doctor Options" screen with single option
   - No clear way to switch between Doctor/Patient views

3. **Lack of Context**
   - No timeline visualization
   - User can't see "where they are" in pregnancy journey
   - No indication of upcoming vs past appointments

4. **Visual Design**
   - Monotonous card layout
   - Weak visual hierarchy
   - No emotional connection to maternal journey

5. **Missing Features**
   - No onboarding for first-time users
   - No way to track multiple patients (for doctors)
   - No appointment reminders visualization

---

## Redesign Goals

### Primary Goals
1. **Reduce cognitive load** - Show relevant information at the right time
2. **Create emotional connection** - Celebrate the pregnancy journey
3. **Streamline navigation** - Fewer taps to key actions
4. **Improve scannability** - Better visual hierarchy

### Secondary Goals
- Support multiple patients (Doctor mode)
- Better notification integration
- Offline-first reliability

---

## Proposed Information Architecture

```
App Launch
    │
    ├── [First Launch] Onboarding Flow
    │   ├── Welcome Screen
    │   ├── Role Selection (Doctor/Mother)
    │   └── Initial Setup
    │
    └── [Returning User] Dashboard
        │
        ├── [DOCTOR MODE]
        │   ├── Quick Calculator (LMP → Results)
        │   ├── Patient List (Future)
        │   └── Settings
        │
        └── [MOTHER MODE]
            ├── My Pregnancy Dashboard
            │   ├── Current Week Card (Hero)
            │   ├── Timeline View
            │   ├── Upcoming Events
            │   └── Quick Actions
            ├── Full Schedule View
            ├── Notifications Settings
            └── Profile/Settings
```

---

## Key Screen Concepts

### 1. Splash Screen (Improved)
- Animated logo with gentle fade
- Warm, reassuring tagline
- 1-second transition

### 2. Onboarding (New)
- 3 screens max
- Role selection with clear icons
- LMP date picker integrated

### 3. Doctor Dashboard (Redesigned)
```
┌─────────────────────────────┐
│  Quick Calculator           │
│  ┌─────────────────────────┐│
│  │ LMP: [Date Picker]     ││
│  └─────────────────────────┘│
│                             │
│  ┌─────────────────────────┐│
│  │ POG: 24 weeks, 3 days  ││
│  │ EDD: 15-Mar-2025       ││
│  └─────────────────────────┘│
│                             │
│  ┌─ USG Schedule ──────────┐│
│  │ USG 1 ○ USG 2 ○ USG 3 ○ ││
│  │ [Expandable Details]    ││
│  └─────────────────────────┘│
│                             │
│  ┌─ Visit Schedule ────────┐│
│  │ [Compact Timeline View] ││
│  └─────────────────────────┘│
└─────────────────────────────┘
```

### 4. Mother Dashboard (New Concept)
```
┌─────────────────────────────┐
│  Week 24                    │
│  ┌─────────────────────────┐│
│  │   🌙                    ││
│  │  Your baby is the size  ││
│  │  of an ear of corn!     ││
│  │                         ││
│  │  ━━━━━━━━━━●━━━━━━━     ││
│  │  0        24       40   ││
│  └─────────────────────────┘│
│                             │
│  Coming Up                  │
│  ┌─────────────────────────┐│
│  │ 📅 USG 3 - Anomaly Scan ││
│  │    In 2 weeks           ││
│  └─────────────────────────┘│
│  ┌─────────────────────────┐│
│  │ 👩‍⚕️ Visit 4            ││
│  │    Week 30              ││
│  └─────────────────────────┘│
│                             │
│  [View Full Timeline]       │
└─────────────────────────────┘
```

### 5. Timeline View (New)
```
┌─────────────────────────────┐
│  Pregnancy Timeline         │
│                             │
│  ┌─ Trimester 1 ───────────┐│
│  │ ✓ USG 1 - Dating Scan   ││
│  │ ✓ USG 2 - NT Scan       ││
│  │ ✓ Visit 1               ││
│  └─────────────────────────┘│
│                             │
│  ┌─ Trimester 2 ───────────┐│
│  │ ● USG 3 - Anomaly Scan  ││
│  │   18-20 weeks           ││
│  │ ○ Visit 2               ││
│  │ ○ Visit 3               ││
│  └─────────────────────────┘│
│                             │
│  ┌─ Trimester 3 ───────────┐│
│  │ ○ USG 4 - Growth Scan   ││
│  │ ○ Visits 4-8            ││
│  │ ○ Expected Delivery     ││
│  └─────────────────────────┘│
└─────────────────────────────┘
```

---

## Design System Recommendations

### Color Palette (Evolved)

| Role | Current | Proposed | Rationale |
|------|---------|----------|-----------|
| Primary | #CC76A1 (Pink) | #E8A5B8 (Soft Rose) | Warmer, more nurturing |
| Secondary | #120A8F (Blue) | #5B7DB1 (Soft Blue) | Less harsh, more calming |
| Accent | - | #F5C77E (Warm Gold) | Celebration, milestones |
| Background | White | #FDF8F5 (Warm White) | Softer, warmer feel |
| Success | - | #7AB89B (Sage Green) | Completed items |
| Text Primary | #4A5660 (Grey) | #3D4852 (Charcoal) | Better contrast |

### Typography

| Element | Current | Proposed |
|---------|---------|----------|
| Hero/Week | 32sp Lexend | 48sp Lexend Bold |
| Section Headers | 25sp | 20sp Lexend Medium |
| Card Titles | 20sp | 16sp Lexend Medium |
| Body Text | 14sp | 14sp Lexend Regular |
| Captions | 12sp | 12sp Lexend Light |

### Spacing System
- Base unit: 8dp
- Card padding: 16dp (2 units)
- Section margins: 24dp (3 units)
- Screen margins: 16dp

### Card Styles

**Hero Card** (Current status)
- Large, prominent
- Gradient background
- Rounded corners: 24dp

**Info Card** (USG/Visits)
- Clean white background
- Subtle shadow
- Left accent border (color-coded)
- Rounded corners: 16dp

**Timeline Item**
- Minimal, list-style
- Status indicator (✓ ● ○)
- Compact height

---

## Interaction Patterns

### Micro-interactions
- Gentle pulse on current week
- Checkmark animation on completion
- Smooth expand/collapse for details

### Gestures
- Swipe between trimesters (timeline)
- Pull to refresh (future: sync)
- Long press for quick actions

### Feedback
- Haptic on date selection
- Subtle sound on milestone (optional)

---

## Implementation Priority

### Phase 1: Quick Wins
1. Simplify navigation (remove DoctorOptions)
2. Improve card hierarchy
3. Add current week highlight
4. Update color palette

### Phase 2: New Features
1. Timeline view
2. Onboarding flow
3. Mother-specific dashboard

### Phase 3: Polish
1. Animations
2. Illustrations
3. Personalization

---

## Files in This Prototype

```
prototype/
├── UX-REDESIGN-BRIEF.md (this file)
├── design-system/
│   ├── colors.xml
│   ├── typography.xml
│   └── spacing.xml
├── wireframes/
│   ├── 01-splash.md
│   ├── 02-onboarding.md
│   ├── 03-doctor-dashboard.md
│   ├── 04-mother-dashboard.md
│   └── 05-timeline.md
└── layouts/
    ├── activity_splash_v2.xml
    ├── activity_doctor_v2.xml
    ├── activity_mother_dashboard.xml
    └── component_timeline_item.xml
```
