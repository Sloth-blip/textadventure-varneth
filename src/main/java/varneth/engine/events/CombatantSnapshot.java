package varneth.engine.events;

public record CombatantSnapshot(
        String name,
        int currentHp,
        int maxHp,
        int currentResource,
        int maxResource
) {}
