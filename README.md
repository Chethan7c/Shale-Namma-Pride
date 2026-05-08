# Shale-Namma Pride 

A "School Transparency & Pride" Android app that showcases daily school life to rebuild trust between government schools and parents.

[![Download APK](https://img.shields.io/badge/Download-APK-green?style=for-the-badge&logo=android)](https://github.com/CHETHANBABUBC/Shale-Namma-Pride/releases/latest)

> **Download the app:** Visit the [Releases](https://github.com/CHETHANBABUBC/Shale-Namma-Pride/releases/latest) page to download the latest APK.

---

## Features

| Feature | Description |
|---------|-------------|
| 🍱 **Meals System** | Daily mid-day meal photos with menu, one-per-day enforcement |
| 🖼️ **Gallery** | Multi-image uploads, categorized views (Labs, Library, Sports) |
| 🎓 **Achievements** | Student achievements with like system and Kannada translation |
| 💬 **Feedback** | Anonymous parent feedback with AI sentiment analysis |
| 🌐 **Kannada/English** | Full language toggle with AI-powered translation |
| ⚡ **Real-Time** | Firebase Firestore live updates across all screens |

---

## Screenshots

| Home | Meals | Gallery | Achievements |
|-------|-------|---------|--------------|
| *Home screen* | *Daily meals* | *Photo gallery* | *Student stars* |

---

## Tech Stack

**Frontend:**
- Kotlin + Jetpack Compose + Material 3
- Hilt (Dependency Injection)
- Kotlin Coroutines & Flows

**Backend:**
- Firebase Firestore (Real-time DB)
- Cloudinary (Image hosting)
- Gemini API (AI translation)

---

## Quick Setup

### Prerequisites
- Android Studio (latest)
- JDK 17+
- Firebase account
- Cloudinary account
- Gemini API key

### Steps
1. Clone the repo
2. Add `google-services.json` to `app/` directory
3. Configure Cloudinary credentials in `CloudinaryService.kt`
4. Add Gemini API key to `GeminiHelper.kt`
5. Build: `.\gradlew.bat assembleDebug`

---

## Role-Based Access

| Feature | Teacher | Parent |
|---------|---------|--------|
| Add Meal | ✅ | ❌ |
| Upload Images | ✅ | ❌ |
| Edit/Delete Achievements | ✅ | ❌ |
| Submit Feedback | ❌ | ✅ |
| View Feedback | ✅ | ❌ |
| Like Achievements | ✅ | ✅ |

---

## Project Status

✅ **Production Ready (May 2026)**

All features implemented, tested, and deployed.

---

## License

MIT License
