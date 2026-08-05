package varneth.engine.events;

import java.util.Optional;
import java.util.Objects;

import varneth.systems.items.EquipmentSlot;

public record EquipmentChanged(
        String ownerName,
        String itemName,
        EquipmentSlot slot,
        boolean equipped,
        Optional<String> replacedItemName
) implements GameEvent {

    public EquipmentChanged {
        Objects.requireNonNull(ownerName);
        Objects.requireNonNull(itemName);
        Objects.requireNonNull(slot);
        Objects.requireNonNull(replacedItemName);
        if (!equipped && replacedItemName.isPresent()) {
            throw new IllegalArgumentException("Unequipping cannot replace another item");
        }
    }
}
