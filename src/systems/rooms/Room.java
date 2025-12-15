package systems.rooms;

import systems.actors.enemy.Enemy;
import systems.actors.npc.NPC;
import systems.interactables.PointOfInterest;

import java.util.List;

public class Room {

    public final RoomDefinition def;
    public final RoomState state;

    public Room(
            RoomDefinition def,
            RoomState stete
    )
    {
        this.def = def;
        this.state = stete;
    }

    @Override
    public String toString(){return def.toString();}


    /** Getter **/

    public String getRoomId() {return def.getRoomId();}
    public String getRoomDescription(){return def.getRoomDescription();}

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

}
