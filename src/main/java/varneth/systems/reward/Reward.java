package varneth.systems.reward;

import java.util.List;

import varneth.systems.items.Item;
import varneth.systems.spells.Skill;

public class Reward {

    private final List<Skill> skills;
    private final List<Item> items;
    private final int xp;
    private final int gold;

    public Reward(List<Skill> skills, List<Item> items, int xp, int gold) {
        if (xp < 0 || gold < 0) {
            throw new IllegalArgumentException("Reward values must not be negative");
        }
        this.skills = List.copyOf(skills);
        this.items = List.copyOf(items);
        this.xp = xp;
        this.gold = gold;
    }

    public Reward(int xp, int gold) {
        this(List.of(), List.of(), xp, gold);
    }

    public Reward(Skill skill, int xp, int gold) {
        this(List.of(skill), List.of(), xp, gold);
    }

    public Reward(Item item) {
        this(List.of(), List.of(item), 0, 0);
    }

    public List<Skill> getSkills() {return skills;}
    public List<Item> getItems() {return items;}
    public int getXp() {return xp;}
    public int getGold() {return gold;}
}
