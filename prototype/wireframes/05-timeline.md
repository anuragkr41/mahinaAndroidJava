# Timeline View Wireframe

## Purpose
- Full pregnancy journey at a glance
- Visual progress tracking
- Easy navigation between stages
- Clear status of all events

---

## Main Layout

```
┌─────────────────────────────────────┐
│ ←  Pregnancy Timeline        🗓️    │
├─────────────────────────────────────┤
│                                     │
│  ━━━━━━━━━━━━━●━━━━━━━━━━━━━━━━━   │
│  Week 0      24              40     │
│                                     │
│  ┌─ Trimester 1 (Week 1-13) ───────┐│
│  │                          ✓      ││
│  │  ● USG 1 - Dating Scan          ││
│  │    6-8 weeks                    ││
│  │    28 Jan - 11 Feb ✓            ││
│  │    ─────────────────────────    ││
│  │  ● USG 2 - NT Scan              ││
│  │    11-13 weeks + 6 days         ││
│  │    4 Mar - 25 Mar ✓             ││
│  │    ─────────────────────────    ││
│  │  ● Visit 1                      ││
│  │    30 days - 13w+6d             ││
│  │    16 Jan - 25 Mar ✓            ││
│  │                                 ││
│  └─────────────────────────────────┘│
│                                     │
│  ┌─ Trimester 2 (Week 14-27) ──────┐│
│  │                          ●      ││
│  │  ◐ USG 3 - Anomaly Scan         ││
│  │    18-20 weeks                  ││
│  │    29 Apr - 13 May              ││
│  │    Window opens in 2 weeks      ││
│  │    ─────────────────────────    ││
│  │  ○ Visit 2 - Week 20            ││
│  │    6 May - 12 May               ││
│  │    ─────────────────────────    ││
│  │  ○ Visit 3 - Week 26            ││
│  │    17 Jun - 23 Jun              ││
│  │                                 ││
│  └─────────────────────────────────┘│
│                                     │
│  ┌─ Trimester 3 (Week 28-40) ──────┐│
│  │                          ○      ││
│  │  ○ USG 4 - Growth Scan          ││
│  │    30-32 weeks                  ││
│  │    12 Aug - 26 Aug              ││
│  │    ─────────────────────────    ││
│  │  ○ Visit 4 - Week 30            ││
│  │    ─────────────────────────    ││
│  │  ○ Visit 5 - Week 34            ││
│  │    ─────────────────────────    ││
│  │  ○ Visit 6 - Week 36            ││
│  │    ─────────────────────────    ││
│  │  ○ Visit 7 - Week 38            ││
│  │    ─────────────────────────    ││
│  │  ○ Visit 8 - Week 40            ││
│  │    ─────────────────────────    ││
│  │  🎉 Expected Delivery           ││
│  │     24 September 2025           ││
│  │                                 ││
│  └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
```

---

## Status Indicators

```
✓ Completed    - Solid checkmark, muted color
● Current      - Solid dot, accent color, pulse animation
◐ Upcoming     - Half-filled, attention color
○ Future       - Empty circle, muted
```

### Visual Legend
```
┌─────────────────────────────────────┐
│  ✓ Done   ● Now   ◐ Soon   ○ Later │
└─────────────────────────────────────┘
```

---

## Trimester Card States

### Completed Trimester
```
┌─ Trimester 1 (Week 1-13) ✓ ────────┐
│                                     │
│  All items completed                │
│  ● USG 1 ✓  ● USG 2 ✓  ● Visit 1 ✓ │
│  [Expand to see details]            │
│                                     │
└─────────────────────────────────────┘

- Collapsed by default
- Green accent (#7AB89B)
- Summary badges
```

