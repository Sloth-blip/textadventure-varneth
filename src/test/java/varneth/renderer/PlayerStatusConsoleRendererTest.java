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

class PlayerStatusConsoleRendererTest {

    @Test
    void rendersCurrentProgressCalculatedAttributesAndEmptySlots() {
        Player player = createPlayer(List.of());

        String rendered = render(player);

        assertTrue(rendered.contains("Status: Arenn"));
        assertTrue(rendered.contains("Level: 1 | EP: 0/10"));
        assertTrue(rendered.contains("HP: 50/50 | Ressource: 25/25"));
        assertTrue(rendered.contains("Stärke: 12 | Intelligenz: 20 | Weisheit: 11"));
        assertTrue(rendered.contains("Hauptattribut: Intelligenz"));
        assertTrue(rendered.contains("Gold: 7"));
        assertTrue(rendered.contains("Haupthand: -"));
        assertTrue(rendered.contains("Körper: -"));
        assertTrue(rendered.contains("Accessoire: -"));
    }

    @Test
    void rendersEquippedItemAndFinalModifiedAttributes() {
        Equipment focus = EquipmentTemplates.get("earth_focus");
        Player player = createPlayer(List.of(focus));
        player.equip(focus);

        String rendered = render(player);

        assertTrue(rendered.contains("Stärke: 12 | Intelligenz: 22 | Weisheit: 12"));
        assertTrue(rendered.contains("Haupthand: Erdmagischer Fokus"));
    }

    private String render(Player player) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            new PlayerStatusConsoleRenderer().render(player);
        } finally {
            System.setOut(originalOut);
        }

        return output.toString(StandardCharsets.UTF_8);
    }

    private Player createPlayer(List<Item> inventory) {
        return new Player(
                new ActorDefinition(
                        "Arenn",
                        40, 10,
                        20, 5,
                        10, 2,
                        15, 5,
                        10, 1,
                        10, 2,
                        MainAttribute.INTELLIGENCE
                ),
                new PlayerState(
                        50,
                        25,
                        1,
                        0,
                        List.of(),
                        inventory,
                        7
                )
        );
    }
}
