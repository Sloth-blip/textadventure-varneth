# Core Architecture

The `GameLoop` acts as the central coordinator for the main gameplay phases:

- `GameStart`
- `ExplorationPhase` ↔ `DialogPhase`
- `CombatScene`

Game logic produces results and passes them back to the `GameLoop`.
The `GameLoop` can then forward them to the UI for presentation and decide how the game should continue.

## Definition - State - Instance

### Definition
Stores immutable base data such as name, base stats, stats per level, and similar core values.

### State
Stores mutable runtime data such as level, current HP, and current calculated stats.

### Instance
Connects definition and state.

An instance should not introduce additional persistent fields beyond `definition` and `state`.
Its main responsibility is to provide the behavior and methods that operate on both.