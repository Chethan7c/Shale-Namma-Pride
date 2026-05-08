# Shale-Namma Pride - Complete Project Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Problem Statement](#problem-statement)
3. [Vision & Solution](#vision--solution)
4. [Features Implementation](#features-implementation)
5. [Technical Architecture](#technical-architecture)
6. [Technology Stack](#technology-stack)
7. [Role-Based Access Control](#role-based-access-control)
8. [Database Schema](#database-schema)
9. [Frontend Implementation](#frontend-implementation)
10. [Backend Implementation](#backend-implementation)
11. [Workflows](#workflows)
12. [Setup Instructions](#setup-instructions)
13. [API Reference](#api-reference)
14. [UI/UX Guidelines](#uiux-guidelines)

---

## Project Overview

**Project Name:** Shale-Namma Pride 

**Type:** Android Mobile Application

**Purpose:** A "School Transparency & Pride" portal that showcases daily school life to rebuild trust between government schools and parents.

**Target Audience:**
- Parents 
- Teachers
- Students

---

## Problem Statement

### The Challenge
Many government schools in India have significantly improved their infrastructure:
- Smart classrooms with digital boards
- Modern science and computer labs
- Improved mid-day meal quality
- Better toilet and facility maintenance

**However:** Parents in villages remain unaware of these upgrades, leading them to choose expensive private schools over local government schools.

### The Impact
- Loss of trust in public education system
- Financial burden on low-income families
- Underutilization of government school infrastructure
- Widening educational inequality

---

## Vision & Solution

### Shale-Namma Pride Portal

A mobile application that serves as a **transparency portal** and **community pride platform**:

1. **Daily Life Showcase** - Photos of mid-day meals, lab experiments, sports activities
2. **Transparency Bridge** - Parents see real improvements → trust rebuilt
3. **Community Engagement** - Feedback system, achievements celebration
4. **Language Accessibility** - Kannada/English toggle for local parents

### Success Criteria Met
| Criteria | Status | Implementation |
|----------|--------|--------------|
| One meal update per day | ✅ | Backend enforcement in `MealRepositoryImpl` |
| Feedback stored anonymously | ✅ | Parents ALWAYS anonymous |
| Child-friendly UI | ✅ | Material 3, emojis, warm colors |
| Kannada support | ✅ | Full i18n with AI translation |

---

## Features Implementation

### 1. Meals System
**What it does:**
- Teachers upload daily mid-day meal photos with menu
- Only ONE meal update allowed per day (enforced at backend)
- Previous meals accessible in a scrollable feed
- Full-screen image viewer for detailed viewing

**Key Files:**
- `MealsScreen.kt` - UI for meal display
- `MealViewModel.kt` - State management
- `MealRepositoryImpl.kt` - Backend logic with date enforcement

**Workflow:**
```
Teacher logs in → Clicks "Add Meal" → Uploads photo + enters menu → 
Saves → If meal exists for today, updates it; otherwise creates new
```

**Role Access:**
- Teacher: Can add/update meals
- Parent/Student: Can view only

---

### 2. Gallery System
**What it does:**
- Multi-image upload capability (select multiple photos at once)
- Grid view with 2-column layout
- Full-screen image viewer with swipe gestures
- Delete functionality (Teacher only)

**Key Files:**
- `OtherScreens.kt` - GalleryScreen, GalleryDetailScreen
- `GalleryViewModel.kt` - Image management
- `GalleryRepositoryImpl.kt` - Firebase operations

**Role-Based UI:**
- Teacher: See "+" button (add images) + delete overlays
- Parent/Student: Read-only view (no add/delete buttons)

**Storage:**
- Images uploaded to Cloudinary cloud storage
- URLs stored in Firebase Firestore

---

### 3. Achievements System
**What it does:**
- Celebrate student achievements 
- Like system (one-like-per-user, device-based for parents)
- Edit functionality for teachers

**Key Files:**
- `OtherScreens.kt` - AchievementsScreen
- `AchievementViewModel.kt` - State & operations
- `AchievementRepositoryImpl.kt` - Firebase CRUD

**Like System:**
- Students/Parents: Like achievements (tracked by device ID)
- Teachers: Can like + edit + delete

**Kannada Translation:**
- "Translate All" button translates all achievements to Kannada using Gemini AI
- Auto-translation for title, description, and student name

---

### 4. Feedback System
**What it does:**
- Parents submit feedback anonymously
- AI-powered sentiment analysis (positive/negative/neutral)
- Teachers view all feedback
- Delete inappropriate feedback (Teacher only)

**Sentiment Analysis:**
- Weighted keyword matching
- Negation handling

**Key Files:**
- `FeedbackListScreen.kt` - Feedback display
- `FeedbackViewModel.kt` - Feedback operations
- `SentimentAnalyzer.kt` - AI analysis (keyword-based)

**Workflow:**
```
Parent submits feedback → Analyzed by SentimentAnalyzer → 
Saved anonymously to Firebase → Teachers view in "View Feedback"
```

**Role Access:**
- Parent: Submit feedback (always anonymous)
- Teacher: View + delete feedback

---

### 5. Language Toggle (Kannada/English)
**What it does:**
- Toggle between Kannada and English
- All UI strings translated via `strings.xml` / `strings-kn.xml`
- Dynamic content (announcements, achievements) support Kannada fields:
  - `titleKn`, `descriptionKn` for achievements
  - `titleKn`, `contentKn` for announcements
- AI-powered translation using Gemini API

**Key Files:**
- `LanguageViewModel.kt` - Language preference management
- `GeminiHelper.kt` - AI translation service
- `MainActivity.kt` - Locale application via `attachBaseContext()`

**How it works:**
```
User toggles language in Profile → Saves preference to SharedPreferences → 
Recreates Activity → MainActivity applies locale → 
All screens refresh with new language
```

---

### 6. Real-Time Updates
**What it does:**
- Firebase Firestore real-time listeners
- All users see changes instantly without refresh
- Live updates for meals, gallery, achievements, feedback

**Implementation:**
- Uses `callbackFlow` in repositories
- `SnapshotListener` on Firestore queries
- Automatic UI updates via Kotlin Flows

---

## Technical Architecture

### Architecture Pattern: MVVM + Repository
```
┌──────────────────────────────────────────────────────┐
│                        UI Layer                      │
│       (Composable Functions - Jetpack Compose)       │
└──────────────────┬───────────────────────────────────┘
                    │
                    ▼
┌──────────────────────────────────────────────────────┐
│                   ViewModel Layer                    │
│            (State Management + Coroutines)           │
└──────────────────┬───────────────────────────────────┘
                    │
                    ▼
┌───────────────────────────────────────────────────┐
│                 Repository Layer                  │
│             (Abstracts data sources)              │
└─── ───────────────┬───────────────────────────────┘
                    │
         ┌──────────┴──────────┐
         ▼                     ▼
 ┌──────────────┐    ┌──────────────────┐
 │  Firebase    │    │    Cloudinary    │
 │  Firestore   │    │    (Images)      │
 └──────────────┘    └──────────────────┘
```


## Technology Stack

### Frontend
| Technology | Purpose | Version |
|------------|---------|---------|
| Kotlin | Programming language | 1.9+ |
| Jetpack Compose | UI framework | Latest |
| Material 3 | Design system | Latest |
| Hilt | Dependency injection | Latest |
| Kotlin Coroutines | Async programming | Latest |
| Kotlin Flows | Reactive streams | Latest |

### Backend
| Technology | Purpose | Details |
|------------|---------|---------|
| Firebase Firestore | NoSQL database | Real-time sync |
| Firebase Storage | File storage | (Images via Cloudinary) |
| Cloudinary | Image hosting | URL-based storage |
| Gemini API | AI translation | `gemini-2.5-pro` model |

---

## Role-Based Access Control

### User Roles
| Role | Value | Can Do |
|------|-------|--------|
| **TEACHER** | `TEACHER` | Add meals, upload images, edit achievements, view/delete feedback |
| **STUDENT** | `STUDENT` | View all content, like achievements (once per device) |
| **PARENT** | `PARENT` | View all content, submit anonymous feedback, like achievements (device-based) |

### Role Assignment
Roles assigned via email addresses:
```kotlin
// In UserRoleManager.kt
"teacher@shale.com" → TEACHER role
// Others → STUDENT or PARENT based on registration
```

### UI Visibility Matrix
| Feature | TEACHER | STUDENT | PARENT |
|---------|---------|---------|--------|
| Add Meal | ✅ | ❌ | ❌ |
| View Meal | ✅ | ✅ | ✅ |
| Add Gallery Images | ✅ | ❌ | ❌ |
| Delete Gallery Images | ✅ | ❌ | ❌ |
| Edit Achievement | ✅ | ❌ | ❌ |
| Delete Achievement | ✅ | ❌ | ❌ |
| Like Achievement | ✅ | ✅ | ✅ |
| Submit Feedback | ❌ | ❌ | ✅ |
| View Feedback List | ✅ | ❌ | ❌ |
| Delete Feedback | ✅ | ❌ | ❌ |
| Kannada Toggle | ✅ | ✅ | ✅ |

---

## Database Schema

### Firestore Collections

#### 1. `meals` Collection
```kotlin
data class Meal(
    val id: String = "",           // Firestore document ID
    val title: String = "",         // e.g., "Mid-Day Meal"
    val description: String = "",  // Menu details
    val imageUrl: String = "",    // Photo URL
    val date: Date = Date(),       // Meal date
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
```

#### 2. `galleries` Collection
```kotlin
data class Gallery(
    val id: String = "",
    val title: String = "",         // e.g., "Science Lab"
    val category: String = "",      // LABS/LIBRARY/TOILETS/SPORTS
    val images: List<GalleryImage> = emptyList(),
    val date: Date = Date()
)

data class GalleryImage(
    val url: String = "",          // Cloudinary URL
    val uploadedAt: Date = Date()
)
```

#### 3. `achievements` Collection
```kotlin
data class Achievement(
    val id: String = "",
    val studentName: String = "",
    val studentNameKn: String = "",   // Kannada translation
    val title: String = "",           // e.g., "Science Fair Winner"
    val titleKn: String = "",         // Kannada translation
    val description: String = "",
    val descriptionKn: String = "",     // Kannada translation
    val category: Category = Category.ACADEMIC,
    val imageUrl: String = "",
    val likes: Int = 0,
    val likedBy: List<String> = emptyList(),  // Device IDs
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
```

#### 4. `feedbacks` Collection
```kotlin
data class Feedback(
    val id: String = "",
    val message: String = "",
    val category: String = "",      // MEAL_ISSUE/FACILITY/TEACHING/SAFETY/OTHER
    val sentiment: String = "",    // POSITIVE/NEGATIVE/NEUTRAL
    val urgency: String = "",      // URGENT/NORMAL
    val summary: String = "",      // AI-generated summary
    val isAnonymous: Boolean = true,
    val createdAt: Date = Date()
)
```

#### 5. `announcements` Collection
```kotlin
data class Announcement(
    val id: String = "",
    val title: String = "",
    val titleKn: String = "",        // Kannada translation
    val content: String = "",
    val contentKn: String = "",      // Kannada translation
    val createdAt: Date = Date()
)
```

---

## Frontend Implementation

### UI Framework: Jetpack Compose
- **Declarative UI** - Kotlin-based UI code
- **State-driven** - UI reacts to state changes
- **Material 3** - Modern Android design system

### Key Screens

#### 1. HomeScreen.kt
- App entry point after login
- Shows quick access cards for: Meals, Gallery, Achievements, Feedback
- Kannada content support

#### 2. MealsScreen.kt
- Displays today's meal prominently
- Previous meals in LazyColumn
- Full-screen image viewer
- Add meal FAB for teachers

#### 3. GalleryScreen.kt (in OtherScreens.kt)
- 2-column grid layout
- Delete overlay (Teacher only)
- Add images button (Teacher only)
- Navigates to GalleryDetailScreen

#### 4. GalleryDetailScreen.kt
- Full-screen image view
- Swipe left/right to navigate
- Delete button in top bar (Teacher only)

#### 5. AchievementsScreen.kt
- List of achievements with like counts
- Edit button 
- Translate All button (Kannada mode)
- Like button with device-based tracking

#### 6. FeedbackListScreen.kt
- List of all feedback with sentiment badges
- Delete button (Teacher only)
- Confirmation dialogs

#### 7. ProfileScreens.kt
- Language toggle (Kannada/English)
- Role-based button placement:
  - Parent: "Send Feedback" ONLY
  - Teacher: "View Feedback" ONLY

### Theme & Colors
```kotlin
// Primary Colors
Colors.TrustBlue      // #1E88E5 - Trust, reliability
Colors.SuccessGreen    // #4CAF50 - Positive actions
Colors.ErrorRed       // #F44336 - Delete, errors
Colors.WarningOrange   // #FF9800 - Warnings
Colors.MediumGray     // #9E9E9E - Secondary text
Colors.White          // #FFFFFF - Backgrounds
```

---

## Backend Implementation

### Firebase Firestore
**Real-time Database:**
- NoSQL document database
- Real-time listeners via `SnapshotListener`
- Automatic synchronization across devices

### Cloudinary Image Storage
**Why Cloudinary over Firebase Storage:**
- Easier URL-based access
- Automatic image optimization
- Direct upload from Android

**Upload Flow:**
```
User selects image → Copies to cache → Uploads to Cloudinary → 
Gets URL → Saves URL to Firestore → Deletes temp file
```

### AI Translation: Gemini API
**Implementation:** `GeminiHelper.kt`
- Model: `gemini-2.5-pro` (better translation)
- Prompt: "Translate this text to Kannada (meaning-based translation). Output ONLY in Kannada script, no English letters allowed"
- Retry logic: Up to 3 attempts with `hasEnglish()` check

---

## Workflows

### Workflow 1: Teacher Adds Daily Meal
```
1. Teacher logs in (role = TEACHER)
2. Navigates to Meals tab
3. Clicks "Add Meal" button
4. Enters menu 
5. Selects meal photo from gallery
6. Clicks "Upload"
7. ViewModel uploads image to Cloudinary
8. Checks if meal exists for today (Firestore query)
   - If YES: Updates existing meal document
   - If NO: Creates new meal document
9. UI updates automatically (real-time listener)
```

### Workflow 2: Parent Submits Feedback
```
1. Parent logs in (role = PARENT)
2. Navigates to Profile → "Send Feedback"
3. Types feedback message (max 500 chars)
4. Clicks "Submit Feedback"
5. FeedbackViewModel calls SentimentAnalyzer.analyzeSentiment()
   - Checks positive/negative keywords with weights
   - Handles negation ("not good", "not bad")
   - Detects urgency (urgent/normal)
6. Feedback saved to Firestore with:
   - sentiment = "POSITIVE"/"NEGATIVE"/"NEUTRAL"
   - isAnonymous = true
   - userId = "" (never stored)
7. Success message shown → Navigates back
```

### Workflow 3: Teacher Edits Achievement 
```
1. Teacher logs in (role = TEACHER)
2. Navigates to Achievements tab
3. Sees "Edit Achievement" button ON TOP (before student name)
4. Clicks edit → Dialog appears with:
   - Student Name field
   - Achievement Title field
5. Modifies both fields
6. Clicks "Update"
7. ViewModel calls updateAchievement()
8. Achievement updated in Firestore
9. UI refreshes via real-time listener
```

### Workflow 4: Language Toggle
```
1. User navigates to Profile screen
2. Toggles "ಕನ್ನಡ" / "English" switch
3. Preference saved to SharedPreferences
4. Activity recreated via `recreate()`
5. MainActivity.attachBaseContext() applies saved local
6. All screens refresh:
   - Static strings: Loaded from strings-kn.xml / strings.xml
   - Dynamic content: Displays Kn fields if available
```

---

## Setup Instructions

### Prerequisites
- Android Studio (latest version)
- JDK 17+
- Firebase account
- Cloudinary account
- Gemini API key

### Step 1: Clone/Open Project
```bash
# Project location
C:\Users\Chethan\OneDrive\Desktop\Shale-Namma Pride
```

### Step 2: Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create project "Shale-Namma Pride"
3. Add Android app with package `com.shalenamma.pride`
4. Download `google-services.json`
5. Place in `app/` directory
6. Enable Firestore Database
7. Enable Firebase Authentication (if needed)

### Step 3: Cloudinary Setup
1. Sign up at [Cloudinary](https://cloudinary.com)
2. Get credentials:
   - Cloud name
   - API Key
   - API Secret
3. Add to `CloudinaryService.kt`:
```kotlin
private const val CLOUD_NAME = "your_cloud_name"
private const val API_KEY = "your_api_key"
private const val API_SECRET = "your_api_secret"
```

### Step 4: Gemini API Setup
1. Get API key from [Google AI Studio](https://ai.google.dev/)
2. Add to `GeminiHelper.kt`:
```kotlin
private const val API_KEY = "your_gemini_api_key"
```

### Step 5: Build & Run
```bash
# Clean build
.\gradlew.bat clean

# Install on device
.\gradlew.bat installDebug

# Or build APK
.\gradlew.bat assembleDebug
```

### Step 6: Test Roles
Set up test accounts:
- `teacher@shale.com` → Teacher role
- Any other email → Student/Parent role

---

---

## UI/UX Guidelines

### Design Principles
1. **Child-Friendly** - Warm colors, rounded corners, emojis
2. **Accessible** - Kannada/English toggle
3. **Role-Appropriate** - Users only see what they need
4. **Real-Time** - Instant updates without refresh

### Emojis Used
- 👤 (Profile)
- 👨‍🏫 (Teacher)
- 👨‍👩‍👧 (Parent/Child)
- 🎓 (Achievement)
- 🍱 (Meals)
- 🖼️ (Gallery)
- 💬 (Feedback)

### Confirmation Dialogs
All destructive actions use confirmation dialogs:
- Delete feedback: "Are you sure you want to delete this feedback?"
- Delete image: "Are you sure you want to delete this image?"
- Delete achievement: "Are you sure you want to delete this achievement?"

### Loading States
- `CircularProgressIndicator` for async operations
- Disabled buttons during loading
- Success messages after operations

---

## Appendix: File Structure

```
app/src/main/java/com/shalenamma/pride/
├── ShaleNammaPrideApp.kt          # Application class
├── MainActivity.kt                   # Entry point + locale
│
├── ai/
│   └── GeminiHelper.kt             # AI translation
│   └── SentimentAnalyzer.kt        # Feedback sentiment analysis
│
├── data/
│   ├── models/                     # Data classes
│   │   ├── Meal.kt
│   │   ├── Gallery.kt
│   │   ├── GalleryImage.kt
│   │   ├── Achievement.kt
│   │   ├── Feedback.kt
│   │   └── Announcement.kt
│   │
│   └── repository/
│       ├── MealRepository.kt
│       ├── GalleryRepository.kt
│       ├── AchievementRepository.kt
│       ├── FeedbackRepository.kt
│       └── impl/
│           ├── MealRepositoryImpl.kt
│           ├── GalleryRepositoryImpl.kt
│           ├── AchievementRepositoryImpl.kt
│           └── FeedbackRepositoryImpl.kt
│
├── ui/
│   ├── theme/
│   │   ├── Theme.kt
│   │   ├── Colors.kt
│   │   ├── Spacing.kt
│   │   └── DesignTokens.kt
│   │
│   ├── components/
│   │   └── ShaleNammaComponents.kt  # Reusable UI components
│   │
│   ├── screens/
│   │   ├── HomeScreen.kt
│   │   ├── MealsScreen.kt
│   │   ├── OtherScreens.kt        # Gallery + Achievements + Feedback
│   │   ├── FeedbackListScreen.kt
│   │   ├── ProfileScreens.kt
│   │   ├── LoginScreen.kt
│   │   ├── SignupScreen.kt
│   │   └── SplashScreen.kt
│   │
│   └── viewmodels/
│       ├── MealViewModel.kt
│       ├── GalleryViewModel.kt
│       ├── AchievementViewModel.kt
│       ├── FeedbackViewModel.kt
│       ├── LanguageViewModel.kt
│       └── UserSessionViewModel.kt
│
└── utils/
    ├── UserRoleManager.kt
    └── DeviceIdManager.kt
```

---

## Project Status: ✅ COMPLETE

**All features implemented and tested:**
- ✅ Meals system with rotation
- ✅ Gallery with role-based UI (Teacher/Parent only)
- ✅ Achievements with conditional edit (Teacher only)
- ✅ Feedback with sentiment analysis (Parent submits, Teacher manages)
- ✅ Kannada/English toggle
- ✅ Real-time Firebase updates
- ✅ Material 3 UI
- ✅ Build successful
