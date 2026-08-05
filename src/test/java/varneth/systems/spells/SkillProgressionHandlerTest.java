package varneth.systems.spells;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.engine.events.EventBus;
import varneth.engine.events.SkillProgressed;
import varneth.systems.items.MagicCrystal;
import varneth.systems.items.MagicCrystalTemplates;

class SkillProgressionHandlerTest {

    @Test
    void successfulElementalCastsProgressPersistentSkillAndPublishEvents() {
        EventBus bus = new EventBus();
        List<SkillProgressed> events = new ArrayList<>();
        bus.subscribe(SkillProgressed.class, events::add);
        Skill pebbles = SpellTemplates.get("pebbles");
        AvailableSpell availableSpell = AvailableSpell.fromElemental(pebbles);
        SkillProgressionHandler handler = new SkillProgressionHandler(bus);

        SkillProgressed firstCast = handler.recordSuccessfulCast(availableSpell).orElseThrow();

        assertEquals(5, pebbles.getCurrentXp());
        assertEquals(1, pebbles.getLevel());
        assertEquals(0, firstCast.levelsGained());
        assertEquals(10, firstCast.experienceThreshold());

        SkillProgressed secondCast = handler.recordSuccessfulCast(availableSpell).orElseThrow();

        assertEquals(0, pebbles.getCurrentXp());
        assertEquals(2, pebbles.getLevel());
        assertEquals(14, pebbles.getModifier());
        assertEquals(1, secondCast.levelsGained());
        assertEquals(40, secondCast.experienceThreshold());
        assertEquals(2, events.size());
    }

    @Test
    void crystalCastDoesNotProgressTemporarySpell() {
        EventBus bus = new EventBus();
        List<SkillProgressed> events = new ArrayList<>();
        bus.subscribe(SkillProgressed.class, events::add);
        Skill flamethrower = SpellTemplates.get("flamethrower");
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        AvailableSpell availableSpell = AvailableSpell.fromCrystal(flamethrower, crystal, 5);

        assertTrue(new SkillProgressionHandler(bus).recordSuccessfulCast(availableSpell).isEmpty());

        assertEquals(0, flamethrower.getCurrentXp());
        assertEquals(1, flamethrower.getLevel());
        assertTrue(events.isEmpty());
    }

    @Test
    void skillXpCanCrossMultipleThresholdsAndKeepsRemainder() {
        Skill pebbles = SpellTemplates.get("pebbles");

        int levelsGained = pebbles.addCurrentXp(55);

        assertEquals(2, levelsGained);
        assertEquals(3, pebbles.getLevel());
        assertEquals(5, pebbles.getCurrentXp());
        assertEquals(90, pebbles.getCurrentXpThreshold());
    }
}
