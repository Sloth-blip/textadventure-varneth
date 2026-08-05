package varneth.renderer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import varneth.engine.events.EventBus;
import varneth.engine.events.SkillProgressed;

class SkillProgressConsoleNarratorTest {

    @Test
    void rendersProgressAndLevelUpFromSkillSnapshot() {
        EventBus bus = new EventBus();
        new SkillProgressConsoleNarrator(bus);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            bus.publish(new SkillProgressed(
                    "pebbles",
                    "Steinschleuder",
                    5,
                    5,
                    10,
                    0,
                    1
            ));
            bus.publish(new SkillProgressed(
                    "pebbles",
                    "Steinschleuder",
                    5,
                    0,
                    40,
                    1,
                    2
            ));
        } finally {
            System.setOut(originalOut);
        }

        String rendered = output.toString(StandardCharsets.UTF_8);
        assertTrue(rendered.contains("Steinschleuder erhält 5 Zauber-EP (5/10)."));
        assertTrue(rendered.contains(
                "Steinschleuder erhält 5 Zauber-EP und steigt auf Zauberlevel 2! (0/40)"
        ));
    }
}
