2048 Game for Android 🧩

A native **Java Android** implementation of the classic 2048 sliding tile puzzle game, created with **Android Studio** by **Sandeep Kumar Jena**.

---

## 🎯 Game Overview

This is a mobile clone of the popular **2048 puzzle game** originally designed by Gabriele Cirulli. Slide numbered tiles around a 4×4 board to merge them and reach the coveted **2048 tile**—but you can keep playing beyond that!

---

## 🛠️ Setup & Installation
1. **Clone the repository:**

   ```bash
   git clone https://github.com/SandeepJena2004/2048_Game.git
   cd 2048_Game
   ```
2. **Open** the project in Android Studio.
3. Allow Gradle to sync and automatically install needed dependencies.
4. **Run** the app on your device or emulator via the Android Studio "Run" button.

---

## 🧠 How the Game Works

| Component       | Details                                                                                                                                                  |
| --------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Board**       | Represented as a 2D array. Supports moves (UP/DOWN/LEFT/RIGHT), spawning new tiles, and checking game-over states. Verifies one merge per tile per move. |
| **Tile Values** | Tiles display powers of 2 (2, 4, 8, ...). Merging creates a tile with double the value.                                                                  |
| **Spawning**    | New tiles (2 or 4) appear in random empty cells after each move.                                                                                         |
| **UI**          | Grid and tiles drawn dynamically. Animations applied for smooth sliding and merges.                                                                      |
| **Score Logic** | Score increases with each merge. Best score is persisted (e.g. via SharedPreferences).                                                                   |

---

Enjoy playing—and good luck reaching that 2048 tile (and beyond)!


