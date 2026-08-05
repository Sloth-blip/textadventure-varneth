package varneth.renderer;

import java.util.ArrayList;
import java.util.List;

import varneth.systems.actors.player.Player;
import varneth.systems.items.Equipment;
import varneth.systems.items.EquipmentModifiers;
import varneth.systems.items.Item;
import varneth.systems.items.MagicCrystal;
import varneth.systems.magic.MagicType;

public class InventoryConsoleRenderer {

    public void render(Player player) {
        System.out.println("Inventar");
        System.out.println("Gold: " + player.getGold());

        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Keine Gegenstände.");
            return;
        }

        for (int index = 0; index < inventory.size(); index++) {
            renderItem(index + 1, inventory.get(index), player);
        }
    }

    private void renderItem(int position, Item item, Player player) {
        System.out.println(position + ". " + item.getName());
        System.out.println("   " + item.getDescription());

        if (item instanceof MagicCrystal crystal) {
            System.out.println(
                    "   Magieart: " + magicTypeLabel(crystal.getMagicType())
                            + " | Ladung: " + crystal.getCurrentCharge()
                            + "/" + crystal.getMaxCharge()
            );
        } else if (item instanceof Equipment equipment) {
            String equippedLabel = player.isEquipped(equipment)
                    ? " | Ausgerüstet"
                    : "";
            System.out.println(
                    "   Slot: " + equipment.getSlot() + equippedLabel
            );
            System.out.println(
                    "   Boni: " + equipmentBonuses(equipment.getModifiers())
            );
        }
    }

    private String equipmentBonuses(EquipmentModifiers modifiers) {
        List<String> bonuses = new ArrayList<>();
        addBonus(bonuses, modifiers.strength(), "Stärke");
        addBonus(bonuses, modifiers.intelligence(), "Intelligenz");
        addBonus(bonuses, modifiers.wisdom(), "Weisheit");
        return bonuses.isEmpty() ? "-" : String.join(", ", bonuses);
    }

    private void addBonus(List<String> bonuses, int amount, String name) {
        if (amount != 0) {
            bonuses.add(String.format("%+d %s", amount, name));
        }
    }

    private String magicTypeLabel(MagicType magicType) {
        return switch (magicType) {
            case EARTH -> "Erde";
            case FIRE -> "Feuer";
            case UR_MAGIC -> "Ur-Magie";
        };
    }
}
