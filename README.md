# Spider Solitaire — ProyectoSpider

**Tech stack:** Java 21, JavaFX 23, MaterialFX, Oracle Database 23c, EclipseLink (JPA), Maven

---

**Problem:**
Building a fully functional Spider Solitaire game that persists player profiles, tracks rankings, and supports mid-game saves required a well-structured architecture that cleanly separated game logic, persistence, and UI concerns.

**Solution:**
Developed a desktop application in Java/JavaFX that implements the complete Spider Solitaire ruleset across three difficulty levels (one, two, and four suits), with drag-and-drop card mechanics, animated transitions, sound effects, and full game-state persistence so players can pause and resume any session.

**Architecture:**
The project follows a layered MVC architecture: FXML-defined views render the UI, JavaFX controllers handle user interactions and delegate to a service layer (`JugadorService`, `PartidaService`) that encapsulates all business logic and database access via JPA/EclipseLink entities (`Jugador`, `Partida`, `CartasPartida`); a dedicated `util` package provides cross-cutting concerns such as navigation (`FlowController`), animations (`AnimationDepartment`), sound (`SoundDepartment`), and deck generation (`MazoGenerator`).

**Impact:**
The application delivers a complete, polished gaming experience with persistent player profiles, accumulated score tracking, a game-history ranking board, and per-player UI customization (card style and background image), all backed by an Oracle relational schema with optimistic locking to ensure data integrity.

**Role:**
Sole developer — responsible for the full project lifecycle, including database schema design, JPA entity mapping, game-logic implementation (card movement rules, sequence detection, hint system), JavaFX UI/UX design with custom animations and neon styling, and Maven build configuration.
