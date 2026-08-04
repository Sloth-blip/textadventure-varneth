package varneth.systems.interactables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

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
}
