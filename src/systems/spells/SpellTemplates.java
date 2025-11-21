package systems.spells;

import java.util.Map;

public class SpellTemplates {

    private static final SpellTemplate PEBBLES = new SpellTemplate(
            "pebbles",
            "Steinschleuder",
            "Macht geringen Schaden",
            1,
            0,
            1,
            10
    );

    private static final Map<String, SpellTemplate> BY_ID = Map.of(
            "pebbles", PEBBLES
    );

    public static SpellTemplate get(String id){
        return BY_ID.get(id);
    }
}
