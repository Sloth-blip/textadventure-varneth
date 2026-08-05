package varneth.systems.items;

public record EquipmentModifiers(
        int strength,
        int intelligence,
        int wisdom
) {

    public static EquipmentModifiers none() {
        return new EquipmentModifiers(0, 0, 0);
    }
}
