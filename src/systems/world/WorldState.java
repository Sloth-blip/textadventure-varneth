package systems.world;

import systems.rooms.RoomState;

import java.util.List;

public class WorldState {

    private final RoomState startRoom;
    private final List<RoomState> allRooms;


    public WorldState(RoomState startRoom, List<RoomState> allRooms) {
        this.startRoom = startRoom;
        this.allRooms = allRooms;
    }

    /** Getter **/

    public RoomState getStartRoom() {return this.startRoom;}
    public List<RoomState> getAllRooms() {return this.allRooms;}

}
