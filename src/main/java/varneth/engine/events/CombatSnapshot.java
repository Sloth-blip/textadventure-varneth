package varneth.engine.events;

import java.util.List;

public record CombatSnapshot(
        CombatantSnapshot player,
        List<CombatantSnapshot> enemies
) {

    public CombatSnapshot {
        enemies = List.copyOf(enemies);
    }
}
