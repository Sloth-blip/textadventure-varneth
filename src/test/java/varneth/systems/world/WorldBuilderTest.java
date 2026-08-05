package varneth.systems.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import varneth.systems.items.Equipment;

class WorldBuilderTest {

    @Test
    void firstRoomBookRewardsPebblesAndEarthFocus() {
        var startRoom = WorldBuilder.buildTestWorld().getStartRoom();
        var book = startRoom.getPOIs().stream()
                .filter(pointOfInterest -> pointOfInterest.getName().equals("Buch"))
                .findFirst()
                .orElseThrow();

        assertEquals("pebbles", book.getRewards().getSkills().get(0).getId());
        Equipment focus = assertInstanceOf(
                Equipment.class,
                book.getRewards().getItems().get(0)
        );
        assertEquals("earth_focus", focus.getId());
    }
}
