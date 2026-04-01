# UI / Logic Separation

This document describes the **interface between game logic and UI**.

It defines **who is responsible for what**, how data flows through the system, and how UI and logic remain decoupled.

The goal is to keep the UI **replaceable**:

- today: console UI
- later: game window, touch UI, mobile UI

The game rules must **never depend on the UI**.

---

## Core Principle

> **Game logic decides,  
> the UI presents and collects input.**

In other words:

- **Logic** = *What happens?*
- **UI** = *How is it shown and how is it selected?*

---

## Responsibilities

### UI Layer (for example `ConsoleMenu`)

**Allowed to:**

- display text
- show menus
- validate input (number entered? valid range?)
- support navigation (`Back`, `Cancel`)

**Not allowed to:**

- implement game rules
- modify game state on its own
- anticipate or pre-decide outcomes

---

## Communication Model

### Intent → Result

The UI communicates with the logic through **intents** (player requests).
The logic responds with **results** (outcomes).

#### Examples of intents

- `ExplorationAction.MOVE`
- `ExplorationAction.INTERACT_POI`
- `CombatAction.BASIC_ATTACK`
- `CombatAction.CAST_SPELL`