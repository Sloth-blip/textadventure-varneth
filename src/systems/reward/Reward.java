package systems.reward;

import systems.spells.Skill;

public class Reward {

    private final Skill skill;
    private final int xp;
    private final int gold;
    //private final List<Item> items;


    public Reward(Skill skill, int xp, int gold) {
        this.skill = skill;
        this.xp = xp;
        this.gold = gold;
    }

    public Skill getSkill() {return skill;}
    public int getXp() {return xp;}
    public int getGold() {return gold;}

}
