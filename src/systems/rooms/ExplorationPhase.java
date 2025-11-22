package systems.rooms;

import systems.interactables.PointofInterestState;
import ui.ConsoleMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExplorationPhase {

    // Flag für besuchte Räume
    List<RoomState> roomsVisited = new ArrayList<>();

    ui.ConsoleMenu cM = new ConsoleMenu();

    public ConsoleMenu.ExplorationAction explorationPhase(RoomState room) {

            System.out.println(room.getRoomDescription());

        if(!roomsVisited.contains(room)){
            cM.consoleMenuDisplayRoomDialog(room.getRoomDialogChunks());
            roomsVisited.add(room);
        }
        cM.consoleMenuExplorationEntered(room);
        return cM.consoleMenuExplorationOptionChooser(room);
    }

    public Optional<RoomState> chooseNextRoom(RoomState currentRoom){
        return cM.consoleMenuDisplayAndChooseConnectedRooms(currentRoom.getConnectedRooms());
    }

    public void replayRoomDialog(List<String> roomDialog){
        cM.consoleMenuDisplayRoomDialog(roomDialog);
    }

    public void playInteractableDialog(RoomState room){

        Optional<PointofInterestState> maybePOI = cM.consoleMenuDisplayAndChooseInteractables(room);
        if (!maybePOI.isEmpty()) {

            PointofInterestState pOI = maybePOI.get();
            cM.consoleMenuDisplayInteractableDialog(
                    pOI.getInteractableDialogChunks()
            );
            room.removeOrFlagInteractable(pOI);
        }
    }
}