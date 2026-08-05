# WorldBuilder / WorldState

This document describes the responsibilities of `WorldBuilder`, `WorldState`,
and their relationship to the active core's central `GameState`.

## WorldBuilder

`WorldBuilder` constructs development worlds. It creates rooms, enemies, points
of interest, rewards, and room connections, then returns a runnable `WorldState`.

It is responsible for content assembly, not exploration/combat rules, UI,
persistence, or deciding which room is currently active.

The current builders are:

```java
public static WorldState buildTestWorld();
public static WorldState buildTestWorldTwo();
```

They remain hard-coded development content until the data-driven story milestone
moves content into validated JSON.

## WorldState

`WorldState` owns the canonical runtime room instances of one world:

```java
public class WorldState {
    private final Room startRoom;
    private final List<Room> allRooms;
}
```

It validates that:

- the start room is one of its canonical room instances
- every room has a unique stable ID

`getRoomById(String)` resolves navigation and restored IDs back to the canonical
mutable `Room` instance. The exposed room list cannot be structurally modified;
the contained rooms still own their mutable `RoomState`.

Despite its historical name, `WorldState` is not the complete running game. It
does not own the player, current-room selection, visited-room progress, phase
control, or UI state.

## GameState relationship

The active `GameStart -> GameLoop` path creates one central `GameState` from a
`Player` and `WorldState`. `GameState` stores the current room as a stable ID and
resolves it through `WorldState`; it never stores a second detached room copy.

`GameState` also owns visited room IDs, so first-visit dialog state can later be
saved and restored. `ExplorationPhase` receives only whether the current entry
is a first visit and keeps no persistent room-progress collection of its own.

The demo path's `ExplorationSessionData` remains separate and frozen. It is not
the authoritative state model for renewed product work.
