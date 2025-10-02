## Habit Tracker (No DB, JSON file storage)

A simple full-stack Habit + Learning tracker built with Spring Boot (Java 21) and plain HTML/CSS/JS. Data persists to JSON files under `./data/` so no database is required.

### Features
- Register/Login (email + password). For real apps, hash passwords and use JWT.
- Track habits (daily/weekly), streaks, and mark done to gain XP and coins.
- Learning modules with completion XP awards.
- Rewards store using coins; unlock cosmetic items.
- Dashboard with XP/Coins/Level; progress and leaderboard.
- All static frontend served by the backend.

### Run (IntelliJ or Maven)
1. Open the project as a Maven project in IntelliJ.
2. Build: `mvn clean package`
3. Run: Run `com.habittracker.HabitTrackerApplication` or `mvn spring-boot:run`
4. Open `http://localhost:8080/`

Console prints sample login: `email: test@local, password: test123`.

### Data Files
- Location: `./data/`
- Files created automatically on first run:
  - `users.json`
  - `habits.json`
  - `learning.json`
  - `rewards.json`

### API (All JSON, prefix `/api`)
- POST `/api/auth/register` body: `{ fullName, email, password, favoriteLearningArea, avatar }`
- POST `/api/auth/login` body: `{ email, password }` -> `{ success, userId, fullName, avatar, xp, coins }`
- GET `/api/users/{id}`
- PUT `/api/users/{id}`
- DELETE `/api/users/{id}`
- GET `/api/habits?userId={id}`
- POST `/api/habits` body: `{ userId, name, frequency }`
- PUT `/api/habits/{id}`
- DELETE `/api/habits/{id}`
- POST `/api/habits/{id}/mark` body: `{ userId }`
- GET `/api/learning?userId={id}`
- POST `/api/learning/{moduleId}/complete` body: `{ userId }`
- GET `/api/rewards`
- POST `/api/rewards/purchase` body: `{ userId, rewardId }`
- GET `/api/leaderboard`
- GET `/api/progress?userId={id}`

### Notes
- Level = `xp / 1000`. Progress bar uses `xp % 1000`.
- Habit mark: XP +10, Coins +5. Streak >= 7 -> +20 bonus XP.
- JSON reads/writes are synchronized to avoid corruption.
- Frontend is simple and uses `fetch` to call the APIs. Chart.js is loaded via CDN on relevant pages.