### Current Trimester
```
┌─ Trimester 2 (Week 14-27) ● ───────┐
│                                     │
│  ◐ USG 3 - Anomaly Scan            │
│    18-20 weeks                      │
│    Window: 29 Apr - 13 May          │
│    ━━━━━━━━━━━━━━━━━━━            │
│    Opens in 2 weeks                 │
│                                     │
│  ─────────────────────────────────  │
│                                     │
│  ○ Visit 2 - Week 20               │
│    6 May - 12 May                   │
│                                     │
└─────────────────────────────────────┘

- Expanded by default
- Blue accent (#5B7DB1)
- Progress bars on upcoming items
```

### Future Trimester
```
┌─ Trimester 3 (Week 28-40) ○ ───────┐
│                                     │
│  Coming up after Week 28            │
│  4 visits + 1 USG + Delivery        │
│  [Expand to preview]                │
│                                     │
└─────────────────────────────────────┘

- Collapsed by default
- Grey accent (#8A9099)
- Preview summary
```

---

## Timeline Item Detail

### Expanded Event Card
```
┌─────────────────────────────────────┐
│  🔬 USG 3 - Anomaly Scan           │
│                                     │
│  Timing: 18-20 weeks               │
│  Window: 29 April - 13 May 2025     │
│                                     │
│  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━   │
│  Starts in 14 days                  │
│                                     │
│  What to expect:                    │
│  Detailed anatomy scan to check     │
│  baby's development. Usually takes  │
│  30-45 minutes.                     │
│                                     │
│  ┌─────────┐ ┌─────────────────┐   │
│  │ 🔔 Set  │ │ ✓ Mark Complete │   │
│  │ Reminder│ │                 │   │
│  └─────────┘ └─────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

---

## Progress Bar Visualization

### Overall Progress (Top)
```
━━━━━━━━━━━━━●━━━━━━━━━━━━━━━━━
0           24                40

- Fixed at top
- Shows current week position
- Trimester color segments
  - T1: #E8A5B8 (rose)
  - T2: #5B7DB1 (blue)
  - T3: #F5C77E (gold)
```

### Event Window Progress
```
For USG 3 (18-20 weeks), at week 16:

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
^           ^──window──^
now

Caption: "Window opens in 2 weeks"
```

```
At week 19:

━━━━━━━━━━━━━●━━━━━━━━━━━━━━━━
         ^──●now──^
         window

Caption: "Currently in window"
```

---

## Empty States

### No Events in Trimester
```
┌─────────────────────────────────────┐
│  No scheduled events                │
│  this trimester                     │
└─────────────────────────────────────┘
```

### LMP Not Set
```
┌─────────────────────────────────────┐
│                                     │
│  Timeline Unavailable               │
│                                     │
│  Please set your LMP date           │
│  to see your pregnancy timeline     │
│                                     │
│  [Set LMP Date]                     │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specifications

### Trimester Section Headers
- Background: Subtle gradient
- Corner radius: 16dp (top), 0dp (content)
- Status badge: Right aligned

### Event Items
- Vertical connector line: 2dp, #E0E0E0
- Status dot: 12dp diameter
- Spacing between items: 16dp

### Colors by Status
```
Completed:
  - Dot: #7AB89B
  - Text: #6B7280 (muted)
  - Background: #F9FAFB

Current:
  - Dot: #5B7DB1 (with pulse)
  - Text: #1F2937
  - Background: #EFF6FF

Upcoming:
  - Dot: #F5C77E
  - Text: #1F2937
  - Background: #FFFBEB

Future:
  - Dot: #D1D5DB
  - Text: #9CA3AF
  - Background: #FFFFFF
```

### Interactions
- Tap trimester header: Expand/collapse
- Tap event: Show detail modal
- Long press event: Quick actions (reminder, complete)
- Swipe event (mother only): Mark complete

---

## Accessibility

### Screen Reader
- Clear status announcements
- "USG 3, Anomaly Scan, upcoming, window opens in 2 weeks"

### Touch Targets
- Minimum 48dp for all interactive elements
- Clear focus indicators

### Color Contrast
- All text meets WCAG AA
- Status not conveyed by color alone (icons + text)
