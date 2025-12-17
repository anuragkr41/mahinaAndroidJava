# Mother Dashboard Wireframe

## Design Philosophy
- Emotional, personal journey
- Celebrate milestones
- Reduce anxiety with clear timeline
- Highlight what's important NOW

---

## Main Layout

```
┌─────────────────────────────────────┐
│  Mahina                    👤 ⚙️    │
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │         Week 24             │    │
│  │           🌙                │    │
│  │                             │    │
│  │   Your baby is about        │    │
│  │   the size of an            │    │
│  │   ear of corn               │    │
│  │                             │    │
│  │  ━━━━━━━━━━━━━●━━━━━━━━━   │    │
│  │  0          24          40  │    │
│  │                             │    │
│  │  EDD: 24 September 2025     │    │
│  │       98 days to go         │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│  Coming Up                          │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  🔬  USG 3 - Anomaly Scan   │    │
│  │      18-20 weeks            │    │
│  │      ━━━━━━━━━━━━━━━━━━━   │    │
│  │      Due in 2 weeks         │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  👩‍⚕️  Doctor Visit 4        │    │
│  │      Week 30                │    │
│  │      25 Jul - 1 Aug         │    │
│  └─────────────────────────────┘    │
│                                     │
│  Completed ✓                        │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ✓ USG 1 - Dating Scan      │    │
│  │  ✓ USG 2 - NT Scan          │    │
│  │  ✓ Visit 1, 2, 3            │    │
│  │      [View all →]           │    │
│  └─────────────────────────────┘    │
│                                     │
├─────────────────────────────────────┤
│  🏠      📅       🔔      👤       │
│  Home  Timeline  Alerts  Profile    │
└─────────────────────────────────────┘
```

---

## Component Details

### Hero Card - Current Week
```
┌─────────────────────────────────────┐
│                                     │
│         Week 24                     │
│           🌙                        │
│                                     │
│   Your baby is about                │
│   the size of an ear of corn        │
│                                     │
│  ━━━━━━━━━━━━━●━━━━━━━━━           │
│  0          24          40          │
│                                     │
│  EDD: 24 September 2025             │
│       98 days to go                 │
│                                     │
└─────────────────────────────────────┘

Design:
- Gradient background: #E8A5B8 → #5B7DB1
- Large week number: 64sp, white, bold
- Moon icon: custom illustration or emoji
- Baby size comparison: fun, relatable
- Progress bar: visual pregnancy journey
- Countdown: creates anticipation
```

### Baby Size by Week (Data)
```
Week 6:  Sweet pea
Week 8:  Raspberry
Week 10: Prune
Week 12: Lime
Week 14: Lemon
Week 16: Avocado
Week 18: Bell pepper
Week 20: Banana
Week 22: Papaya
Week 24: Ear of corn
Week 26: Lettuce head
Week 28: Eggplant
Week 30: Cabbage
Week 32: Squash
Week 34: Cantaloupe
Week 36: Honeydew melon
Week 38: Pumpkin
Week 40: Watermelon
```

### Coming Up Section
```
┌─────────────────────────────────────┐
│  🔬  USG 3 - Anomaly Scan          │
│      18-20 weeks                    │
│      ━━━━━━━━━━━━━━━━━━━━━━━━━━   │
│      Due in 2 weeks                 │
└─────────────────────────────────────┘

Design:
- Icon on left (emoji or custom)
- Title bold, primary color
- Timing subtitle
- Mini progress bar (within window)
- Urgency indicator (changes color)
  - Green: >2 weeks away
  - Orange: <2 weeks
  - Red: Overdue
```

### Completed Section
```
┌─────────────────────────────────────┐
│  ✓ USG 1 - Dating Scan             │
│  ✓ USG 2 - NT Scan                 │
│  ✓ Visit 1, 2, 3                   │
│      [View all →]                   │
└─────────────────────────────────────┘

Design:
- Muted colors (#7AB89B checkmark)
- Collapsed by default
- Tap to expand full history
- Link to timeline view
```

### Bottom Navigation
```
┌─────────────────────────────────────┐
│  🏠      📅       🔔      👤       │
│  Home  Timeline  Alerts  Profile    │
└─────────────────────────────────────┘

Icons:
- Home: Current dashboard
- Timeline: Full pregnancy view
- Alerts: Notification settings
- Profile: Edit LMP, settings
```

---

## Alternative Layouts

### Trimester View (Tab Alternative)
```
┌─────────────────────────────────────┐
│  [Tri 1] [Tri 2●] [Tri 3]          │
├─────────────────────────────────────┤
│                                     │
│  Trimester 2 (Week 14-27)          │
│  You are in week 24                 │
│                                     │
│  ┌─────────────────────────────┐    │
│  │ ✓ USG 3 - Anomaly Scan      │    │
│  └─────────────────────────────┘    │
│  ┌─────────────────────────────┐    │
│  │ ○ Visit 3 - Week 26         │    │
│  └─────────────────────────────┘    │
│                                     │
└─────────────────────────────────────┘
```

---

## Empty State (No LMP Set)

```
┌─────────────────────────────────────┐
│                                     │
│         ┌───────────────┐           │
│         │      🌙       │           │
│         └───────────────┘           │
│                                     │
│    Let's Start Your Journey         │
│                                     │
│    Tell us when your last           │
│    period started so we can         │
│    calculate your timeline          │
│                                     │
│      ┌───────────────────────┐      │
│      │    Set My LMP Date    │      │
│      └───────────────────────┘      │
│                                     │
└─────────────────────────────────────┘
```

---

## Notifications Integration

### Upcoming Reminder Card
```
┌─────────────────────────────────────┐
│  🔔 Reminder                        │
│                                     │
│  USG 3 - Anomaly Scan is due        │
│  in 3 days. Have you scheduled      │
│  your appointment?                  │
│                                     │
│  [Dismiss]    [Set Reminder]        │
└─────────────────────────────────────┘
```

---

## Design Specs

### Hero Card
- Height: 240dp
- Corner radius: 24dp
- Gradient: 135deg, #E8A5B8 → #5B7DB1
- Text color: White
- Week number: 64sp Lexend Bold

### Event Cards
- Height: auto (content)
- Corner radius: 16dp
- Background: White
- Left border: 4dp colored
- Shadow: 2dp elevation

### Colors by Event Type
- USG events: #E8A5B8 (rose)
- Doctor visits: #5B7DB1 (blue)
- Completed: #7AB89B (sage)

### Progress Indicators
- Track: #E0E0E0
- Fill: #F5C77E (gold)
- Height: 8dp
- Corner radius: 4dp

### Bottom Nav
- Height: 56dp
- Background: White
- Active color: #5B7DB1
- Inactive: #8A9099
- Elevation: 8dp
