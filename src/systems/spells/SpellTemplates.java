package systems.spells;

import java.util.Map;

public class SpellTemplates {

    private static final Skill PEBBLES =
            new Skill(new Skilldefinition(
                  "pebbles",
                    "Steinschleuder",
                    5,
                    10,
                    2,
                    10,
                    2,
                    ModifyingAttribute.INTELLIGENCE
            ),
                    new SkillState(
                            1,
                            0
                    )
            );

    private static final Map<String, Skill> BY_ID = Map.of(
            "pebbles", PEBBLES
    );

    public static Skill get(String id){
        return BY_ID.get(id);
    }
}
