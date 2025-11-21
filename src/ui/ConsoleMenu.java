package ui;

import engine.player.PlayerState;
import input.TextInput;
import systems.actors.enemy.EnemyState;
import systems.actors.interactables.PointofInterestState;
import systems.rooms.RoomState;
import systems.spells.KnownSpell;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsoleMenu {

    public enum GameStartAction {
        NEWGAME("Neues Spiel"),
        LOAD("Spiel laden"),
        SETTINGS("Einstellungen"),
        END("Spiel beenden");

        public final String displayName;

        GameStartAction(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString(){
            return this.displayName;
        }
    }

    public enum ExplorationAction {
        COMBAT("Kampf"),
        INTERACTABLES("Interagieren"),
        ROOMDESCRIPTION("RaumIntro"),
        ROOMNAVIGATION("Raumnavigation"),
        MAINMENU("Hauptmenü");

        public final String displayName;

        ExplorationAction(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString(){
            return this.displayName;
        }
    }

    public enum DialogResult{
        CONTINUE,
        BACK,
        ENDED,
        GRANTREWARDS;
    }

    public enum CombatAction {
        BASICATTACK("Normaler Angriff"),
        SPELL("Zauber"),
        ITEM("Inventar"),
        FLEE("Fliehen");

        public final String displayName;

        CombatAction(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString(){
            return this.displayName;
        }
    }

    enum PuzzleAction {

    }

    public enum MainMenuAction {
        CONTINUE("Spiel Fortsetzen"),
        SAVE("Spiel Speichern"),
        LOAD("Spiel laden"),
        SETTINGS("Einstellungen"),
        END("Spiel beenden");

        public final String displayName;

        MainMenuAction(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString(){
            return this.displayName;
        }
    }

    static TextInput ti = new TextInput();

    /** GameLoop **/

    public GameStartAction consoleMenuGameStart() {
        int menuOption = 1;
        List<GameStartAction> actions = new ArrayList<>();

        System.out.println("Willkommen beim Textadventure Varneth! Wie möchtest du starten?");

        for (GameStartAction a : GameStartAction.values()){
            System.out.println(menuOption + ".: " + a);
            actions.add(a);
            menuOption++;
        }

        int selection = ti.inputVerifier(actions.size()) - 1;
        return actions.get(selection);

    }

    /** Exploration Menu **/

    public void consoleMenuExplorationEntered(RoomState room){
        System.out.println("Du befindest dich in " + room + ".");
        if (!room.getConnectedRooms().isEmpty()){
            System.out.print(room.getConnectedRooms().size() + " Räume verbunden. ");
        }
        if (!room.getEnemies().isEmpty()){
            System.out.print(room.getEnemies().size() + " Gegner vorhanden. ");
        }
        if (!room.getInteractables().isEmpty()){
            System.out.print(room.getInteractables().size() + " Interaktionen vorhanden. ");
        }
        System.out.println();
    }

    public ExplorationAction consoleMenuExplorationOptionChooser(RoomState room){
        int menuOption = 1;
        List<ExplorationAction> actions = new ArrayList<>();
        if(!room.getEnemies().isEmpty()){
            System.out.println(menuOption + ".: " + ExplorationAction.COMBAT);
            actions.add(ExplorationAction.COMBAT);
            menuOption++;
        }

        if(!room.getInteractables().isEmpty() && room.getEnemies().isEmpty()){
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

        int selection = ti.inputVerifier(actions.size()) - 1;
        return actions.get(selection);
    }

    public Optional<RoomState> consoleMenuDisplayAndChooseConnectedRooms(List<RoomState> connectedRooms) {
        int menuOption = 1;
        List<RoomState> choice = new ArrayList<>();

        for (RoomState r : connectedRooms){
            System.out.println(menuOption + ".: " + r);
            choice.add(r);
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = ti.inputVerifier(choice.size() + 1);
        if (menuOption == selection) {
            return Optional.empty();
        }

        return Optional.of(connectedRooms.get(selection - 1));
    }

    /** Interactables **/

    public Optional<PointofInterestState> consoleMenuDisplayAndChooseInteractables(RoomState room) {
        int menuOption = 1;

        List<PointofInterestState> choice = new ArrayList<>();

        for (PointofInterestState pOI : room.getInteractables()){
            System.out.println(menuOption + ".: " + pOI);
            choice.add(pOI);
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = ti.inputVerifier(choice.size() + 1);
        if (menuOption == selection){
            return Optional.empty();
        }

        return Optional.of(choice.get(selection - 1));

    }

    public void consoleMenuDisplayInteractableDialog(List<String> interactableDialog) {

        if (!interactableDialog.isEmpty()) {
            for (String line : interactableDialog) {
                System.out.println(line + " (Weiter mit Enter)");
                ti.scanner.nextLine();
            }
        }
    }

    /** Room Intro **/

    public void consoleMenuDisplayRoomDialog(List<String> roomDialog){

        if(!roomDialog.isEmpty()){
            for (String line : roomDialog){
                System.out.println(line + " (Weiter mit Enter)");
                ti.scanner.nextLine();
            }
        }

    }

    /** Combat Menu **/

    public void consoleMenuCombatSceneBegin(PlayerState player, List<EnemyState> enemies) {
        System.out.println("""
                %s %d/%d
                vs""".formatted(
                player, player.getHealthPointsCurrent(), player.getHealthPointsMax()
                ));
        for (EnemyState enemy : enemies) {
            if (!enemy.isDead()) {
                System.out.println("""
                        %s %d/%d""".formatted(
                        enemy, enemy.getHealthPointsCurrent(), enemy.getHealthPointsMax()
                ));
            }
        }
    }

    public void consoleMenuCombatSceneState(PlayerState player, List<EnemyState> enemies) {
        System.out.println(player + " " + player.getHealthPointsCurrent() + "/" + player.getHealthPointsMax());
        for (EnemyState enemy : enemies) {
            if(!enemy.isDead()) {
                System.out.println(enemy + " " + enemy.getHealthPointsCurrent() + "/" + enemy.getHealthPointsMax());
            }
        }
    }

    public Optional<EnemyState> consoleMenuTargetChooser(List<EnemyState> enemies) {

        List<EnemyState> verifiedEnemies = new ArrayList<>();
        for (EnemyState enemy : enemies) {
            if (!enemy.isDead()){
            verifiedEnemies.add(enemy);
            }
        }

        /** Falls ich Targetauswahl nicht immer haben möchte **/

//        if (verifiedEnemies.size() <= 1){
//            return verifiedEnemies.getFirst();
//        }

        int menuListNr = 1;
        System.out.println("Wähle das Ziel aus.");
        for (EnemyState enemy : verifiedEnemies) {
            System.out.println(menuListNr + ".: " + enemy);
            menuListNr += 1;
            }

        System.out.println(menuListNr + ".: Zurück");

        int selection = ti.inputVerifier(verifiedEnemies.size() + 1);

        if (menuListNr == selection){
            return Optional.empty();
        }

        return Optional.of(verifiedEnemies.get(selection - 1));
    }

    public Optional<KnownSpell> consoleMenuSpellChooser(List<KnownSpell> knownSpells){
        int menuOption = 1;
        System.out.println("Wähle den Zauber:");
        for(KnownSpell spell : knownSpells){
            System.out.println(menuOption + ".: " + spell);
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = ti.inputVerifier(knownSpells.size() + 1);

        if (menuOption == selection){
            return Optional.empty();
        }
        return Optional.of(knownSpells.get(selection-1));
    }

    public CombatAction consoleMenuCombatMenu(){
        int menuOption = 1;
        List<CombatAction> choice = new ArrayList<>();

        for (CombatAction c : CombatAction.values()){
            System.out.println(menuOption + ".: " + c);
            choice.add(c);
            menuOption++;
        }

        int selection = ti.inputVerifier(choice.size()) -1;
        System.out.println(choice.get(selection) + " gewählt.");
        return choice.get(selection);
    }

    /** Main Menu **/

    public MainMenuAction consoleMenuMainMenu(){
        int menuOption = 1;
        List<MainMenuAction> choice = new ArrayList<>();
        System.out.println("Hauptmenü");

        for ( MainMenuAction mainMenuAction : MainMenuAction.values()){
            System.out.println(menuOption + ".: " + mainMenuAction);
            choice.add(mainMenuAction);
            menuOption++;
        }

        int selection = ti.inputVerifier(choice.size()) - 1;
        return choice.get(selection);
    }

}


