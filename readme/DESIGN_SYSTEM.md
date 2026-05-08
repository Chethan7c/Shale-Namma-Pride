# Shale-Namma Pride - UI/UX Design System

## 1. DESIGN PHILOSOPHY

- **Target Audience:** Rural village parents (Hindi/Kannada speaking, mixed literacy levels)
- **Principle:** Simple, intuitive, accessible - max 3 taps to reach any feature
- **Accessibility:** Large touch targets, high contrast, clear iconography
- **Cultural Fit:** Warm, community-focused design that builds trust
- **Offline First:** Works smoothly with slow/intermittent internet

---

## 2. COLOR PALETTE

### Primary Colors
| Color | Hex | Usage | Reason |
|-------|-----|-------|--------|
| **Trust Blue** | #1F5AA0 | Primary actions, headers, CTAs | Trust, education, authority |
| **Success Green** | #4CAF50 | Achievements, positive feedback | Growth, success, community pride |
| **Warning Orange** | #FF9800 | Meal updates, important notices | Attention, warmth, food-related |
| **Light Gray** | #F5F5F5 | Card backgrounds, dividers | Clean, modern, reduces eye strain |
| **Dark Gray** | #333333 | Body text, primary text | Readability, high contrast |

### Neutral Colors
| Color | Hex | Usage |
|-------|-----|-------|
| White | #FFFFFF | Background, cards |
| Light Gray | #E0E0E0 | Dividers, disabled states |
| Medium Gray | #9E9E9E | Secondary text, hints |
| Dark Charcoal | #212121 | Deep text, emphasis |

### Status Colors
| Status | Color | Hex |
|--------|-------|-----|
| Success | Green | #4CAF50 |
| Error | Red | #F44336 |
| Warning | Orange | #FF9800 |
| Info | Blue | #2196F3 |

---

## 3. TYPOGRAPHY

### Font Family
- **Primary Font:** Roboto (system default on Android)
- **Fallback:** sans-serif
- **Kannada Support:** Use system Kannada fonts for automatic rendering

### Type Scales

| Component | Size | Weight | Line Height | Usage |
|-----------|------|--------|-------------|-------|
| **H1 (Headline 1)** | 32sp | 700 (Bold) | 40sp | App title, main screen headers |
| **H2 (Headline 2)** | 28sp | 700 (Bold) | 36sp | Section headers |
| **H3 (Headline 3)** | 24sp | 600 (SemiBold) | 32sp | Card titles, feature titles |
| **H4 (Headline 4)** | 20sp | 600 (SemiBold) | 28sp | Subheadings |
| **Body 1** | 16sp | 400 (Regular) | 24sp | Primary body text, descriptions |
| **Body 2** | 14sp | 400 (Regular) | 20sp | Secondary text, captions |
| **Button** | 14sp | 600 (SemiBold) | 20sp | Button labels |
| **Caption** | 12sp | 400 (Regular) | 16sp | Timestamps, metadata |

### Minimum Size Guideline
- **Body text minimum:** 14sp (accessibility requirement)
- **Touch target minimum:** 48dp x 48dp

---

## 4. SPACING & LAYOUT

### Spacing Scale (8dp grid system)
| Unit | dp | Usage |
|------|----|----|
| xs | 4 | Icon spacing, tight layouts |
| sm | 8 | Component padding, small gaps |
| md | 16 | Card padding, section gaps |
| lg | 24 | Major section spacing |
| xl | 32 | Screen-level spacing |
| xxl | 48 | Large layout sections |

### Screen Padding
- **Horizontal:** 16dp on all screens
- **Vertical:** 16dp top/bottom
- **Safe Area:** Respect system status bar (24dp top) and nav bar (56dp bottom)

### Card Spacing
- **Padding:** 16dp all sides
- **Margin:** 8dp between cards (16dp between sections)
- **Corner Radius:** 12dp
- **Elevation:** 2dp (Material Design 3)

---

## 5. COMPONENT SPECIFICATIONS

