package ui.consolemenus;


import input.TextInput;
import systems.actors.Actor;
import systems.spells.Skill;
import ui.enums.GameStartAction;
import ui.enums.MainMenuAction;

import java.util.ArrayList;
import java.util.List;


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

    /** Reward Handler **/

    public void consoleMessageSkillLearned(Skill skill, Actor actor){
        System.out.println(actor.getName() + " hat " + skill.getName() + " erlernt!");
    }

    public void consoleMessageExperienceGranted(int xp, Actor actor){
        System.out.println(actor.getName() + " hat " + xp + " Erfahrung erhalten!");
    }

    /** Misc Console Messages **/

}


