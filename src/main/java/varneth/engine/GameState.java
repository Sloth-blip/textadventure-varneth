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
    private final Set<String> storyFlags;
    private String currentRoomId;

    public static GameState startNew(Player player, WorldState world) {
        return new GameState(
                player,
                world,
                world.getStartRoom().getRoomId(),
                Set.of(),
                Set.of()
        );
    }

    public GameState(
            Player player,
            WorldState world,
            String currentRoomId,
            Set<String> visitedRoomIds
    ) {
        this(player, world, currentRoomId, visitedRoomIds, Set.of());
    }

    public GameState(
            Player player,
            WorldState world,
            String currentRoomId,
            Set<String> visitedRoomIds,
            Set<String> storyFlags
    ) {
        this.player = Objects.requireNonNull(player);
        this.world = Objects.requireNonNull(world);
        this.visitedRoomIds = new HashSet<>(Objects.requireNonNull(visitedRoomIds));
        this.storyFlags = new HashSet<>(Objects.requireNonNull(storyFlags));

        world.getRoomById(currentRoomId);
        for (String visitedRoomId : this.visitedRoomIds) {
            world.getRoomById(visitedRoomId);
        }
        for (String storyFlag : this.storyFlags) {
            requireStoryFlag(storyFlag);
        }
        this.currentRoomId = currentRoomId;
    }

    public Player getPlayer() {return player;}
    public WorldState getWorld() {return world;}
    public String getCurrentRoomId() {return currentRoomId;}
    public Room getCurrentRoom() {return world.getRoomById(currentRoomId);}
    public Set<String> getVisitedRoomIds() {return Set.copyOf(visitedRoomIds);}
    public Set<String> getStoryFlags() {return Set.copyOf(storyFlags);}

    public boolean hasStoryFlag(String storyFlag) {
        return storyFlags.contains(requireStoryFlag(storyFlag));
    }

    public boolean addStoryFlag(String storyFlag) {
        return storyFlags.add(requireStoryFlag(storyFlag));
    }

    public void enterRoom(String roomId) {
        world.getRoomById(roomId);
        currentRoomId = roomId;
    }

    public boolean markCurrentRoomVisited() {
        return visitedRoomIds.add(currentRoomId);
    }

    private String requireStoryFlag(String storyFlag) {
        Objects.requireNonNull(storyFlag);
        if (storyFlag.isBlank()) {
            throw new IllegalArgumentException("Story flag must not be blank");
        }
        return storyFlag;
    }
}
