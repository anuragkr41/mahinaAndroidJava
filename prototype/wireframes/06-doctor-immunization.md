# Doctor Immunization Calculator Screen

## Purpose
Quick calculator for doctors to determine baby vaccination schedule based on DOB.
**No data saved. No notifications.**

---

## Layout - Initial State

```
┌─────────────────────────────────────┐
│ ←  Immunization Schedule            │
├─────────────────────────────────────┤
│                                     │
│  Select Baby's Date of Birth        │
│                                     │
│  ┌─────────────────────────────┐    │
│  │                             │    │
│  │    📅  Select Date          │    │
│  │                             │    │
│  │    Tap to choose DOB        │    │
│  │                             │    │
│  └─────────────────────────────┘    │
│                                     │
│                                     │
│         ┌───────────────┐           │
│         │               │           │
│         │  [Baby/syringe│           │
│         │   illustration│           │
│         │   or empty    │           │
│         │   state icon] │           │
│         │               │           │
│         └───────────────┘           │
│                                     │
│    Select date of birth to          │
│    view vaccination schedule        │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

---

## Layout - After Date Selected

```
┌─────────────────────────────────────┐
│ ←  Immunization Schedule            │
├─────────────────────────────────────┤
│                                     │
│  Baby's Date of Birth               │
│  ┌─────────────────────────────┐    │
│  │  📅  15 December 2024       │    │
│  │      Tap to change          │    │
│  └─────────────────────────────┘    │
│                                     │
│  Baby's Age: 1 month, 2 days        │
│                                     │
│  Vaccination Schedule               │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  At Birth                   │    │
│  │  ─────────────────────────  │    │
│  │  BCG                        │    │
│  │  OPV-0                      │    │
│  │  Hepatitis B - Birth dose   │    │
│  │                             │    │
│  │  📅 15 Dec 2024             │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  6 Weeks                    │    │
│  │  ─────────────────────────  │    │
│  │  DTwP/DTaP - 1              │    │
│  │  IPV - 1                    │    │
│  │  Hepatitis B - 2            │    │
│  │  Hib - 1                    │    │
│  │  Rotavirus - 1              │    │
│  │  PCV - 1                    │    │
│  │                             │    │
│  │  📅 26 Jan 2025             │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  10 Weeks                   │    │
│  │  ─────────────────────────  │    │
│  │  DTwP/DTaP - 2              │    │
│  │  IPV - 2                    │    │
│  │  Hib - 2                    │    │
│  │  Rotavirus - 2              │    │
│  │  PCV - 2                    │    │
│  │                             │    │
│  │  📅 23 Feb 2025             │    │
│  └─────────────────────────────┘    │
│                                     │
│  ┌─────────────────────────────┐    │
│  │  14 Weeks                   │    │
│  │  ... (more vaccines)        │    │
│  └─────────────────────────────┘    │
│                                     │
│  ... (scroll for more)              │
│                                     │
│  ┌─────────────────────────────┐    │
│  │     📤  Share Schedule      │    │
│  └─────────────────────────────┘    │
│                                     │
│  ⚠️ Data not saved (calculator)    │
│                                     │
└─────────────────────────────────────┘
```

---

## Design Specs

### App Bar
- Same as Doctor USG screen
- Title: "Immunization Schedule"

### Date Picker Card
- Same design as Doctor USG screen
- Label: "Baby's Date of Birth"

### Age Display
- Text: 16sp, Lexend Regular, #6B7280
- Format: "Baby's Age: X months, Y days"

### Vaccination Cards
- Background: #FFFFFF
- Corner radius: 12dp
- Elevation: 1dp
- Left border: 4dp, #7AB89B (sage green - health)
- Padding: 16dp
- Margin between cards: 12dp

### Card Header
- Age label: 18sp, Lexend Medium, #3D4852
- Divider: 1dp, #E5E7EB

### Vaccine List
- Each vaccine: 14sp, Lexend Regular, #3D4852
- Bullet style: None (clean list)
- Line height: 1.6

### Date Badge
- Icon: 16dp calendar, #5B7DB1
- Text: 14sp, Lexend Medium, #5B7DB1
- Margin top: 8dp

### Share Button & Footer
- Same as Doctor USG screen

---

## Immunization Schedule Data (IAP India 2024)

```java
public class ImmunizationSchedule {

