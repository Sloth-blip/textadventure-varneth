package systems.rooms;

import systems.actors.player.Player;
import systems.interactables.PointOfInterest;
import systems.reward.RewardHandler;
import ui.consolemenus.ExplorationConsoleMenu;
import ui.enums.ExplorationAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExplorationPhase {

    RewardHandler rewardHandler = new RewardHandler();

    // Flag für besuchte Räume
    List<RoomStateTest> roomsVisited = new ArrayList<>();

    ExplorationConsoleMenu explorationConsoleMenu = new ExplorationConsoleMenu();

    public ExplorationAction explorationPhase(RoomStateTest room) {

            System.out.println(room.getRoomDescription());

        if(!roomsVisited.contains(room)){
            explorationConsoleMenu.consoleMenuDisplayRoomDialog(room.getRoomDialogChunks());
            roomsVisited.add(room);
        }

        explorationConsoleMenu.consoleMenuExplorationEntered(room);
        return explorationConsoleMenu.consoleMenuExplorationOptionChooser(room);
    }

    public Optional<RoomStateTest> chooseNextRoom(RoomStateTest currentRoom){
        return explorationConsoleMenu.consoleMenuDisplayAndChooseConnectedRooms(currentRoom.getConnectedRooms());
    }

    public void replayRoomDialog(List<String> roomDialog){
        explorationConsoleMenu.consoleMenuDisplayRoomDialog(roomDialog);
    }

    public void playInteractableDialog(RoomStateTest room, Player player){

        Optional<PointOfInterest> maybePOI = explorationConsoleMenu.consoleMenuDisplayAndChooseInteractables(room);
        if (maybePOI.isPresent()) {

            PointOfInterest pOI = maybePOI.get();
            explorationConsoleMenu.consoleMenuDisplayInteractableDialog(
                    pOI.getDialogChunks()
            );
            if (Objects.equals(maybePOI.get().getName(), "Rastplatz")){
                player.takeRest();
                explorationConsoleMenu.explorationMenuTakeRest(player);
            }
            rewardHandler.grantRewardsFromPOI(pOI, player);
            room.removeOrFlagInteractable(pOI);
        }
    }
}