package systems.rooms;

import systems.actors.player.Player;
import systems.interactables.PointOfInterest;
import systems.interactables.PointOfInterestState;
import systems.reward.RewardHandler;
import ui.ConsoleMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExplorationPhase {

    RewardHandler rewardHandler = new RewardHandler();

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

    public void playInteractableDialog(RoomState room, Player player){

        Optional<PointOfInterest> maybePOI = cM.consoleMenuDisplayAndChooseInteractables(room);
        if (maybePOI.isPresent()) {

            PointOfInterest pOI = maybePOI.get();
            cM.consoleMenuDisplayInteractableDialog(
                    pOI.getDialogChunks()
            );
            rewardHandler.grantRewardsFromPOI(pOI, player);
            room.removeOrFlagInteractable(pOI);
        }
    }
}