package varneth.systems.items;

import java.util.Map;

public class EquipmentTemplates {

    private static final EquipmentDefinition EARTH_FOCUS =
            new EquipmentDefinition(
                    "earth_focus",
                    "Erdmagischer Fokus",
                    "Ein handgroßer Fokus aus geschichtetem Schiefer. "
                            + "Feine Adern darin antworten auf Arenns Magie.",
                    EquipmentSlot.MAIN_HAND,
                    new EquipmentModifiers(0, 2, 1)
            );

    private static final Map<String, EquipmentDefinition> BY_ID = Map.of(
            "earth_focus", EARTH_FOCUS
    );

    public static Equipment get(String id) {
        EquipmentDefinition def = BY_ID.get(id);
        if (def == null) {
            throw new IllegalArgumentException("Unknown equipment: " + id);
        }
        return new Equipment(def);
    }
}
