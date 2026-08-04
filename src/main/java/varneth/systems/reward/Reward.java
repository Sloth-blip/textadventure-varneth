package varneth.systems.reward;

import varneth.systems.items.Item;
import varneth.systems.spells.Skill;

public class Reward {

    private final Skill skill;
    private final Item item;
    private final int xp;
    private final int gold;

    public Reward(Skill skill, int xp, int gold) {
        this(skill, null, xp, gold);
    }

    public Reward(Item item) {
        this(null, item, 0, 0);
    }

    public Reward(Skill skill, Item item, int xp, int gold) {
        this.skill = skill;
        this.item = item;
        this.xp = xp;
        this.gold = gold;
    }

    public Skill getSkill() {return skill;}
    public Item getItem() {return item;}
    public int getXp() {return xp;}
    public int getGold() {return gold;}

}
