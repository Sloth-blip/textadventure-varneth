package varneth.systems.riddles;

import java.util.Objects;

import varneth.engine.GameState;

public class RiddleHandler {

    public RiddleAttemptResult attempt(
            RiddleDefinition riddle,
            String answer,
            GameState gameState
    ) {
        Objects.requireNonNull(riddle);
        Objects.requireNonNull(gameState);

        if (gameState.hasStoryFlag(riddle.getSolvedStoryFlag())) {
            return RiddleAttemptResult.ALREADY_SOLVED;
        }
        if (!riddle.accepts(answer)) {
            return RiddleAttemptResult.INCORRECT;
        }

        gameState.addStoryFlag(riddle.getSolvedStoryFlag());
        return RiddleAttemptResult.SOLVED;
    }
}
