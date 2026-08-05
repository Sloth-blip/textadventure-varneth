package varneth.renderer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import varneth.engine.events.EquipmentChanged;
import varneth.engine.events.EventBus;
import varneth.systems.items.EquipmentSlot;

class EquipmentConsoleNarratorTest {

    @Test
    void rendersEquipReplacementAndUnequipResults() {
        EventBus bus = new EventBus();
        new EquipmentConsoleNarrator(bus);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            bus.publish(new EquipmentChanged(
                    "Arenn",
                    "Erdmagischer Fokus",
                    EquipmentSlot.MAIN_HAND,
                    true,
                    Optional.empty()
            ));
            bus.publish(new EquipmentChanged(
                    "Arenn",
                    "Kraftgriff",
                    EquipmentSlot.MAIN_HAND,
                    true,
                    Optional.of("Erdmagischer Fokus")
            ));
            bus.publish(new EquipmentChanged(
                    "Arenn",
                    "Kraftgriff",
                    EquipmentSlot.MAIN_HAND,
                    false,
                    Optional.empty()
            ));
        } finally {
            System.setOut(originalOut);
        }

        String rendered = output.toString(StandardCharsets.UTF_8);
        assertTrue(rendered.contains(
                "Arenn rüstet „Erdmagischer Fokus“ aus (Haupthand)."
        ));
        assertTrue(rendered.contains(
                "Arenn ersetzt „Erdmagischer Fokus“ durch „Kraftgriff“ (Haupthand)."
        ));
        assertTrue(rendered.contains(
                "Arenn legt „Kraftgriff“ ab (Haupthand)."
        ));
    }
}
