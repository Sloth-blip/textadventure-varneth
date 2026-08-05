package varneth.systems.magic;

public final class CrystalCastingCost {

    public static final int BASELINE_WISDOM = 10;

    private CrystalCastingCost() {}

    public static int calculate(int baseCost, int wisdom) {
        if (baseCost <= 0) {
            throw new IllegalArgumentException("Base cost must be positive");
        }
        if (wisdom < 0) {
            throw new IllegalArgumentException("Wisdom must not be negative");
        }

        int effectiveWisdom = Math.max(1, wisdom);
        long numerator = (long) baseCost * BASELINE_WISDOM;
        long effectiveCost = (numerator + effectiveWisdom - 1L) / effectiveWisdom;
        return (int) Math.max(1L, Math.min(Integer.MAX_VALUE, effectiveCost));
    }
}
