# Role Selection Screen

## When Shown
- **First launch only** (no role saved in SharedPreferences)
- After user explicitly resets from Settings

---

## Layout

```
┌─────────────────────────────────────┐
│                                     │
│                                     │
│              🌙                     │
│            MAHINA                   │
│                                     │
│                                     │
│       Who is using this app?        │
│                                     │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │         👨‍⚕️                 │    │
│  │                             │    │
│  │    I am a Doctor /          │    │
│  │    Healthcare Professional  │    │
│  │                             │    │
│  │    Quick calculations for   │    │
│  │    patient consultations    │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │         🤰                  │    │
│  │                             │    │
│  │    I am an Expecting        │    │
│  │    Mother / Patient         │    │
│  │                             │    │
│  │    Track my pregnancy       │    │
│  │    with reminders           │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│   ℹ️ You can change this later     │
│      in Settings                    │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specs

### Header
- Logo: 48dp moon icon
- App name: 28sp, Lexend Bold, #5B7DB1
- Question: 20sp, Lexend Regular, #3D4852
- Vertical spacing: 24dp between elements

### Cards
- Width: match_parent with 24dp horizontal margin
- Height: wrap_content (approx 140dp)
- Corner radius: 20dp
- Elevation: 4dp
- Background: White
- Spacing between cards: 16dp

### Card Content
- Icon: 48dp emoji or vector
- Title: 18sp, Lexend Medium, #3D4852
- Description: 14sp, Lexend Regular, #6B7280
- Padding: 24dp all sides
- Alignment: Center

### Card States
```
Normal:
- Background: #FFFFFF
- Border: none

Pressed/Selected:
- Background: #F5F0ED
- Border: 2dp solid #5B7DB1 (Doctor) or #E8A5B8 (Patient)
```

### Footer Note
- Size: 12sp
- Color: #9CA3AF
- Icon: Info icon 16dp
- Alignment: Center

---

## Interaction

1. User taps on a card
2. Card shows selected state (border highlight)
3. Brief haptic feedback
4. Save role to SharedPreferences
5. Navigate to respective Home screen
6. Animation: Fade out + slide to Home

---

## Accessibility

- Cards are focusable
- Content description: "Select Doctor mode" / "Select Patient mode"
- Minimum touch target: 48dp (cards are larger)
- High contrast text on cards

---

## Code Logic

```java
// On card click
void selectRole(String role) {
    SharedPreferences prefs = getSharedPreferences("MahinaPrefs", MODE_PRIVATE);
    prefs.edit().putString("user_role", role).apply();

    Intent intent;
    if (role.equals("doctor")) {
        intent = new Intent(this, DoctorHomeActivity.class);
    } else {
        intent = new Intent(this, PatientHomeActivity.class);
    }
    startActivity(intent);
    finish(); // Don't allow back to role selection
}
```

---

## SharedPreferences

```java
Key: "user_role"
Values: "doctor" | "patient"
Default: null (triggers this screen)
```
