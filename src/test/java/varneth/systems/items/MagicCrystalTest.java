package varneth.systems.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import varneth.systems.magic.MagicType;

class MagicCrystalTest {

    @Test
    void matchingCrystalConsumesChargeWithoutDroppingBelowZero() {
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");

        assertTrue(crystal.canPower(MagicType.FIRE, 5));
        assertFalse(crystal.canPower(MagicType.EARTH, 5));
        assertTrue(crystal.consumeCharge(5));
        assertEquals(5, crystal.getCurrentCharge());

        assertFalse(crystal.consumeCharge(6));
        assertEquals(5, crystal.getCurrentCharge());
    }
}
