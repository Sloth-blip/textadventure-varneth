package varneth.systems.rooms;

import java.util.List;
import java.util.Objects;

import varneth.systems.actors.enemy.Enemy;
import varneth.systems.actors.npc.NPC;
import varneth.systems.interactables.PointOfInterest;

public class Room {

    private final RoomDefinition def;
    private final RoomState state;

    public Room(
            RoomDefinition def,
            RoomState state
    )
    {
        this.def = def;
        this.state = state;
    }

    @Override
    public String toString(){return def.toString();}


    /** Getter **/

    public String getRoomId() {return def.getRoomId();}
    public String getName() {return def.getRoomName();}
    public String getRoomDescription(){return def.getRoomDescription();}
    public int getMapX() {return def.getMapX();}
    public int getMapY() {return def.getMapY();}

    public List<Enemy> getEnemies(){return state.getEnemies();}
    public List<NPC> getNpcs() {return state.getNpcs();}
    public List<PointOfInterest> getPOIs(){return state.getPOIs();}
    public List<Room> getConnectedRooms() {return state.getConnectedRooms();}
    public List<String> getRoomDialogChunks(){return state.getRoomDialogChunks();}


    /** Setter **/

    public void setEnemies(List<Enemy> enemies) {state.setEnemies(enemies);}
    public void setNpcs(List<NPC> npcs) {state.setNpcs(npcs);}
    public void setPOIs(List <PointOfInterest> pOIs) {state.setPOIs(pOIs);}
    public void setConnectedRooms(List<Room> connectedRooms) {state.setConnectedRooms(connectedRooms);}
    public void setRoomDialogChunks(List<String> roomDialogChunks) {state.setRoomDialogChunks(roomDialogChunks);}

    /** Helper & Misc. **/

    public void removeOrFlagInteractable(PointOfInterest pOI){
        pOI.setPOIUsed();
        if(!pOI.isPersistent()){
            state.getPOIs().remove(pOI);
        }
    }

    public boolean connectTo(Room room) {
        Objects.requireNonNull(room);
        if (getRoomId().equals(room.getRoomId())) {
            throw new IllegalArgumentException("A room cannot connect to itself");
        }
        boolean alreadyConnected = getConnectedRooms().stream()
                .anyMatch(connected -> connected.getRoomId().equals(room.getRoomId()));
        if (alreadyConnected) {
            return false;
        }

        getConnectedRooms().add(room);
        return true;
    }

}
