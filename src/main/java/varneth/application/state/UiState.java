package varneth.application.state;

import java.util.List;
import java.util.Map;

import varneth.application.engine.ui.MapView;
import varneth.application.engine.ui.PlayerStatus;

public class UiState {

    private final String contextText;
    private final List<ActionOption> actions;
    private final List<String> inventory;
    private final List<String> quests;
    private final List<String> mapEntries;
    private final List<String> devLog;
    private final GameMode mode;
    private final PlayerStatus playerStatus;
    private final MapView map;
    private final Map<String, List<String>> dialogNotebook;

    public UiState(
            String contextText,
            List<ActionOption> actions,
            List<String> inventory,
            List<String> quests,
            List<String> mapEntries,
            List<String> devLog,
            GameMode mode,
            PlayerStatus playerStatus,
            MapView map,
            Map<String, List<String>> dialogNotebook
    ) {
        this.contextText = contextText;
        this.actions = actions;
        this.inventory = inventory;
        this.quests = quests;
        this.mapEntries = mapEntries;
        this.devLog = devLog;
        this.mode = mode;
        this.playerStatus = playerStatus;
        this.map = map;
        this.dialogNotebook = dialogNotebook;
    }

    public String getContextText() {return contextText;}
    public List<ActionOption> getActions() {return actions;}
    public List<String> getInventory() {return inventory;}
    public List<String> getQuests() {return quests;}
    public List<String> getMapEntries() {return mapEntries;}
    public List<String> getDevLog() {return devLog;}
    public GameMode getMode() {return mode;}
    public PlayerStatus getPlayerStatus() {return playerStatus;}
    public MapView getMap() {return map;}
    public Map<String, List<String>> getDialogNotebook() {return dialogNotebook;}
}