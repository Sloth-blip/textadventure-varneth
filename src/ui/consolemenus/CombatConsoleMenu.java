package ui.consolemenus;

import input.TextInput;
import systems.actors.Actor;
import systems.actors.enemy.Enemy;
import systems.actors.player.Player;
import systems.spells.Skill;
import ui.enums.CombatAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CombatConsoleMenu {

    TextInput textInput = new TextInput();

    public void consoleMenuCombatSceneBegin(Player player, List<Enemy> enemies) {
        System.out.println("""
                %s %d/%d
                vs""".formatted(
                player.getName(), player.getCurrentHp(), player.getMaxHp()
        ));
        for (Enemy enemy : enemies) {
            if (!enemy.isDead()) {
                System.out.println("""
                        %s %d/%d""".formatted(
                        enemy.getName(), enemy.getCurrentHp(), enemy.getMaxHp()
                ));
            }
        }
    }

    public void consoleMenuCombatSceneState(Player player, List<Enemy> enemies) {
        System.out.println(player.getName() + " " + player.getCurrentHp() + "/" + player.getMaxHp());
        for (Enemy enemy : enemies) {
            if(!enemy.isDead()) {
                System.out.println(enemy.getName() + " " + enemy.getCurrentHp() + "/" + enemy.getMaxHp());
            }
        }
    }

    public Optional<Enemy> consoleMenuTargetChooser(List<Enemy> enemies) {

        List<Enemy> verifiedEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
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
        for (Enemy enemy : verifiedEnemies) {
            System.out.println(menuListNr + ".: " + enemy.getName());
            menuListNr += 1;
        }

        System.out.println(menuListNr + ".: Zurück");

        int selection = textInput.inputVerifier(verifiedEnemies.size() + 1);

        if (menuListNr == selection){
            return Optional.empty();
        }

        return Optional.of(verifiedEnemies.get(selection - 1));
    }

    public Optional<Skill> consoleMenuSpellChooser(List<Skill> learnedSkills){
        int menuOption = 1;
        System.out.println("Wähle den Zauber:");
        for(Skill spell : learnedSkills){
            System.out.println(menuOption + ".: " + spell.getName());
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = textInput.inputVerifier(learnedSkills.size() + 1);

        if (menuOption == selection){
            return Optional.empty();
        }
        return Optional.of(learnedSkills.get(selection-1));
    }

    public CombatAction consoleMenuCombatMenu(){
        int menuOption = 1;
        List<CombatAction> choice = new ArrayList<>();

        for (CombatAction c : CombatAction.values()){
            System.out.println(menuOption + ".: " + c);
            choice.add(c);
            menuOption++;
        }

        int selection = textInput.inputVerifier(choice.size()) -1;
        System.out.println(choice.get(selection) + " gewählt.");
        return choice.get(selection);
    }

    /** Combat Info **/

    public void damagePrintDefault(Actor actor, Actor target, int dmg){
        System.out.println(actor.getName() + " hat " + dmg + " Schaden an " + target.getName() + " verursacht!");
    }

    public void damagePrintSkill(Actor actor, Skill spell, Actor target, int dmg){
        System.out.println(actor.getName() + " hat mit " + spell + " " + dmg + " Schaden an " + target.getName() + " verursacht!");
    }

    public void actorDied(Actor actor){
        System.out.println(actor.getName() + " besiegt!");
    }

}
