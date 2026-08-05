package varneth.systems.actors.player;

import java.util.List;

import varneth.systems.actors.ActorState;
import varneth.systems.items.Item;
import varneth.systems.spells.Skill;

public class PlayerState extends ActorState {

    private final List<Item> inventory;
    private int gold;

    public PlayerState(
            int currentHp,
            int currentResource,
            int level,
            int currentXp,
            List<Skill> learnedSkills,
            List<Item> inventory,
            int gold
    ) {
        super(currentHp, currentResource, level, currentXp, learnedSkills);
        if (gold < 0) {
            throw new IllegalArgumentException("Gold must not be negative");
        }
        this.inventory = inventory;
        this.gold = gold;
    }

    protected List<Item> getInventory() {return inventory;}
    protected int getGold() {return gold;}
    protected void addItem(Item item) {inventory.add(item);}
    protected boolean removeItem(Item item) {return inventory.remove(item);}

    protected void addGold(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Gold amount must not be negative");
        }
        gold += amount;
    }
}
