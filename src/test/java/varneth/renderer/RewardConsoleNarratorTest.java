package varneth.renderer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import varneth.engine.events.EventBus;
import varneth.engine.events.RewardGranted;

class RewardConsoleNarratorTest {

    @Test
    void rendersEveryGrantedRewardPartAndAggregatedLevelUps() {
        EventBus bus = new EventBus();
        new RewardConsoleNarrator(bus);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            bus.publish(new RewardGranted(
                    "Arenn",
                    List.of("Steinschleuder"),
                    List.of("Feuermagiekristall"),
                    500,
                    15,
                    4,
                    5
            ));
        } finally {
            System.setOut(originalOut);
        }

        String rendered = output.toString(StandardCharsets.UTF_8);
        assertTrue(rendered.contains("Arenn hat Steinschleuder erlernt!"));
        assertTrue(rendered.contains("Arenn hat Feuermagiekristall erhalten!"));
        assertTrue(rendered.contains("Arenn hat 500 Erfahrung erhalten!"));
        assertTrue(rendered.contains("Arenn hat 15 Gold erhalten!"));
        assertTrue(rendered.contains("Arenn ist 4 Level aufgestiegen und ist nun Level 5"));
    }
}
