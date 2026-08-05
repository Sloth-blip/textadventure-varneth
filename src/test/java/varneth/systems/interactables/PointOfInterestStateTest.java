package varneth.systems.interactables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.systems.riddles.SpellSealDefinition;
import varneth.systems.spells.SpellSource;
import varneth.systems.riddles.RiddleDefinition;

class PointOfInterestStateTest {

    @Test
    void newStateStartsUnusedAndKeepsPersistenceFlag() {
        PointOfInterestState persistentState = new PointOfInterestState(true);
        PointOfInterestState temporaryState = new PointOfInterestState(false);

        assertFalse(persistentState.isUsed());
        assertTrue(persistentState.isPersistent());
        assertFalse(temporaryState.isPersistent());
    }

    @Test
    void markingPointOfInterestAsUsedChangesRuntimeState() {
        PointOfInterestState state = new PointOfInterestState(true);

        state.setPOIUsed();

        assertTrue(state.isUsed());
    }

    @Test
    void dialogChangesAfterPointOfInterestWasUsed() {
        PointOfInterest pointOfInterest = new PointOfInterest(
                new PointOfInterestDefinition(
                        "test",
                        "Test",
                        PointOfInterestType.STORY,
                        List.of(List.of("first"), List.of("repeat")),
                        null
                ),
                new PointOfInterestState(true)
        );

        assertEquals(List.of("first"), pointOfInterest.getDialogChunks());

        pointOfInterest.setPOIUsed();

        assertEquals(List.of("repeat"), pointOfInterest.getDialogChunks());
    }

    @Test
    void emptyDialogDefinitionReturnsEmptyChunk() {
        PointOfInterest pointOfInterest = new PointOfInterest(
                new PointOfInterestDefinition(
                        "empty",
                        "Empty",
                        PointOfInterestType.STORY,
                        List.of(),
                        null
                ),
                new PointOfInterestState(false)
        );

        assertTrue(pointOfInterest.getDialogChunks().isEmpty());
    }

    @Test
    void riddleTypeAndDefinitionMustBeProvidedTogether() {
        RiddleDefinition riddle = new RiddleDefinition(
                "test_riddle",
                "answer",
                "riddle.test.solved"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new PointOfInterestDefinition(
                        "missing_riddle",
                        "Missing",
                        PointOfInterestType.RIDDLE,
                        List.of(),
                        null
                )
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new PointOfInterestDefinition(
                        "wrong_type",
                        "Wrong",
                        PointOfInterestType.STORY,
                        List.of(),
                        null,
                        riddle
                )
        );

        PointOfInterestDefinition valid = new PointOfInterestDefinition(
                "valid_riddle",
                "Valid",
                PointOfInterestType.RIDDLE,
                List.of(),
                null,
                riddle
        );
        assertEquals(riddle, valid.getRiddle().orElseThrow());
    }

    @Test
    void spellSealTypeRequiresSpellSealDefinition() {
        SpellSealDefinition seal = new SpellSealDefinition(
                "test_seal",
                "source",
                "destination",
                "knowledge.test",
                "flamethrower",
                SpellSource.CRYSTAL,
                "world.test.opened"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new PointOfInterestDefinition(
                        "missing_seal",
                        "Missing",
                        PointOfInterestType.SPELL_SEAL,
                        List.of(),
                        null
                )
        );

        PointOfInterestDefinition valid = new PointOfInterestDefinition(
                "valid_seal",
                "Valid",
                PointOfInterestType.SPELL_SEAL,
                List.of(),
                null,
                seal
        );
        assertEquals(seal, valid.getSpellSeal().orElseThrow());
    }
}
