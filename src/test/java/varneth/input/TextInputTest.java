package varneth.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class TextInputTest {

    @Test
    void readsAndTrimsFreeText() {
        var output = new ByteArrayOutputStream();
        var input = new TextInput(
                new Scanner("  Steinschleuder  \n"),
                new PrintStream(output)
        );

        assertEquals("Steinschleuder", input.readText().orElseThrow());
        assertEquals("", output.toString());
    }

    @Test
    void emptyLineAndEndOfInputCancelTextEntry() {
        var output = new ByteArrayOutputStream();
        var blankInput = new TextInput(
                new Scanner("   \n"),
                new PrintStream(output)
        );
        var exhaustedInput = new TextInput(
                new Scanner(""),
                new PrintStream(output)
        );

        assertTrue(blankInput.readText().isEmpty());
        assertTrue(exhaustedInput.readText().isEmpty());
        assertTrue(output.toString().contains("Keine Eingabe verfügbar."));
    }
}
