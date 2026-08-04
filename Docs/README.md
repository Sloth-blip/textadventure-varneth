# Varneth

A modular text adventure / RPG project built in Java, with a growing web interface for browser-based play.

Varneth is both a portfolio and learning project focused on clean architecture, separation of immutable game definitions and mutable runtime state, and designing systems that can grow without turning into a monolith.

## Why this project exists

The project started as an exercise in object-oriented design, but gradually evolved into a broader architecture project.

The goal is not only to create a playable text adventure, but also to explore questions such as:

- How should static content and runtime state be separated?
- How can exploration, dialogue, and combat stay modular?
- How can game logic be structured so that different UI layers can be added later without rewriting the core systems?

## Current features

The current version already includes core systems for:

- actor, enemy, and player modeling
- combat and exploration phases
- dialogue chunk handling
- skill and spell systems
- world and room logic

The project also contains separate documentation for architecture and UI/game-logic boundaries.

## Architecture approach

One of the central design ideas is:

**Definition → State → Instance**

- **Definition** stores immutable base data
- **State** stores mutable runtime data
- **Instance** connects both and provides behavior

The game flow is also split into distinct phases such as:

- `GameStart`
- `ExplorationPhase`
- `DialogPhase`
- `CombatScene`

This makes the codebase easier to extend, reason about, and adapt to future features.

## Web direction

In addition to the Java-based core systems, the project is being expanded with a Vue-based web UI.

The long-term idea is to keep the backend responsible for game logic and state, while the frontend focuses on presentation, interaction, and session-based play in the browser.

## Tech stack

- Java 17
- Gradle
- Vue.js
- modular package structure
- Markdown documentation for architecture decisions

## Project structure

```text
src/main/java/varneth/
├── application/
├── content/
├── engine/
├── input/
├── renderer/
├── systems/
├── ui/
└── Main.java

Docs/
├── ARCHITECTURE.md
├── Frontend-Architecture.md
├── CombatScene.md
├── UI-LOGIC.md
└── WorldState.md
```
## What I practice with this project

This project is where I actively practice:

 - object-oriented design
 - clean code structure
 - separation of concerns
 - extensible system design
 - frontend/backend thinking
 - Status

Active portfolio project.

## Current focus:

 - improving the project presentation for public viewing
 - expanding the web interface
 - keeping the architecture modular as the project grows
