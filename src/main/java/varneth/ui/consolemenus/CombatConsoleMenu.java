package varneth.ui.consolemenus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import varneth.input.TextInput;
import varneth.systems.actors.enemy.Enemy;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.SpellSource;
import varneth.ui.enums.CombatAction;

public class CombatConsoleMenu {

    TextInput textInput = new TextInput();

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

    public Optional<AvailableSpell> consoleMenuSpellChooser(List<AvailableSpell> availableSpells){
        int menuOption = 1;
        System.out.println("Wähle den Zauber:");
        for(AvailableSpell availableSpell : availableSpells){
            System.out.println(
                    menuOption + ".: " + availableSpell.skill().getName()
                            + " (" + spellSourceLabel(availableSpell) + ")"
            );
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = textInput.inputVerifier(availableSpells.size() + 1);

        if (menuOption == selection){
            return Optional.empty();
        }
        return Optional.of(availableSpells.get(selection-1));
    }

    String spellSourceLabel(AvailableSpell availableSpell) {
        if (availableSpell.source() == SpellSource.ELEMENTAL) {
            return "Ressource " + availableSpell.cost();
        }
        String crystalState = availableSpell.crystal().getName()
                + " " + availableSpell.crystal().getCurrentCharge()
                + "/" + availableSpell.crystal().getMaxCharge();
        if (availableSpell.isPartialCast()) {
            return crystalState
                    + ", benötigt " + availableSpell.requiredCost()
                    + ", Restcast " + availableSpell.effectivenessPercent()
                    + "% – Kristall zerbricht";
        }
        return crystalState + ", Kosten " + availableSpell.cost();
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

}
