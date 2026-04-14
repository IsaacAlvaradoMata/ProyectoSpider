# Spider Solitaire — ProyectoSpider

A fully-featured **Spider Solitaire** card game built with **JavaFX 23** and a **cyberpunk/neon** aesthetic, backed by **Oracle Database XE**, with user accounts, global rankings, game persistence, and interface customization.

---

## Table of Contents

- [Overview](#overview)
- [Screenshots](#screenshots)
- [System Requirements](#system-requirements)
- [Installation & Setup](#installation--setup)
- [How to Run](#how-to-run)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Views & Controllers](#views--controllers)
- [Models & Database](#models--database)
- [Game Logic](#game-logic)
- [Animation System](#animation-system)
- [Audio System](#audio-system)
- [Customization](#customization)
- [Dependencies](#dependencies)
- [Authors](#authors)

---

## Overview

**ProyectoSpider** is a complete implementation of the classic **Spider Solitaire** card game, developed as a university project at **Universidad Nacional (UNA)**. The application includes:

- **Player registration and login**
- **3 difficulty levels**: Easy (1 suit), Medium (2 suits), Hard (4 suits)
- **Save and resume** games stored in the database
- **Points system and global ranking** of players
- **Customization** of background and card style (Classic / Cyberpunk)
- **Smooth animations** with glitch and neon effects
- **Background music and ambient sound effects**
- **Custom animated spider cursor**
- **Hint system** and **move undo**

---

## Screenshots

> The following views make up the complete application interface:

| View | Description |
|---|---|
| Intro | Welcome splash screen with logo animation and glitch effect |
| Login | Player registration and sign-in |
| Menu | Main panel with stats, difficulty selector, and paused games |
| Game | Full board with 10 columns, stock pile, and completed piles |
| Points | Player ranking table with search filter |
| Personalization | Background and card style selection |
| Help | Game guide with tabbed sections and animated GIFs |
| Info | Project credits screen |

---

## System Requirements

| Requirement | Minimum Version |
|---|---|
| Java JDK | 21 |
| Maven | 3.6+ |
| Oracle Database XE | 21c (XEPDB1) |
| Operating System | Windows / Linux / macOS |
| RAM | 4 GB recommended |

---

## Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/IsaacAlvaradoMata/ProyectoSpider.git
cd ProyectoSpider
```

### 2. Configure Oracle Database

The project connects to Oracle XE using the following credentials (set in `persistence.xml`):

```
Host:     localhost
Port:     1521
Service:  XEPDB1
User:     spider
Password: spider
```

**Option A — Oracle XE with Docker:**

```bash
docker run -d -p 1521:1521 --name oracle-xe \
  -e ORACLE_PASSWORD=oracle \
  container-registry.oracle.com/database/express:21.3.0-xe
```

**Option B — Local Oracle XE installation:** make sure the service is running on port `1521`.

### 3. Create the database user

Connect as `SYS` or `SYSTEM` and run:

```
DATABASE/Create User in DB/Crear usuario SPIDER.sql
```

This creates the `spider` user with password `spider` and DBA privileges.

### 4. Create the schema

Connected as the `spider` user, run:

```
DATABASE/Script DB/DATABASE.sql
```

This creates all tables, sequences, triggers, and indexes.

---

## How to Run

```bash
mvn clean javafx:run
```

To generate an executable JAR:

```bash
mvn clean package
java -jar target/ProyectoSpider-1.0-SNAPSHOT-shaded.jar
```

---

## Project Structure

```
ProyectoSpider/
│
├── DATABASE/
│   ├── Script DB/
│   │   └── DATABASE.sql                  # Full schema (tables, sequences, triggers)
│   └── Create User in DB/
│       └── Crear usuario SPIDER.sql      # Oracle user creation script
│
├── src/
│   └── main/
│       ├── java/cr/ac/una/proyectospider/
│       │   ├── Main.java                 # Entry point
│       │   ├── App.java                  # JavaFX Application class
│       │   │
│       │   ├── model/                    # JPA entities and DTOs
│       │   │   ├── Jugador.java
│       │   │   ├── Partida.java
│       │   │   ├── CartasPartida.java
│       │   │   ├── JugadorDto.java
│       │   │   ├── PartidaDto.java
│       │   │   ├── CartasPartidaDto.java
│       │   │   ├── JugadorRankingDto.java
│       │   │   └── PartidaCompletaDto.java
│       │   │
│       │   ├── controller/               # JavaFX FXML controllers (MVC)
│       │   │   ├── Controller.java       # Abstract base class
│       │   │   ├── IntroController.java
│       │   │   ├── LoginController.java
│       │   │   ├── MenuController.java
│       │   │   ├── GameController.java
│       │   │   ├── PointsController.java
│       │   │   ├── PersonalizationController.java
│       │   │   ├── HelpController.java
│       │   │   └── InfoController.java
│       │   │
│       │   ├── service/                  # Business logic and data access
│       │   │   ├── JugadorService.java
│       │   │   └── PartidaService.java
│       │   │
│       │   └── util/                     # Cross-cutting utilities
│       │       ├── FlowController.java   # View navigation manager
│       │       ├── AppContext.java       # Global session context (Singleton)
│       │       ├── AnimationDepartment.java
│       │       ├── SoundDepartment.java
│       │       ├── MazoGenerator.java
│       │       ├── CustomAlert.java
│       │       ├── CustomCursor.java
│       │       ├── FontDepartment.java
│       │       ├── BindingUtils.java
│       │       ├── Formato.java
│       │       └── BooleanToIntegerConverter.java
│       │
│       └── resources/cr/ac/una/proyectospider/
│           ├── view/                     # FXML layout files
│           │   ├── IntroView.fxml
│           │   ├── LoginView.fxml
│           │   ├── MenuView.fxml
│           │   ├── GameView.fxml
│           │   ├── PointsView.fxml
│           │   ├── PersonalizationView.fxml
│           │   ├── HelpView.fxml
│           │   ├── InfoView.fxml
│           │   └── StyleSpider.css
│           │
│           └── resources/                # Multimedia assets
│               ├── *.png / *.gif         # Images, cards, backgrounds, icons
│               ├── *.mp3 / *.wav         # Music and sound effects
│               └── *.ttf                 # Custom fonts
│
├── pom.xml                               # Maven configuration
└── nbactions.xml                         # NetBeans actions
```

---

## Architecture

The project follows the **MVC (Model-View-Controller)** pattern with the following layers:

```
┌─────────────────────────────────────────────────┐
│                   VIEW (FXML)                   │
│   IntroView · LoginView · MenuView · GameView   │
│   PointsView · PersonalizationView · HelpView   │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│              CONTROLLER (JavaFX)                │
│   IntroCtrl · LoginCtrl · MenuCtrl · GameCtrl   │
│   PointsCtrl · PersonalCtrl · HelpCtrl          │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│              SERVICE (Business Logic)           │
│         JugadorService · PartidaService         │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│              MODEL (JPA Entities)               │
│        Jugador · Partida · CartasPartida        │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│           DATABASE (Oracle XE)                  │
│    EclipseLink JPA · RESOURCE_LOCAL TX          │
└─────────────────────────────────────────────────┘
```

**Cross-cutting utility classes:**

| Class | Purpose |
|---|---|
| `FlowController` | Singleton managing navigation between FXML views |
| `AppContext` | Singleton storing global session state |
| `AnimationDepartment` | All JavaFX animations used in the app |
| `SoundDepartment` | Centralized audio and sound effects control |
| `MazoGenerator` | Deck generation and distribution by difficulty |
| `CustomAlert` | Styled modal dialogs with neon theme |
| `CustomCursor` | Animated spider-themed cursor |
| `FontDepartment` | Loads custom fonts (Cynatar, Joystix, Raster) |

---

## Views & Controllers

### IntroView — Splash screen
- Animated logo with **epic entrance** effect
- Title text with **glitch and flicker** effect
- Split title entrance animation (`titleSplitEntrance`)
- "Let's go" button transitions to LoginView

### LoginView — Registration & login
- New player registration (validates duplicates)
- Sign-in by username
- Entrance animations with glitch effects
- "About" button navigates to InfoView

### MenuView — Main menu
- Player stats: games won, total points, accumulated score
- Preview of active customizations (background, card style)
- Difficulty selector: **Easy / Medium / Hard**
- Table of paused games available to resume
- Navigation to: New Game, Continue, Personalization, Ranking, Help, Logout

### GameView — Game board
- **10 card columns** with adaptive vertical scroll
- **Stock pile** to deal new cards
- **8 completed piles** at the top of the board
- Bottom panel: live score, time, and move counter
- Buttons: Save, Hint, Undo, Undo All, Surrender
- Drag-and-drop cards with move validation

### PointsView — Player ranking
- Table with: Player, Games Won, Total Points
- Search field to filter by username
- Custom cell styling with neon theme

### PersonalizationView — Customization
- Background selection (6 default backgrounds + custom image upload)
- Card style: **Classic** vs **Cyberpunk** (RadioButtons)
- Real-time preview of changes
- Persistent save to the database

### HelpView — Help guide
- TabPane with 5 tabs:
  - Game objective
  - Controls and keyboard shortcuts
  - Difficulty levels explained
  - Game conditions and rules
  - General explanation with animated GIFs

### InfoView — Credits
- University project information
- Team member photos and names
- Technologies used
- Terminal/hacking style animation

---

## Models & Database

### JPA Entities

#### Jugador (Player)
```
Id_Jugador        — Primary key (SEQ_JUGADOR)
Nombre_Usuario    — Unique, not null
Partidas_Ganadas  — Total games won
Puntos_Acumulados — Total accumulated points
Estilo_Cartas     — 1 = Classic, 2 = Cyberpunk
Imagen_Fondo      — BLOB (lazy fetch)
Version           — Optimistic locking counter
```

#### Partida (Game Session)
```
Id_Partida           — Primary key (SEQ_PARTIDA)
Id_Jugador           — FK → Jugador (CASCADE DELETE)
Fecha_Inicio         — TIMESTAMP
Fecha_Fin            — TIMESTAMP
Puntos               — >= 0
Tiempo_Jugado        — Elapsed seconds
Movimientos          — Total moves made
Estado               — EN_JUEGO | PAUSADA | TERMINADA | PERDIDA
Dificultad           — FACIL | MEDIA | DIFICIL
Fondo_Seleccionado   — BLOB (background snapshot at game start)
Reverso_Seleccionado — Card back image filename
Version              — Optimistic locking counter
```

#### CartasPartida (Game Cards)
```
Id_Carta_Partida — Primary key (SEQ_CARTASPARTIDA)
Id_Partida       — FK → Partida (CASCADE DELETE)
Palo             — ESPADAS | CORAZONES | DIAMANTES | TREBOLES
Valor            — 1–13
Columna          — 0–9 (board) | -1 (stock pile)
Orden            — Vertical position in the column
Boca_Arriba      — 0 = face-down, 1 = face-up
En_Mazo          — 0/1
En_Pila          — 0/1
Nombre_Carta     — Card image filename
Version          — Optimistic locking counter
```

### Database Triggers

Each table has 2 triggers:
- **INSERT**: automatically assigns the ID from the corresponding sequence
- **UPDATE**: raises an error if an attempt is made to modify the primary key

### Relationship Diagram

```
JUGADOR (1) ──────── (N) PARTIDA (1) ──────── (N) CARTASPARTIDA
           CASCADE DELETE            CASCADE DELETE
```

---

## Game Logic

### Deck Generation (`MazoGenerator`)

| Difficulty | Suits | Composition |
|---|---|---|
| EASY | 1 (Spades only) | 104 cards of the same suit |
| MEDIUM | 2 (Spades + Hearts) | 52 cards × 2 suits |
| HARD | 4 (all suits) | 26 cards × 4 suits |

### Initial Board Distribution

```
Columns 0–3:  6 cards each  (24 cards)
Columns 4–9:  5 cards each  (30 cards)
Stock pile:   54 remaining cards
──────────────────────────────────────
Total:        108 cards  (104 in play)
```

The top card of each column starts **face-up**.

### Scoring System

- **Starting score**: 500 points
- **Hint used**: -5 points
- **Undo move**: -10 points
- **Complete a pile**: score bonus
- **Victory**: final score saved to player statistics

### Move History

Implemented with a **Deque** (`historialMovimientos`):
- **Undo** (Ctrl+Z): reverts the last individual move
- **Undo All**: restores the game to its initial state

### Victory Detection

Victory animations and music trigger when all **8 completed piles** are filled (a full K→A sequence of the same suit per pile).

### Game Save

When pausing or exiting, the full game state is persisted:
1. Game metadata (score, time, moves, state)
2. The state of **every card**: column, order, face direction, in stock, in pile
3. Snapshot of the selected background and card back

---

## Animation System

`AnimationDepartment` centralizes all JavaFX animations:

| Animation | Description |
|---|---|
| `fadeIn()` | Progressive opacity fade-in with configurable delay |
| `glitchTextWithFlicker()` | Glitch effect with color shifts (cyan / magenta / red) |
| `epicLogoAnimation()` | Dramatic entrance for the main logo |
| `titleSplitEntrance()` | Title splits in from both edges of the screen |
| `pulse()` | Heartbeat pulse effect on a node |
| `slideFromTop()` | Element slides in from above |
| `slideInFromBottom()` | Element slides in from below |
| `slideUpWithEpicBounceClean()` | Slide-up with smooth bounce |
| `animateNeonGlow()` | Pulsating neon glow effect |
| `glitchFadeIn() / glitchFadeOut()` | Glitch-distorted fade in/out |
| `animateNeonBorderWithLED()` | Neon border with LED lighting effect |
| `subtleBounce()` | Subtle bounce on appearance |
| `stopAllAnimations()` | Stops and cleans up all active animations |

> All animations are tracked internally to prevent memory leaks when switching views.

---

## Audio System

`SoundDepartment` manages all `MediaPlayer` instances:

### Background Music

| File | Usage |
|---|---|
| `Night Drive.mp3` | Main menu background music |
| `Spider1–4.mp3` | In-game music variants |
| `CYBERPUNK.mp3` | Victory music |

### Sound Effects

| File | Event |
|---|---|
| `flipcard.mp3` | Flipping a card |
| `card.mp3` | Placing a card on a column |
| `pilas2.mp3` | Completing a pile |
| `Deal.wav` | Dealing cards from the stock pile |
| `hint.mp3` | Using a hint |
| `undo.mp3` | Undoing a move |
| `UndoAll.mp3` | Undoing all moves |
| `click.wav` | UI button click |
| `Alerts.wav` | Alert dialogs |
| `error.mp3` | Invalid move |
| `RadioButtons.mp3` | Changing a radio button option |
| `Transition.wav / Transition2.wav` | View transitions |
| `victory.wav` | Victory fanfare |
| `gameover.mp3` | Game over (defeat) |
| `EnterWin.mp3` | Entering the victory screen |
| `EndWind.mp3` | Game end wind-down |
| `save.mp3` | Saving a game |

---

## Customization

### Card Styles

| Style | Filename Pattern | Description |
|---|---|---|
| Classic | `{suit}-{value}.png` | Traditional card design |
| Cyberpunk | `{suit}s-{value}.png` | Neon cyberpunk card design |

Where `suit` is: `1`=Spades, `2`=Hearts, `3`=Diamonds, `4`=Clubs, and `value` ranges from `1` (Ace) to `13` (King).

### Default Backgrounds

6 backgrounds included: `DefaultBack1.png` through `DefaultBack6.png`

Players can also load a **custom image** from their file system via a `FileChooser`.

### Custom Fonts

| Font | File | Usage |
|---|---|---|
| Cynatar | `Cynatar.ttf` | Main UI font |
| Joystix | `Joystix.ttf` | Retro/arcade text |
| Raster | `Raster.ttf` | Bitmap-style text |

---

## Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| `javafx-controls` | 23.0.1 | JavaFX UI controls |
| `javafx-fxml` | 23.0.1 | FXML layout engine |
| `javafx-media` | 23.0.1 | Audio and video playback |
| `javafx-swing` | 21 | Swing-JavaFX integration |
| `materialfx` | 11.16.1 | Material Design components for JavaFX |
| `ojdbc11` | 23.2.0.0 | Oracle JDBC driver |
| `eclipselink` | 4.0.2 | JPA implementation (ORM) |

**Maven Plugins:**

| Plugin | Version | Purpose |
|---|---|---|
| `maven-compiler-plugin` | 3.8.0 | Java 21 compilation |
| `javafx-maven-plugin` | 0.0.8 | Run with `mvn javafx:run` |
| `maven-shade-plugin` | 3.5.1 | Executable uber JAR generation |

---

## Authors

Project developed at **Universidad Nacional de Costa Rica (UNA)** as a final course project.

| Name | Git Username |
|---|---|
| Isaac Alvarado Mata | `crossac` / `IsaacAlvaradoMata` |
| Matiw Rivera | `MatiwRC18` / `Matiw777` |
| Emmanuel Gamboa Retana | `gamboaxx` |

---

> Academic project — Universidad Nacional (UNA) · Costa Rica
