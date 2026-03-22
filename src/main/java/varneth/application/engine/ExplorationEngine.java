package varneth.application.engine;

import java.util.ArrayList;
import java.util.List;

import varneth.application.engine.ui.MapEdgeView;
import varneth.application.engine.ui.MapNodeView;
import varneth.application.engine.ui.MapView;
import varneth.application.intent.PlayerIntent;
import varneth.application.state.ActionOption;
import varneth.systems.rooms.Room;
import varneth.systems.world.WorldBuilder;
import varneth.systems.world.WorldState;

public class ExplorationEngine {

    public void initialize(
        List<ActionOption> actions,
        List<String> inventory,
        List<String> quests,
        List<String> mapEntries,
        List<String> devLog,
        ExplorationSessionData data
    ) {

        WorldState worldState = WorldBuilder.buildTestWorldTwo();
        Room startRoom = worldState.getStartRoom();

        data.setWorldState(worldState);
        data.setCurrentRoom(startRoom);
        data.resetDiscovery();
        discoverFromCurrentRoom(startRoom, data);
        data.setContextText(buildRoomContext(startRoom));
        
        rebuildActions(actions, startRoom);

        inventory.clear();
        inventory.add("ToDo");

        quests.clear();
        quests.add("ToDo");

        mapEntries.clear();
        mapEntries.add(startRoom.toString());

        devLog.clear();
        devLog.add("Game gestartet");
        devLog.add("Start-Raum: " + startRoom.toString());
    }

    public PoiInteractionResult handleIntent(
        PlayerIntent intent,
        List<ActionOption> actions,
        List<String> mapEntries,
        List<String> devLog,
        ExplorationSessionData data
    ) {
        if(!"SELECT_ACTION".equals(intent.getType())) {
            devLog.add("Unbekannter Exploration-Intent: " + intent.getType());
            return null;
        }

        return handleAction(intent.getValue(), mapEntries, devLog, data, actions);
    }

    private PoiInteractionResult handleAction(
        String actionId,
        List<String> mapEntries,
        List<String> devLog,
        ExplorationSessionData data,
        List<ActionOption> actions
    ) {
        Room currentRoom = data.getCurrentRoom();

        switch (actionId) {
        }

            if (actionId.equals("FIGHT")) {
                data.setContextText("Du stellst dich dem Kampf.");
                devLog.add("FIGHT ausgewählt");
                return null;
            }
            
            if(actionId.startsWith("MOVE_")) {
                String targetRoomId = actionId.substring("MOVE_".length());

                for (Room connectedRoom : currentRoom.getConnectedRooms()) {
                    if (connectedRoom.getRoomId().equals(targetRoomId)) {
                        data.setCurrentRoom(connectedRoom);
                        discoverFromCurrentRoom(connectedRoom, data);
                        data.setContextText(buildRoomContext(connectedRoom));

                        rebuildActions(actions, connectedRoom);
                        mapEntries.add(connectedRoom.toString());
                        devLog.add("Raum gewechselt zu: " + connectedRoom.toString());
                        return null;
                    }
                }

                devLog.add("Ungültiger Raumwechsel: " + actionId);
                return null;
            }

            if(actionId.startsWith("POI_")) {
                String poiId = actionId.substring("POI_".length());

                for (var poi : currentRoom.getPOIs()) {
                    if (poi.getId().equals(poiId)) {

                        boolean firstUse = !poi.isUsed();

                        devLog.add("POI gestartet: " + poi.getName());

                        return new PoiInteractionResult(poi, firstUse);
                    }
                }
                devLog.add("POI nicht gefunden: " + poiId);
                return null;
            }
            devLog.add("Unbekannte Aktion: " + actionId);
            return null;
        }
    

    public String buildRoomContext(Room room) {
    StringBuilder sb = new StringBuilder();

    sb.append(room.toString()).append("\n");
    sb.append(room.getRoomDescription());

    if (!room.getPOIs().isEmpty()) {
        sb.append("\n\nInteressante Dinge:");
        room.getPOIs().forEach(poi -> sb.append("\n- ").append(poi.getName()));
    }

    if (!room.getEnemies().isEmpty()) {
        sb.append("\n\nGegner im Raum: ").append(room.getEnemies().size());
    }

    return sb.toString();
    }

    public void rebuildActions(List<ActionOption> actions, Room room) {
        actions.clear();

        if(!room.getEnemies().isEmpty()) {
            actions.add(new ActionOption("FIGHT", "Kämpfen"));
            return;
        }

        for(Room connectedRoom : room.getConnectedRooms()) {
            actions.add(new ActionOption(
                "MOVE_" + connectedRoom.getRoomId(),
                "Gehe zu: " + connectedRoom.toString()
            ));
        }

        room.getPOIs().forEach(poi ->
            actions.add(new ActionOption(
                "POI_" + poi.getId(),
                "Interagiere mit: " + poi.getName()
            ))
        );
    }

    public void completePoiInteraction(
        PoiInteractionResult poiResult,
        List<ActionOption> actions,
        List<String> devLog,
        ExplorationSessionData data
    ) {
        Room currentRoom = data.getCurrentRoom();
        
        currentRoom.removeOrFlagInteractable(poiResult.getPoi());
        rebuildActions(actions, currentRoom);

        devLog.add("POI abgeschlossen: " + poiResult.getSourceName());
    }

    // Map-Helper

    private String buildEdgeKey(Room roomA, Room roomB) {
    String idA = roomA.getRoomId();
    String idB = roomB.getRoomId();

    return idA.compareTo(idB) <= 0
            ? idA + "|" + idB
            : idB + "|" + idA;
    }

    private void discoverFromCurrentRoom(Room room, ExplorationSessionData data) {
        data.markDiscovered(room);

        for (Room connectedRoom : room.getConnectedRooms()) {
            data.markDiscovered(connectedRoom);
            data.markDiscoveredEdge(buildEdgeKey(room, connectedRoom));
        }
    }

    public MapView buildMapView(ExplorationSessionData data) {
        if (data.getWorldState() == null) {
            return new MapView(List.of(), List.of());
        }

        List<MapNodeView> nodes = new ArrayList<>();
        List<MapEdgeView> edges = new ArrayList<>();

        for (Room room : data.getWorldState().getAllRooms()) {
            boolean discovered = data.getDiscoveredRoomIds().contains(room.getRoomId());
            boolean visited = data.getVisitedRoomIds().contains(room.getRoomId());
            boolean current = data.getCurrentRoom() != null
                    && data.getCurrentRoom().getRoomId().equals(room.getRoomId());

            if (!discovered && !visited && !current) {
                continue;
            }

            nodes.add(new MapNodeView(
                    room.getRoomId(),
                    room.getName(),
                    room.getMapX(),
                    room.getMapY(),
                    visited,
                    current,
                    discovered
            ));
        }

        for (Room room : data.getWorldState().getAllRooms()) {
            for (Room connectedRoom : room.getConnectedRooms()) {
                if (room.getRoomId().compareTo(connectedRoom.getRoomId()) > 0) {
                    continue;
                }

                String edgeKey = buildEdgeKey(room, connectedRoom);

                if (!data.getDiscoveredEdgeIds().contains(edgeKey)) {
                    continue;
                }

                edges.add(new MapEdgeView(
                        room.getRoomId(),
                        connectedRoom.getRoomId(),
                        true
                ));
            }
        }

        return new MapView(nodes, edges);
    }
}
