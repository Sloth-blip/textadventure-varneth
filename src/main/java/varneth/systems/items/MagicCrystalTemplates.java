package varneth.systems.items;

import java.util.Map;

import varneth.systems.magic.MagicType;

public class MagicCrystalTemplates {

    private static final MagicCrystalDefinition FIRE_CRYSTAL =
            new MagicCrystalDefinition(
                    "fire_crystal",
                    "Feuermagiekristall",
                    "Ein kleiner Speicherkristall, in dessen Innerem träge Flammen pulsieren.",
                    MagicType.FIRE,
                    10
            );

    private static final Map<String, MagicCrystalDefinition> BY_ID = Map.of(
            "fire_crystal", FIRE_CRYSTAL
    );

    public static MagicCrystal get(String id) {
        MagicCrystalDefinition def = BY_ID.get(id);
        if (def == null) {
            throw new IllegalArgumentException("Unknown magic crystal: " + id);
        }
        return new MagicCrystal(def, new MagicCrystalState(def.getMaxCharge()));
    }
}
