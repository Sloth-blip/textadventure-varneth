package varneth.systems.actors;

public enum MainAttribute {
    STRENGTH("Stärke"),
    INTELLIGENCE("Intelligenz"),
    NONE("-");

    public final String name;

    MainAttribute(String name) {
        this.name = name;
    }

    @Override
    public String toString(){return name;}
}
