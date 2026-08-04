# Varneth repository guide

## Project at a glance

Varneth is an active portfolio and learning project: a story-first modular text
adventure/RPG with a Java game core. Preserve the deliberately incremental nature
of the codebase; prefer small, coherent improvements over broad rewrites.

The Vue frontend and HTTP server were built for a demo and are not the active
product direction. The active path starts with the author's original Java console
layer and moves toward a LibGDX desktop app, followed by Android. The game core is
authoritative for rules and runtime state; no presentation layer may duplicate
combat, exploration, story, reward, or progression rules.

## Current product direction

Read [`Docs/PRODUCT-ROADMAP.md`](Docs/PRODUCT-ROADMAP.md) before planning or making
product, architecture, UI, content, persistence, or platform changes. It is the
source of truth for the intended journey:

- story and branching chapters first; combat supports the narrative
- JSON-authored content, with a visual story editor deferred until useful
- the original `GameStart -> GameLoop -> systems` path as the recovery baseline
- LibGDX/LWJGL3 and portrait-oriented PC testing before an Android module
- offline autosave and story branches for combat victory, defeat, and escape
- story-unlocked spells that level up and are cast through a free-drawn rune

Keep current and target architecture distinct. The repository currently uses
Java 21 and is not yet a LibGDX multi-module project. Java 17, LibGDX, the `core` /
`lwjgl3` split, and Android are roadmap targets, not current repository facts.

## Toolchain and commands

- Java 21, Gradle wrapper, application main class `varneth.Main`.
- Console game: `./gradlew run`
- Web backend on port 8080: `./gradlew run --args=web`
- Frontend requires Node `^20.19.0 || >=22.12.0`.
- Install frontend dependencies: `cd frontend && npm ci`
- Frontend dev server on the usual Vite port 5173: `cd frontend && npm run dev`
- Production frontend build: `cd frontend && npm run build`
- Backend verification: `./gradlew build`

There are currently no Java or frontend test files and no frontend lint/test
scripts. For a change, run the relevant builds and add focused tests if a test
framework is introduced as part of the task.

## Architecture

The core modeling rule is **Definition -> State -> Instance**:

- Definition contains immutable base/content data.
- State contains mutable runtime data.
- Instance connects definition and state and provides behavior.
- Instances should not acquire additional persistent fields beyond their
  definition and state without a strong architectural reason.

Two gameplay paths currently coexist. The original path is `GameStart -> GameLoop
-> ExplorationPhase / CombatScene`; it is the baseline for renewed product work.
The later demo path uses `ConsoleGameRunner` or `Server` with
`application/session/GameSession.java`, which delegates to exploration/combat
engines and builds a UI-facing `UiState`. Do not add a feature independently to
both paths. Follow the roadmap's staged consolidation instead.

Important backend areas:

- `varneth/application/session`: browser sessions and game-flow coordination
- `varneth/application/engine`: UI-independent exploration/combat operations and
  session data
- `varneth/application/state`: API-facing UI state and action options
- `varneth/application/intent`: player input sent by clients
- `varneth/systems`: domain models for actors, rooms, world, POIs, rewards, spells
- `varneth/systems/world/WorldBuilder.java`: hard-coded development/test worlds
- `varneth/ui` and `varneth/renderer`: legacy/current console presentation
- `varneth/Server.java`: lightweight JDK HTTP server and API boundary

The frozen demo web API currently consists of:

- `GET /api/state`: creates or resumes a session; returns `X-Session-Id`.
- `POST /api/intent`: accepts `PlayerIntent` with an existing `X-Session-Id`.
- The frontend sends `{ "type": "SELECT_ACTION", "value": actionId }`.
- CORS currently permits only `http://localhost:5173`.
- Sessions are in memory; there is no persistence.

The demo Vue 3 frontend lives in `frontend/`. `App.vue` composes the screen,
`composables/useGameApi.js` owns API/session communication, and
`composables/useMapView.js` converts backend map data into renderable layout data.
If a task explicitly touches the demo, keep component state presentational and
treat the returned `UiState` as the source of truth for that demo path.

## Working conventions

- Read the relevant document under `Docs/` before changing its subsystem.
- Use `Docs/PRODUCT-ROADMAP.md` to choose the next milestone and avoid pulling
  deferred work into the current milestone.
- Keep UI code separate from game decisions, including action availability.
- Only when explicitly changing the web demo, route browser actions through
  `PlayerIntent` and return updated `UiState`; never create frontend-only gameplay
  branches.
- Preserve stable action IDs and entity IDs. Display labels may be localized or
  changed independently.
- World/runtime collections are mutable in several places. Be deliberate about
  aliasing when exposing lists/maps or reusing entity instances.
- Keep Java packages under `varneth` and use the existing package boundaries.
- Match nearby formatting; the repository currently has no enforced formatter.
- User-facing game text is mixed German/English. Do not normalize or translate
  existing content unless the task asks for it.
- Update the corresponding Markdown document when an architectural contract or
  subsystem responsibility changes.

## Documentation caveat

`Docs/` contains useful design intent, but some examples and class names lag behind
the implementation (for example older `CombatResult` and `RoomStateTest`
references). Treat executable code as the source of truth, then update stale docs
when the task touches that area. Start with:

- `Docs/README.md`
- `Docs/PRODUCT-ROADMAP.md`
- `Docs/ARCHITECTURE.md`
- `Docs/Frontend-Architecture.md`
- `Docs/WorldState.md`
- `Docs/CombatScene.md`

## Before handing off changes

1. Inspect `git diff` and preserve unrelated user work.
2. Run `./gradlew build` for backend changes.
3. Run `npm run build` from `frontend/` for frontend changes.
4. If the API contract or both sides changed, verify backend and frontend builds
   and check the session/header flow together.
5. Mention any verification that could not be run and why.
