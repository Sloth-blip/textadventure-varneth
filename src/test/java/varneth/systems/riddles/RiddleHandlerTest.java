package varneth.systems.riddles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import varneth.engine.GameState;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;
import varneth.systems.world.WorldBuilder;

class RiddleHandlerTest {

    private static final String SOLVED_FLAG =
            "riddle.rune_book_pebbles.solved";

    @Test
    void incorrectAnswerDoesNotChangeStoryState() {
        GameState gameState = createGameState();
        RiddleDefinition riddle = createRiddle();

        RiddleAttemptResult result = new RiddleHandler().attempt(
                riddle,
                "Flammenwerfer",
                gameState
        );

        assertEquals(RiddleAttemptResult.INCORRECT, result);
        assertFalse(gameState.hasStoryFlag(SOLVED_FLAG));
    }

    @Test
    void normalizedCorrectAnswerSolvesRiddleExactlyOnce() {
        GameState gameState = createGameState();
        RiddleDefinition riddle = createRiddle();
        RiddleHandler handler = new RiddleHandler();

        assertEquals(
                RiddleAttemptResult.SOLVED,
                handler.attempt(riddle, "  steinschleuder ", gameState)
        );
        assertTrue(gameState.hasStoryFlag(SOLVED_FLAG));
        assertEquals(
                RiddleAttemptResult.ALREADY_SOLVED,
                handler.attempt(riddle, "anything", gameState)
        );
    }

    @Test
    void definitionRejectsMissingPrototypeContracts() {
        assertThrows(
                NullPointerException.class,
                () -> new RiddleDefinition(null, "answer", "flag")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new RiddleDefinition("id", " ", "flag")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new RiddleDefinition("id", "answer", "")
        );
    }

    private RiddleDefinition createRiddle() {
        return new RiddleDefinition(
                "rune_book_pebbles",
                "Steinschleuder",
                SOLVED_FLAG
        );
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
