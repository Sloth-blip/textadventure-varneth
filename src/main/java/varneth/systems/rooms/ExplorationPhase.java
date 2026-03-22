package varneth.systems.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import varneth.systems.actors.player.Player;
import varneth.systems.interactables.PointOfInterest;
import varneth.systems.interactables.PointOfInterestType;
import varneth.systems.reward.RewardHandler;
import varneth.ui.consolemenus.ExplorationConsoleMenu;
import varneth.ui.enums.ExplorationAction;

public class ExplorationPhase {

    RewardHandler rewardHandler = new RewardHandler();

    // Flag für besuchte Räume
    List<Room> roomsVisited = new ArrayList<>();

    ExplorationConsoleMenu explorationConsoleMenu = new ExplorationConsoleMenu();

    public ExplorationAction explorationPhase(Room room) {

            System.out.println(room.getRoomDescription());

        if(!roomsVisited.contains(room)){
            explorationConsoleMenu.consoleMenuDisplayRoomDialog(room.getRoomDialogChunks());
            roomsVisited.add(room);
        }

        explorationConsoleMenu.consoleMenuExplorationEntered(room);
        return explorationConsoleMenu.consoleMenuExplorationOptionChooser(room);
    }

    public Optional<Room> chooseNextRoom(Room currentRoom){
        return explorationConsoleMenu.consoleMenuDisplayAndChooseConnectedRooms(currentRoom.getConnectedRooms());
    }

    public void replayRoomDialog(List<String> roomDialog){
        explorationConsoleMenu.consoleMenuDisplayRoomDialog(roomDialog);
    }

    public void playInteractableDialog(Room room, Player player){

        Optional<PointOfInterest> maybePOI = explorationConsoleMenu.consoleMenuDisplayAndChooseInteractables(room);
        if (maybePOI.isPresent()) {

            PointOfInterest pOI = maybePOI.get();
            explorationConsoleMenu.consoleMenuDisplayInteractableDialog(
                    pOI.getDialogChunks()
            );
            if (pOI.getType() == PointOfInterestType.REST){
                player.takeRest();
                explorationConsoleMenu.explorationMenuTakeRest(player);
            }
            rewardHandler.grantRewardsFromPOI(pOI, player);
            room.removeOrFlagInteractable(pOI);
        }
    }
}