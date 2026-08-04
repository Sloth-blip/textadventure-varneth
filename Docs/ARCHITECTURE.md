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

The first inventory slice follows the same split:

- `PlayerState` extends actor runtime state with the player's item inventory.
- `MagicCrystalDefinition` contains stable identity, display text, magic type,
  and maximum charge.
- `MagicCrystalState` contains the remaining charge.
- `MagicCrystal` validates compatibility and changes its charge.

`AvailableSpell` is derived from learned spells, current player resource, and
usable inventory crystals. It is a transient combat option and is not persistent state.

## Combat presentation boundary

`CombatScene` resolves game rules and publishes an immutable `CombatStateChanged`
snapshot at action boundaries: when combat begins, after a player action, and
after each enemy attack. The snapshot contains only the values a presentation
layer needs and does not expose mutable actors.

`CombatConsoleNarrator` renders those state changes for the console, while
`CombatConsoleMenu` remains responsible for player input. A later graphical UI
can subscribe to the same state event without moving combat rules into the UI.
