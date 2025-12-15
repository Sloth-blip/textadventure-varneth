package systems.world;

import systems.rooms.Room;

import java.util.List;

public class WorldState {

    private final Room startRoom;
    private final List<Room> allRooms;


    public WorldState(Room startRoom, List<Room> allRooms) {
        this.startRoom = startRoom;
        this.allRooms = allRooms;
    }

    /** Getter **/

    public Room getStartRoom() {return this.startRoom;}
    public List<Room> getAllRooms() {return this.allRooms;}

}
