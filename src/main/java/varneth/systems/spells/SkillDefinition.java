package varneth.systems.spells;

import varneth.systems.actors.MainAttribute;

public class SkillDefinition {

    private final String id;
    private final String name;
    private final String description;
    private final int baseManaCost;
    private final int baseModifier;
    private final int modifierPerLevel;
    private final int baseXpThreshold;
    private final int xpThresholdExponent;
    private final MainAttribute modifyingAttribute;

    @Override
    public String toString(){return this.name;}

    public SkillDefinition(
            String id,
            String name,
            String description,
            int baseManaCost,
            int baseModifier, int modifierPerLevel,
            int baseXpThreshold,
            int xpThresholdExponent,
            MainAttribute modifyingAttribute
    )
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.baseManaCost = baseManaCost;
        this.baseModifier = baseModifier;
        this.modifierPerLevel = modifierPerLevel;
        this.baseXpThreshold = baseXpThreshold;
        this.xpThresholdExponent = xpThresholdExponent;
        this.modifyingAttribute = modifyingAttribute;
    }

    /** Getter **/

    public String getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public int getBaseManaCost() {return baseManaCost;}
    public int getBaseModifier() {return baseModifier;}
    public int getModifierPerLevel() {return modifierPerLevel;}
    public int getBaseXpThreshold() {return baseXpThreshold;}
    public int getXpThresholdExponent() {return xpThresholdExponent;}
    public MainAttribute getModifyingAttribute() {return modifyingAttribute;}

}
