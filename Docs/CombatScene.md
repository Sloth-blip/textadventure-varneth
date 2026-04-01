# CombatScene

## Purpose

`CombatScene` encapsulates the full flow of a combat encounter between the player and one or more enemy instances.

The class is responsible for:

- turn order handling (player → enemies)
- evaluating combat outcomes
- ending combat (victory, defeat, escape)

At the moment, the class still contains **both game logic and console UI calls**.
This coupling is **intentional but temporary** and will be resolved step by step as the architecture evolves.

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