package systems.spells;

import engine.player.PlayerState;

public class KnownSpell {

    private final SpellTemplate template;

    private int level;
    private int exP;
    private int manaCost;
    private int modifier;

    @Override
    public String toString(){return template.getDisplayName();}

    public KnownSpell(SpellTemplate template){
        this.template = template;

        this.level = template.getBaseLevel();
        this.exP = template.getBaseExP();
        this.manaCost = template.getBaseManaCost();
        this.modifier = template.getBaseModifier();
    }

    /** Level-up logic **/

    public void gainExperiencePoints(int amountGained){
        this.exP += amountGained;
        while (this.exP >= experiencePointsNeededForNextLevel()) {
            levelUp();
        }
    }

    private void levelUp() {
        this.exP -= experiencePointsNeededForNextLevel();
        this.level++;
    }

    public int experiencePointsNeededForNextLevel() {
        return (int) Math.round(10 * Math.pow(this.level, 2));
    }

    /** Test **/

    public int damageSpell(PlayerState player){
        int dmg = player.getIntelligence() + this.modifier;
        gainExperiencePoints(dmg);
        return dmg;
    }
}
