package varneth.ui.consolemenus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import varneth.input.TextInput;
import varneth.systems.actors.player.Player;
import varneth.systems.interactables.PointOfInterest;
import varneth.systems.riddles.RiddleAttemptResult;
import varneth.systems.riddles.SpellSealResult;
import varneth.systems.rooms.Room;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.SpellSource;
import varneth.ui.enums.ExplorationAction;

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
        if (!room.getPOIs().isEmpty() && room.getEnemies().isEmpty()){
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

        System.out.println(menuOption + ".: " + ExplorationAction.INVENTORY);
        actions.add(ExplorationAction.INVENTORY);
        menuOption++;

        System.out.println(menuOption + ".: " + ExplorationAction.PLAYER_STATUS);
        actions.add(ExplorationAction.PLAYER_STATUS);
        menuOption++;

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

    public Optional<String> consoleMenuReadRiddleAnswer() {
        System.out.println(
                "Gib deine Lösung ein. Leer lassen bricht den Versuch ab:"
        );
        return textInput.readText();
    }

    public void consoleMenuDisplayRiddleResult(RiddleAttemptResult result) {
        switch (result) {
            case SOLVED -> System.out.println(
                    "Die Struktur fügt sich zusammen. Du hast das Muster verstanden."
            );
            case INCORRECT -> System.out.println(
                    "Die Struktur reagiert nicht. Diese Lösung passt nicht zum Muster."
            );
            case CANCELLED -> System.out.println(
                    "Du lässt das ungelöste Muster vorerst zurück."
            );
            case ALREADY_SOLVED -> System.out.println(
                    "Du hast dieses Muster bereits entschlüsselt."
            );
        }
    }

    public void consoleMenuDisplaySpellSealResult(SpellSealResult result) {
        switch (result) {
            case READY -> {}
            case MISSING_KNOWLEDGE -> System.out.println(
                    "Die Zeichen bleiben unverständlich. Dir fehlt Wissen über ihre Reihenfolge."
            );
            case NO_AVAILABLE_SPELLS -> System.out.println(
                    "Du verstehst das Siegel, kannst aber gerade keinen passenden Zauber wirken."
            );
            case CANCELLED -> System.out.println(
                    "Du lässt das Siegel vorerst unberührt."
            );
            case CAST_FAILED -> System.out.println(
                    "Der Zauber lässt sich nicht vollständig auslösen."
            );
            case WRONG_SPELL -> System.out.println(
                    "Der gewirkte Zauber trifft das Siegel, doch die roten Linien weisen ihn zurück."
            );
            case OPENED -> System.out.println(
                    "Der Feuerstrom folgt der entschlüsselten Sequenz. Das Siegel erlischt."
            );
            case ALREADY_OPENED -> System.out.println(
                    "Das Feuersiegel ist bereits erloschen."
            );
        }
    }

    public void consoleMenuDisplayCastingState(
            Player player,
            AvailableSpell selectedSpell
    ) {
        if (selectedSpell.source() == SpellSource.CRYSTAL) {
            System.out.println(
                    selectedSpell.crystal().getName() + ": "
                            + selectedSpell.crystal().getCurrentCharge() + "/"
                            + selectedSpell.crystal().getMaxCharge() + " Ladung"
            );
            return;
        }
        System.out.println(
                player.getName() + " Ressource: " + player.getCurrentResource()
                        + "/" + player.getMaxResource()
        );
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
