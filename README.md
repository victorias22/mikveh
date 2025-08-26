# 📱 Android Application - Technical Overview

## 🛠️ Technology Stack

| Technology               | Description |
|--------------------------|-------------|
| **Java + Android SDK**   | Core logic written in Java using the Android Framework for native app development |
| **Gradle**               | Build automation system for dependency management, version control, and modular execution |
| **Firebase**             | Integrated for app distribution (via App Distribution) and service hooks via `google-services` |
| **Jenkins**              | CI/CD pipeline for automated build, unit testing, and APK deployment |
| **JUnit**                | Framework for both unit tests and instrumentation tests (e.g., `MikvehTest.java`) |
| **ProGuard**             | Code shrinking and obfuscation for secure and optimized release builds |
| **ViewBinding**          | Safer and cleaner view access, replacing `findViewById` for reduced boilerplate |

---

## ▶️ Build & Run Instructions

### Prerequisites
- Android Studio installed
- Gradle configured (bundled with Android Studio)

### Open the Project
1. Launch **Android Studio**
2. Select **"Open an existing project"**
3. Navigate to the project root directory

### Build the Debug APK
```bash
./gradlew assembleDebug
