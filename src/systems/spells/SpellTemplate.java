package systems.spells;

public class SpellTemplate {

    private final String iD;
    private final String displayName;
    private final String description;
    private final int baseLevel;
    private final int baseExP;
    private final int baseManaCost;
    private final int baseModifier;

    @Override
    public String toString(){return this.displayName;}

    public SpellTemplate(
            String iD,
            String displayName,
            String description,
            int baseLevel,
            int baseExP,
            int baseManaCost,
            int baseModifier
    )
    {
        this.iD = iD;
        this.displayName = displayName;
        this.description = description;
        this.baseLevel = baseLevel;
        this.baseExP = baseExP;
        this.baseManaCost = baseManaCost;
        this.baseModifier = baseModifier;
    }

    /** Getter **/

    public String getiD() {return iD;}
    public String getDisplayName() {return displayName;}
    public String getDescription() {return description;}
    public int getBaseLevel() {return baseLevel;}
    public int getBaseExP() {return baseExP;}
    public int getBaseManaCost() {return baseManaCost;}
    public int getBaseModifier() {return baseModifier;}
}
