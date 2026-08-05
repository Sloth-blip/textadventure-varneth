package varneth.ui.consolemenus;

import java.util.List;
import java.util.Optional;

import varneth.input.TextInput;
import varneth.systems.actors.player.Player;
import varneth.systems.items.Equipment;

public class EquipmentConsoleMenu {

    private final TextInput textInput = new TextInput();

    public Optional<Equipment> chooseEquipment(Player player) {
        List<Equipment> equipment = player.getEquipmentInInventory();
        if (equipment.isEmpty()) {
            return Optional.empty();
        }

        System.out.println("Ausrüstung ändern:");
        int menuOption = 1;
        for (Equipment item : equipment) {
            String action = player.isEquipped(item) ? "ablegen" : "anlegen";
            System.out.println(
                    menuOption + ".: " + item.getName()
                            + " " + action + " (" + item.getSlot() + ")"
            );
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");
        int selection = textInput.inputVerifier(equipment.size() + 1);
        if (selection == menuOption) {
            return Optional.empty();
        }
        return Optional.of(equipment.get(selection - 1));
    }
}
