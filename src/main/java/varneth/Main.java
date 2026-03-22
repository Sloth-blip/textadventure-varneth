package varneth;
import varneth.ui.consolemenus.ConsoleGameRunner;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length > 0 && args[0].equals("web")) {
            Server.start();
        } else { ConsoleGameRunner runner = new ConsoleGameRunner();
        runner.run();

        }
    }
}
