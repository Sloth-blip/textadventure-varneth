package varneth.application.session;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import varneth.application.engine.CombatEngine;
import varneth.application.engine.CombatResult;
import varneth.application.engine.CombatRewardBundle;
import varneth.application.engine.CombatSessionData;
import varneth.application.engine.DialogSessionData;
import varneth.application.engine.ExplorationEngine;
import varneth.application.engine.ExplorationSessionData;
import varneth.application.engine.PoiInteractionResult;
import varneth.application.engine.ui.MapView;
import varneth.application.engine.ui.PlayerStatus;
import varneth.application.intent.PlayerIntent;
import varneth.application.state.ActionOption;
import varneth.application.state.GameMode;
import varneth.application.state.UiState;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;
import varneth.systems.interactables.PointOfInterestType;
import varneth.systems.reward.Reward;

public class GameSession {

    private List<ActionOption> actions;
    private List<String> inventory;
    private List<String> quests;
    private List<String> mapEntries;
    private List<String> devLog;
    private GameMode mode;
    private Map<String, List<String>> dialogNotebook;

    private Player player;

    private final ExplorationEngine explorationEngine = new ExplorationEngine();
    private final ExplorationSessionData explorationData = new ExplorationSessionData("");

    private final CombatEngine combatEngine = new CombatEngine();
    private final CombatSessionData combatData = new CombatSessionData("");

    private final DialogSessionData dialogData = new DialogSessionData("");

    public UiState startNewGame() {

        actions = new ArrayList<>();
        inventory = new ArrayList<>();
        quests = new ArrayList<>();
        mapEntries = new ArrayList<>();
        devLog = new ArrayList<>();
        dialogNotebook = new LinkedHashMap<>();

        player = new Player(
            new ActorDefinition(
                "Spieler",
                30, 
                5, 
                10, 
                2, 
                6, 
                2, 
                4, 
                2, 
                4, 
                1, 
                10, 
                2, 
                MainAttribute.STRENGTH
            ),
             new PlayerState(
                35, 
                12, 
                1, 
                0, 
                new ArrayList<>(List.of()),
                new ArrayList<>(List.of())
            )
        );

        mode = GameMode.EXPLORATION;

        explorationEngine.initialize(actions, inventory, quests, mapEntries, devLog, explorationData);

        maybeStartRoomDialog();

        return buildState();
    }

    public UiState handleIntent(PlayerIntent intent) {

        devLog.add("Intent: " + intent.getType() + " -> " + intent.getValue());

        switch (mode) {
            case EXPLORATION -> handleExplorationIntent(intent);

            case COMBAT -> handleCombatIntent(intent);

            case DIALOGUE -> handleDialogIntent(intent);
        }
        return buildState();
    }

    public UiState getCurrentState() {
        return buildState();
    }

    private PlayerStatus buildPlayerStatus() {
        
        List<String> skillNames = player.getLearnedSkills().stream().map(s -> s.getName()).toList();
        return new PlayerStatus(
            player.getLevel(),
            player.getCurrentHp(), 
            player.getMaxHp(), 
            player.getCurrentResource(), 
            player.getMaxResource(), 
            player.getCurrentXp(), 
            player.getCurrentXpThreshold(), 
            skillNames
        );

    }

    private UiState buildState() {

        PlayerStatus status = buildPlayerStatus();
        MapView mapView = explorationEngine.buildMapView(explorationData);
        
        return new UiState(
                currentContextText(),
                actions,
                inventory,
                quests,
                mapEntries,
                devLog,
                mode,
                status,
                mapView,
                dialogNotebook
        );
    }

    private void handleExplorationIntent(PlayerIntent intent) {
        var roomBefore = explorationData.getCurrentRoom();

        PoiInteractionResult poiResult = explorationEngine.handleIntent(intent, actions, mapEntries, devLog, explorationData);

        if (poiResult != null) {
            startDialog(poiResult);
            return;
        }

        var roomAfter = explorationData.getCurrentRoom();

        if (roomAfter != null && roomAfter != roomBefore) {
            maybeStartRoomDialog();
            return;
        }

        if ("SELECT_ACTION".equals(intent.getType()) && "FIGHT".equals(intent.getValue())) {
        mode = GameMode.COMBAT;
        combatEngine.initialize(actions, devLog, combatData, explorationData.getCurrentRoom(), player);
        devLog.add("Zu Combat gewechselt");
        }
    }

    private void handleCombatIntent(PlayerIntent intent) {
        CombatResult result = combatEngine.handleIntent(
            intent,
            actions,
            devLog,
            combatData,
            explorationData.getCurrentRoom(),
            player
    );

        if (result.isPlayerDefeated()) {
            devLog.add("Game Over noch nicht implementiert");
            return;
        }

        if (result.isCombatFinished()) {
            applyCombatRewards(result.getRewardBundle());

            mode = GameMode.EXPLORATION;
            explorationData.setContextText(explorationEngine.buildRoomContext(explorationData.getCurrentRoom()));
            explorationEngine.rebuildActions(actions, explorationData.getCurrentRoom());
            devLog.add("Zurück zu Exploration gewechselt");
        }
    }

    private String currentContextText() {
        return switch (mode) {
            case EXPLORATION -> explorationData.getContextText();
            case COMBAT -> combatData.getContextText();
            case DIALOGUE -> dialogData.getContextText();
        };
    }

