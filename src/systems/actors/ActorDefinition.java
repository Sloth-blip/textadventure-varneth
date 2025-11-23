package systems.actors;

public class ActorDefinition {

    private final String name;
    private final int baseHp;
    private final int hpPerLevel;
    private final int baseResource;
    private final int resourcePerLevel;
    private final int baseStrength;
    private final int strengthPerLevel;
    private final int baseIntelligence;
    private final int intelligencePerLevel;
    private final int baseWisdom;
    private final int wisdomPerLevel;
    private final int baseXpThreshold;
    private final int xpThresholdExponent;
    private final MainAttribute mainAttribute;


    public ActorDefinition(
            String name,
            int baseHp,
            int hpPerLevel,
            int baseResource,
            int resourcePerLevel,
            int baseStrength,
            int strengthPerLevel,
            int baseIntelligence,
            int intelligencePerLevel,
            int baseWisdom,
            int wisdomPerLevel,
            int baseXpThreshold,
            int xpThresholdExponent,
            MainAttribute mainAttribute
    )
    {
        this.name = name;
        this.baseHp = baseHp;
        this.hpPerLevel = hpPerLevel;
        this.baseResource = baseResource;
        this.resourcePerLevel = resourcePerLevel;
        this.baseStrength = baseStrength;
        this.strengthPerLevel = strengthPerLevel;
        this.baseIntelligence = baseIntelligence;
        this.intelligencePerLevel = intelligencePerLevel;
        this.baseWisdom = baseWisdom;
        this.wisdomPerLevel = wisdomPerLevel;
        this.baseXpThreshold = baseXpThreshold;
        this.xpThresholdExponent = xpThresholdExponent;
        this.mainAttribute = mainAttribute;
    }

    @Override
    public String toString() {return this.name;}

    public String getName() {return name;}
    public int getBaseHp() {return baseHp;}
    public int getHpPerLevel() {return hpPerLevel;}
    public int getBaseResource() {return baseResource;}
    public int getResourcePerLevel() {return resourcePerLevel;}
    public int getBaseStrength() {return baseStrength;}
    public int getStrengthPerLevel() {return strengthPerLevel;}
    public int getBaseIntelligence() {return baseIntelligence;}
    public int getIntelligencePerLevel() {return intelligencePerLevel;}
    public int getBaseWisdom() {return baseWisdom;}
    public int getWisdomPerLevel() {return wisdomPerLevel;}
    public int getBaseXpThreshold() {return baseXpThreshold;}
    public int getXpThresholdExponent() {return xpThresholdExponent;}
    public MainAttribute getMainAttribute() {return mainAttribute;}
}