### Button Styles

#### Primary Button
```
Size: 48dp height, full width or wrap content
Padding: 16dp horizontal, 12dp vertical
Corner Radius: 8dp
Color: Trust Blue (#1F5AA0)
Text Color: White
Font: Button (14sp SemiBold)
Elevation: 2dp
```

#### Secondary Button
```
Size: 48dp height
Border: 2dp Trust Blue
Background: White
Text Color: Trust Blue
Corner Radius: 8dp
```

#### Tertiary/Text Button
```
Background: Transparent
Text Color: Trust Blue
Font: Button (14sp SemiBold)
No elevation
```

### Input Fields
```
Height: 56dp
Padding: 16dp horizontal, 12dp vertical
Border: 1dp Light Gray
Border Radius: 8dp
Focus Border: 2dp Trust Blue
Error Border: 2dp Red
Label Font: 12sp Medium Gray
Input Font: Body 1 (16sp)
```

### Cards
```
Background: White
Border Radius: 12dp
Padding: 16dp
Elevation: 2dp
Margin: 8dp between cards
Border: None (elevation provides depth)
```

### Badges & Chips
```
Height: 32dp
Padding: 8dp horizontal, 4dp vertical
Border Radius: 16dp
Font: 12sp SemiBold
Colors: Primary, Success, Warning, or Gray
```

### Bottom Navigation
```
Height: 56dp
Background: White
Item Height: 56dp
Icon Size: 24dp
Label Font: 12sp Regular
Active Color: Trust Blue
Inactive Color: Medium Gray
```

---

## 6. ICONOGRAPHY

### Icon Sizes
| Size | Usage |
|------|-------|
| 16dp | Small inline icons, badges |
| 24dp | Standard UI icons (navigation, actions) |
| 32dp | Card headers, featured icons |
| 48dp | Large action icons, feature highlights |

### Icon Style
- **Library:** Material Design Icons (system icons)
- **Style:** Outlined or filled (consistent throughout)
- **Color:** Match text color (Dark Gray for primary, Medium Gray for secondary)
- **Design Rules:** Avoid custom icons where Material Design has equivalents

### Common Icons
| Feature | Icon | Color |
|---------|------|-------|
| Home | home | Trust Blue |
| Meals | restaurant_menu | Orange |
| Gallery | image | Trust Blue |
| Achievements | star | Success Green |
| Feedback | chat_bubble | Trust Blue |
| Notifications | notifications | Trust Blue |
| Settings | settings | Gray |
| Menu | menu | Dark Gray |
| Back | arrow_back | Dark Gray |
| Add | add_circle | Trust Blue |

---

## 7. NAVIGATION STRUCTURE

### Bottom Navigation Tabs (Main)
1. **Home** - Dashboard overview
2. **Meals** - Daily meal updates
3. **Gallery** - Facility tours
4. **Achievements** - Student recognition
5. **Profile** - User profile & settings

### Nested Navigation
- **Feedback** - Accessible from Home > "Send Feedback" button
- **Notifications** - Bell icon in top app bar
- **Language** - Settings in Profile tab
- **Admin Dashboard** - Visible only to Admin/Teacher roles

---

## 8. SCREENS & LAYOUTS

### Screen Heights
- **Top App Bar:** 56dp (without elevation)
- **Bottom Navigation:** 56dp
- **Available Content Area:** Screen height - 56dp (top) - 56dp (bottom)
- **Safe Margins:** 16dp horizontal on all screens

### Dark Mode
- **Status:** Not planned for v1.0
- **Future:** Can be added in v1.2
- **Foundation:** Use semantic colors (text_primary, bg_primary) for easy theming

---

## 9. ANIMATIONS & TRANSITIONS

### Navigation Transitions
- **Slide animations:** 200ms duration
- **Fade animations:** 150ms duration
- **Material standard easing:** Ease in-out

