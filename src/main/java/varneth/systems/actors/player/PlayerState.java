package varneth.systems.actors.player;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import varneth.systems.actors.ActorState;
import varneth.systems.items.Equipment;
import varneth.systems.items.EquipmentSlot;
import varneth.systems.items.Item;
import varneth.systems.spells.Skill;

public class PlayerState extends ActorState {

    private final List<Item> inventory;
    private final EnumMap<EquipmentSlot, Equipment> equippedItems;
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
        this(
                currentHp,
                currentResource,
                level,
                currentXp,
                learnedSkills,
                inventory,
                gold,
                Map.of()
        );
    }

    public PlayerState(
            int currentHp,
            int currentResource,
            int level,
            int currentXp,
            List<Skill> learnedSkills,
            List<Item> inventory,
            int gold,
            Map<EquipmentSlot, Equipment> equippedItems
    ) {
        super(currentHp, currentResource, level, currentXp, learnedSkills);
        if (gold < 0) {
            throw new IllegalArgumentException("Gold must not be negative");
        }
        this.inventory = inventory;
        this.equippedItems = new EnumMap<>(EquipmentSlot.class);
        for (Map.Entry<EquipmentSlot, Equipment> entry : equippedItems.entrySet()) {
            Equipment equipment = entry.getValue();
            if (entry.getKey() != equipment.getSlot() || !inventory.contains(equipment)) {
                throw new IllegalArgumentException(
                        "Equipped items must use their own slot and belong to the inventory"
                );
            }
            this.equippedItems.put(entry.getKey(), equipment);
        }
        this.gold = gold;
    }

    protected List<Item> getInventory() {return inventory;}
    protected Map<EquipmentSlot, Equipment> getEquippedItems() {
        return Map.copyOf(equippedItems);
    }
    protected int getGold() {return gold;}
    protected void addItem(Item item) {inventory.add(item);}
    protected boolean removeItem(Item item) {return inventory.remove(item);}
    protected Equipment equip(Equipment equipment) {
        return equippedItems.put(equipment.getSlot(), equipment);
    }
    protected Equipment unequip(EquipmentSlot slot) {return equippedItems.remove(slot);}

    protected void addGold(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Gold amount must not be negative");
        }
        gold += amount;
    }
}
