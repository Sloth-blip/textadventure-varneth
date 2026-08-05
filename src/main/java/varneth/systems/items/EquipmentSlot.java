package varneth.systems.items;

public enum EquipmentSlot {
    MAIN_HAND("Haupthand"),
    BODY("Körper"),
    ACCESSORY("Accessoire");

    private final String displayName;

    EquipmentSlot(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