### Component Animations
- **Button press:** 100ms ripple effect (Material Design)
- **List item load:** Staggered 50ms fade-in
- **Image load:** Fade-in 200ms
- **Page scroll:** Smooth, no janky transitions

---

## 10. ACCESSIBILITY GUIDELINES

### Text & Readability
- [ ] Minimum font size: 14sp for body text
- [ ] Minimum contrast ratio: 4.5:1 for text on background
- [ ] Line height: 1.5x font size minimum
- [ ] Avoid justified text alignment

### Touch & Interaction
- [ ] Minimum touch target: 48dp x 48dp
- [ ] Spacing between targets: 8dp minimum
- [ ] Clear visual feedback on press states
- [ ] No hover-only interactions (mobile)

### Navigation
- [ ] Max 3 taps to reach any feature
- [ ] Clear back button behavior
- [ ] Consistent navigation patterns
- [ ] Logical tab order for screen readers

### Images & Media
- [ ] Alt text for all images (described in Kannada/English)
- [ ] Image descriptions in gallery
- [ ] Captions for video content
- [ ] No critical info in images alone

### Localization
- [ ] All text must be translatable
- [ ] Avoid text in images
- [ ] Support RTL layout if expanding to Arabic
- [ ] Special character support for Kannada

---

## 11. DEVICE SPECIFICATIONS

