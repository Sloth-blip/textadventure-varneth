package ui.consolemenus;

import input.TextInput;
import systems.actors.player.Player;
import systems.interactables.PointOfInterest;
import systems.rooms.Room;
import ui.enums.ExplorationAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExplorationConsoleMenu {

    TextInput textInput = new TextInput();

    /** Exploration Misc **/

    public void consoleMenuExplorationEntered(Room room){
        System.out.println("Du befindest dich in " + room + ".");
        if (!room.getConnectedRooms().isEmpty()){
            System.out.print(room.getConnectedRooms().size() + " Räume verbunden. ");
        }
        if (!room.getEnemies().isEmpty()){
            System.out.print(room.getEnemies().size() + " Gegner vorhanden. ");
        }
        if (!room.getPOIs().isEmpty()){
            System.out.print(room.getPOIs().size() + " Interaktionen vorhanden. ");
        }
        System.out.println();
    }

    public ExplorationAction consoleMenuExplorationOptionChooser(Room room){
        int menuOption = 1;
        List<ExplorationAction> actions = new ArrayList<>();
        if(!room.getEnemies().isEmpty()){
            System.out.println(menuOption + ".: " + ExplorationAction.COMBAT);
            actions.add(ExplorationAction.COMBAT);
            menuOption++;
        }

        if(!room.getPOIs().isEmpty() && room.getEnemies().isEmpty()){
            System.out.println(menuOption + ".: " + ExplorationAction.INTERACTABLES);
            actions.add(ExplorationAction.INTERACTABLES);
            menuOption++;
        }

        System.out.println(menuOption + ".: " + ExplorationAction.ROOMDESCRIPTION + " erneut auspielen");
        actions.add(ExplorationAction.ROOMDESCRIPTION);
        menuOption++;

        if(!room.getConnectedRooms().isEmpty() && room.getEnemies().isEmpty()){
            System.out.println(menuOption + ".: " + ExplorationAction.ROOMNAVIGATION);
            actions.add(ExplorationAction.ROOMNAVIGATION);
            menuOption++;
        }
        System.out.println(menuOption + ".: " + ExplorationAction.MAINMENU);
        actions.add(ExplorationAction.MAINMENU);
        menuOption++;

        int selection = textInput.inputVerifier(actions.size()) - 1;
        return actions.get(selection);
    }

    public Optional<Room> consoleMenuDisplayAndChooseConnectedRooms(List<Room> connectedRooms) {
        int menuOption = 1;
        List<Room> choice = new ArrayList<>();

        for (Room r : connectedRooms){
            System.out.println(menuOption + ".: " + r);
            choice.add(r);
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = textInput.inputVerifier(choice.size() + 1);
        if (menuOption == selection) {
            return Optional.empty();
        }

        return Optional.of(connectedRooms.get(selection - 1));
    }

    /** PointOfInterests **/

    public Optional<PointOfInterest> consoleMenuDisplayAndChooseInteractables(Room room) {
        int menuOption = 1;

        List<PointOfInterest> choice = new ArrayList<>();

        for (PointOfInterest pOI : room.getPOIs()){
            System.out.println(menuOption + ".: " + pOI.getName());
            choice.add(pOI);
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = textInput.inputVerifier(choice.size() + 1);
        if (menuOption == selection){
            return Optional.empty();
        }

        return Optional.of(choice.get(selection - 1));

    }

    public void consoleMenuDisplayInteractableDialog(List<String> interactableDialog) {

        if (!interactableDialog.isEmpty()) {
            for (String line : interactableDialog) {
                System.out.println(line + " (Weiter mit Enter)");
                TextInput.scanner.nextLine();
            }
        }
    }

    public void explorationMenuTakeRest(Player player) {
        System.out.println(player.getName() + " HP: " + player.getCurrentHp() + "/" + player.getMaxHp());
    }

    /** Room Intro **/

    public void consoleMenuDisplayRoomDialog(List<String> roomDialog){

        if(!roomDialog.isEmpty()){
            for (String line : roomDialog){
                System.out.println(line + " (Weiter mit Enter)");
                TextInput.scanner.nextLine();
            }
        }

    }


}
