package varneth.engine.events;

public record CombatStateChanged(
        CombatSnapshot snapshot
) implements GameEvent {}
