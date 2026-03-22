package varneth.input;

import java.util.Scanner;

public class TextInput {

    public static final Scanner scanner = new Scanner(System.in);

    public int inputVerifier(int range) {
        while (true) {

            if (!scanner.hasNextLine()) {
                System.out.println("Keine Eingabe verfügbar.");
                return -1;
            }

            String toVerify = scanner.nextLine();

            try {
                int verified = Integer.parseInt(toVerify);

                if (verified <= range && verified > 0){
                    return verified;
                }

                System.out.println("Nicht im Wertebereich!");

            } catch (NumberFormatException e) {
                System.out.println("Bitte gib eine gültige Zahl ein.");
            }
        }
    }
}