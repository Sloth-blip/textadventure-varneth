package varneth.systems.reward;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.engine.events.EventBus;
import varneth.engine.events.RewardGranted;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;
import varneth.systems.interactables.PointOfInterest;
import varneth.systems.interactables.PointOfInterestDefinition;
import varneth.systems.interactables.PointOfInterestState;
import varneth.systems.interactables.PointOfInterestType;
import varneth.systems.items.MagicCrystal;
import varneth.systems.items.MagicCrystalTemplates;
import varneth.systems.spells.Skill;
import varneth.systems.spells.SpellTemplates;

class RewardHandlerTest {

    @Test
    void grantsCombinedRewardAndPublishesAppliedResult() {
        EventBus bus = new EventBus();
        List<RewardGranted> events = new ArrayList<>();
        bus.subscribe(RewardGranted.class, events::add);
        Player player = createPlayer();
        Skill pebbles = SpellTemplates.get("pebbles");
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        Reward reward = new Reward(
                List.of(pebbles),
                List.of(crystal),
                25,
                15
        );

        RewardGranted result = new RewardHandler(bus).grantRewards(reward, player);

        assertEquals(List.of(pebbles), player.getLearnedSkills());
        assertEquals(List.of(crystal), player.getInventory());
        assertEquals(15, player.getGold());
        assertEquals(2, player.getLevel());
        assertEquals(15, player.getCurrentXp());
        assertEquals(1, result.levelsGained());
        assertEquals(2, result.resultingLevel());
        assertEquals(List.of("Steinschleuder"), result.learnedSkillNames());
        assertEquals(List.of("Feuermagiekristall"), result.receivedItemNames());
        assertEquals(1, events.size());
        assertSame(result, events.get(0));
    }

    @Test
    void usedPointOfInterestCannotGrantItsRewardAgain() {
        EventBus bus = new EventBus();
        List<RewardGranted> events = new ArrayList<>();
        bus.subscribe(RewardGranted.class, events::add);
        Player player = createPlayer();
        PointOfInterest pointOfInterest = new PointOfInterest(
                new PointOfInterestDefinition(
                        "coin_cache",
                        "Münzversteck",
                        PointOfInterestType.STORY,
                        List.of(List.of("Gefunden."), List.of("Leer.")),
                        new Reward(0, 7)
                ),
                new PointOfInterestState(true)
        );
        RewardHandler handler = new RewardHandler(bus);

        assertTrue(handler.grantRewardsFromPOI(pointOfInterest, player).isPresent());
        pointOfInterest.setPOIUsed();
        assertFalse(handler.grantRewardsFromPOI(pointOfInterest, player).isPresent());

        assertEquals(7, player.getGold());
        assertEquals(1, events.size());
    }

    private Player createPlayer() {
        ActorDefinition definition = new ActorDefinition(
                "Arenn",
                40, 10,
                20, 5,
                10, 2,
                15, 5,
                10, 1,
                10, 1,
                MainAttribute.INTELLIGENCE
        );
        PlayerState state = new PlayerState(
                50,
                25,
                1,
                0,
                new ArrayList<>(),
                new ArrayList<>(),
                0
        );
        return new Player(definition, state);
    }
}
