# Shale-Namma Pride - Developer Handoff & Implementation Guide

## Document Overview

This document provides developers with all necessary information to implement Shale-Namma Pride following the finalized UI/UX design system.

---

## TABLE OF CONTENTS

1. Design System Overview
2. Component Library Usage
3. File Structure & Organization
4. Implementation Checklist
5. Common Implementation Patterns
6. Troubleshooting & FAQs
7. Design Review Process
8. Design System Updates

---

## 1. DESIGN SYSTEM OVERVIEW

### What You're Building With

The Shale-Namma Pride design system is built on:
- **Material Design 3** — Latest Material Design standard
- **Jetpack Compose** — Modern Android declarative UI framework
- **8dp Grid System** — Consistent spacing and sizing
- **Trust Blue (#1F5AA0)** — Primary brand color
- **Roboto Font** — System default with full Kannada support

### Key Design Principles

| Principle | What It Means | How to Apply |
|-----------|--------------|-------------|
| **Accessibility First** | Design for rural parents with low digital literacy | Use 14sp+ font sizes, 48dp+ touch targets, high contrast |
| **Max 3 Taps** | Any feature should be reachable in 3 taps | Deep linking + clear navigation hierarchy |
| **Offline Resilient** | Works with slow/no internet | Cache content, graceful error states |
| **Bilingual Native** | Kannada/English toggle everywhere | All text must be translatable, no text in images |
| **Trust Building** | Design conveys authority & care | Use professional colors, consistent branding |

---

## 2. COMPONENT LIBRARY USAGE

### Available Components

All components are in `ShaleNammaComponents.kt` and ready to import.

#### Button Components

```kotlin
// Primary Button (most common)
PrimaryButton(
    text = "Sign In",
    onClick = { /* handle click */ },
    fullWidth = true,
    isLoading = false,
    enabled = true
)

// Secondary Button (outline style)
SecondaryButton(
    text = "Cancel",
    onClick = { /* handle click */ }
)

// Text Button (minimal style)
TextButtonComponent(
    text = "Forgot Password?",
    onClick = { /* handle click */ }
)
```

#### Input Fields

```kotlin
// Email input
EmailInputField(
    value = emailValue,
    onValueChange = { emailValue = it },
    isError = emailError != null,
    errorMessage = emailError
)

// Password input (with visibility toggle)
PasswordInputField(
    value = passwordValue,
    onValueChange = { passwordValue = it },
    isError = passwordError != null,
    errorMessage = passwordError
)

// Generic text field
ShaleNammaTextField(
    value = text,
    onValueChange = { text = it },
    label = "Full Name",
    placeholder = "Enter your full name",
    keyboardType = KeyboardType.Text,
    maxLines = 1
)

// Multi-line text field (feedback form)
ShaleNammaTextField(
    value = feedbackText,
    onValueChange = { feedbackText = it },
    label = "Your Message",
    maxLines = 5,
    minLines = 3,
    singleLine = false
)
```

#### Card Components

```kotlin
// Basic card (clickable)
ShaleNammaCard(
    modifier = Modifier.padding(Spacing.md),
    onClick = { navigateToDetail() }
) {
    Text("Card Content")
}

// Info card (with icon + text)
InfoCard(
    iconText = "📅",
    title = "Today's Updates",
    subtitle = "Meal Update Status",
    onClick = { /* navigate */ }
)

// Image list item (for galleries, meals)
ImageListItem(
    imageUrl = "https://...",
    title = "Science Lab",
    subtitle = "12 photos",
    trailingText = "Updated Apr 28"
)
```

#### Badge & Status Components

```kotlin
// Badge (for category labels)
ShaleNammaBadge(
    text = "STUDENT OF THE WEEK",
    backgroundColor = Colors.SuccessGreen
)

// Rating bar (interactive)
RatingBar(
    rating = 4.5f,
    maxRating = 5,
    onRatingChanged = { newRating -> /* save */ }
)

// Rating bar (read-only)
RatingBar(
    rating = 4.2f,
    readOnly = true
)
```

#### Layout Components

```kotlin
// Section header with divider
SectionHeader(text = "Previous Meals")

// Divider
ShaleNammaDivider(
    modifier = Modifier.padding(horizontal = Spacing.md)
)

// Loading indicator
ShaleNammaLoadingIndicator()

// Empty state
EmptyStateCard(
    iconText = "📭",
    title = "No Meals Posted",
    subtitle = "Check back soon for updates",
    actionText = "Post First Meal",
    onActionClick = { navigateToPostMeal() }
)
```

#### Utility Components

```kotlin
// Checkbox with label
CheckboxWithLabel(
    checked = isAnonymous,
    onCheckedChange = { isAnonymous = it },
    label = "Submit Anonymously"
)
```

---

## 3. FILE STRUCTURE & ORGANIZATION

### Recommended Android Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/shalenamma/pride/
│   │   │   ├── ui/
│   │   │   │   ├── theme/
│   │   │   │   │   ├── DesignTokens.kt      # Color, Typography, Spacing
│   │   │   │   │   ├── Theme.kt             # Material 3 Theme setup
│   │   │   │   │   └── Color.kt             # Color definitions
│   │   │   │   ├── components/
│   │   │   │   │   ├── ShaleNammaComponents.kt  # All reusable components
│   │   │   │   │   ├── Buttons.kt           # Button variants (optional)
│   │   │   │   │   ├── TextFields.kt        # Input components (optional)
│   │   │   │   │   └── Cards.kt             # Card variants (optional)
│   │   │   │   └── screens/
│   │   │   │       ├── auth/
│   │   │   │       │   ├── LoginScreen.kt
│   │   │   │       │   └── SignupScreen.kt
│   │   │   │       ├── home/
│   │   │   │       │   └── HomeScreen.kt
│   │   │   │       ├── meals/
│   │   │   │       │   ├── MealsScreen.kt
│   │   │   │       │   ├── MealDetailScreen.kt
│   │   │   │       │   └── PostMealScreen.kt
│   │   │   │       ├── gallery/
│   │   │   │       │   ├── GalleryScreen.kt
│   │   │   │       │   └── GalleryDetailScreen.kt
│   │   │   │       ├── achievements/
│   │   │   │       │   └── AchievementsScreen.kt
│   │   │   │       ├── profile/
│   │   │   │       │   └── ProfileScreen.kt
│   │   │   │       └── feedback/
│   │   │   │           └── FeedbackScreen.kt
│   │   │   ├── viewmodel/
│   │   │   │   ├── AuthViewModel.kt
│   │   │   │   ├── MealsViewModel.kt
│   │   │   │   ├── GalleryViewModel.kt
│   │   │   │   ├── AchievementsViewModel.kt
│   │   │   │   └── FeedbackViewModel.kt
│   │   │   ├── repository/
│   │   │   │   ├── AuthRepository.kt
│   │   │   │   ├── MealsRepository.kt
│   │   │   │   └── [other repositories]
│   │   │   ├── model/
│   │   │   │   ├── User.kt
│   │   │   │   ├── Meal.kt
│   │   │   │   ├── Achievement.kt
│   │   │   │   ├── Feedback.kt
│   │   │   │   └── Gallery.kt
│   │   │   ├── MainActivity.kt
│   │   │   ├── App.kt (for Hilt)
│   │   │   └── navigation/
│   │   │       └── NavGraph.kt
│   │   └── res/
│   │       ├── drawable/
│   │       ├── values/
│   │       └── strings.xml    # All UI strings (for i18n)
│   └── test/
│       └── [unit tests]
└── build.gradle
```

### Important Naming Conventions

- **Screens:** `*Screen.kt` (e.g., `LoginScreen.kt`, `HomeScreen.kt`)
- **ViewModels:** `*ViewModel.kt` (e.g., `AuthViewModel.kt`)
- **Components:** Composable function names = PascalCase (e.g., `PrimaryButton`, `ShaleNammaCard`)
- **Resources:** lowercase_with_underscore (e.g., `ic_home`, `btn_primary`)

---

## 4. IMPLEMENTATION CHECKLIST

### Phase 1: Setup & Foundation (Days 1-5)

- [ ] Create Android project with Kotlin + Compose
- [ ] Set up Gradle dependencies (Compose, Firebase, Hilt)
- [ ] Copy `DesignTokens.kt` to `ui/theme/`
- [ ] Copy `ShaleNammaComponents.kt` to `ui/components/`
- [ ] Setup Material 3 Theme in `MainActivity`
- [ ] Create basic app navigation structure
- [ ] Set up Firebase project & credentials
- [ ] Create Firestore database schema

**Deliverable:** App can boot, Material 3 theme renders correctly

### Phase 2: Core Features (Days 6-14)

- [ ] **Authentication (Days 6-8)**
  - [ ] Login screen with validation
  - [ ] Signup screen (Admin/Teacher only)
  - [ ] Firebase Auth integration
  - [ ] Session management (SharedPreferences)
  - [ ] "Remember Me" functionality

- [ ] **Meals Feature (Days 8-9)**
  - [ ] Meals list screen with empty state
  - [ ] Post meal dialog (Admin only)
  - [ ] Meal detail view
  - [ ] Meal rating functionality
  - [ ] Past meals history (30-day window)

- [ ] **Gallery Feature (Days 9-11)**
  - [ ] Gallery list screen
  - [ ] Gallery detail pager view
  - [ ] Image sharing (WhatsApp, Facebook)
  - [ ] Pinch-to-zoom functionality
  - [ ] Download image functionality

- [ ] **Achievements (Days 11-12)**
  - [ ] Achievements list with filters
  - [ ] Post achievement dialog (Admin only)
  - [ ] Achievement detail view
  - [ ] Like/Share functionality

- [ ] **Push Notifications (Days 12-14)**
  - [ ] FCM setup
  - [ ] Notification handling (deep linking)
  - [ ] Notification history
  - [ ] Notification preferences

**Deliverable:** All core features functional, Firebase data sync working

### Phase 3: Polish & Launch (Days 15-20)

- [ ] **Bilingual Support (Days 15-16)**
  - [ ] Kannada string resources
  - [ ] Language toggle implementation
  - [ ] Persistence of language preference
  - [ ] Special character rendering

- [ ] **Feedback System (Days 16-17)**
  - [ ] Feedback form with categories
  - [ ] Anonymous submission toggle
  - [ ] Feedback submission to Firebase
  - [ ] Admin feedback dashboard

- [ ] **Admin Dashboard (Days 17-18)**
  - [ ] Analytics overview
  - [ ] Content management
  - [ ] User management
  - [ ] Feedback analytics

- [ ] **Quality Assurance (Days 18-20)**
  - [ ] Unit tests (> 80% coverage)
  - [ ] Integration tests
  - [ ] Manual QA testing
  - [ ] Bug fixes
  - [ ] Performance optimization

**Deliverable:** App ready for Play Store release

---

## 5. COMMON IMPLEMENTATION PATTERNS

### Pattern 1: Login Screen

```kotlin
@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToSignup: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val rememberMe = remember { mutableStateOf(false) }
    val loginState = viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.md),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Shale-Namma Pride",
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.lg)
        )

        EmailInputField(
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            isError = loginState.value is LoginState.Error
        )

        PasswordInputField(
            value = passwordValue.value,
            onValueChange = { passwordValue.value = it },
            isError = loginState.value is LoginState.Error,
            modifier = Modifier.padding(top = Spacing.md)
        )

        CheckboxWithLabel(
            checked = rememberMe.value,
            onCheckedChange = { rememberMe.value = it },
            label = "Remember Me",
            modifier = Modifier.padding(top = Spacing.md)
        )

        PrimaryButton(
            text = "Sign In",
            onClick = {
                viewModel.login(
                    email = emailValue.value,
                    password = passwordValue.value,
                    rememberMe = rememberMe.value
                )
            },
            isLoading = loginState.value is LoginState.Loading,
            modifier = Modifier.padding(top = Spacing.lg)
        )

        TextButtonComponent(
            text = "Don't have an account? Sign Up",
            onClick = onNavigateToSignup,
            modifier = Modifier.padding(top = Spacing.md)
        )

        // Handle navigation on success
        LaunchedEffect(loginState.value) {
            if (loginState.value is LoginState.Success) {
                onNavigateToHome()
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    class Success(val user: User) : LoginState()
    class Error(val message: String) : LoginState()
}
```

### Pattern 2: List Screen with Empty State

```kotlin
@Composable
fun MealsScreen(
    viewModel: MealsViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val meals = viewModel.meals.collectAsState(initial = emptyList())
    val isLoading = viewModel.isLoading.collectAsState(initial = false)

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ShaleNammaLoadingIndicator()
        }
        return
    }

    if (meals.value.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EmptyStateCard(
                iconText = "🍽️",
                title = "No Meals Posted",
                subtitle = "Check back soon for updates",
                actionText = "Post First Meal",
                onActionClick = { /* admin action */ }
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        items(meals.value) { meal ->
            ImageListItem(
                imageUrl = meal.imageUrl,
                title = meal.title,
                subtitle = "${meal.timestamp} • ⭐${meal.rating}",
                onClick = { onNavigateToDetail(meal.id) }
            )
        }
    }
}
```

### Pattern 3: Form with Validation

```kotlin
@Composable
fun FeedbackScreen(
    viewModel: FeedbackViewModel = hiltViewModel()
) {
    val feedbackText = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("Facility") }
    val isAnonymous = remember { mutableStateOf(false) }
    val errors = remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    val submitState = viewModel.submitState.collectAsState()

    fun validateForm(): Boolean {
        val newErrors = mutableMapOf<String, String>()
        if (feedbackText.value.length < 10) {
            newErrors["feedback"] = "Minimum 10 characters required"
        }
        if (feedbackText.value.length > 500) {
            newErrors["feedback"] = "Maximum 500 characters allowed"
        }
        errors.value = newErrors
        return newErrors.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text("Share Your Feedback", style = MaterialTheme.typography.displaySmall)

        ShaleNammaTextField(
            value = feedbackText.value,
            onValueChange = { feedbackText.value = it.take(500) },
            label = "Your Message",
            maxLines = 5,
            minLines = 3,
            singleLine = false,
            isError = errors.value.containsKey("feedback"),
            errorMessage = errors.value["feedback"]
        )

        Text(
            text = "${feedbackText.value.length}/500",
            style = MaterialTheme.typography.labelSmall,
            color = Colors.MediumGray
        )

        CheckboxWithLabel(
            checked = isAnonymous.value,
            onCheckedChange = { isAnonymous.value = it },
            label = "Submit Anonymously"
        )

        PrimaryButton(
            text = "Submit Feedback",
            onClick = {
                if (validateForm()) {
                    viewModel.submitFeedback(
                        text = feedbackText.value,
                        category = category.value,
                        isAnonymous = isAnonymous.value
                    )
                }
            },
            isLoading = submitState.value is SubmitState.Loading
        )

        LaunchedEffect(submitState.value) {
            if (submitState.value is SubmitState.Success) {
                // Show toast or navigate back
            }
        }
    }
}
```

---

## 6. TROUBLESHOOTING & FAQs

### Q: How do I use the design tokens in my screens?

**A:** Import the color and spacing constants:

```kotlin
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.Spacing

