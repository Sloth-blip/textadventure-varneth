package engine;



import systems.actors.player.Player;
import systems.combat.CombatScene;
import systems.rooms.ExplorationPhase;
import systems.rooms.RoomStateTest;
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
                    switch (result){
                        case WON -> {
                            System.out.println("Kampf gewonnen!");
                            currentRoom.setEnemies(List.of());
                        }
                        case LOST -> {
                            System.out.println("Kampf verloren, Game over!");
                            running = false;
                        }
                        case FLED -> System.out.println("Kampf entflohen!");
                    }
                }
                case INTERACTABLES -> eP.playInteractableDialog(currentRoom, player);
                case ROOMDESCRIPTION -> eP.replayRoomDialog(currentRoom.getRoomDialogChunks());
                case ROOMNAVIGATION -> {
                    Optional<RoomStateTest> maybeNextRoom = eP.chooseNextRoom(currentRoom);
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

