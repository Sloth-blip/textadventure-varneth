package varneth.renderer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.engine.events.CombatantSnapshot;
import varneth.engine.events.CombatSnapshot;
import varneth.engine.events.CombatStateChanged;
import varneth.engine.events.EventBus;

class CombatConsoleNarratorTest {

    @Test
    void rendersCurrentCombatStateAndHidesDefeatedEnemies() {
        EventBus bus = new EventBus();
        new CombatConsoleNarrator(bus);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            bus.publish(new CombatStateChanged(
                    new CombatSnapshot(
                            new CombatantSnapshot("Arenn", 42, 50, 20, 25),
                            List.of(
                                    new CombatantSnapshot("Schleim", 8, 15, 0, 0),
                                    new CombatantSnapshot("Besiegter Schleim", 0, 15, 0, 0)
                            )
                    )
            ));
        } finally {
            System.setOut(originalOut);
        }

        String rendered = output.toString(StandardCharsets.UTF_8);
        assertTrue(rendered.contains("Arenn HP 42/50 | Ressource 20/25"));
        assertTrue(rendered.contains("Schleim HP 8/15"));
        assertFalse(rendered.contains("Besiegter Schleim"));
    }
}
