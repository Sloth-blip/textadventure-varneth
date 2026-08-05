package varneth.systems.world;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import varneth.systems.rooms.Room;

public class WorldState {

    private final Room startRoom;
    private final List<Room> allRooms;

    public WorldState(Room startRoom, List<Room> allRooms) {
        this.startRoom = Objects.requireNonNull(startRoom);
        this.allRooms = List.copyOf(allRooms);

        if (this.allRooms.stream().noneMatch(room -> room == startRoom)) {
            throw new IllegalArgumentException("Start room must belong to the world");
        }

        Set<String> roomIds = new HashSet<>();
        for (Room room : this.allRooms) {
            if (!roomIds.add(room.getRoomId())) {
                throw new IllegalArgumentException(
                        "Duplicate room id: " + room.getRoomId()
                );
            }
        }
    }

    public Room getStartRoom() {return startRoom;}
    public List<Room> getAllRooms() {return allRooms;}

    public Optional<Room> findRoomById(String roomId) {
        Objects.requireNonNull(roomId);
        return allRooms.stream()
                .filter(room -> room.getRoomId().equals(roomId))
                .findFirst();
    }

    public Room getRoomById(String roomId) {
        return findRoomById(roomId).orElseThrow(
                () -> new IllegalArgumentException("Unknown room id: " + roomId)
        );
    }
}
