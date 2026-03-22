package varneth.application.engine;

import java.util.HashSet;
import java.util.Set;

import varneth.systems.rooms.Room;
import varneth.systems.world.WorldState;

public class ExplorationSessionData {

    private String contextText;
    private WorldState worldState;
    private Room currentRoom;

    private final Set<String> visitedRoomIds = new HashSet<>();
    private final Set<String> discoveredRoomIds = new HashSet<>();
    private final Set<String> discoveredEdgeIds = new HashSet<>();

    public ExplorationSessionData(String contextText) {
        this.contextText = contextText;
    }

    public String getContextText() {return contextText;}
    public WorldState getWorldState() {return worldState;}
    public Room getCurrentRoom() {return currentRoom;}

    public Set<String> getVisitedRoomIds() { return visitedRoomIds; }
    public Set<String> getDiscoveredRoomIds() { return discoveredRoomIds; }
    public Set<String> getDiscoveredEdgeIds() { return discoveredEdgeIds; }

    public boolean hasVisited(Room room) {return visitedRoomIds.contains(room.getRoomId());}

    public void setContextText(String contextText) {this.contextText = contextText;}
    public void setWorldState(WorldState worldState) {this.worldState = worldState;}
    public void setCurrentRoom(Room currentRoom) {this.currentRoom = currentRoom;}

    public void resetDiscovery() {
        visitedRoomIds.clear();
        discoveredRoomIds.clear();
        discoveredEdgeIds.clear();
    }

    public void markVisited(Room room) {
        visitedRoomIds.add(room.getRoomId());
        discoveredRoomIds.add(room.getRoomId());
    }
    public void markDiscovered(Room room) {discoveredRoomIds.add(room.getRoomId());}
    public void markDiscoveredEdge(String edgeId) {discoveredEdgeIds.add(edgeId);}
}
