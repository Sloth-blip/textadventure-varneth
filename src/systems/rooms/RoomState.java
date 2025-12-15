package systems.rooms;

import systems.actors.enemy.Enemy;
import systems.actors.npc.NPC;
import systems.interactables.PointOfInterest;

import java.util.List;

public class RoomState {

    private List<Enemy> enemies;
    private List<NPC> npcs;
    private List<PointOfInterest> pOIs;
    private List<Room> connectedRooms;
    private List<String> roomDialogChunks;

    public RoomState(
            List<Enemy> enemies,
            List<NPC> npcs,
            List<PointOfInterest> pOIs,
            List<Room> connectedRooms,
            List<String> roomDialogChunks
    )
    {
        this.enemies = enemies;
        this.npcs = npcs;
        this.pOIs = pOIs;
        this.connectedRooms = connectedRooms;
        this.roomDialogChunks = roomDialogChunks;
    }

    /** Getter **/

    protected List<Enemy> getEnemies(){return this.enemies;}
    protected List<NPC> getNpcs() {return npcs;}
    protected List<PointOfInterest> getPOIs(){return this.pOIs;}
    protected List<Room> getConnectedRooms() {return connectedRooms;}
    protected List<String> getRoomDialogChunks(){return this.roomDialogChunks;}

    /** Setter **/

    protected void setEnemies(List<Enemy> enemies) {this.enemies = enemies;}
    protected void setNpcs(List<NPC> npcs) {this.npcs = npcs;}
    protected void setPOIs(List<PointOfInterest> pOIs) {this.pOIs = pOIs;}
    protected void setConnectedRooms(List<Room> connectedRooms) {this.connectedRooms = connectedRooms;}
    protected void setRoomDialogChunks(List<String> roomDialogChunks) {this.roomDialogChunks = roomDialogChunks;}
}
