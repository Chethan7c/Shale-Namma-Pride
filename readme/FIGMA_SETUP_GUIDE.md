# Figma Design File - Setup & Creation Guide

## Overview

This guide provides instructions for creating the Shale-Namma Pride design file in Figma. While I cannot directly create Figma files via API, you can follow these steps to set up your design file and import the specifications.

---

## STEP 1: CREATE FIGMA FILE

1. Go to [figma.com](https://figma.com)
2. Click **"New File"** → Name it: `Shale-Namma Pride - App Design v1.0`
3. Set up the file structure:
   - Page 1: **Design System** (colors, typography, components)
   - Page 2: **Screens** (all 11 screens)
   - Page 3: **Components Library** (reusable components)
   - Page 4: **Prototypes** (interactive flows)

---

## STEP 2: CREATE DESIGN SYSTEM PAGE

### Color Styles

1. In the **Design System** page, create a section for colors
2. Create color frames with labels:

   **Primary Colors:**
   ```
   Trust Blue (#1F5AA0) - Primary actions, headers
   Success Green (#4CAF50) - Achievements, positive feedback
   Warning Orange (#FF9800) - Meal updates, important notices
   Light Gray (#F5F5F5) - Card backgrounds
   Dark Gray (#333333) - Body text
   ```

3. For each color, create a rectangle and apply the hex color
4. Add the color name as a label below
5. Create **Figma Color Styles**:
   - Right-click color → **"Create Color Style"** → Name it (e.g., `primary/trust-blue`)
   - Repeat for all colors

### Typography Styles

1. Create a section for typography
2. Create text frames with each style:

   ```
   H1 (32sp, Bold) - "Sample Heading 1"
   H2 (28sp, Bold) - "Sample Heading 2"
   H3 (24sp, SemiBold) - "Sample Heading 3"
   H4 (20sp, SemiBold) - "Sample Heading"
   Body 1 (16sp, Regular) - "Sample body text with full details"
   Body 2 (14sp, Regular) - "Sample secondary text"
   Button (14sp, SemiBold) - "BUTTON LABEL"
   Caption (12sp, Regular) - "Sample caption text"
   ```

3. For each, create **Figma Typography Styles**:
   - Select text frame → **Right-click** → **"Create Typography Style"** → Name it (e.g., `heading/h1`, `body/body-1`)

### Spacing Grid

1. Create a reference guide showing the 8dp grid:
   ```
   XS: 4dp  | SM: 8dp  | MD: 16dp | LG: 24dp | XL: 32dp | XXL: 48dp
   ```

2. Create rectangles of each size with labels
3. Add these as **Figma components** (right-click → **"Create component"**)

### Component Styles

Create component frames:

**Buttons:**
- [ ] Primary Button (blue, 48dp height, full-width)
- [ ] Secondary Button (blue outline, 48dp)
- [ ] Text Button (blue text, no background)

**Input Fields:**
- [ ] Email Input (with icon, placeholder)
- [ ] Password Input (with visibility toggle)
- [ ] Text Area (multi-line, character counter)

**Cards:**
- [ ] Basic Card (white, 12dp radius, 2dp shadow)
- [ ] Info Card (with icon, title, subtitle)
- [ ] Image List Item (image + text)

**Badges:**
- [ ] Primary Badge (blue)
- [ ] Success Badge (green)
- [ ] Warning Badge (orange)

**Other:**
- [ ] Rating Bar (5 stars)
- [ ] Checkbox with Label
- [ ] Divider
- [ ] Loading Indicator
- [ ] Empty State

---

## STEP 3: CREATE SCREEN MOCKUPS

### Page: **Screens**

Create frames for all 11 screens. Use **iPhone 13** preset (390x844px):

1. **Splash Screen** - Logo centered, loading indicator
2. **Login Screen** - Email, password, remember me, sign in button
3. **Signup Screen** - Full name, email, phone, school, role, password
4. **Home Screen** - Welcome greeting, meal card, achievement card, quick actions
5. **Meals Screen** - Meal image, menu, rating, past meals list
6. **Gallery Screen** - Gallery cards with preview images
7. **Gallery Detail** - Full-screen pager with image, zoom, share buttons
8. **Achievements Screen** - Achievement cards with student photos
9. **Profile Screen** - Profile picture, email, phone, role, language toggle, buttons
10. **Feedback Screen** - Category dropdown, message textarea, anonymous checkbox
11. **Notifications** - Notification list with timestamps

### For Each Screen:

1. **Naming Convention:** `[Role] - Screen Name` (e.g., `USER - Login Screen`)
2. **Add to Frame:**
   - Status bar (24dp, Trust Blue background)
   - Top app bar (56dp, title + icons)
   - Content area
   - Bottom navigation (56dp, 5 tabs)
3. **Use created components:**
   - Drag in the button components you created
   - Use color styles for consistency
   - Use typography styles for all text
4. **Add annotations:**
   - Spacing between elements (using the spacing grid)
   - Font size and weight (reference design system)
   - Color codes in hex

### Example: Login Screen Layout

```
┌─ Frame: "USER - Login Screen" (390x844)
│  ├─ Group: Status Bar (390x24)
│  │  └─ Rect: Trust Blue background
│  ├─ Group: Top App Bar (390x56)
│  │  └─ Text: "Shale-Namma Pride"
│  ├─ Group: Content (390x668)
│  │  ├─ Text: "Heading"
│  │  ├─ Component: Email Input Field
│  │  ├─ Component: Password Input Field
│  │  ├─ Component: Checkbox with Label
│  │  ├─ Component: Primary Button
│  │  ├─ Text: Link to Signup
│  │  └─ Text: Link to Forgot Password
│  └─ Group: Bottom Navigation (390x56)
│     └─ 5 Navigation items
```

---

## STEP 4: CREATE INTERACTIVE PROTOTYPES

### Page: **Prototypes**

1. Go to **Prototype** panel (right sidebar)
2. Set up interactions for each screen:

**Login Screen:**
- [Sign In] button → Navigate to Home Screen
- [Sign Up] link → Navigate to Signup Screen
- [Forgot Password] link → Navigate to Password Reset Screen

**Home Screen:**
- [Meals] tab → Navigate to Meals Screen
- [Gallery] tab → Navigate to Gallery Screen
- [Achievements] tab → Navigate to Achievements Screen
- [Profile] tab → Navigate to Profile Screen

**Meals Screen:**
- Meal card → Navigate to Meal Detail Screen
- [+ Post Meal] button (Admin only) → Navigate to Post Meal Dialog

**Gallery Screen:**
- Gallery card → Navigate to Gallery Detail Screen (Pager)

**Achievements Screen:**
- Achievement card → Navigate to Achievement Detail
- [Share] button → Show Share Dialog

3. Set animation:
   - **Ease-in-out, 200ms** for screen transitions
   - **Ease-in-out, 150ms** for dialogs

---

## STEP 5: CREATE COMPONENT LIBRARY

### Page: **Components Library**

This page contains reusable components for team use.

1. **Create Button Components:**
   - `Button/Primary` (with states: default, hover, pressed, disabled)
   - `Button/Secondary` (outline style)
   - `Button/Text` (minimal style)

2. **Create Input Components:**
   - `TextField/Email` (with label, placeholder, error state)
   - `TextField/Password` (with visibility toggle)
   - `TextField/Generic` (multi-line capable)

3. **Create Card Components:**
   - `Card/Base` (basic card with shadow)
   - `Card/Info` (with icon slot)
   - `Card/ImageListItem` (with image + text layout)

4. **Create Utility Components:**
   - `Badge/Primary` (blue)
   - `Badge/Success` (green)
   - `Badge/Warning` (orange)
   - `Rating/Stars` (5-star selector)
   - `Checkbox/WithLabel`
   - `Divider/Horizontal`
   - `LoadingIndicator/Circular`

### Making a Reusable Component:

1. Design the component (e.g., a button)
2. **Right-click** → **"Create component"**
3. Name it (e.g., `Button/Primary`)
4. In the component panel (right), add **properties:**
   - `State`: Default, Hover, Pressed, Disabled
   - `Size`: Small, Medium, Large
   - `Text`: Editable text layer

5. Create variants:
   - **Right-click** → **"Create variant"** for each state
   - Modify the appearance for each state (e.g., darker blue for pressed)

---

## STEP 6: DESIGN HANDOFF SETUP

1. Share the Figma file with developers:
   - **File → Share → Invite**
   - Add developer emails with **"View"** access

2. Enable **"Code Export"** plugin:
   - Go to **Assets** panel
   - Click **"Community"** → Search for **"Figma to Code"**
   - Install the plugin
   - This allows developers to export CSS/React from components

3. Create a **README section** in Figma:
   - Add a page called **"📖 README"**
   - Document:
     - Color palette usage
     - Typography scale
     - Spacing grid rules
     - Component naming conventions
     - How to use components

4. Add **Design Specs:**
   - Select any element
   - Right panel → **"Inspect"** tab
   - Figma will show:
     - Dimensions (width, height)
     - Position (X, Y)
     - Colors (hex values)
     - Typography (font, size, weight)
     - Effects (shadows, strokes)

---

## STEP 7: TEAM COLLABORATION

### For Designers:

- Use the **Components Library** page to create new screens
- **Drag components** into frames rather than creating new elements
- Update components in the library, changes automatically sync to all screens

### For Developers:

- Open the file in **"View"** mode
- Use the **"Inspect"** panel to see specifications
- Reference component names when implementing
- Export components using the **"Figma to Code"** plugin

---

## STEP 8: KEEPING DESIGN UPDATED

Whenever design changes:

1. **Update the component** in the Components Library
2. All instances automatically update across screens
3. **Version the file:**
   - File menu → **"Version history"** → Create save
   - Name it with version (e.g., `v1.0.1`)

4. **Notify developers** via Slack/Email:
   - What changed
   - Which components affected
   - Any migration steps needed

---

## QUICK CHECKLIST

- [ ] Create Figma file
- [ ] Set up Design System page with colors, typography, spacing
- [ ] Create Color Styles in Figma
- [ ] Create Typography Styles in Figma
- [ ] Design 11 screen mockups with correct dimensions
- [ ] Add top app bar (56dp) and bottom nav (56dp) to all screens
- [ ] Use created components in screens
- [ ] Create interactive prototypes with navigation flows
- [ ] Set up Components Library page with reusable components
- [ ] Add component variants (states: default, hover, pressed, disabled)
- [ ] Share file with developers (View access)
- [ ] Install "Figma to Code" plugin
- [ ] Add "README" page with guidelines
- [ ] Enable Inspect for design specs
- [ ] Create version 1.0 save

---

## SHARING WITH DEVELOPERS

Once your Figma file is complete:

1. **Generate Figma link:**
   - File menu → **"Share"** → Copy link
   - Share link in Slack/Email/GitHub

2. **Developers can:**
   - View all screens and components
   - Inspect dimensions, colors, typography
   - Use Figma to Code plugin for component export
   - Reference component specs during implementation

---

## EXAMPLE FIGMA FILE STRUCTURE

```
Shale-Namma Pride - App Design v1.0
│
├─ 📖 README
│  └─ Design guidelines, color usage, spacing rules
│
├─ 🎨 Design System
│  ├─ Colors (with style definitions)
│  ├─ Typography (with style definitions)
│  ├─ Spacing (8dp grid reference)
│  └─ Components (button, input, card examples)
│
├─ 📱 Screens
│  ├─ USER - Splash Screen
│  ├─ USER - Login Screen
│  ├─ USER - Signup Screen
│  ├─ USER/ADMIN - Home Screen
│  ├─ USER/ADMIN - Meals Screen
│  ├─ USER/ADMIN - Meals Detail
│  ├─ USER/ADMIN - Gallery Screen
│  ├─ USER/ADMIN - Gallery Pager
│  ├─ USER/ADMIN - Achievements Screen
│  ├─ USER/ADMIN - Profile Screen
│  └─ USER/ADMIN - Feedback Screen
│
├─ 🔌 Components Library
│  ├─ Button/Primary (+ variants)
│  ├─ Button/Secondary (+ variants)
│  ├─ Button/Text (+ variants)
│  ├─ TextField/Email (+ error state)
│  ├─ TextField/Password (+ visibility toggle)
│  ├─ TextField/Generic (+ focused, error)
│  ├─ Card/Base (+ states)
│  ├─ Card/Info (+ states)
│  ├─ Badge/Primary (+ variants)
│  ├─ Badge/Success (+ variants)
│  ├─ Rating/Stars
│  ├─ Checkbox/WithLabel
│  ├─ Divider/Horizontal
│  └─ LoadingIndicator/Circular
│
└─ 🔗 Prototypes
   └─ Interactive flows with navigation
```

---

## RESOURCES

- **Figma Docs:** https://help.figma.com/
- **Material Design 3 in Figma:** https://www.figma.com/community/file/1035203688168986460
- **Figma to Code:** https://www.figma.com/community/plugin/1071195754202656711/Figma-to-Code
- **Icon Library:** https://www.figma.com/community/file/748468846914988320

---

**Note:** Once you create the Figma file following this guide, share the link with developers so they can reference it during implementation.

**File Created:** April 25, 2026  
**Status:** Ready for Figma Setup
