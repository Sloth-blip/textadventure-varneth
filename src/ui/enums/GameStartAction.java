package ui.enums;

public enum GameStartAction {

    NEWGAME("Neues Spiel"),
    LOAD("Spiel laden"),
    SETTINGS("Einstellungen"),
    END("Spiel beenden");

    public final String displayName;

    GameStartAction(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return this.displayName;
    }
}
