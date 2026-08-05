package varneth.systems.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import varneth.systems.items.Equipment;
import varneth.systems.interactables.PointOfInterestType;
import varneth.systems.spells.SpellSource;

class WorldBuilderTest {

    @Test
    void firstRoomRuneRequiresRiddleBeforePebblesAndEarthFocusReward() {
        var startRoom = WorldBuilder.buildTestWorld().getStartRoom();
        var rune = startRoom.getPOIs().stream()
                .filter(pointOfInterest -> pointOfInterest.getId().equals("book"))
                .findFirst()
                .orElseThrow();

        assertEquals(PointOfInterestType.RIDDLE, rune.getType());
        assertEquals(
                "rune_book_pebbles",
                rune.getRiddle().orElseThrow().getId()
        );
        assertEquals("pebbles", rune.getRewards().getSkills().get(0).getId());
        Equipment focus = assertInstanceOf(
                Equipment.class,
                rune.getRewards().getItems().get(0)
        );
        assertEquals("earth_focus", focus.getId());
    }

    @Test
    void fireKnowledgeAndSealReferenceInitiallyHiddenDestination() {
        var world = WorldBuilder.buildTestWorld();
        var firePattern = world.getRoomById("2").getPOIs().stream()
                .filter(pointOfInterest -> pointOfInterest.getId().equals("fire_pattern"))
                .findFirst()
                .orElseThrow();
        var fireSeal = world.getRoomById("3").getPOIs().stream()
                .filter(pointOfInterest -> pointOfInterest.getId().equals("fire_seal"))
                .findFirst()
                .orElseThrow();

        assertEquals(PointOfInterestType.RIDDLE, firePattern.getType());
        assertEquals(
                "knowledge.fire_seal.sequence",
                firePattern.getRiddle().orElseThrow().getSolvedStoryFlag()
        );
        assertEquals(PointOfInterestType.SPELL_SEAL, fireSeal.getType());
        assertEquals(
                "4",
                fireSeal.getSpellSeal().orElseThrow().getDestinationRoomId()
        );
        assertEquals(
                SpellSource.CRYSTAL,
                fireSeal.getSpellSeal().orElseThrow().getRequiredSource()
        );
        assertFalse(
                world.getRoomById("3").getConnectedRooms().contains(
                        world.getRoomById("4")
                )
        );
    }
}
