package varneth.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.Test;

import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;
import varneth.systems.world.WorldBuilder;

class GameStateTest {

    @Test
    void newGameStartsAtCanonicalWorldStartAndTracksFirstVisits() {
        Player player = createPlayer();
        var world = WorldBuilder.buildTestWorld();
        GameState gameState = GameState.startNew(player, world);

        assertSame(player, gameState.getPlayer());
        assertSame(world, gameState.getWorld());
        assertSame(world.getStartRoom(), gameState.getCurrentRoom());
        assertTrue(gameState.markCurrentRoomVisited());
        assertFalse(gameState.markCurrentRoomVisited());
        assertTrue(gameState.getVisitedRoomIds().contains("1"));
    }

    @Test
    void roomChangesAndRestoredStateResolveStableIdsThroughWorld() {
        Player player = createPlayer();
        var world = WorldBuilder.buildTestWorld();
        GameState gameState = GameState.startNew(player, world);

        gameState.enterRoom("2");

        assertSame(world.getRoomById("2"), gameState.getCurrentRoom());

        GameState restored = new GameState(
                player,
                world,
                "2",
                Set.of("1", "2")
        );

        assertSame(world.getRoomById("2"), restored.getCurrentRoom());
        assertTrue(restored.getVisitedRoomIds().containsAll(Set.of("1", "2")));
    }

    @Test
    void rejectsUnknownCurrentVisitedAndDestinationRoomIds() {
        Player player = createPlayer();
        var world = WorldBuilder.buildTestWorld();

        assertThrows(
                IllegalArgumentException.class,
                () -> new GameState(player, world, "missing", Set.of())
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameState(player, world, "1", Set.of("missing"))
        );

        GameState gameState = GameState.startNew(player, world);
        assertThrows(
                IllegalArgumentException.class,
                () -> gameState.enterRoom("missing")
        );
    }

    @Test
    void tracksAndRestoresStoryFlagsByStableId() {
        Player player = createPlayer();
        var world = WorldBuilder.buildTestWorld();
        GameState gameState = GameState.startNew(player, world);

        assertFalse(gameState.hasStoryFlag("riddle.stone_door.solved"));
        assertTrue(gameState.addStoryFlag("riddle.stone_door.solved"));
        assertFalse(gameState.addStoryFlag("riddle.stone_door.solved"));
        assertTrue(gameState.hasStoryFlag("riddle.stone_door.solved"));

        GameState restored = new GameState(
                player,
                world,
                "1",
                Set.of("1"),
                gameState.getStoryFlags()
        );

        assertEquals(
                Set.of("riddle.stone_door.solved"),
                restored.getStoryFlags()
        );
        assertThrows(
                UnsupportedOperationException.class,
                () -> restored.getStoryFlags().add("another.flag")
        );
    }

    @Test
    void rejectsInvalidStoryFlags() {
        Player player = createPlayer();
        var world = WorldBuilder.buildTestWorld();
        GameState gameState = GameState.startNew(player, world);

        assertThrows(NullPointerException.class, () -> gameState.addStoryFlag(null));
        assertThrows(IllegalArgumentException.class, () -> gameState.addStoryFlag(" "));
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameState(
                        player,
                        world,
                        "1",
                        Set.of(),
                        Set.of("")
                )
        );
    }

    private Player createPlayer() {
        return new Player(
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
    }
}
