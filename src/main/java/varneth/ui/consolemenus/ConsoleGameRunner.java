package varneth.ui.consolemenus;

import java.util.List;
import java.util.Scanner;

import varneth.application.engine.ui.PlayerStatus;
import varneth.application.intent.PlayerIntent;
import varneth.application.session.GameSession;
import varneth.application.state.ActionOption;
import varneth.application.state.UiState;

public class ConsoleGameRunner {

    private final GameSession session;
    private final Scanner scanner;

    public ConsoleGameRunner() {
        this.session = new GameSession();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        UiState currentState = session.startNewGame();

        while (true) {
            render(currentState);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Spiel beendet.");
                break;
            }

            PlayerIntent intent = mapInputToIntent(input, currentState);
            currentState = session.handleIntent(intent);
        }
    }

    private void render(UiState state) {
        System.out.println();
        System.out.println("=== MODE: " + state.getMode() + " ===");
        System.out.println();
        System.out.println(state.getContextText());
        System.out.println();

        PlayerStatus ps = state.getPlayerStatus();

        System.out.println("=== STATUS ===");
        System.out.println("HP: " + ps.getCurrentHp() + "/" + ps.getMaxHp());
        System.out.println("MP: " + ps.getCurrentRessource() + "/" + ps.getMaxRessource());
        System.out.println("XP: " + ps.getXp() + "/" + ps.getNextXpThreshold());

        if(!ps.getSkills().isEmpty()) {
            System.out.println("Skills:");
            ps.getSkills().forEach(s -> System.out.println("- " + s));
        }


        List<ActionOption> actions = state.getActions();
        for (int i = 0; i < actions.size(); i++) {
            ActionOption action = actions.get(i);
            System.out.println((i + 1) + ". " + action.getLabel());
        }

        System.out.println();
        System.out.println("[Inventar] " + state.getInventory());
        System.out.println("[Quests] " + state.getQuests());
        System.out.println("[Map] " + state.getMapEntries());
        System.out.println("[DevLog] " + state.getDevLog());
        System.out.println();
        System.out.print("> ");
    }

    private PlayerIntent mapInputToIntent(String input, UiState state) {
        List<ActionOption> actions = state.getActions();

        try {
            int selectedNumber = Integer.parseInt(input);
            int index = selectedNumber - 1;

            if (index >= 0 && index < actions.size()) {
                ActionOption selectedAction = actions.get(index);
                return new PlayerIntent("SELECT_ACTION", selectedAction.getId());
            }
        } catch (NumberFormatException ignored) {
        }

        return new PlayerIntent("RAW_INPUT", input);
    }
}