package ui.enums;

public enum CombatAction {
    BASICATTACK("Normaler Angriff"),
    SPELL("Zauber"),
    ITEM("Inventar"),
    FLEE("Fliehen");

    public final String displayName;

    CombatAction(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return this.displayName;
    }
}
