package varneth.renderer;

import java.util.Map;

import varneth.systems.actors.player.Player;
import varneth.systems.items.Equipment;
import varneth.systems.items.EquipmentSlot;

public class PlayerStatusConsoleRenderer {

    public void render(Player player) {
        System.out.println("Status: " + player.getName());
        System.out.println(
                "Level: " + player.getLevel()
                        + " | EP: " + player.getCurrentXp()
                        + "/" + player.getCurrentXpThreshold()
        );
        System.out.println(
                "HP: " + player.getCurrentHp() + "/" + player.getMaxHp()
                        + " | Ressource: " + player.getCurrentResource()
                        + "/" + player.getMaxResource()
        );
        System.out.println(
                "Stärke: " + player.getStrength()
                        + " | Intelligenz: " + player.getIntelligence()
                        + " | Weisheit: " + player.getWisdom()
        );
        System.out.println("Hauptattribut: " + player.getMainAttribute());
        System.out.println("Gold: " + player.getGold());
        renderEquipment(player.getEquippedItems());
    }

    private void renderEquipment(Map<EquipmentSlot, Equipment> equippedItems) {
        System.out.println("Ausrüstung:");
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            Equipment equipment = equippedItems.get(slot);
            String itemName = equipment == null ? "-" : equipment.getName();
            System.out.println("   " + slot + ": " + itemName);
        }
    }
}
