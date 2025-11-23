package ui.enums;

public enum MainMenuAction {
    CONTINUE("Spiel Fortsetzen"),
    SAVE("Spiel Speichern"),
    LOAD("Spiel laden"),
    SETTINGS("Einstellungen"),
    END("Spiel beenden");

    public final String displayName;

    MainMenuAction(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return this.displayName;
    }
}
