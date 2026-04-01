# WorldBuilder / WorldState

This document describes the responsibilities of `WorldBuilder` and `WorldState`.

Together, they define how a concrete game world is created and how its current runtime state is represented.

---

# WorldBuilder

## Purpose

`WorldBuilder` is responsible for constructing a concrete game world.

It creates instances of:

- rooms
- enemies
- interactables (points of interest)
- connections between rooms

At the moment, the builder mainly encapsulates **test and development worlds** and serves as the central place for trying out gameplay mechanics.

---

## Responsibilities

### WorldBuilder is responsible for:

- creating a runnable world (`WorldState`)
- placing interactables in rooms
- spawning enemy instances
- connecting rooms

### WorldBuilder is not responsible for:

- game logic (exploration, combat)
- UI or presentation
- persistent storage
- rule decisions

---

## Current implementation

### `buildTestWorld()`

```java
public static WorldState buildTestWorld();
```

## WorldState

## Purpose

`WorldState` represents the current state of the entire game world.

It acts as a central object for:

 - navigation
 - phase control
 - future save/load mechanics

```java
public class WorldState {
    private final RoomStateTest startRoom;
    private final List<RoomStateTest> allRooms;
}
```