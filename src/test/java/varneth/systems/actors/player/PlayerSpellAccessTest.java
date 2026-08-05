package varneth.systems.actors.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.engine.events.EventBus;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.items.MagicCrystal;
import varneth.systems.items.MagicCrystalTemplates;
import varneth.systems.reward.Reward;
import varneth.systems.reward.RewardHandler;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.SpellSource;
import varneth.systems.spells.SpellTemplates;

class PlayerSpellAccessTest {

    @Test
    void fireCrystalRewardUnlocksBasicSpellWithoutLearningIt() {
        Player player = createPlayer(25);
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");

        new RewardHandler(new EventBus()).grantRewards(new Reward(crystal), player);

        assertEquals(List.of(crystal), player.getInventory());
        assertTrue(player.getLearnedSkills().isEmpty());

        AvailableSpell availableSpell = player.getAvailableSpells().get(0);
        assertEquals("flamethrower", availableSpell.skill().getId());
        assertEquals(SpellSource.CRYSTAL, availableSpell.source());
        assertSame(crystal, availableSpell.crystal());
    }

    @Test
    void crystalSpellConsumesCrystalChargeButNotPlayerResource() {
        Player player = createPlayer(25);
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        player.addItem(crystal);
        AvailableSpell flamethrower = player.getAvailableSpells().get(0);

        assertTrue(player.tryPayCastingCost(flamethrower));

        assertEquals(5, crystal.getCurrentCharge());
        assertEquals(25, player.getCurrentResource());

        assertTrue(player.tryPayCastingCost(flamethrower));
        assertEquals(0, crystal.getCurrentCharge());
        assertTrue(player.getInventory().contains(crystal));
        assertTrue(player.getAvailableSpells().isEmpty());
    }

    @Test
    void partialCrystalCastScalesDamageAndDestroysCrystal() {
        Player player = createPlayer(25);
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        assertTrue(crystal.consumeCharge(6));
        player.addItem(crystal);

        AvailableSpell partialCast = player.getAvailableSpells().get(0);

        assertTrue(partialCast.isPartialCast());
        assertTrue(partialCast.crystalBreaks());
        assertEquals(5, partialCast.requiredCost());
        assertEquals(4, partialCast.paymentAmount());
        assertEquals(80, partialCast.effectivenessPercent());
        assertEquals(26, partialCast.scaleDamage(32));
        assertEquals(1, partialCast.scaleDamage(1));

        assertTrue(player.tryPayCastingCost(partialCast));

        assertEquals(0, crystal.getCurrentCharge());
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    void fullCrystalIsPreferredOverEarlierPartialCrystal() {
        Player player = createPlayer(25);
        MagicCrystal partialCrystal = MagicCrystalTemplates.get("fire_crystal");
        MagicCrystal fullCrystal = MagicCrystalTemplates.get("fire_crystal");
        assertTrue(partialCrystal.consumeCharge(6));
        player.addItem(partialCrystal);
        player.addItem(fullCrystal);

        AvailableSpell selectedCast = player.getAvailableSpells().get(0);

        assertSame(fullCrystal, selectedCast.crystal());
        assertFalse(selectedCast.isPartialCast());
        assertTrue(player.tryPayCastingCost(selectedCast));
        assertEquals(5, fullCrystal.getCurrentCharge());
        assertEquals(4, partialCrystal.getCurrentCharge());
    }

    @Test
    void highestRemainingChargeIsPreferredWhenOnlyPartialCastsExist() {
        Player player = createPlayer(25);
        MagicCrystal lowerCharge = MagicCrystalTemplates.get("fire_crystal");
        MagicCrystal higherCharge = MagicCrystalTemplates.get("fire_crystal");
        assertTrue(lowerCharge.consumeCharge(8));
        assertTrue(higherCharge.consumeCharge(6));
        player.addItem(lowerCharge);
        player.addItem(higherCharge);

        AvailableSpell selectedCast = player.getAvailableSpells().get(0);

        assertSame(higherCharge, selectedCast.crystal());
        assertTrue(selectedCast.isPartialCast());
        assertEquals(4, selectedCast.paymentAmount());
    }

    @Test
    void elementalSpellConsumesPlayerResourceButNotCrystalCharge() {
        Player player = createPlayer(25);
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        player.addItem(crystal);
        player.addLearnedSkill(SpellTemplates.get("pebbles"));
        AvailableSpell pebbles = player.getAvailableSpells().stream()
                .filter(option -> option.skill().getId().equals("pebbles"))
                .findFirst()
                .orElseThrow();

        assertTrue(player.tryPayCastingCost(pebbles));

        assertEquals(20, player.getCurrentResource());
        assertEquals(10, crystal.getCurrentCharge());
    }

    @Test
    void levelAdjustedWisdomRecalculatesCrystalCostBetweenCasts() {
        Player player = createPlayer(25);
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        player.addItem(crystal);

        AvailableSpell firstCast = player.getAvailableSpells().get(0);
        assertEquals(11, player.getWisdom());
        assertEquals(5, firstCast.cost());
        assertTrue(player.tryPayCastingCost(firstCast));
        assertEquals(5, crystal.getCurrentCharge());

        player.levelUp();
        player.levelUp();
        player.levelUp();
        player.levelUp();

        AvailableSpell secondCast = player.getAvailableSpells().get(0);
        assertEquals(15, player.getWisdom());
        assertEquals(4, secondCast.cost());
        assertTrue(player.tryPayCastingCost(secondCast));
        assertEquals(1, crystal.getCurrentCharge());
        assertEquals(player.getMaxResource(), player.getCurrentResource());
    }

    @Test
    void wisdomReducesCrystalCostButNotElementalResourceCost() {
        Player player = createPlayer(25, 20, 0);
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        player.addItem(crystal);
        player.addLearnedSkill(SpellTemplates.get("pebbles"));

        AvailableSpell elementalSpell = player.getAvailableSpells().stream()
                .filter(option -> option.source() == SpellSource.ELEMENTAL)
                .findFirst()
                .orElseThrow();
        AvailableSpell crystalSpell = player.getAvailableSpells().stream()
                .filter(option -> option.source() == SpellSource.CRYSTAL)
                .findFirst()
                .orElseThrow();

        assertEquals(5, elementalSpell.cost());
        assertEquals(3, crystalSpell.cost());
        assertTrue(player.tryPayCastingCost(crystalSpell));
        assertEquals(7, crystal.getCurrentCharge());
        assertEquals(25, player.getCurrentResource());

        assertTrue(player.tryPayCastingCost(elementalSpell));
        assertEquals(7, crystal.getCurrentCharge());
        assertEquals(20, player.getCurrentResource());
    }

    @Test
    void elementalSpellIsUnavailableWithoutEnoughPlayerResource() {
        Player player = createPlayer(4);
        player.addLearnedSkill(SpellTemplates.get("pebbles"));

        assertTrue(player.getAvailableSpells().isEmpty());
    }

    private Player createPlayer(int currentResource) {
        return createPlayer(currentResource, 10, 1);
    }

    private Player createPlayer(int currentResource, int baseWisdom, int wisdomPerLevel) {
        ActorDefinition definition = new ActorDefinition(
                "Arenn",
                40, 10,
                20, 5,
                10, 2,
                15, 5,
                baseWisdom, wisdomPerLevel,
                10, 2,
                MainAttribute.INTELLIGENCE
        );
        PlayerState state = new PlayerState(
                50,
                currentResource,
                1,
                0,
                new ArrayList<>(),
                new ArrayList<>(),
                0
        );
        return new Player(definition, state);
    }
}
