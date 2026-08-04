package varneth.systems.actors.player;

import java.util.List;

import varneth.systems.actors.ActorState;
import varneth.systems.items.Item;
import varneth.systems.spells.Skill;

public class PlayerState extends ActorState {

    private final List<Item> inventory;

    public PlayerState(
            int currentHp,
            int currentResource,
            int level,
            int currentXp,
            List<Skill> learnedSkills,
            List<Item> inventory
    ) {
        super(currentHp, currentResource, level, currentXp, learnedSkills);
        this.inventory = inventory;
    }

    protected List<Item> getInventory() {return inventory;}
    protected void addItem(Item item) {inventory.add(item);}
}