    public static List<VaccinationMilestone> getSchedule(LocalDate dob) {
        List<VaccinationMilestone> schedule = new ArrayList<>();

        // At Birth
        schedule.add(new VaccinationMilestone(
            "At Birth",
            dob,
            Arrays.asList("BCG", "OPV-0", "Hepatitis B - Birth dose")
        ));

        // 6 Weeks
        schedule.add(new VaccinationMilestone(
            "6 Weeks",
            dob.plusWeeks(6),
            Arrays.asList(
                "DTwP/DTaP - 1",
                "IPV - 1",
                "Hepatitis B - 2",
                "Hib - 1",
                "Rotavirus - 1",
                "PCV - 1"
            )
        ));

        // 10 Weeks
        schedule.add(new VaccinationMilestone(
            "10 Weeks",
            dob.plusWeeks(10),
            Arrays.asList(
                "DTwP/DTaP - 2",
                "IPV - 2",
                "Hib - 2",
                "Rotavirus - 2",
                "PCV - 2"
            )
        ));

        // 14 Weeks
        schedule.add(new VaccinationMilestone(
            "14 Weeks",
            dob.plusWeeks(14),
            Arrays.asList(
                "DTwP/DTaP - 3",
                "IPV - 3",
                "Hib - 3",
                "Rotavirus - 3",
                "PCV - 3"
            )
        ));

        // 6 Months
        schedule.add(new VaccinationMilestone(
            "6 Months",
            dob.plusMonths(6),
            Arrays.asList("OPV - 1", "Hepatitis B - 3")
        ));

        // 9 Months
        schedule.add(new VaccinationMilestone(
            "9 Months",
            dob.plusMonths(9),
            Arrays.asList("MMR - 1", "OPV - 2")
        ));

        // 9-12 Months
        schedule.add(new VaccinationMilestone(
            "9-12 Months",
            dob.plusMonths(9),
            Arrays.asList("Typhoid Conjugate Vaccine")
        ));

        // 12 Months
        schedule.add(new VaccinationMilestone(
            "12 Months",
            dob.plusMonths(12),
            Arrays.asList("Hepatitis A - 1")
        ));

        // 15 Months
        schedule.add(new VaccinationMilestone(
            "15 Months",
            dob.plusMonths(15),
            Arrays.asList("MMR - 2", "Varicella - 1", "PCV Booster")
        ));

        // 16-18 Months
        schedule.add(new VaccinationMilestone(
            "16-18 Months",
            dob.plusMonths(16),
            Arrays.asList(
                "DTwP/DTaP - Booster 1",
                "IPV - Booster 1",
                "Hib - Booster"
            )
        ));

        // 18 Months
        schedule.add(new VaccinationMilestone(
            "18 Months",
            dob.plusMonths(18),
            Arrays.asList("Hepatitis A - 2")
        ));

        // 4-6 Years
        schedule.add(new VaccinationMilestone(
            "4-6 Years",
            dob.plusYears(4),
            Arrays.asList(
                "DTwP/DTaP - Booster 2",
                "OPV - 3",
                "Varicella - 2",
                "MMR - 3"
            )
        ));

        // 10-12 Years
        schedule.add(new VaccinationMilestone(
            "10-12 Years",
            dob.plusYears(10),
            Arrays.asList("Tdap/Td", "HPV (for girls)")
        ));

        return schedule;
    }
}
```

---

## Share Text Format

```
Baby Vaccination Schedule
Date of Birth: 15 December 2024

At Birth (15 Dec 2024)
• BCG
• OPV-0
• Hepatitis B - Birth dose

6 Weeks (26 Jan 2025)
• DTwP/DTaP - 1
• IPV - 1
• Hepatitis B - 2
• Hib - 1
• Rotavirus - 1
• PCV - 1

10 Weeks (23 Feb 2025)
• DTwP/DTaP - 2
• IPV - 2
• Hib - 2
• Rotavirus - 2
• PCV - 2

... (continues for all milestones)

Generated by Mahina App
```

---

## Notes

1. Schedule follows **IAP (Indian Academy of Pediatrics) 2024** recommendations
2. Some vaccines have ranges (e.g., 9-12 months) - show earliest date
3. Vaccines are listed in recommended order
4. Doctor can share the full schedule with patient
5. **No data is saved** - each session is independent
