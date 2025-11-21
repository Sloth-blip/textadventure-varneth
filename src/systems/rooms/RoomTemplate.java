package systems.rooms;

import systems.actors.enemy.EnemyState;
import systems.actors.enemy.EnemyTemplate;
import systems.actors.interactables.PointofInterestState;
import systems.actors.npc.NPCState;

import java.util.List;

public class RoomTemplate {

    private final String roomId;
    private final String roomName;
    private final String roomDescription;
    private final List<String> roomDialogChunks;
    private final List<String> enemies;
    private final List<String> npcs;
    private final List<String> interactables;

    private final List<String> connectedRooms;

    public RoomTemplate(String roomId, String roomName, String roomDescription, List<String> roomDialogChunks, List<String> enemies, List<String> npcs, List<String> interactables, List<String> connectedRooms) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomDialogChunks = roomDialogChunks;
        this.enemies = enemies;
        this.npcs = npcs;
        this.interactables = interactables;
        this.connectedRooms = connectedRooms;
    }
}
