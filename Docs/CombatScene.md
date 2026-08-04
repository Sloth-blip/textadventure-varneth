# CombatScene

## Purpose

`CombatScene` encapsulates the full flow of a combat encounter between the player and one or more enemy instances.

The class is responsible for:

- turn order handling (player → enemies)
- evaluating combat outcomes
- ending combat (victory, defeat, escape)

`CombatScene` still requests blocking player choices from `CombatConsoleMenu`.
Combat state, damage, defeat, and reward output are event-driven. The remaining
input coupling is temporary and can be replaced when a non-blocking UI is introduced.

---

## Responsibilities

### CombatScene is responsible for:

- the combat flow (round logic)
- damage calculation (via `Player` / `Enemy`)
- death checks (`Player` / `Enemy`)
- triggering rewards
- returning a `CombatResult`

### CombatScene is not responsible for:

- persistent game state outside of combat
- graphical presentation or animation
- menu layout or long-term input validation

---

## Combat Result

Combat ends with a `CombatResult`:

```java
public enum CombatResult {
    WON,
    LOST,
    FLED
} 
```

The result is then passed back to the `Gameloop`.