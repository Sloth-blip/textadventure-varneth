package varneth.systems.world;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.systems.rooms.Room;
import varneth.systems.rooms.RoomDefinition;
import varneth.systems.rooms.RoomState;

class WorldStateTest {

    @Test
    void resolvesCanonicalRoomByStableId() {
        WorldState world = WorldBuilder.buildTestWorld();

        assertSame(world.getAllRooms().get(1), world.getRoomById("2"));
        assertThrows(
                UnsupportedOperationException.class,
                () -> world.getAllRooms().clear()
        );
    }

    @Test
    void demoDevelopmentWorldAlsoSatisfiesWorldInvariants() {
        assertDoesNotThrow(WorldBuilder::buildTestWorldTwo);
    }

    @Test
    void rejectsStartOutsideWorldAndDuplicateRoomIds() {
        Room start = createRoom("start");
        Room duplicateA = createRoom("duplicate");
        Room duplicateB = createRoom("duplicate");

        assertThrows(
                IllegalArgumentException.class,
                () -> new WorldState(start, List.of(duplicateA))
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new WorldState(
                        start,
                        List.of(start, duplicateA, duplicateB)
                )
        );
    }

    private Room createRoom(String id) {
        return new Room(
                new RoomDefinition(id, id, id, 0, 0),
                new RoomState(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        List.of()
                )
        );
    }
}
