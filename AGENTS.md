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

Read [`Docs/CONTENT-AUTHORING.md`](Docs/CONTENT-AUTHORING.md) before writing or
transferring story, scene, dialogue, quest, or riddle content. It defines the
current handoff between Notion and the executable repository copy.

Keep current and target architecture distinct. The repository currently uses
Java 17 and is not yet a LibGDX multi-module project. LibGDX, the `core` /
`lwjgl3` split, and Android are roadmap targets, not current repository facts.

## Toolchain and commands

- Java 17, Gradle wrapper, application main class `varneth.Main`.
- Console game: `./gradlew run`
- Demo console: `./gradlew run --args=demo`
- Web backend on port 8080: `./gradlew run --args=web`
- Frontend requires Node `^20.19.0 || >=22.12.0`.
- Install frontend dependencies: `cd frontend && npm ci`
- Frontend dev server on the usual Vite port 5173: `cd frontend && npm run dev`
- Production frontend build: `cd frontend && npm run build`
- Backend verification: `./gradlew build`

Java core tests use JUnit Jupiter under `src/test/java`. The frontend still has
no lint/test scripts. Run the checks relevant to every changed area.

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

## Working with the author

This repository is also a learning project for its author. Work as a collaborative
pair programmer, not as a silent implementation service:

- Explain the relevant existing flow before changing it.
- Keep implementation steps small enough to review and understand.
- State the reason and tradeoff behind architectural decisions in plain language.
- Do not perform broad refactors or jump ahead across roadmap milestones without
  discussing them first.
- After diagnostics or a completed slice, pause with concrete findings and choose
  the next change together.
- Prefer helping the author implement or reason through a subsystem when they want
  to learn it; only take over complete implementation when explicitly requested.
- Call out bugs and risks separately from proposed fixes so the author can
  participate in the decision.

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

## Container editing caveat

In this Docker workspace, the `apply_patch` helper may fail before reading a
patch because Bubblewrap cannot create an unprivileged namespace. After that
exact `bwrap` error, do not retry the helper repeatedly. Use GNU `patch` with a
standard unified diff instead; `*** Begin Patch` / `*** End Patch` markers belong
only to the helper and are invalid input for GNU `patch`. After the fallback,
check for generated `*.orig` and `*.rej` files, remove only artifacts created by
the current edit, and run `git diff --check`.

## Documentation caveat

`Docs/` contains useful design intent, but some examples and class names lag behind
the implementation (for example older `CombatResult` and `RoomStateTest`
references). Treat executable code as the source of truth, then update stale docs
when the task touches that area. Start with:

- `Docs/README.md`
- `Docs/PRODUCT-ROADMAP.md`
- `Docs/CONTENT-AUTHORING.md`
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