// Use in your composables
Box(
    modifier = Modifier
        .background(Colors.TrustBlue)
        .padding(Spacing.md)
)
```

### Q: My text doesn't look like the design. What's wrong?

**A:** Use Material 3 typography styles:

```kotlin
// ✅ Correct
Text(
    text = "Headline",
    style = MaterialTheme.typography.displayMedium  // 28sp, Bold
)

// ❌ Wrong
Text(
    text = "Headline",
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold
)
```

### Q: How do I make a component full-width?

**A:** Use `Modifier.fillMaxWidth()`:

```kotlin
PrimaryButton(
    text = "Submit",
    onClick = { /* */ },
    modifier = Modifier.fillMaxWidth()  // or use fullWidth param
)
```

### Q: I need to add custom padding. What value should I use?

**A:** Use the spacing scale (`Spacing.xs`, `Spacing.sm`, `Spacing.md`, etc.):

```kotlin
Box(modifier = Modifier.padding(Spacing.md))  // 16dp
Button(modifier = Modifier.padding(Spacing.lg)) // 24dp
```

### Q: How do I handle dark mode?

**A:** Dark mode is not in v1.0 scope. The theme automatically supports it once implemented:

```kotlin
ShaleNammaPrideTheme(
    darkTheme = isSystemInDarkTheme(),  // Will work once colors are added
    content = { /* */ }
)
```

### Q: How do I add the Material 3 theme to MainActivity?

**A:** Wrap your app with `ShaleNammaPrideTheme`:

```kotlin
@Composable
fun App() {
    ShaleNammaPrideTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Your app content
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
```

### Q: My buttons are too small/large. How do I adjust?

**A:** Use the `modifier` parameter. Button height is fixed at 48dp per design:

```kotlin
PrimaryButton(
    text = "Click me",
    onClick = { /* */ },
    modifier = Modifier
        .width(200.dp)  // Adjust width if needed
        .padding(Spacing.md)
)
```

### Q: How do I implement the language toggle?

**A:** Use Android's `LocalConfiguration`:

```kotlin
val currentLanguage = remember { 
    mutableStateOf(Locale.getDefault().language) 
}

fun switchLanguage(languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    // Recreate activity or use LocalizationViewModel
}
```

### Q: I need to show an error message. What color should I use?

**A:** Use `Colors.ErrorRed` (#F44336):

```kotlin
if (isError) {
    Text(
        text = "Invalid email",
        color = Colors.ErrorRed,
        style = MaterialTheme.typography.labelSmall
    )
}
```

### Q: How do I implement image caching?

**A:** Use Coil library (recommended for Compose):

```kotlin
// Add to build.gradle
implementation("io.coil-kt:coil-compose:2.4.0")

// Use in Composable
AsyncImage(
    model = imageUrl,
    contentDescription = "Meal image",
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
    contentScale = ContentScale.Crop
)
```

### Q: My screen doesn't look right on tablets. What should I do?

**A:** Adapt layouts for different screen sizes using `BoxWithConstraints`:

```kotlin
@Composable
fun ResponsiveLayout() {
    BoxWithConstraints {
        if (maxWidth < 480.dp) {
            // Phone layout
        } else if (maxWidth < 720.dp) {
            // Tablet layout
        } else {
            // Large tablet layout
        }
    }
}
```

---

## 7. DESIGN REVIEW PROCESS

### Before Submitting a Screen for Review

1. **Visual Checklist**
   - [ ] All text uses Material 3 typography styles
   - [ ] All spacing uses `Spacing.*` constants
   - [ ] All colors use `Colors.*` constants
   - [ ] Touch targets are minimum 48dp
   - [ ] Contrast ratio is 4.5:1 for text
   - [ ] Empty states are implemented
   - [ ] Loading states are handled
   - [ ] Error states are handled

2. **Functionality Checklist**
   - [ ] Navigation works correctly
   - [ ] All buttons are functional
   - [ ] Form validation works
   - [ ] Data is loaded from Firebase
   - [ ] No console errors or warnings
   - [ ] Back button works correctly

3. **Accessibility Checklist**
   - [ ] All interactive elements have 48dp+ touch targets
   - [ ] Text is readable (minimum 14sp)
   - [ ] Colors have sufficient contrast
   - [ ] Touch feedback is visible (ripple effect)
   - [ ] All UI can be operated without images alone

### Design Review Approval Criteria

Once your screen is ready, have it reviewed by:

1. **Designer** — Verify visual accuracy to wireframes
2. **Tech Lead** — Verify code quality and performance
3. **QA** — Test on multiple devices and Android versions

---

## 8. DESIGN SYSTEM UPDATES

### How to Request a Design System Update

If you need to:
- Add a new component
- Modify a color or typography
- Change spacing values
- Add accessibility features

**Steps:**

1. Create an issue in GitHub with:
   - Justification (why the change is needed)
   - Design mockup or screenshot
   - Impact analysis (which screens are affected)

2. Discuss with design & engineering leads

3. Update:
   - `DESIGN_SYSTEM.md` (this doc)
   - `DesignTokens.kt` (code)
   - `ShaleNammaComponents.kt` (if new component)

4. Document the change:
   - Add to "Design System Updates" section
   - Notify developers via PR comment

### Version History

| Date | Version | Change | Approved By |
|------|---------|--------|------------|
| Apr 25, 2026 | 1.0 | Initial design system | Design Lead |
| [Date] | 1.1 | [Update description] | [Name] |

---

## 9. QUICK REFERENCE: COLOR USAGE

| Component | Color | Hex |
|-----------|-------|-----|
| Primary buttons | TrustBlue | #1F5AA0 |
| Secondary buttons | TrustBlue (outline) | #1F5AA0 |
| Success badges | SuccessGreen | #4CAF50 |
| Warning badges | WarningOrange | #FF9800 |
| Error states | ErrorRed | #F44336 |
| Text (primary) | DarkCharcoal | #212121 |
| Text (secondary) | MediumGray | #9E9E9E |
| Dividers | LightGrayBorder | #E0E0E0 |
| Backgrounds | White/LightGray | #FFFFFF / #F5F5F5 |
| Disabled state | MediumGray | #9E9E9E |

---

## 10. QUICK REFERENCE: SPACING USAGE

| Component | Spacing | Size |
|-----------|---------|------|
| Icon spacing | xs | 4dp |
| Component padding | sm | 8dp |
| Card padding | md | 16dp |
| Section gaps | lg | 24dp |
| Screen padding | md | 16dp |
| Major sections | xl | 32dp |
| Large layouts | xxl | 48dp |

---

## 11. QUICK REFERENCE: RESPONSIVE BREAKPOINTS

| Screen Size | Width | Layout |
|------------|-------|--------|
| Small Phone | < 480dp | Single column, full-width cards |
| Phone | 480-720dp | Single column with margins |
| Tablet | > 720dp | Multi-column grid layout |

---

## 12. FIREBASE DATA SCHEMA (Quick Reference)

### Collections

```
users/
├── {uid}/
│   ├── email: string
│   ├── name: string
│   ├── role: "admin" | "teacher" | "parent"
│   ├── school: string
│   ├── createdAt: timestamp
│   └── language: "en" | "kn"

meals/
├── {mealId}/
│   ├── title: string
│   ├── menu: string[]
│   ├── imageUrl: string
│   ├── createdBy: string (uid)
│   ├── createdAt: timestamp
│   ├── rating: number (avg)
│   └── ratings: map<uid, number>

achievements/
├── {achievementId}/
│   ├── studentName: string
│   ├── studentClass: string
│   ├── title: string
│   ├── description: string
│   ├── imageUrl: string
│   ├── category: string
│   ├── createdAt: timestamp
│   └── likes: map<uid, boolean>

feedback/
├── {feedbackId}/
│   ├── text: string
│   ├── category: string
│   ├── submittedBy: string (uid or "anonymous")
│   ├── submittedAt: timestamp
│   ├── isAnonymous: boolean
│   └── status: "new" | "acknowledged" | "done"

galleries/
├── {galleryId}/
│   ├── name: string
│   ├── description: string
│   ├── images: image[]
│   │   ├── url: string
│   │   ├── caption: string
│   │   └── uploadedAt: timestamp
│   └── createdAt: timestamp
```

---

## Next Steps for Developers

1. ✅ Review this entire document
2. ✅ Set up Android project with Compose
3. ✅ Copy `DesignTokens.kt` and `ShaleNammaComponents.kt`
4. ✅ Follow implementation checklist
5. ✅ Use component library for all UI
6. ✅ Request design review before merging PRs

---

**Questions?** Reach out to the design lead or create an issue in the repository.

**Document Updated:** April 25, 2026  
**Version:** 1.0  
**Status:** Final for Development
