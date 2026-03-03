# LeaguesAndTeams App - Darryl Mak

A simple sports app that allows users to:
1. **Browse Leagues** - Display a list of available sports leagues
2. **View Teams** - Browse teams within a selected league
3. **Team Details** - View detailed information about a team


https://github.com/user-attachments/assets/c2d8605f-038d-4ba6-a172-44136bd35f54


### Setup Instructions

#### Prerequisites
- Android Studio: Android Studio Otter (2025.2.3) or newer.
- Java/JDK Version: JDK 21.
- •Android SDK
  - Compile SDK:  36
  - Minimum SDK:  24
- Gradle Version: Gradle 9.0 or newer.

#### Open & Run Project
1. Unzip the project
2. Open Android Studio
3. Select "Open" and choose the root project directory
4. Let Gradle sync complete
5. run unit tests with `./gradlew clean testDebugUnitTest`

#### AI Usage Summary 

I primarily used AI as a design and review partner to evaluate architectural trade-offs and validate decisions, rather than to generate feature code.  I used Codex for some quick refactoring and unit testing.

Specifically, I leveraged AI to:
* Validate feature-based modularization for my application, ensuring clean boundaries between feature modules and shared core layers (domain, data, navigation).
* Refine MVVM + Flow usage, including best practices around StateFlow, SharedFlow, and one-off navigation events.
* Design type-safe navigation with Jetpack Navigation, focusing on where navigation events should live (ViewModel vs UI) and how to safely pop the back stack without leaking UI concerns.

All final architectural decisions and implementations were made deliberately, with AI acting as a second set of eyes.


## Technical Details

### Architecture
- Implement **MVVM** architecture pattern
- Use clear separation of concerns between UI, domain, and data layers
- proper state management

### UI
- Use **Jetpack Compose** (already configured)
- Proper loading, error, and empty states

### Networking
- Integrate with [TheSportsDB API](https://www.thesportsdb.com/documentation)
- Handle network errors gracefully.
- Proper error messages for the user

### Modularization
- Implement features and other architectural components in separate modules.
- Maintain proper dependency direction
- Keep modules focused and single-purpose.

### Testing
- Includes unit tests for ViewModels and Repositories

### Build Configuration
- Debug and release build types are configured
- ProGuard/R8 is enabled for release builds

---

## API Reference

**Base URL:** `https://www.thesportsdb.com/api/v1/json/3/`

| Endpoint | Description | Example |
|----------|-------------|---------|
| `all_leagues.php` | List all leagues (limited to 10 on free tier) | N/A |
| `search_all_leagues.php?c={country}` | List leagues by country | `?c=England` |
| `search_all_teams.php?l={league}` | Get teams in a league | `?l=English Premier League` |
| `searchteams.php?t={name}` | Search teams by name (recommended for team details) | `?t=Arsenal` |
