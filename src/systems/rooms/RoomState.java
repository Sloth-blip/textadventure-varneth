package systems.rooms;

import systems.actors.enemy.Enemy;
import systems.actors.npc.NPC;
import systems.interactables.PointOfInterest;

import java.util.List;

public class RoomState {

    private List<Enemy> enemies;
    private List<NPC> npcs;
    private List<PointOfInterest> pOIs;
    private List<RoomState> connectedRooms;
    private List<String> roomDialogChunks;
}
