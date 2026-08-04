package varneth.systems.actors.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertTrue(player.getAvailableSpells().isEmpty());
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
    void elementalSpellIsUnavailableWithoutEnoughPlayerResource() {
        Player player = createPlayer(4);
        player.addLearnedSkill(SpellTemplates.get("pebbles"));

        assertTrue(player.getAvailableSpells().isEmpty());
    }

    private Player createPlayer(int currentResource) {
        ActorDefinition definition = new ActorDefinition(
                "Arenn",
                40, 10,
                20, 5,
                10, 2,
                15, 5,
                10, 1,
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
