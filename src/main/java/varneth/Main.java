package varneth;

import varneth.engine.GameStart;
import varneth.ui.consolemenus.ConsoleGameRunner;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            new GameStart().gameStartStart();
            return;
        }

        switch (args[0]) {
            case "demo" -> new ConsoleGameRunner().run();
            case "web" -> Server.start();
            default -> throw new IllegalArgumentException(
                    "Unbekannter Startmodus: " + args[0]
                            + ". Erlaubt sind: demo, web"
            );
        }
    }
}
