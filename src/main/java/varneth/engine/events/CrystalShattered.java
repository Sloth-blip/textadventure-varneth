package varneth.engine.events;

public record CrystalShattered(
        String ownerName,
        String crystalName
) implements GameEvent {
}