    private void applyCombatRewards(CombatRewardBundle rewardBundle) {
        for (var reward : rewardBundle.getRewards()) {

            if (reward.getGold() > 0) {
                inventory.add(reward.getGold() + " Gold");
            }

            if (reward.getXp() > 0) {
                player.gainXp(reward.getXp());
            }

            if (reward.getSkill() != null) {
                player.addLearnedSkill(reward.getSkill());
            }
        }

        for (String message : rewardBundle.getMessages()) {
            devLog.add("Reward angewendet: " + message);
        }
    }

    private void applyReward(Reward reward, String sourceName) {
        if (reward == null) {
            return;
        }

        if (reward.getGold() > 0) {
            inventory.add(reward.getGold() + " Gold");
            devLog.add(sourceName + ": " + reward.getGold() + " Gold erhalten");
        }

        if (reward.getXp() > 0) {
            player.gainXp(reward.getXp());
            devLog.add(sourceName + ": " + reward.getXp() + " XP erhalten");
        }

        if (reward.getSkill() != null) {
            player.addLearnedSkill(reward.getSkill());
            devLog.add(sourceName + ": " + reward.getSkill().getName() + " erlernt");
        }
    }

    private void startDialog(PoiInteractionResult poiResult) {
        List<String> chunks = poiResult.getDialogChunks();

        if (chunks == null || chunks.isEmpty()) {
            chunks = List.of("...");
        }

        dialogData.setPoiResult(poiResult);
        dialogData.setSourceName(poiResult.getSourceName());
        dialogData.setDialogChunks(chunks);
        dialogData.setCurrentChunkIndex(0);
        dialogData.setContextText(chunks.get(0));

        rememberDialogChunk(dialogData.getSourceName(), chunks.get(0));

        mode = GameMode.DIALOGUE;
        rebuildDialogActions();

        devLog.add("Dialog gestartet: " + poiResult.getSourceName());
    }

    private void rebuildDialogActions() {
        actions.clear();

        boolean isLastChunk = dialogData.getCurrentChunkIndex() >= dialogData.getDialogChunks().size() - 1;

        actions.add(new ActionOption("NEXT_DIALOG", isLastChunk ? "Fertig" : "Weiter"));
    }

    private void handleDialogIntent(PlayerIntent intent) {
        if (!"SELECT_ACTION".equals(intent.getType())) {
            devLog.add("Unbekannter Dialog-Intent: " + intent.getType());
            return;
        }

        if (!"NEXT_DIALOG".equals(intent.getValue())) {
            devLog.add("Unbekannter Dialog-Intent: " + intent.getValue());
            return;
        }

        int nextIndex = dialogData.getCurrentChunkIndex() + 1;
        List<String> chunks = dialogData.getDialogChunks();

        if(nextIndex < chunks.size()) {
            dialogData.setCurrentChunkIndex(nextIndex);
            dialogData.setContextText(chunks.get(nextIndex));
            rememberDialogChunk(dialogData.getSourceName(), chunks.get(nextIndex));
            rebuildDialogActions();
            return;
        }

        finishDialog();
    }

    private void startRoomDialog() {
        var room = explorationData.getCurrentRoom();
        List<String> chunks = room.getRoomDialogChunks();
        
        if (chunks == null || chunks.isEmpty()) {
            explorationData.setContextText(explorationEngine.buildRoomContext(room));
            return;
        }

        dialogData.clear();
        dialogData.setPoiResult(null);
        dialogData.setSourceName(room.getName());
        dialogData.setDialogChunks(chunks);
        dialogData.setCurrentChunkIndex(0);
        dialogData.setContextText(chunks.get(0));
        dialogData.setReturnContextText(explorationEngine.buildRoomContext(room));

        rememberDialogChunk(dialogData.getSourceName(), chunks.get(0));

        mode = GameMode.DIALOGUE;
        rebuildDialogActions();

        devLog.add("Raumdialog gestartet: " + room.toString());
    }

    private void maybeStartRoomDialog() {
        var room = explorationData.getCurrentRoom();

        if (room == null) {
            return;
        }

        if (explorationData.hasVisited(room)) {
            explorationData.setContextText(explorationEngine.buildRoomContext(room));
            return;
        }

        explorationData.markVisited(room);

        if (room.getRoomDialogChunks() == null || room.getRoomDialogChunks().isEmpty()) {
            explorationData.setContextText(explorationEngine.buildRoomContext(room));
            return;
        }

        startRoomDialog();
    }

    private void finishDialog() {
        PoiInteractionResult poiResult = dialogData.getPoiResult();

        if (poiResult != null) {
            if (poiResult.getPoi().getType() == PointOfInterestType.REST) {
                player.takeRest();
                devLog.add("Du hast gerastet.");
            }
            applyReward(poiResult.getReward(), poiResult.getSourceName());
            explorationEngine.completePoiInteraction(poiResult, actions, devLog, explorationData);
        }

        explorationData.setContextText(explorationEngine.buildRoomContext(explorationData.getCurrentRoom()));

        if (mode != GameMode.COMBAT) {
            explorationEngine.rebuildActions(actions, explorationData.getCurrentRoom());
        }

        dialogData.clear();
        mode = GameMode.EXPLORATION;
        devLog.add("Dialog beendet");
    }

    private void rememberDialogChunk(String sourceName, String chunk) {
        if (sourceName == null || sourceName.isBlank()) {
            return;
        }

        if (chunk == null || chunk.isBlank()) {
            return;
        }

        List<String> rememberedChunks =
                dialogNotebook.computeIfAbsent(sourceName, key -> new ArrayList<>());

        if (!rememberedChunks.contains(chunk)) {
            rememberedChunks.add(chunk);
        }
    }

}