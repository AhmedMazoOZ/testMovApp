# Movie App

## Overview
This Modularized Android application is built using modern Android development practices, following Clean Architecture principles and MVI pattern. 
It allows users to browse movies, view details, and favorite movie action.

## Architecture
The project follows Clean Architecture with a modular approach:

### Modules
- **app**: Main application module containing Android components 
- **presentation**: UI layer with Fragments, ViewModels, and UI logic
- **domain**: Business logic and use cases
- **data**: Data handling and repository implementations and DI setup
- **navigation**: Navigation components and routing logic

### Tech Stack
- **Language**: Kotlin
- **Architecture Pattern**: MVI (Model-View-Intent)
- **Dependency Injection**: Dagger Hilt
- **Navigation**: Android Navigation Component
- **Concurrency**: Kotlin Coroutines & Flow
- **UI**: XML layouts with Material Design components
- **Build System**: Gradle (Kotlin DSL)

## Features
- Browse movie listings
- View detailed movie information
- favorite movie action

## Project Setup
1. Clone the repository

2. Open the project in Android Studio

3. Build and run the application

## Dependencies
- kotlin version : 2.0.20
- Java version : 17
- AndroidX Core KTX: 1.12.0
- Navigation Component: 2.7.7
- Dagger Hilt: 2.47
- [List other major dependencies]

## Architecture Components:
### Presentation Layer
- Implements MVI pattern
- Uses ViewModels for managing UI-related data
- Handles UI events and user interactions

### Domain Layer
- Contains business logic
- Defines use cases and domain models
- Independent of Android framework

### Data Layer
- Implements repositories
- Handles data operations
- Manages data sources

## Navigation
The app uses the Navigation Component for handling navigation between screens by single Activity:
- HomeFragment
- MovieDetailsFragment

## Build Configuration
- minSdk: 24
- targetSdk: 34
- compileSdk: 34
- Kotlin Version: [2.0.21]
- Gradle Version: [8.10.1]

## Testing
- Unit tests for use case


## Contact
[ahmedazooz15@gmail.com]
