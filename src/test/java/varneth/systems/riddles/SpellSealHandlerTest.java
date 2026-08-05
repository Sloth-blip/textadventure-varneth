package varneth.systems.riddles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.engine.GameState;
import varneth.engine.events.CrystalShattered;
import varneth.engine.events.EventBus;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;
import varneth.systems.items.MagicCrystal;
import varneth.systems.items.MagicCrystalTemplates;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.SpellTemplates;
import varneth.systems.world.WorldBuilder;

class SpellSealHandlerTest {

    @Test
    void missingKnowledgePreventsCastingAndOpening() {
        EventBus bus = new EventBus();
        GameState gameState = createGameState();
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        gameState.getPlayer().addItem(crystal);
        SpellSealDefinition seal = enterSealRoom(gameState);

        SpellSealResult result = new SpellSealHandler(bus).inspect(
                seal,
                gameState
        );

        assertEquals(SpellSealResult.MISSING_KNOWLEDGE, result);
        assertEquals(10, crystal.getCurrentCharge());
        assertFalse(gameState.hasStoryFlag(seal.getOpenedStoryFlag()));
        assertFalse(isSealPassageConnected(gameState));
    }

    @Test
    void wrongSpellStillConsumesItsResourceWithoutOpeningSeal() {
        EventBus bus = new EventBus();
        GameState gameState = createGameState();
        gameState.addStoryFlag("knowledge.fire_seal.sequence");
        gameState.getPlayer().addLearnedSkill(SpellTemplates.get("pebbles"));
        SpellSealDefinition seal = enterSealRoom(gameState);
        AvailableSpell pebbles = gameState.getPlayer().getAvailableSpells()
                .stream()
                .filter(option -> option.skill().getId().equals("pebbles"))
                .findFirst()
                .orElseThrow();

        SpellSealResult result = new SpellSealHandler(bus).attempt(
                seal,
                pebbles,
                gameState
        );

        assertEquals(SpellSealResult.WRONG_SPELL, result);
        assertEquals(20, gameState.getPlayer().getCurrentResource());
        assertFalse(isSealPassageConnected(gameState));
    }

    @Test
    void correctSpellFromWrongSourceIsRejectedAfterCasting() {
        EventBus bus = new EventBus();
        GameState gameState = createGameState();
        gameState.addStoryFlag("knowledge.fire_seal.sequence");
        gameState.getPlayer().addLearnedSkill(SpellTemplates.get("flamethrower"));
        SpellSealDefinition seal = enterSealRoom(gameState);
        AvailableSpell elementalFlamethrower =
                gameState.getPlayer().getAvailableSpells().get(0);

        SpellSealResult result = new SpellSealHandler(bus).attempt(
                seal,
                elementalFlamethrower,
                gameState
        );

        assertEquals(SpellSealResult.WRONG_SPELL, result);
        assertEquals(20, gameState.getPlayer().getCurrentResource());
        assertFalse(isSealPassageConnected(gameState));
    }

    @Test
    void crystalFlamethrowerConsumesChargeAndOpensPassageOnce() {
        EventBus bus = new EventBus();
        GameState gameState = createGameState();
        gameState.addStoryFlag("knowledge.fire_seal.sequence");
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        gameState.getPlayer().addItem(crystal);
        SpellSealDefinition seal = enterSealRoom(gameState);
        AvailableSpell flamethrower =
                gameState.getPlayer().getAvailableSpells().get(0);
        SpellSealHandler handler = new SpellSealHandler(bus);

        assertEquals(
                SpellSealResult.OPENED,
                handler.attempt(seal, flamethrower, gameState)
        );

        assertEquals(5, crystal.getCurrentCharge());
        assertTrue(gameState.hasStoryFlag("world.fire_seal.opened"));
        assertTrue(isSealPassageConnected(gameState));

        assertEquals(
                SpellSealResult.ALREADY_OPENED,
                handler.inspect(seal, gameState)
        );
        assertEquals(2, gameState.getCurrentRoom().getConnectedRooms().size());
        assertEquals(
                1,
                gameState.getWorld().getRoomById("4").getConnectedRooms().size()
        );
    }

    @Test
    void partialCrystalCastCanOpenSealAndPublishesCrystalBreak() {
        EventBus bus = new EventBus();
        List<CrystalShattered> events = new ArrayList<>();
        bus.subscribe(CrystalShattered.class, events::add);
        GameState gameState = createGameState();
        gameState.addStoryFlag("knowledge.fire_seal.sequence");
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        assertTrue(crystal.consumeCharge(6));
        gameState.getPlayer().addItem(crystal);
        SpellSealDefinition seal = enterSealRoom(gameState);
        AvailableSpell partialFlamethrower =
                gameState.getPlayer().getAvailableSpells().get(0);

        SpellSealResult result = new SpellSealHandler(bus).attempt(
                seal,
                partialFlamethrower,
                gameState
        );

        assertEquals(SpellSealResult.OPENED, result);
        assertTrue(gameState.getPlayer().getInventory().isEmpty());
        assertEquals(0, crystal.getCurrentCharge());
        assertEquals(1, events.size());
        assertEquals(
                crystal.getName(),
                events.get(0).crystalName()
        );
        assertTrue(isSealPassageConnected(gameState));
    }

    private SpellSealDefinition enterSealRoom(GameState gameState) {
        gameState.enterRoom("3");
        return gameState.getCurrentRoom().getPOIs().stream()
                .filter(pointOfInterest -> pointOfInterest.getId().equals("fire_seal"))
                .findFirst()
                .orElseThrow()
                .getSpellSeal()
                .orElseThrow();
    }

    private boolean isSealPassageConnected(GameState gameState) {
        return gameState.getWorld().getRoomById("3").getConnectedRooms().stream()
                .anyMatch(room -> room.getRoomId().equals("4"));
    }

    private GameState createGameState() {
        Player player = new Player(
                new ActorDefinition(
                        "Arenn",
                        40, 10,
                        20, 5,
                        10, 2,
                        15, 5,
                        10, 1,
                        10, 2,
                        MainAttribute.INTELLIGENCE
                ),
                new PlayerState(
                        50,
                        25,
                        1,
                        0,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        0
                )
        );
        return GameState.startNew(player, WorldBuilder.buildTestWorld());
    }
}
