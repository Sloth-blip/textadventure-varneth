package varneth.systems.actors.player;

import java.util.ArrayList;
import java.util.List;

import varneth.systems.actors.Actor;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.items.Item;
import varneth.systems.items.MagicCrystal;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.Skill;
import varneth.systems.spells.SpellSource;
import varneth.systems.spells.SpellTemplates;

public class Player extends Actor<ActorDefinition> {

    private final PlayerState playerState;

    public Player(ActorDefinition def, PlayerState state) {
        super(def, state);
        this.playerState = state;
    }

    public boolean isDead() {return getCurrentHp() == 0;}

    public List<Item> getInventory() {return List.copyOf(playerState.getInventory());}

    public void addItem(Item item) {playerState.addItem(item);}

    public List<AvailableSpell> getAvailableSpells() {
        List<AvailableSpell> availableSpells = new ArrayList<>();

        for (Skill skill : getLearnedSkills()) {
            if (getCurrentResource() >= skill.getBaseManaCost()) {
                availableSpells.add(AvailableSpell.fromElemental(skill));
            }
        }

        for (Item item : getInventory()) {
            if (item instanceof MagicCrystal crystal) {
                for (Skill skill : SpellTemplates.getBasicSpellsFor(crystal.getMagicType())) {
                    boolean alreadyAvailable = availableSpells.stream()
                            .anyMatch(option -> option.source() == SpellSource.CRYSTAL
                                    && option.skill().getId().equals(skill.getId()));
                    if (!alreadyAvailable && crystal.canPower(skill.getMagicType(), skill.getBaseManaCost())) {
                        availableSpells.add(AvailableSpell.fromCrystal(skill, crystal));
                    }
                }
            }
        }

        return availableSpells;
    }

    public boolean tryPayCastingCost(AvailableSpell availableSpell) {
        return switch (availableSpell.source()) {
            case ELEMENTAL -> trySpendResource(availableSpell.cost());
            case CRYSTAL -> getInventory().contains(availableSpell.crystal())
                    && availableSpell.crystal().canPower(
                            availableSpell.skill().getMagicType(),
                            availableSpell.cost()
                    )
                    && availableSpell.crystal().consumeCharge(availableSpell.cost());
        };
    }
}
