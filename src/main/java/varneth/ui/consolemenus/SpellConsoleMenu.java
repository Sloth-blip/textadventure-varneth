package varneth.ui.consolemenus;

import java.util.List;
import java.util.Optional;

import varneth.input.TextInput;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.SpellSource;

public class SpellConsoleMenu {

    private final TextInput textInput;

    public SpellConsoleMenu() {
        this(new TextInput());
    }

    SpellConsoleMenu(TextInput textInput) {
        this.textInput = textInput;
    }

    public Optional<AvailableSpell> chooseSpell(
            List<AvailableSpell> availableSpells
    ) {
        int menuOption = 1;
        System.out.println("Wähle den Zauber:");
        for (AvailableSpell availableSpell : availableSpells) {
            System.out.println(
                    menuOption + ".: " + availableSpell.skill().getName()
                            + " (" + sourceLabel(availableSpell) + ")"
            );
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = textInput.inputVerifier(availableSpells.size() + 1);
        if (menuOption == selection) {
            return Optional.empty();
        }
        return Optional.of(availableSpells.get(selection - 1));
    }

    String sourceLabel(AvailableSpell availableSpell) {
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
}
