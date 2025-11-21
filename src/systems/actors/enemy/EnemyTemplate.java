package systems.actors.enemy;

import systems.spells.EnemySpell;

import java.util.List;

public class EnemyTemplate {

    private final String id;
    private final String displayName;
    private final int baseLevel;
    private final int baseHealthPoints;
    private final int baseIntelligence;
    private final int baseStrength;
    private final List<EnemySpell> baseSpells;
    private final int baseXpReward;

    @Override
    public String toString(){
        return this.displayName;
    }

    public EnemyTemplate(
            String id,
            String displayName,
            int baseLevel,
            int baseHealthPoints,
            int baseIntelligence,
            int strength,
            List<EnemySpell> baseSpells,
            int xpReward
    )
    {
        this.id = id;
        this.displayName = displayName;
        this.baseLevel = baseLevel;
        this.baseHealthPoints = baseHealthPoints;
        this.baseIntelligence = baseIntelligence;
        this.baseStrength = strength;
        this.baseSpells = baseSpells;
        this.baseXpReward = xpReward;
    }

    /** Getter **/

    public String getId() {
        return this.id;
    }
    public String getDisplayName() {
        return this.displayName;
    }
    public int getBaseLevel(){
        return this.baseLevel;
    }
    public int getBaseHealthPoints() {
        return this.baseHealthPoints;
    }
    public int getBaseIntelligence() {
        return this.baseIntelligence;
    }
    public int getBaseStrength() {
        return this.baseStrength;
    }
    public List<EnemySpell> getBaseSpells(){return this.baseSpells;}
    public int getBaseXpReward(){return this.baseXpReward;}
//    public List<EnemySpell> getBaseSpells() {
//        return this.baseSpells;
//    }

}
