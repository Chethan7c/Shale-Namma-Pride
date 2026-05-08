# Shale-Namma Pride - Screen Wireframes & User Flows

## SCREEN LAYOUT GUIDE

All screens follow this standard structure:
```
┌─────────────────────────────────┐
│  Status Bar (24dp)              │ Trust Blue background
├─────────────────────────────────┤
│  Top App Bar (56dp)             │ Title + Icons (Home screens)
├─────────────────────────────────┤
│                                 │
│  Content Area                   │ Scrollable
│  (Variable height)              │ Safe margin: 16dp horizontal
│                                 │
└─────────────────────────────────┘
│  Bottom Navigation (56dp)       │ 5 tabs for authenticated users
└─────────────────────────────────┘
```

---

## 1. SPLASH SCREEN (Unauthenticated Users)

```
┌─────────────────────────────────┐
│                                 │
│                                 │
│      [SCHOOL LOGO]              │ 64dp logo, centered
│                                 │
│   Shale-Namma Pride             │ H2 (28sp), centered, Trust Blue
│                                 │
│  "School Transparency &         │ Caption (12sp), centered, Gray
│   Community Trust Portal"        │
│                                 │
│        [Loading...]             │ Circular progress, 2s duration
│                                 │
│                                 │
└─────────────────────────────────┘

Behavior:
- Display for 2 seconds
- Check authentication state
- Route to: LoginScreen OR HomeScreen
```

---

## 2. LOGIN SCREEN

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│                                 │
│  Shale-Namma Pride              │ H2 (28sp), centered, Trust Blue
│                                 │
│  School Transparency Portal     │ Body 2 (14sp), centered, Gray
│                                 │
│  ┌───────────────────────────┐  │
│  │ Email or Phone            │  │ TextField, 56dp height
│  └───────────────────────────┘  │ Hint: "Enter your email"
│                 md spacing       │
│  ┌───────────────────────────┐  │
│  │ Password                  │  │ TextField, icon eye toggle
│  └───────────────────────────┘  │ Hint: "Minimum 6 characters"
│                                 │
│  [   Remember Me   ]            │ Checkbox + Label (14sp)
│                                 │
│              md spacing          │
│                                 │
│  ┌───────────────────────────┐  │
│  │  Sign In                  │  │ Primary Button, 48dp, full width
│  └───────────────────────────┘  │
│                                 │
│   Don't have an account?        │ Body 2 (14sp), centered, Blue link
│   Sign Up                       │ (routes to SignupScreen)
│                                 │
│       Forgot Password?          │ Body 2, centered, Blue link
│                                 │
└─────────────────────────────────┘

Keyboard: EMAIL keyboard for first field, default for password

Validations:
- Email: Valid email format or 10-digit phone number
- Password: Min 6 characters
- Error message: "Invalid email or password"

Error State:
- Red border on TextField
- Error message below field (12sp, Red)
- Toast: "Login failed. Please try again."

Loading State:
- Button shows spinner, text hidden
- Fields disabled during request
- Timeout: 10 seconds
```

---

## 3. SIGNUP SCREEN (Admin/Teacher Only)

```
┌─────────────────────────────────┐
│  [<] Status Bar                 │ Back button to Login
├─────────────────────────────────┤
│  Create Account                 │ H2 (28sp), left-aligned
│                                 │
│  School Admin or Teacher        │ Body 2 (14sp), Gray
│  account required for posting   │
│                                 │
│              md spacing         │
│                                 │
│  ┌───────────────────────────┐  │
│  │ Full Name                 │  │ TextField
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ Email Address             │  │
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ Phone Number              │  │ Indian format validation
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ School Name               │  │ Dropdown/autocomplete
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ Role                      │  │ Dropdown: Admin / Teacher
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ Password                  │  │
│  └───────────────────────────┘  │
│                                 │
│  ☐ I agree to Terms & Privacy   │ Checkbox + link
│                                 │
│  ┌───────────────────────────┐  │
│  │  Create Account           │  │
│  └───────────────────────────┘  │
│                                 │
└─────────────────────────────────┘

Validation:
- All fields required
- Email unique check
- Phone format: +91 XXXXX XXXXX
- Password: Min 8 chars, 1 uppercase, 1 number
- Terms checkbox: Must be checked

