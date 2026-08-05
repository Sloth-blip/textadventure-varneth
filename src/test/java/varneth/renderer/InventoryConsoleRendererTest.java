package varneth.renderer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;
import varneth.systems.items.Equipment;
import varneth.systems.items.EquipmentTemplates;
import varneth.systems.items.Item;
import varneth.systems.items.MagicCrystal;
import varneth.systems.items.MagicCrystalTemplates;

class InventoryConsoleRendererTest {

    @Test
    void rendersGoldAndEmptyInventory() {
        String rendered = render(createPlayer(3, List.of()));

        assertTrue(rendered.contains("Inventar"));
        assertTrue(rendered.contains("Gold: 3"));
        assertTrue(rendered.contains("Keine Gegenstände."));
    }

    @Test
    void rendersCrystalLoreMagicTypeAndCurrentCharge() {
        MagicCrystal crystal = MagicCrystalTemplates.get("fire_crystal");
        assertTrue(crystal.consumeCharge(6));
        Player player = createPlayer(7, List.of(crystal));

        String rendered = render(player);

        assertTrue(rendered.contains("Gold: 7"));
        assertTrue(rendered.contains("1. Feuermagiekristall"));
        assertTrue(rendered.contains(crystal.getDescription()));
        assertTrue(rendered.contains("Magieart: Feuer | Ladung: 4/10"));
    }

    @Test
    void rendersEquipmentSlotBonusesAndEquippedState() {
        Equipment focus = EquipmentTemplates.get("earth_focus");
        Player player = createPlayer(0, List.of(focus));
        player.equip(focus);

        String rendered = render(player);

        assertTrue(rendered.contains("1. Erdmagischer Fokus"));
        assertTrue(rendered.contains(focus.getDescription()));
        assertTrue(rendered.contains("Slot: Haupthand | Ausgerüstet"));
        assertTrue(rendered.contains("Boni: +2 Intelligenz, +1 Weisheit"));
    }

    private String render(Player player) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            new InventoryConsoleRenderer().render(player);
        } finally {
            System.setOut(originalOut);
        }

        return output.toString(StandardCharsets.UTF_8);
    }

    private Player createPlayer(int gold, List<Item> inventory) {
        ActorDefinition definition = new ActorDefinition(
                "Arenn",
                40, 10,
                20, 5,
                10, 2,
                15, 5,
                10, 1,
                10, 2,
                MainAttribute.INTELLIGENCE
        );
        PlayerState state = new PlayerState(
                50,
                25,
                1,
                0,
                List.of(),
                inventory,
                gold
        );
        return new Player(definition, state);
    }
}
