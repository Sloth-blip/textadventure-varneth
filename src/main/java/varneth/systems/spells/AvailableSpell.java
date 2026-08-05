package varneth.systems.spells;

import varneth.systems.items.MagicCrystal;

public record AvailableSpell(
        Skill skill,
        SpellSource source,
        MagicCrystal crystal,
        int requiredCost,
        int paymentAmount,
        boolean crystalBreaks
) {

    public AvailableSpell {
        if (requiredCost <= 0 || paymentAmount <= 0 || paymentAmount > requiredCost) {
            throw new IllegalArgumentException("Casting costs must be positive and payment cannot exceed requirement");
        }
        if (source == SpellSource.CRYSTAL && crystal == null) {
            throw new IllegalArgumentException("A crystal spell requires a crystal");
        }
        if (source == SpellSource.ELEMENTAL && crystal != null) {
            throw new IllegalArgumentException("An elemental spell cannot use a crystal");
        }
        boolean isPartialCrystalCast =
                source == SpellSource.CRYSTAL && paymentAmount < requiredCost;
        if (crystalBreaks != isPartialCrystalCast) {
            throw new IllegalArgumentException("Only a partial crystal cast can break its crystal");
        }
    }

    public static AvailableSpell fromElemental(Skill skill) {
        int cost = skill.getBaseManaCost();
        return new AvailableSpell(
                skill,
                SpellSource.ELEMENTAL,
                null,
                cost,
                cost,
                false
        );
    }

    public static AvailableSpell fromCrystal(
            Skill skill,
            MagicCrystal crystal,
            int requiredCost
    ) {
        int paymentAmount = Math.min(requiredCost, crystal.getCurrentCharge());
        if (paymentAmount <= 0) {
            throw new IllegalArgumentException("A crystal spell requires remaining charge");
        }
        return new AvailableSpell(
                skill,
                SpellSource.CRYSTAL,
                crystal,
                requiredCost,
                paymentAmount,
                paymentAmount < requiredCost
        );
    }

    public int cost() {return requiredCost;}

    public boolean isPartialCast() {return paymentAmount < requiredCost;}

    public int effectivenessPercent() {
        return (int) Math.round((double) paymentAmount * 100 / requiredCost);
    }

    public int scaleDamage(int fullDamage) {
        if (fullDamage <= 0) {
            return 0;
        }
        long scaledDamage = Math.round((double) fullDamage * paymentAmount / requiredCost);
        return (int) Math.max(1L, Math.min(Integer.MAX_VALUE, scaledDamage));
    }
}