Success Flow:
- Show "Verification email sent"
- Route to EmailVerificationScreen
```

---

## 4. HOME SCREEN (Dashboard)

```
┌─────────────────────────────────┐
│  Status Bar (Trust Blue)        │
├─────────────────────────────────┤
│ Shale-Namma     [🔔][👤]        │ Title + Notification bell + Profile
├─────────────────────────────────┤
│                                 │
│  Welcome back, Headmaster!      │ H3 (24sp), Body text
│                                 │
│  ┌───────────────────────────┐  │
│  │ 📅 Today's Updates        │  │ Card, tap for Meals screen
│  │                           │  │
│  │ Meal Update Status:       │  │
│  │ ⏰ Updated 2 hours ago    │  │
│  │ [View Today's Meal] →     │  │
│  └───────────────────────────┘  │
│              md spacing         │
│  ┌───────────────────────────┐  │
│  │ ⭐ Latest Achievement     │  │
│  │                           │  │
│  │ "Student of the Week"     │  │
│  │ Raj Kumar - Sports        │  │
│  │ Posted 1 day ago          │  │
│  │ [View More] →             │  │
│  └───────────────────────────┘  │
│              md spacing         │
│  ┌───────────────────────────┐  │
│  │ Quick Actions             │  │
│  │                           │  │
│  │ [+ Post Update] [Share]   │  │ Two buttons in row
│  │ [Send Feedback]           │  │
│  └───────────────────────────┘  │
│              lg spacing         │
│                                 │
│  ─── ANNOUNCEMENTS ────         │ Section header
│                                 │
│  📢 "School event on Friday"    │ List item
│  📢 "Facility maintenance"      │
│  📢 "New gallery added"         │
│                                 │
│  [View All Announcements] →     │ Link, 14sp Blue
│                                 │
│              lg spacing         │
│                                 │
│  [Send Feedback]                │ Button at bottom
│                                 │
│  (content scrollable)           │
│                                 │
└─────────────────────────────────┘
│ [Home][Meals][Gallery][🏆][👤]  │ Bottom Navigation
└─────────────────────────────────┘

Roles:
- Admin/Teacher: Full dashboard with "Post Update" buttons
- Parent (unauthenticated): Simplified view, no posting options
```

---

## 5. MEALS SCREEN

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│ Meals & Nutrition       [⚙️]    │ H2, Settings icon
├─────────────────────────────────┤
│                                 │
│  Today's Meal                   │ H3 (24sp), Body text
│  ┌───────────────────────────┐  │
│  │                           │  │
│  │   [    MEAL IMAGE    ]    │  │ AspectRatio 4:3, Rounded corners
│  │                           │  │ Tap to view fullscreen
│  └───────────────────────────┘  │
│                                 │
│  Lunch Menu                     │ H4 (20sp)
│  ┌───────────────────────────┐  │
│  │ • Rice with sambar        │  │ Bullet list
│  │ • Dal curry               │  │
│  │ • Vegetables              │  │
│  │ • Roti (2)                │  │
│  └───────────────────────────┘  │
│                                 │
│  Updated: 10:30 AM              │ Caption (12sp), Gray
│  ⭐⭐⭐⭐⭐ (4.2/5.0) - 127 ratings │ Star rating widget
│                                 │
│  [Rate This Meal] [Share]       │ Secondary buttons
│                                 │
│              lg spacing         │
│                                 │
│  ─── PREVIOUS MEALS ────         │ Section header
│                                 │
│  Apr 30                         │ Date group header
│  ┌─────┬────────────────────┐   │
│  │[IMG]│ Dinner              │   │ Horizontal list item
│  │     │ Rice + Curry        │   │
│  │     │ ⭐⭐⭐⭐⭐ (4.0) → │   │ Tap to view details
│  └─────┴────────────────────┘   │
│                                 │
│  Apr 29                         │
│  ┌─────┬────────────────────┐   │
│  │[IMG]│ Lunch               │   │
│  │     │ Pulao + Sambar      │   │
│  │     │ ⭐⭐⭐⭐ (3.8) →  │   │
│  └─────┴────────────────────┘   │
│                                 │
│  [View Last 30 Days] →          │ Link to history
│                                 │
│  (scrollable list)              │
│                                 │
└─────────────────────────────────┘
│ [Home][Meals][Gallery][🏆][👤]  │
└─────────────────────────────────┘

Admin/Teacher View (Additional):
- [+ Post New Meal] button at top
- Edit/Delete options on cards (long-press menu)

Rate Dialog:
- Star rating (1-5) selector
- Optional comment field
- [Submit] button

Error State:
- "No meals posted yet" empty state message
- [Post First Meal] CTA button
```

---

## 6. GALLERY SCREEN

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│ School Facilities       [⚙️]    │ H2, Settings icon
├─────────────────────────────────┤
│                                 │
│  ─── FACILITY GALLERIES ────     │ Section header
│                                 │
│  ┌───────────────────────────┐  │
│  │  🔬 Science Lab           │  │ Card, 1 column layout
│  │                           │  │ Tap to open pager
│  │ [  IMAGE PREVIEW  ]       │  │
│  │                           │  │ 12 photos available
│  │ 12 photos in gallery      │  │
│  └───────────────────────────┘  │
│              md spacing         │
│  ┌───────────────────────────┐  │
│  │  📚 Library               │  │ Second gallery
│  │                           │  │
│  │ [  IMAGE PREVIEW  ]       │  │
│  │                           │  │
│  │ 8 photos in gallery       │  │
│  └───────────────────────────┘  │
│              md spacing         │
│  ┌───────────────────────────┐  │
│  │  🏃 Sports Facilities     │  │ Third gallery
│  │                           │  │
│  │ [  IMAGE PREVIEW  ]       │  │
│  │                           │  │
│  │ 15 photos in gallery      │  │
│  └───────────────────────────┘  │
│              md spacing         │
│  ┌───────────────────────────┐  │
│  │  🚽 Infrastructure        │  │ Fourth gallery
│  │                           │  │
│  │ [  IMAGE PREVIEW  ]       │  │
│  │                           │  │
│  │ 6 photos in gallery       │  │
│  └───────────────────────────┘  │
│                                 │
│  (scrollable list)              │
│                                 │
└─────────────────────────────────┘
│ [Home][Meals][Gallery][🏆][👤]  │
└─────────────────────────────────┘

Admin/Teacher View:
- [+ Add Gallery] button at top
- Edit/Delete menu on long-press

Gallery Detail Screen (Pager):
```
┌─────────────────────────────────┐
│  [<] Science Lab        [⋯]    │ Back button, Title, Menu
├─────────────────────────────────┤
│                                 │
│      [    FULL IMAGE    ]       │ Pager view, swipeable
│                                 │ Pinch-to-zoom enabled
│                                 │
│      [HIGH RESOLUTION]          │
│                                 │
│  Science Lab - Main             │ Description (H4)
│  Updated equipment and          │ Multi-line description
│  modern setup for experiments   │ (Kannada/English)
│                                 │
│  [Download] [Share] [Like]      │ Action buttons
│                                 │
│  Uploaded: Apr 28, 2026         │ Metadata
│  12 of 15 →                     │ Page indicator
│                                 │
│  ────────────────────           │ Swipe left/right to navigate
│  (Swipe left for next image)    │
│                                 │
└─────────────────────────────────┘

Share Menu:
- WhatsApp
- Facebook
- Copy Link
- Download
```

---

## 7. ACHIEVEMENTS SCREEN (Student Recognition)

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│ Student Achievements    [⚙️]    │ H2, Settings icon
├─────────────────────────────────┤
│                                 │
│  ─── THIS WEEK ────              │ Section header
│                                 │
│  ┌───────────────────────────┐  │
│  │ 🏆 STUDENT OF THE WEEK    │  │ Badge, orange background
│  │                           │  │
│  │ [    STUDENT PHOTO    ]   │  │ Square, 100dp
│  │                           │  │
│  │ Raj Kumar - Class 8A      │ H4 (20sp), Name
│  │                           │
│  │ Outstanding performance   │ Body text (14sp)
│  │ in Mathematics Olympiad   │
│  │                           │
│  │ Posted: 2 hours ago       │ Caption (12sp)
│  │                           │
│  │ [Share] [Like: 45] [Msg]  │ Action buttons
│  └───────────────────────────┘  │
│              md spacing         │
│  ┌───────────────────────────┐  │
│  │ ⚽ SPORTS CHAMPION        │  │ Badge, green background
│  │                           │  │
│  │ [    STUDENT PHOTO    ]   │  │
│  │                           │  │
│  │ Priya Singh - Class 10    │
│  │                           │
│  │ Won State level          │
│  │ Badminton Championship    │
│  │                           │
│  │ Posted: 1 day ago         │
│  │                           │
│  │ [Share] [Like: 78] [Msg]  │
│  └───────────────────────────┘  │
│              md spacing         │
│                                 │
│  ─── PREVIOUS ACHIEVEMENTS ──   │ Section header
│                                 │
│  Apr 2026                       │ Month group header
│  ┌─────┬────────────────────┐   │
│  │[IMG]│ Chess Club         │   │ Horizontal list item
│  │     │ Vikram - Class 9   │   │
│  │     │ 👍 34             │   │
│  │     │ Posted Apr 28 → │   │
│  └─────┴────────────────────┘   │
│                                 │
│  ┌─────┬────────────────────┐   │
│  │[IMG]│ Art Competition    │   │
│  │     │ Sneha - Class 7    │   │
│  │     │ 👍 52             │   │
│  │     │ Posted Apr 25 → │   │
│  └─────┴────────────────────┘   │
│                                 │
│  (scrollable list)              │
│                                 │
└─────────────────────────────────┘
│ [Home][Meals][Gallery][🏆][👤]  │
└─────────────────────────────────┘

Admin/Teacher View:
- [+ Post Achievement] button at top
- Edit/Delete on long-press

Share Dialog:
- Student name (auto-filled from post)
- Pre-filled message: "[Name] achieved [achievement]! 🏆"
- WhatsApp / Facebook / Copy Link
```

---

## 8. PROFILE SCREEN (Authenticated User)

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│ Profile                 [⚙️]    │ H2, Settings icon
├─────────────────────────────────┤
│                                 │
│  ┌───────────────────────────┐  │
│  │      [PROFILE PIC]        │  │ 80dp circle, tap to edit
│  │                           │  │
│  │  Raj Kumar                │  │ H3 (24sp)
│  │  School Administrator     │  │ Body 2 (14sp), gray
│  │                           │  │
│  │  ✅ Verified Account      │  │ Badge
│  │  📍 St. Mary's School     │  │ School name
│  │                           │  │
│  └───────────────────────────┘  │
│              lg spacing         │
│                                 │
│  ─── ACCOUNT INFO ────           │ Section header
│                                 │
│  Email                          │ H4 (20sp)
│  rajkumar@stmarys.edu.in        │ Body text (14sp)
│                                 │
│  Phone                          │
│  +91 9876543210                 │
│                                 │
│  Role                           │
│  School Administrator           │
│                                 │
│  Member Since                   │
│  January 15, 2024               │
│                                 │
│              lg spacing         │
│                                 │
│  ─── PREFERENCES ────            │ Section header
│                                 │
│  📱 Language Preference         │ With divider
│  [English] / Kannada            │ Chip toggle
│                                 │
│  🔔 Notifications               │
│  [Enable All] [Customize]       │ Toggle + Link
│                                 │
│  🌙 Dark Mode                   │
│  [Off]                          │ Toggle (future)
│                                 │
│              lg spacing         │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [Edit Profile]            │  │ Primary button
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [Change Password]         │  │ Secondary button
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [Feedback & Support]      │  │ Tertiary button
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [Sign Out]                │  │ Tertiary button, Red text
│  └───────────────────────────┘  │
│                                 │
│              lg spacing         │
│                                 │
│  App Version: 1.0.0             │ Caption (12sp), gray, centered
│  © 2026 Shale-Namma Pride       │
│                                 │
└─────────────────────────────────┘
│ [Home][Meals][Gallery][🏆][👤]  │
└─────────────────────────────────┘

Parent View (Unauthenticated):
- No Email/Phone (if parent)
- Show account link: "Sign in to post"
- Language toggle still available
- Feedback box instead of profile editing

Edit Profile Dialog:
- Full Name (editable)
- Email (read-only)
- Phone (editable)
- Profile Picture (upload or camera)
- [Save Changes] button
```

---

## 9. FEEDBACK SCREEN

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│ [<] Feedback & Suggestions      │ Back button, Title
├─────────────────────────────────┤
│                                 │
│  Share your feedback to help    │ Body text (14sp)
│  us improve the school          │
│                                 │
│              md spacing         │
│                                 │
│  What's your feedback about?    │ H4 (20sp), label
│  ┌───────────────────────────┐  │
│  │ [Facility] ▼              │  │ Dropdown/chips
│  └───────────────────────────┘  │ Options: Facility, Meal,
│                                 │ Teaching, Safety, Other
│
│              md spacing         │
│                                 │
│  Your Message                   │ H4 (20sp), label
│  ┌───────────────────────────┐  │
│  │                           │  │ MultilineTextField
│  │ "Enter your suggestions"  │  │ Min 500 chars
│  │                           │  │ (currently: 0/500)
│  │                           │  │
│  └───────────────────────────┘  │
│  0/500                          │ Character counter
│                                 │
│              md spacing         │
│                                 │
│  ☐ Submit Anonymously           │ Checkbox + label
│  (Your name won't be shared)    │ Hint text (12sp)
│                                 │
│              lg spacing         │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [Submit Feedback]         │  │ Primary button
│  └───────────────────────────┘  │ Disabled if empty
│                                 │
│  ┌───────────────────────────┐  │
│  │ [Cancel]                  │  │ Secondary button
│  └───────────────────────────┘  │
│                                 │
└─────────────────────────────────┘

Validation:
- Message min 10 chars, max 500
- Category required
- Show error if character limit exceeded
- Disable button if empty

Success:
- Toast: "Thank you! Feedback submitted."
- Clear form
- Navigate back

Admin Dashboard View (Feedback List):
┌─────────────────────────────────┐
│ Feedback & Suggestions  [⚙️]    │
├─────────────────────────────────┤
│                                 │
│  All Feedback (47)              │ H3, count
│                                 │
│  Filter: [All] [Facility]       │ Chip filters
│         [Meal] [Safety]         │
│                                 │
│  Apr 29, 2026                   │ Date group
│                                 │
│  ┌───────────────────────────┐  │
│  │ 🟠 Facility               │  │ Category badge
│  │ "Improve lighting in       │  │ Feedback text (truncated)
│  │  boys toilet..."           │  │
│  │ Anonymous                  │  │ Sender (or Anonymous)
│  │ Status: New                │  │ Status badge
│  │ [Mark Done] [Archive]      │  │ Action buttons
│  └───────────────────────────┘  │
│                                 │
│  Apr 28, 2026                   │
│  ┌───────────────────────────┐  │
│  │ 🟢 Meal                   │  │ Different color badge
│  │ "Today's meal was         │  │
│  │  excellent! More variety" │  │
│  │ Positive feedback 👍      │  │ Sentiment indicator
│  │ Parent Name               │  │
│  │ Status: Acknowledged      │  │
│  │ [Archive]                 │  │
│  └───────────────────────────┘  │
│                                 │
│  (scrollable list)              │
│                                 │
└─────────────────────────────────┘

Admin can:
- View all feedback (with timestamp)
- Filter by category
- Mark as "Acknowledged" / "In Progress" / "Done"
- Archive feedback
- View anonymous feedback
- See sentiment (positive/negative/neutral)
```

---

## 10. NOTIFICATIONS CENTER

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│ [<] Notifications       [Mark ✓] │ Back, Title, Mark all read
├─────────────────────────────────┤
│                                 │
│  Apr 30, 2026 - Today           │ Date group header
│                                 │
│  🍽️ New Meal Posted             │ Notification item
│  Today's lunch menu is ready.   │ Notification message
│  Tap to view meal details.      │ Tap action
│  10:30 AM • Read                │ Time + read status
│                                 │
│  ⭐ Student Achievement          │
│  Raj Kumar was recognized!      │
│  Tap to see achievement         │
│  9:15 AM • Read                 │
│                                 │
│  Apr 29, 2026 - Yesterday       │ Date group
│                                 │
│  📢 New Gallery Added           │
│  Check out facility photos      │
│  3:45 PM • Unread               │ Bold if unread
│                                 │
│  🔔 System Notification         │
│  App update available v1.1      │
│  2:20 PM • Read                 │
│                                 │
│  (showing last 50 notifications)│
│                                 │
│  [View All] (if > 50)           │ Link to older notifications
│                                 │
└─────────────────────────────────┘

Tap Action:
- Meal notification → Routes to Meals screen, scrolls to today's meal
- Achievement notification → Routes to Achievements, shows specific achievement
- Gallery notification → Routes to Gallery screen
- Other → Shows details in bottom sheet
```

---

## 11. ADMIN DASHBOARD

```
┌─────────────────────────────────┐
│  Status Bar                     │
├─────────────────────────────────┤
│ Admin Dashboard         [⚙️]    │ H2, Settings
├─────────────────────────────────┤
│                                 │
│  ─── ENGAGEMENT METRICS ────     │ Section header
│                                 │
│  Daily Active Users             │ Metric label
│  1,234 Users Today              │ Large number (H2)
│  +5% from yesterday             │ Trend indicator
│                                 │
│  ┌───────────────────────────┐  │ Chart placeholder
│  │      [LINE CHART]         │  │ 7-day trend
│  │                           │  │
│  └───────────────────────────┘  │
│              lg spacing         │
│                                 │
│  Monthly Active Users           │ Metric
│  8,456 Users This Month         │
│  +12% from last month           │
│                                 │
│              lg spacing         │
│                                 │
│  ─── CONTENT MANAGEMENT ────     │ Section header
│                                 │
│  Total Posts This Month         │ Metric row
│  Meals: 28 | Achievements: 12   │
│                                 │
│  Total Engagement               │ Metric row
│  Views: 4.2K | Shares: 340      │
│  Likes: 2.1K | Ratings: 1.8K    │
│                                 │
│              lg spacing         │
│                                 │
│  ─── QUICK ACTIONS ────          │ Section header
│                                 │
│  ┌───────────────────────────┐  │
│  │ [+ Post Meal Update]      │  │ Button with icon
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [+ Post Achievement]      │  │
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [+ Add Gallery]           │  │
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [View Feedback (47)]      │  │
│  └───────────────────────────┘  │
│                                 │
│  ┌───────────────────────────┐  │
│  │ [User Management]         │  │
│  └───────────────────────────┘  │
│                                 │
│              lg spacing         │
│                                 │
│  ─── RECENT FEEDBACK ────        │ Section header
│                                 │
│  Showing 5 most recent:         │ Subtitle
│                                 │
│  ⭐ "Excellent meal variety"     │ Feedback item
│  Parent - 2 hours ago           │
│  [View All Feedback] →          │
│                                 │
│  (scrollable list)              │
│                                 │
└─────────────────────────────────┘

Admin Only Features:
- Analytics dashboard (visible only to Admin role)
- User management (view registered users, roles)
- Content moderation
- System settings
```

---

## NAVIGATION FLOW DIAGRAM

```
┌──────────┐
│ SPLASH   │
└────┬─────┘
     │
     ├─ [Authenticated?] ───→ YES ──→ ┌───────────┐
     │                                 │   HOME    │
     │                            ┌────┴───────────┴────┐
     └─ NO ──→ ┌──────────┐       │ Nav Bar (5 tabs)    │
               │  LOGIN   │       └────┬───────────┬────┘
               └────┬─────┘            │           │
                    │                  │           │
                    ├─ [Sign Up]       │           │
                    │  (New user)      │           │
                    │                  ▼           ▼
                    │             ┌────────┐  ┌────────┐
                    │             │ MEALS  │  │GALLERY │
                    │             └────────┘  └────────┘
                    │
                    └─ [Forgot Password]
                       (Reset flow)

From HOME:
- Bell icon → NOTIFICATIONS
- Profile icon → PROFILE
- "Send Feedback" button → FEEDBACK
- Card taps → Navigate to detail screens

From MEALS:
- Admin buttons → POST MEAL
- Card tap → MEAL DETAIL
- Rate option → RATING DIALOG

From GALLERY:
- Card tap → GALLERY PAGER (detail)
- Swipe → Next/Previous images

From ACHIEVEMENTS:
- Card tap → ACHIEVEMENT DETAIL
- Share button → SHARE DIALOG

From PROFILE:
- Edit Profile → EDIT PROFILE
- Settings icon → SETTINGS (future)
- Sign Out → LOGOUT (return to Login)

Settings:
- Accessible from Profile > Settings icon
- Language toggle available on all screens
```

---

## RESPONSIVE LAYOUT GUIDE

### Phone (< 480dp width)
- All layouts: Single column, full width
- Navigation: Bottom bar (5 tabs)
- Cards: Full width minus 16dp padding
- Modals: Full height or bottom sheet

### Tablet (> 720dp width)
- Gallery: Grid layout (2-3 columns)
- Cards: Wider, side-by-side where possible
- Navigation: Side navigation or expanded bottom bar (future)
- Modals: Centered dialog, max 560dp width

---

## USER FLOW SUMMARY

1. **Unauthenticated User (Parent):**
   - Splash → Home (read-only) → Explore Meals/Gallery/Achievements → Optional: Send Feedback

2. **Authenticated User (Admin/Teacher):**
   - Splash → Home → Dashboard → Create Content (Meals/Achievements/Galleries)
   - Can also: View Feedback, User Management, Analytics

3. **First-Time User:**
   - Splash → Login → [No Account?] → Sign Up → Email Verification → Home

4. **Returning User:**
   - Splash → Auto-login → Home → Resume prior activity

---

## DESIGN HANDOFF CHECKLIST

- [x] Design system documented
- [x] Screen wireframes created (11 screens)
- [x] Navigation flow defined
- [x] Responsive layouts specified
- [x] Component specifications included
- [ ] Figma file created (next step)
- [ ] Component library set up in Compose
- [ ] Developer handoff and Q&A
