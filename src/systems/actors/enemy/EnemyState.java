package systems.actors.enemy;

import systems.spells.EnemySpell;

import java.util.List;

public class EnemyState {

    private final EnemyTemplate template;

    private int level;
    private int healthPointsMax;
    private int healthPointsCurrent;
    private int intelligence;
    private int strength;
    private List<EnemySpell> knownSpells;
    private int xpReward;

    @Override
    public String toString(){
        return template.getDisplayName();
    }

    public EnemyState(EnemyTemplate template) {
        this.template = template;

        this.level = template.getBaseLevel();
        this.healthPointsMax = template.getBaseHealthPoints();
        this.healthPointsCurrent = this.healthPointsMax;
        this.intelligence = template.getBaseIntelligence();
        this.strength = template.getBaseStrength();
        this.knownSpells = template.getBaseSpells();
        this.xpReward = template.getBaseXpReward();
    }

    /** Clampers **/
    private void clampHealthPoints(){
        if (this.healthPointsCurrent < 0) this.healthPointsCurrent = 0;
        if (this.healthPointsCurrent > this.healthPointsMax) this.healthPointsCurrent = this.healthPointsMax;
    }

    /** Getter **/

    public int getLevel() {
        return this.level;
    }
    public int getHealthPointsMax() {
        return this.healthPointsMax;
    }
    public int getHealthPointsCurrent() {
        return this.healthPointsCurrent;
    }
    public int getXpReward(){return this.xpReward;}

    public EnemyTemplate getTemplate() { return this.template; }
    /** Combat **/

    public int defaultAttack(){
        return this.strength + 1;
    }

    public void recieveDamage(int damage){
        this.healthPointsCurrent -= damage;
        clampHealthPoints();
    }

    public void heal(int heal) {
        this.healthPointsCurrent += heal;
        clampHealthPoints();
    }

    public boolean isDead() {
        return this.healthPointsCurrent == 0;
    }
}
