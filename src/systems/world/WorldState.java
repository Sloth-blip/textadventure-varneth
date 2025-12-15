package systems.world;

import systems.rooms.RoomStateTest;

import java.util.List;

public class WorldState {

    private final RoomStateTest startRoom;
    private final List<RoomStateTest> allRooms;


    public WorldState(RoomStateTest startRoom, List<RoomStateTest> allRooms) {
        this.startRoom = startRoom;
        this.allRooms = allRooms;
    }

    /** Getter **/

    public RoomStateTest getStartRoom() {return this.startRoom;}
    public List<RoomStateTest> getAllRooms() {return this.allRooms;}

}
