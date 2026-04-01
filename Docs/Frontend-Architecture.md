# Frontend Architecture

This document describes the structure and role of the Vue-based frontend.

Its purpose is to define how the web UI is organized, what responsibilities belong to the frontend, and how it interacts with the backend.

The frontend is not meant to contain core game rules.
Its job is to present game state, collect player input, and communicate with the backend in a clean and replaceable way.

---

## Purpose

The Vue frontend provides a browser-based interface for playing Varneth.

It is responsible for:

- presenting the current game context
- displaying available actions
- sending player input to the backend
- rendering backend responses in a readable and interactive way
- supporting session-based play in the browser

The backend remains responsible for game rules, world state, combat logic, exploration logic, and progression.

---

## Design Goal

The frontend should make the game easier to interact with without taking over the actual game logic.

The architecture follows this principle:

> **Backend decides what happens.  
> Frontend decides how it is shown and how the player interacts with it.**

This keeps the UI flexible and allows the project to evolve from a console-first structure into a web-based experience without rewriting the core systems.

---

## Core Responsibilities

### Frontend is responsible for:

- rendering text, menus, and interaction options
- structuring the screen into clear UI regions
- managing local presentation state
- sending user actions to the backend API
- displaying returned results
- handling browser-side session flow

### Frontend is not responsible for:

- combat resolution
- exploration rules
- reward calculation
- world progression logic
- persistent game state decisions

---

## UI Regions

The web UI is structured into multiple functional areas.

### Main context area

The central area displays the current game situation, for example:

- room descriptions
- dialogue text
- combat messages
- story progression
- feedback after actions

This is the primary focus of the interface.

### Action area

The action area displays the currently available interactions, for example:

- movement
- dialogue choices
- combat actions
- interaction with points of interest

The available actions are determined by backend state.

### Additional panels

Optional side or overlay panels may show supporting information such as:

- inventory
- quests
- map
- notebook or discovered lore
- debug or development logs

These panels support the main context area but should not overwhelm it.

---

## Frontend State

The frontend may store temporary UI-related state, such as:

- which panel is open
- which action is currently highlighted
- whether a popup is visible
- scroll position
- whether the user is currently in a loading state

This state is purely presentational.

The frontend should not become the source of truth for core gameplay data.
Important gameplay state belongs to the backend.

---

## Backend Communication

The frontend communicates with the backend through HTTP requests.

A typical flow looks like this:

1. the frontend loads the current session state
2. the backend returns the current context and available actions
3. the player selects an action in the frontend
4. the frontend sends the action to the backend
5. the backend processes the action and returns the updated result
6. the frontend renders the new state

This keeps the frontend reactive while the backend remains authoritative.

---

## Session Model

Browser sessions are handled through a session-based backend approach.

The frontend does not manage game sessions on its own.
Instead, it works with the session provided by the backend and uses it to continue the player’s current run.

This makes it possible to support multiple browser sessions while keeping game logic centralized.

---

## Architectural Direction

The long-term frontend direction is based on three goals:

- clear separation between UI and game logic
- modular and expandable interface structure
- support for richer browser-based interaction over time

The frontend is expected to evolve step by step, starting with a simple playable interface and gradually moving toward a more refined presentation layer.

---

## Current Status

The current Vue frontend is still in an evolving stage.

Its present focus is:

- building a usable browser-based play interface
- keeping the main context readable
- connecting actions cleanly to backend responses
- preparing the structure for future UI improvements

The goal is not visual perfection first, but a solid and maintainable interaction layer.