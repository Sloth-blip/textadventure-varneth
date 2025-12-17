package systems.spells;

import systems.actors.MainAttribute;

import java.util.Map;

public class SpellTemplates {

    private static final SkillDefinition PEBBLES =
            new SkillDefinition(
                  "pebbles",
                    "Steinschleuder",
                    5,
                    10,
                    2,
                    10,
                    2,
                    MainAttribute.INTELLIGENCE
            );

    private static final Map<String, SkillDefinition> BY_ID = Map.of(
            "pebbles", PEBBLES
    );

    public static Skill get(String id){
        SkillDefinition def = BY_ID.get(id);
        return new Skill(
                def,
                new SkillState(
                        1,
                        0
                )
        );

    }

}