### Screen Support
| Device Type | Screen Size | Resolution | Supported |
|------------|-------------|------------|-----------|
| Phone (Small) | 4.5" | 720 x 1280 | ✅ |
| Phone (Standard) | 5.5" | 1080 x 1920 | ✅ |
| Phone (Large) | 6.0" | 1440 x 2560 | ✅ |
| Tablet (7") | 7" | 1024 x 600 | ✅ (future) |

### Android Versions
- **Minimum:** Android 8.0 (API 26)
- **Target:** Android 14+ (API 34+)
- **Compose Min:** Android 5.0 (API 21) via Compose runtime

### Orientation
- **Default:** Portrait (phones)
- **Landscape:** Support for tablets and rotation
- **Responsive:** Adapt layouts for all screen sizes

---

## 12. MATERIAL DESIGN 3 COMPLIANCE

### Key Principles
- **Color System:** Dynamic color support (Material You)
- **Components:** Use Jetpack Compose Material 3 components
- **Elevation:** Tonal colors instead of shadows (MD3 style)
- **Typography:** Material 3 type scale system
- **Spacing:** Material 3 spacing grid (4dp baseline)

### Compose Material 3 Integration
```kotlin
// All UI should use Material 3 components:
- Button → Button (MD3)
- TextField → TextField (MD3)
- Card → Card (MD3)
- TopAppBar → TopAppBar (MD3)
- NavigationBar → NavigationBar (MD3)
```

---

## 13. IMAGE SPECIFICATIONS

### Meal Photos
- **Aspect Ratio:** 4:3 (landscape preferred)
- **Resolution:** Min 1080px width (2x scale)
- **Format:** JPEG (compressed)
- **Max File Size:** 2MB
- **Optimization:** Firebase Cloud Storage auto-optimizes

### Gallery Images
- **Aspect Ratio:** 16:9 (landscape) or 4:3
- **Resolution:** Min 1440px width for fullscreen
- **Format:** JPEG or WebP
- **Max File Size:** 3MB
- **Display:** Pager with smooth transitions

### Profile/Achievement Photos
- **Aspect Ratio:** 1:1 (square)
- **Resolution:** 256px minimum
- **Format:** JPEG
- **Max File Size:** 1MB

### App Icons
- **Icon Size:** 192dp (xxxhdpi = 192px)
- **Safe Zone:** 72dp from center
- **Format:** PNG with transparency
- **No rounded corners:** Let system apply

---

## 14. LOADING STATES & EMPTY STATES

### Loading State
- **Indicator:** Material 3 CircularProgressIndicator
- **Color:** Trust Blue
- **Size:** 48dp
- **Position:** Centered on screen
- **Timeout:** Show error if > 10 seconds

### Empty State (No Data)
```
- Icon: 64dp, Medium Gray
- Title: "No [Items] Yet"
- Subtitle: "Check back soon"
- CTA Button: If applicable (e.g., "Post First Update")
- Color: Light Gray background card
```

### Error State
```
- Icon: 64dp, Red
- Title: "Something went wrong"
- Message: User-friendly error description
- CTA: "Retry" button or "Go Back"
```

---

## 15. LANGUAGE & LOCALIZATION

### Language Support
- **English:** Default
- **Kannada:** Full support with auto-toggle button
- **Translation API:** Google Translate (fallback) or on-device translation

### Language Toggle
- **Location:** Top app bar (flag icon)
- **Behavior:** Toggle between EN ↔ KN
- **Persistence:** Save preference to user profile
- **Scope:** All user-facing text

### Kannada Considerations
- Font rendering: System supports ನನುಗ (Unicode)
- Text direction: LTR (same as English)
- Special characters: Supported by Roboto
- Line breaks: May differ from English (account for text expansion)

---

## 16. PLATFORM-SPECIFIC BEHAVIORS

### Status Bar
- **Color:** Trust Blue (#1F5AA0)
- **Icon Color:** White (light icons)
- **Height:** 24dp (system managed)

### Navigation Bar (System Back/Home)
- **Color:** White (default)
- **Visibility:** Show on all screens
- **Gesture Navigation:** Support both button and gesture nav

### Notification Channel
- **Sound:** Default notification sound
- **Vibration:** Default pattern
- **LED:** Device default
- **Priority:** HIGH for important updates

---

## 17. RESPONSIVE DESIGN

### Breakpoints
| Breakpoint | Width | Layout |
|------------|-------|--------|
| Small | < 480dp | Single column, full-width cards |
| Medium | 480-720dp | Single column with wider padding |
| Large | > 720dp | Two columns (tablets) |

### Adaptive Layouts
- **Cards:** Stack on small, grid on large
- **Lists:** Single column on phone, grid on tablet
- **Forms:** Full-width inputs on all sizes
- **Navigation:** Bottom nav on phone, sidebar on tablet (future)

---

## 18. MOTION & MICRO-INTERACTIONS

### Page Transitions
- **Slide:** New screens slide in from right (200ms)
- **Fade:** Dialogs fade in (150ms)
- **Scale:** Bottom sheets scale up (250ms)

### Button Feedback
- **Press:** Ripple effect (Material Design standard)
- **Duration:** 100ms
- **Color:** Trust Blue with 24% opacity

### Loading Animations
- **Meal image:** Skeleton loading placeholder
- **List items:** Staggered fade-in (50ms delay each)
- **Gallery:** Smooth pager transitions

---

## 19. DIALOGS & MODALS

### Confirmation Dialog
```
Title: 16sp SemiBold
Message: 14sp Regular
Buttons: 2x Primary (side-by-side) or stacked
Max Width: 90% of screen (min 280dp, max 560dp)
Padding: 24dp all sides
```

### Bottom Sheet
```
Height: Variable, min 200dp
Padding: 16dp all sides
Top radius: 16dp
Drag indicator: Visible
Scrim: Semi-transparent dark overlay (32% opacity)
```

### Toast/Snackbar
```
Height: 48dp
Padding: 16dp
Margin: 16dp from bottom nav
Position: Above navigation bar
Duration: 3 seconds (dismissible by swipe)
Action Button: Optional (e.g., "Undo")
```

---

## 20. DESIGN HANDOFF CHECKLIST

- [x] Color palette documented
- [x] Typography scale defined
- [x] Spacing system established
- [x] Component specifications completed
- [x] Navigation structure finalized
- [x] Accessibility guidelines set
- [x] Responsive breakpoints defined
- [x] Material Design 3 compliance verified
- [x] Language/localization strategy confirmed
- [ ] Figma wireframes created (next step)
- [ ] Design review & approval
- [ ] Component library setup in Compose
