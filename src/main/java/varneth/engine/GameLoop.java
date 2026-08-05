package varneth.engine;



import java.util.List;
import java.util.Optional;

import varneth.engine.events.EventBus;
import varneth.renderer.CombatConsoleNarrator;
import varneth.renderer.CrystalConsoleNarrator;
import varneth.renderer.EquipmentConsoleNarrator;
import varneth.renderer.InventoryConsoleRenderer;
import varneth.renderer.PlayerStatusConsoleRenderer;
import varneth.renderer.RewardConsoleNarrator;
import varneth.renderer.SkillProgressConsoleNarrator;
import varneth.systems.actors.player.Player;
import varneth.systems.items.EquipmentHandler;
import varneth.systems.combat.CombatScene;
import varneth.systems.rooms.ExplorationPhase;
import varneth.systems.rooms.Room;
import varneth.ui.consolemenus.ConsoleMenuGeneral;
import varneth.ui.consolemenus.EquipmentConsoleMenu;
import varneth.ui.enums.ExplorationAction;
import varneth.ui.enums.MainMenuAction;

public class GameLoop {


    public void gameLoopStart(GameState gameState) {

        ConsoleMenuGeneral userInterface = new ConsoleMenuGeneral();
        EventBus bus = new EventBus();
        new CombatConsoleNarrator(bus);
        new CrystalConsoleNarrator(bus);
        new EquipmentConsoleNarrator(bus);
        new RewardConsoleNarrator(bus);
        new SkillProgressConsoleNarrator(bus);
        InventoryConsoleRenderer inventoryRenderer = new InventoryConsoleRenderer();
        PlayerStatusConsoleRenderer playerStatusRenderer = new PlayerStatusConsoleRenderer();
        EquipmentConsoleMenu equipmentMenu = new EquipmentConsoleMenu();
        EquipmentHandler equipmentHandler = new EquipmentHandler(bus);
        Player player = gameState.getPlayer();

        var cS = new CombatScene(bus);
        var eP = new ExplorationPhase(bus);

        boolean running = true;

        while (running) {

            Room currentRoom = gameState.getCurrentRoom();
            boolean firstVisit = gameState.markCurrentRoomVisited();
            ExplorationAction nextStep = eP.explorationPhase(currentRoom, firstVisit);

            switch (nextStep){
                case COMBAT -> {
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
                        gameState.enterRoom(maybeNextRoom.get().getRoomId());
                    }
                }
                case INVENTORY -> {
                    inventoryRenderer.render(player);
                    equipmentMenu.chooseEquipment(player).ifPresent(
                            equipment -> equipmentHandler.toggle(player, equipment)
                    );
                }
                case PLAYER_STATUS -> playerStatusRenderer.render(player);
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

