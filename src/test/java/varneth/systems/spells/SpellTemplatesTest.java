package varneth.systems.spells;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SpellTemplatesTest {

    @Test
    void pebblesSeparatesStableIdFromUserFacingText() {
        Skill pebbles = SpellTemplates.get("pebbles");

        assertAll(
                () -> assertEquals("pebbles", pebbles.getId()),
                () -> assertEquals("Steinschleuder", pebbles.getName()),
                () -> assertEquals(
                        "Ein einfacher Erdzauber, der lose Steine weckt und mit arkaner Kraft auf sein Ziel schleudert.",
                        pebbles.getDescription()
                )
        );
    }
}
