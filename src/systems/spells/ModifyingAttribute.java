package systems.spells;

public enum ModifyingAttribute {
    STRENGTH("Stärke"),
    INTELLIGENCE("Intelligenz"),
    WISDOM("Weisheit");

    private final String name;

    ModifyingAttribute(String name) {
        this.name = name;
    }

    @Override
    public String toString() {return name;}

}
