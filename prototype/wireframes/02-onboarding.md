# Onboarding Flow Wireframe

## Purpose
- First-time user setup
- Role selection
- Initial LMP input (for mothers)

---

## Screen 1: Welcome

```
┌─────────────────────────────────────┐
│                                     │
│                                     │
│         ┌───────────────┐           │
│         │               │           │
│         │  [Illustration│           │
│         │   mother &    │           │
│         │   baby icon]  │           │
│         │               │           │
│         └───────────────┘           │
│                                     │
│       Welcome to Mahina             │
│                                     │
│   Track your pregnancy journey      │
│   with WHO-recommended schedules    │
│                                     │
│                                     │
│                                     │
│                                     │
│      ┌───────────────────────┐      │
│      │     Get Started       │      │
│      └───────────────────────┘      │
│                                     │
│              ○ ○ ○                  │
│                                     │
└─────────────────────────────────────┘
```

---

## Screen 2: Role Selection

```
┌─────────────────────────────────────┐
│                                     │
│         I am a...                   │
│                                     │
│                                     │
│    ┌─────────────────────────┐      │
│    │                         │      │
│    │     👩‍⚕️                 │      │
│    │                         │      │
│    │    Healthcare           │      │
│    │    Professional         │      │
│    │                         │      │
│    │  Quick calculations     │      │
│    │  for patient consults   │      │
│    │                         │      │
│    └─────────────────────────┘      │
│                                     │
│    ┌─────────────────────────┐      │
│    │                         │      │
│    │     🤰                  │      │
│    │                         │      │
│    │    Expecting            │      │
│    │    Mother               │      │
│    │                         │      │
│    │  Track your personal    │      │
│    │  pregnancy timeline     │      │
│    │                         │      │
│    └─────────────────────────┘      │
│                                     │
│              ○ ● ○                  │
│                                     │
└─────────────────────────────────────┘
```

---

## Screen 3a: Doctor Setup (Minimal)

```
┌─────────────────────────────────────┐
│                                     │
│         All Set!                    │
│                                     │
│         ┌───────────────┐           │
│         │      ✓        │           │
│         └───────────────┘           │
│                                     │
│   You're ready to start             │
│   calculating pregnancy dates       │
│                                     │
│                                     │
│                                     │
│                                     │
│                                     │
│      ┌───────────────────────┐      │
│      │   Open Calculator     │      │
│      └───────────────────────┘      │
│                                     │
│              ○ ○ ●                  │
│                                     │
└─────────────────────────────────────┘
```

---

## Screen 3b: Mother Setup (LMP Input)

```
┌─────────────────────────────────────┐
│                                     │
│    When was the first day of        │
│    your last menstrual period?      │
│                                     │
│                                     │
│    ┌─────────────────────────┐      │
│    │                         │      │
│    │    [Calendar Picker]    │      │
│    │                         │      │
│    │       December 2024     │      │
│    │    Su Mo Tu We Th Fr Sa │      │
│    │     1  2  3  4  5  6  7 │      │
│    │     8  9 10 11 12 13 14 │      │
│    │    15 16 17 18 19 20 21 │      │
│    │    22 23 24 25 26 27 28 │      │
│    │    29 30 31             │      │
│    │                         │      │
│    └─────────────────────────┘      │
│                                     │
│    ℹ️ This helps us calculate       │
│    your pregnancy timeline          │
│                                     │
│      ┌───────────────────────┐      │
│      │      Continue         │      │
│      └───────────────────────┘      │
│                                     │
│              ○ ○ ●                  │
│                                     │
└─────────────────────────────────────┘
```

---

## Interaction Notes

### Navigation
- Swipe left/right between screens
- Page indicators at bottom
- Skip option (top right) for doctors

### Persistence
- Save role to SharedPreferences
- Save LMP for mothers
- Don't show onboarding on subsequent launches

### Transition
- Smooth slide animation between screens
- Fade in for confirmation screen
