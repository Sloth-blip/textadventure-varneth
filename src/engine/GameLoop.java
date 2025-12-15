package engine;



import systems.actors.player.Player;
import systems.combat.CombatScene;
import systems.rooms.ExplorationPhase;
import systems.rooms.Room;
import systems.world.WorldBuilder;


import ui.consolemenus.ConsoleMenuGeneral;
import ui.enums.ExplorationAction;
import ui.enums.MainMenuAction;


import java.util.List;
import java.util.Optional;

public class GameLoop {


    public void gameLoopStart(Player player) {

        ConsoleMenuGeneral userInterface = new ConsoleMenuGeneral();


        var cS = new CombatScene();
        var eP = new ExplorationPhase();

        var testWorld = WorldBuilder.buildTestWorld();
        var currentRoom = testWorld.getStartRoom();

        boolean running = true;

        while (running) {


            ExplorationAction nextStep = eP.explorationPhase(currentRoom);

            switch (nextStep){
                case COMBAT ->{
                    CombatScene.CombatResult result = cS.combatLoop(player, currentRoom.getEnemies());
                    userInterface.consoleMessageCombatResult(nextStep, result);
                    switch (result){
                        case WON -> {
                            currentRoom.setEnemies(List.of());
                        }
                        case LOST -> {
                            running = false;
                        }
                    }
                }
                case INTERACTABLES -> eP.playInteractableDialog(currentRoom, player);
                case ROOMDESCRIPTION -> eP.replayRoomDialog(currentRoom.getRoomDialogChunks());
                case ROOMNAVIGATION -> {
                    Optional<Room> maybeNextRoom = eP.chooseNextRoom(currentRoom);
                    if (maybeNextRoom.isPresent()) {
                        currentRoom = maybeNextRoom.get();
                    }
                }
                case MAINMENU -> {
                    MainMenuAction mainMenuChoice = userInterface.consoleMenuMainMenu();
                    switch (mainMenuChoice){
                        case CONTINUE -> {}
                        case SAVE, LOAD, SETTINGS -> System.out.println("ToDo");
                        case END -> {
                            System.out.println("bye");
                            running = false;
                        }
                    }
                }
            }
        }
    }
}

