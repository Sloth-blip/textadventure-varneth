package varneth.systems.rooms;

import java.util.List;
import java.util.Optional;
import varneth.engine.events.EventBus;

import varneth.systems.actors.player.Player;
import varneth.systems.interactables.PointOfInterest;
import varneth.systems.interactables.PointOfInterestType;
import varneth.systems.reward.RewardHandler;
import varneth.ui.consolemenus.ExplorationConsoleMenu;
import varneth.ui.enums.ExplorationAction;

public class ExplorationPhase {

    private final RewardHandler rewardHandler;

    public ExplorationPhase(EventBus bus) {
        rewardHandler = new RewardHandler(bus);
    }

    ExplorationConsoleMenu explorationConsoleMenu = new ExplorationConsoleMenu();

    public ExplorationAction explorationPhase(Room room, boolean firstVisit) {
        System.out.println(room.getRoomDescription());

        if(firstVisit) {
            explorationConsoleMenu.consoleMenuDisplayRoomDialog(room.getRoomDialogChunks());
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