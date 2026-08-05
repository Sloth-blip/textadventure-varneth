package varneth.renderer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import varneth.engine.events.CrystalShattered;
import varneth.engine.events.EventBus;

class CrystalConsoleNarratorTest {

    @Test
    void rendersCrystalShatteringEvent() {
        EventBus bus = new EventBus();
        new CrystalConsoleNarrator(bus);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            bus.publish(new CrystalShattered("Arenn", "Feuermagiekristall"));
        } finally {
            System.setOut(originalOut);
        }

        String rendered = output.toString(StandardCharsets.UTF_8);
        assertTrue(rendered.contains(
                "Arenns Feuermagiekristall wird vollständig aufgezehrt und zerbricht!"
        ));
    }
}
