package systems.rooms;

import systems.actors.enemy.Enemy;
import systems.interactables.PointOfInterest;
import systems.actors.npc.NPC;

import java.util.List;

public class RoomState {

    private int roomId;
    private final String roomName;
    private final String roomDescription;
    private final List<String> roomDialogChunks;
    private List<Enemy> enemies;
    private List<NPC> npcs;
    private final List<PointOfInterest> interactables;

    private List<RoomState> connectedRooms;


    @Override
    public String toString(){
        return roomName;
    }


    public RoomState (
            int roomId,
            String roomName,
            String roomDescription,
            List<String> roomDialogChunks,
            List<PointOfInterest> interactables)
    {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomDialogChunks = roomDialogChunks;
        this.interactables = interactables;
    }

    /** Getter **/
    public String getRoomDescription(){return this.roomDescription;}
    public List<String> getRoomDialogChunks(){return this.roomDialogChunks;}
    public List<Enemy> getEnemies(){return this.enemies;}
    public List<PointOfInterest> getInteractables(){return this.interactables;}

    public List<RoomState> getConnectedRooms(){return connectedRooms;}

    /** Setter **/
    public void setEnemies(List<Enemy> enemies){this.enemies = enemies;}

    public void setConnectedRooms(List<RoomState> connectedRooms) {this.connectedRooms = connectedRooms;}

    /** Helper **/
    public void removeOrFlagInteractable(PointOfInterest pOI){
        pOI.setPOIUsed();
        if(!pOI.isPersistent()){
            this.interactables.remove(pOI);
        }
    }

}
