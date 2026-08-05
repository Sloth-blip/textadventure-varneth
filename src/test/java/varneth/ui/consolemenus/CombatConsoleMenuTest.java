package varneth.ui.consolemenus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import varneth.systems.items.MagicCrystal;
import varneth.systems.items.MagicCrystalTemplates;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.SpellTemplates;

class CombatConsoleMenuTest {

    @Test
    void partialCastLabelWarnsAboutEffectivenessAndCrystalBreaking() {
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        assertTrue(crystal.consumeCharge(6));
        AvailableSpell partialCast = AvailableSpell.fromCrystal(
                SpellTemplates.get("flamethrower"),
                crystal,
                5
        );

        assertEquals(
                "Feuermagiekristall 4/10, benötigt 5, Restcast 80% – Kristall zerbricht",
                new CombatConsoleMenu().spellSourceLabel(partialCast)
        );
    }
}
