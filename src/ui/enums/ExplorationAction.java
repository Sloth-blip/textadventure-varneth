package ui.enums;

public enum ExplorationAction {
    COMBAT("Kampf"),
    INTERACTABLES("Interagieren"),
    ROOMDESCRIPTION("RaumIntro"),
    ROOMNAVIGATION("Raumnavigation"),
    MAINMENU("Hauptmenü");

    public final String displayName;

    ExplorationAction(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return this.displayName;
    }
}

