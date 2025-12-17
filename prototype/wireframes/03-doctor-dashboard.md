# Doctor Dashboard Wireframe

## Design Goals
- Quick, efficient calculation
- Scannable results
- Professional appearance
- Single-screen workflow

---

## Main Layout

```
┌─────────────────────────────────────┐
│ ← Calculator              ⚙️        │
├─────────────────────────────────────┤
│                                     │
│  Last Menstrual Period (LMP)        │
│  ┌─────────────────────────────┐    │
│  │  📅  17 December 2024       │    │
│  │      [Tap to change]        │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │   Period of Gestation       │    │
│  │   ━━━━━━━━━━━━━━━━━━━━━    │    │
│  │        24 weeks             │    │
│  │         3 days              │    │
│  │                             │    │
│  │   EDD: 24 September 2025    │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│  USG Schedule                       │
│  ┌─────────────────────────────┐    │
│  │ ▼ USG 1 - Dating Scan       │    │
│  │   6-8 weeks                 │    │
│  │   28 Jan - 11 Feb 2025      │    │
│  ├─────────────────────────────┤    │
│  │ ▶ USG 2 - NT Scan           │    │
│  │   11w - 13w+6d              │    │
│  ├─────────────────────────────┤    │
│  │ ▶ USG 3 - Anomaly Scan      │    │
│  │   18-20 weeks               │    │
│  ├─────────────────────────────┤    │
│  │ ▶ USG 4 - Growth Scan       │    │
│  │   30-32 weeks               │    │
│  └─────────────────────────────┘    │
│                                     │
│  Doctor Visits                      │
│  ┌─────────────────────────────┐    │
│  │ Visit 1   │ Visit 2-3       │    │
│  │ 30d-13w+6d│ Week 20, 26     │    │
│  │ 16 Jan -  │ 6 May - 27 Jun  │    │
│  │ 25 Mar    │                 │    │
│  ├───────────┼─────────────────┤    │
│  │ Visit 4-5 │ Visit 6-8       │    │
│  │ Week 30,34│ Week 36,38,40   │    │
│  │ 25 Jul -  │ 5 Sep - 24 Sep  │    │
│  │ 22 Aug    │                 │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │        📤 Share             │    │
│  └─────────────────────────────┘    │
│                                     │
└─────────────────────────────────────┘
```

---

## Component Breakdown

### Header Bar
- Back navigation (if applicable)
- Title: "Calculator"
- Settings gear icon

### LMP Input Card
```
┌─────────────────────────────────────┐
│  📅  17 December 2024              │
│      Tap to change                  │
└─────────────────────────────────────┘

- Prominent calendar icon
- Large date display
- Subtle tap hint
- Opens bottom sheet date picker on tap
```

### POG/EDD Hero Card
```
┌─────────────────────────────────────┐
│                                     │
│   Period of Gestation               │
│   ━━━━━━━━━━━●━━━━━━━━             │
│        24 weeks                     │
│         3 days                      │
│                                     │
│   EDD: 24 September 2025            │
│                                     │
└─────────────────────────────────────┘

- Gradient background (#E8A5B8 → #F5C77E)
- Progress bar showing journey
- Large, prominent weeks display
- Subtle days display
- EDD clearly visible
```

### USG Schedule (Expandable)
```
┌─────────────────────────────────────┐
│ ▼ USG 1 - Dating Scan              │
│   Timing: 6-8 weeks                 │
│   Date Range: 28 Jan - 11 Feb 2025  │
│   Purpose: Confirm pregnancy,       │
│            establish dates          │
├─────────────────────────────────────┤
│ ▶ USG 2 - NT Scan                  │
│   11 weeks - 13 weeks + 6 days      │
└─────────────────────────────────────┘

- Accordion style
- One expanded by default
- Color-coded left border (pink)
- ▶ collapsed, ▼ expanded
```

### Visit Schedule (Compact Grid)
```
┌─────────────┬─────────────┐
│ Visit 1     │ Visit 2-3   │
│ 30d-13w+6d  │ Week 20,26  │
│ 16 Jan-25Mar│ 6 May-27Jun │
├─────────────┼─────────────┤
│ Visit 4-5   │ Visit 6-8   │
│ Week 30,34  │ Week 36-40  │
│ 25Jul-22Aug │ 5Sep-24Sep  │
└─────────────┴─────────────┘

- 2x2 grid for compactness
- Grouped by trimester
- Color-coded left border (blue)
```

### Share Button
- Full-width secondary button
- Generates shareable text/image
- Opens share sheet

---

## Interaction Flow

1. **Date Selection**
   - Tap LMP card
   - Bottom sheet calendar appears
   - Select date
   - Results update instantly

2. **USG Details**
   - Tap any USG row
   - Expands to show full details
   - Previous collapses (accordion)

3. **Share**
   - Tap share button
   - Generates formatted text
   - Opens system share sheet

---

## Empty State

```
┌─────────────────────────────────────┐
│                                     │
│  Select LMP to Calculate            │
│  ┌─────────────────────────────┐    │
│  │  📅  Select Date            │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │   [Empty state illustration]│    │
│  │   Select a date above       │    │
│  │   to see pregnancy          │    │
│  │   timeline                  │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specs

### Card Styling
- Corner radius: 16dp
- Elevation: 2dp
- Background: #FFFFFF
- Border: none (use left accent)

### Left Accent Borders
- USG cards: 4dp, #E8A5B8
- Visit cards: 4dp, #5B7DB1
- Hero card: gradient background instead

### Spacing
- Card margins: 16dp horizontal
- Card internal padding: 16dp
- Section gap: 24dp
- Item gap within section: 8dp
