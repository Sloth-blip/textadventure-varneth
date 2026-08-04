package varneth.systems.spells;

import varneth.systems.items.MagicCrystal;

public record AvailableSpell(
        Skill skill,
        SpellSource source,
        MagicCrystal crystal
) {

    public AvailableSpell {
        if (source == SpellSource.CRYSTAL && crystal == null) {
            throw new IllegalArgumentException("A crystal spell requires a crystal");
        }
        if (source == SpellSource.ELEMENTAL && crystal != null) {
            throw new IllegalArgumentException("An elemental spell cannot use a crystal");
        }
    }

    public static AvailableSpell fromElemental(Skill skill) {
        return new AvailableSpell(skill, SpellSource.ELEMENTAL, null);
    }

    public static AvailableSpell fromCrystal(Skill skill, MagicCrystal crystal) {
        return new AvailableSpell(skill, SpellSource.CRYSTAL, crystal);
    }

    public int cost() {return skill.getBaseManaCost();}
}
