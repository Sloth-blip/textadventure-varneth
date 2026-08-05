package varneth.systems.spells;

import java.util.List;
import java.util.Map;

import varneth.systems.actors.MainAttribute;
import varneth.systems.magic.MagicType;

public class SpellTemplates {

    private static final SkillDefinition PEBBLES =
            new SkillDefinition(
                    "pebbles",
                    "Steinschleuder",
                    "Ein einfacher Erdzauber, der lose Steine weckt und mit arkaner Kraft auf sein Ziel schleudert.",
                    MagicType.EARTH,
                    5,
                    10,
                    2,
                    10,
                    2,
                    5,
                    MainAttribute.INTELLIGENCE
            );

    private static final SkillDefinition FLAMETHROWER =
            new SkillDefinition(
                    "flamethrower",
                    "Flammenwerfer",
                    "Der Kristall stößt einen kurzen, ungezähmten Feuerstrom aus.",
                    MagicType.FIRE,
                    5,
                    10,
                    2,
                    10,
                    2,
                    5,
                    MainAttribute.INTELLIGENCE
            );

    private static final Map<String, SkillDefinition> BY_ID = Map.of(
            "pebbles", PEBBLES,
            "flamethrower", FLAMETHROWER
    );

    private static final Map<MagicType, List<SkillDefinition>> BASIC_BY_MAGIC_TYPE = Map.of(
            MagicType.FIRE, List.of(FLAMETHROWER)
    );

    public static Skill get(String id){
        SkillDefinition def = BY_ID.get(id);
        if (def == null) {
            throw new IllegalArgumentException("Unknown spell: " + id);
        }
        return createSkill(def);
    }

    public static List<Skill> getBasicSpellsFor(MagicType magicType) {
        return BASIC_BY_MAGIC_TYPE.getOrDefault(magicType, List.of()).stream()
                .map(SpellTemplates::createSkill)
                .toList();
    }

    private static Skill createSkill(SkillDefinition def) {
        return new Skill(
                def,
                new SkillState(
                        1,
                        0
                )
        );
    }
}
