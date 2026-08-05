package varneth.systems.spells;

import varneth.systems.actors.MainAttribute;
import varneth.systems.magic.MagicType;

public class SkillDefinition {

    private final String id;
    private final String name;
    private final String description;
    private final MagicType magicType;
    private final int baseManaCost;
    private final int baseModifier;
    private final int modifierPerLevel;
    private final int baseXpThreshold;
    private final int xpThresholdExponent;
    private final int xpPerCast;
    private final MainAttribute modifyingAttribute;

    @Override
    public String toString(){return this.name;}

    public SkillDefinition(
            String id,
            String name,
            String description,
            MagicType magicType,
            int baseManaCost,
            int baseModifier, int modifierPerLevel,
            int baseXpThreshold,
            int xpThresholdExponent,
            int xpPerCast,
            MainAttribute modifyingAttribute
    )
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.magicType = magicType;
        this.baseManaCost = baseManaCost;
        this.baseModifier = baseModifier;
        this.modifierPerLevel = modifierPerLevel;
        this.baseXpThreshold = baseXpThreshold;
        this.xpThresholdExponent = xpThresholdExponent;
        this.xpPerCast = xpPerCast;
        this.modifyingAttribute = modifyingAttribute;
    }

    /** Getter **/

    public String getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public MagicType getMagicType() {return magicType;}
    public int getBaseManaCost() {return baseManaCost;}
    public int getBaseModifier() {return baseModifier;}
    public int getModifierPerLevel() {return modifierPerLevel;}
    public int getBaseXpThreshold() {return baseXpThreshold;}
    public int getXpThresholdExponent() {return xpThresholdExponent;}
    public int getXpPerCast() {return xpPerCast;}
    public MainAttribute getModifyingAttribute() {return modifyingAttribute;}

}
