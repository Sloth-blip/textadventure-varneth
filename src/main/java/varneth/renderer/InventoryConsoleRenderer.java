package varneth.renderer;

import java.util.List;

import varneth.systems.actors.player.Player;
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
            renderItem(index + 1, inventory.get(index));
        }
    }

    private void renderItem(int position, Item item) {
        System.out.println(position + ". " + item.getName());
        System.out.println("   " + item.getDescription());

        if (item instanceof MagicCrystal crystal) {
            System.out.println(
                    "   Magieart: " + magicTypeLabel(crystal.getMagicType())
                            + " | Ladung: " + crystal.getCurrentCharge()
                            + "/" + crystal.getMaxCharge()
            );
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
