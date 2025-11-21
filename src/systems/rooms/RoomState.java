package systems.rooms;

import systems.actors.enemy.EnemyState;
import systems.actors.interactables.PointofInterestState;
import systems.actors.npc.NPCState;

import java.util.List;

public class RoomState {

    private int roomId;
    private final String roomName;
    private final String roomDescription;
    private final List<String> roomDialogChunks;
    private List<EnemyState> enemies;
    private List<NPCState> npcs;
    private final List<PointofInterestState> interactables;

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
            List<PointofInterestState> interactables)
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
    public List<EnemyState> getEnemies(){return this.enemies;}
    public List<PointofInterestState> getInteractables(){return this.interactables;}

    public List<RoomState> getConnectedRooms(){return connectedRooms;}

    /** Setter **/
    public void setEnemies(List<EnemyState> enemies){this.enemies = enemies;}

    public void setConnectedRooms(List<RoomState> connectedRooms) {this.connectedRooms = connectedRooms;}

    /** Helper **/
    public void removeOrFlagInteractable(PointofInterestState pOI){
        pOI.setInteractableUsed();
        if(!pOI.isPersistent()){
            this.interactables.remove(pOI);
        }
    }

}
