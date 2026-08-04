package varneth.ui.consolemenus;


import java.util.ArrayList;
import java.util.List;

import varneth.input.TextInput;
import varneth.ui.enums.GameStartAction;
import varneth.ui.enums.MainMenuAction;


public class ConsoleMenuGeneral {

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

    /** Misc. Console Messages **/

    public void consoleMessageCombatResult(Enum combat, Enum result){
        System.out.println(combat + " " + result);
    }

}


