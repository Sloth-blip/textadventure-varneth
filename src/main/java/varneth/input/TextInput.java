package varneth.input;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class TextInput {

    public static final Scanner scanner = new Scanner(System.in);
    private final Scanner inputScanner;
    private final PrintStream output;

    public TextInput() {
        this(scanner, System.out);
    }

    public TextInput(Scanner inputScanner, PrintStream output) {
        this.inputScanner = Objects.requireNonNull(inputScanner);
        this.output = Objects.requireNonNull(output);
    }

    public int inputVerifier(int range) {
        while (true) {

            if (!inputScanner.hasNextLine()) {
                output.println("Keine Eingabe verfügbar.");
                return -1;
            }

            String toVerify = inputScanner.nextLine();

            try {
                int verified = Integer.parseInt(toVerify);

                if (verified <= range && verified > 0){
                    return verified;
                }

                output.println("Nicht im Wertebereich!");

            } catch (NumberFormatException e) {
                output.println("Bitte gib eine gültige Zahl ein.");
            }
        }
    }

    public Optional<String> readText() {
        if (!inputScanner.hasNextLine()) {
            output.println("Keine Eingabe verfügbar.");
            return Optional.empty();
        }

        String input = inputScanner.nextLine().trim();
        if (input.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(input);
    }
}