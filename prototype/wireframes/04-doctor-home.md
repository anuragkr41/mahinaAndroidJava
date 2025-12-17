# Doctor Home Screen

## Purpose
Main menu for healthcare professionals with 2 calculator options.

---

## Layout

```
┌─────────────────────────────────────┐
│                                     │
│  ┌───┐                        ⚙️   │
│  │🌙│  Mahina                       │
│  └───┘  For Healthcare Professionals│
│                                     │
├─────────────────────────────────────┤
│                                     │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │    ┌─────┐                  │    │
│  │    │ 📅  │                  │    │
│  │    └─────┘                  │    │
│  │                             │    │
│  │    View USG Dates           │    │
│  │                             │    │
│  │    Calculate pregnancy      │    │
│  │    ultrasound schedule      │    │
│  │    based on LMP             │    │
│  │                             │    │
│  │                        →    │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │    ┌─────┐                  │    │
│  │    │ 💉  │                  │    │
│  │    └─────┘                  │    │
│  │                             │    │
│  │    View Immunization        │    │
│  │    Schedule                 │    │
│  │                             │    │
│  │    Calculate baby           │    │
│  │    vaccination dates        │    │
│  │    based on DOB             │    │
│  │                             │    │
│  │                        →    │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  ℹ️ Calculator mode          │    │
│  │  Data is not saved          │    │
│  └─────────────────────────────┘    │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specs

### Header Bar
- Height: 64dp
- Logo: 32dp moon icon
- App name: 24sp, Lexend Bold, #5B7DB1
- Subtitle: 14sp, Lexend Regular, #6B7280
- Settings icon: 24dp, #6B7280, right aligned
- Background: #FDF8F5 (warm white)

### Option Cards
- Width: match_parent - 32dp (16dp margins)
- Height: ~160dp (wrap_content)
- Corner radius: 16dp
- Elevation: 2dp
- Background: #FFFFFF
- Left accent: 4dp, #5B7DB1
- Padding: 20dp

### Card Icon Container
- Size: 48dp x 48dp
- Background: #EFF6FF (light blue)
- Corner radius: 12dp
- Icon: 28dp emoji or vector

### Card Text
- Title: 20sp, Lexend Medium, #3D4852
- Description: 14sp, Lexend Regular, #6B7280
- Arrow: 20dp chevron-right, #9CA3AF

### Info Banner
- Background: #F5F0ED
- Corner radius: 8dp
- Padding: 12dp
- Icon: 16dp info, #9CA3AF
- Text: 12sp, #6B7280
- Margin: 16dp horizontal, 24dp top

### Spacing
- Cards gap: 16dp
- First card margin top: 24dp

---

## Color Theme (Doctor)

```
Primary: #5B7DB1 (Professional Blue)
Primary Light: #EFF6FF
Accent border: #5B7DB1
Icon backgrounds: #EFF6FF
```

---

## Interactions

### Card Tap
1. Ripple effect on card
2. Navigate to respective calculator screen
3. Transition: Slide left

### Settings Tap
1. Navigate to Settings screen
2. Transition: Slide up or fade

---

## Navigation

```
Doctor Home
    │
    ├── [USG Card] → Doctor USG Calculator
    │
    ├── [Immunization Card] → Doctor Immunization Calculator
    │
    └── [Settings ⚙️] → Settings Screen
```

---

## XML Structure

```xml
<LinearLayout orientation="vertical">

    <!-- Header -->
    <RelativeLayout height="64dp">
        <ImageView logo />
        <LinearLayout>
            <TextView appName />
            <TextView subtitle />
        </LinearLayout>
        <ImageButton settings />
    </RelativeLayout>

    <!-- Content -->
    <ScrollView>
        <LinearLayout orientation="vertical">

            <!-- USG Card -->
            <MaterialCardView>
                ...
            </MaterialCardView>

            <!-- Immunization Card -->
            <MaterialCardView>
                ...
            </MaterialCardView>

            <!-- Info Banner -->
            <LinearLayout>
                <ImageView infoIcon />
                <TextView infoText />
            </LinearLayout>

        </LinearLayout>
    </ScrollView>

</LinearLayout>
```
