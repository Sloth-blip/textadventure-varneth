package varneth.systems.items;

import java.util.Objects;

public class EquipmentDefinition {

    private final String id;
    private final String name;
    private final String description;
    private final EquipmentSlot slot;
    private final EquipmentModifiers modifiers;

    public EquipmentDefinition(
            String id,
            String name,
            String description,
            EquipmentSlot slot,
            EquipmentModifiers modifiers
    ) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Equipment id and name must not be blank");
        }
        this.id = id;
        this.name = name;
        this.description = Objects.requireNonNull(description);
        this.slot = Objects.requireNonNull(slot);
        this.modifiers = Objects.requireNonNull(modifiers);
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public EquipmentSlot getSlot() {return slot;}
    public EquipmentModifiers getModifiers() {return modifiers;}
}
