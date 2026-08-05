package varneth.engine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import varneth.systems.actors.player.Player;
import varneth.systems.rooms.Room;
import varneth.systems.world.WorldState;

public class GameState {

    private final Player player;
    private final WorldState world;
    private final Set<String> visitedRoomIds;
    private String currentRoomId;

    public static GameState startNew(Player player, WorldState world) {
        return new GameState(
                player,
                world,
                world.getStartRoom().getRoomId(),
                Set.of()
        );
    }

    public GameState(
            Player player,
            WorldState world,
            String currentRoomId,
            Set<String> visitedRoomIds
    ) {
        this.player = Objects.requireNonNull(player);
        this.world = Objects.requireNonNull(world);
        this.visitedRoomIds = new HashSet<>(Objects.requireNonNull(visitedRoomIds));

        world.getRoomById(currentRoomId);
        for (String visitedRoomId : this.visitedRoomIds) {
            world.getRoomById(visitedRoomId);
        }
        this.currentRoomId = currentRoomId;
    }

    public Player getPlayer() {return player;}
    public WorldState getWorld() {return world;}
    public String getCurrentRoomId() {return currentRoomId;}
    public Room getCurrentRoom() {return world.getRoomById(currentRoomId);}
    public Set<String> getVisitedRoomIds() {return Set.copyOf(visitedRoomIds);}

    public void enterRoom(String roomId) {
        world.getRoomById(roomId);
        currentRoomId = roomId;
    }

    public boolean markCurrentRoomVisited() {
        return visitedRoomIds.add(currentRoomId);
    }
}
