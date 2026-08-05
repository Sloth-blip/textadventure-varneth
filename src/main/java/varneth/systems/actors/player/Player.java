package varneth.systems.actors.player;

import java.util.ArrayList;
import java.util.List;

import varneth.systems.actors.Actor;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.items.Item;
import varneth.systems.items.MagicCrystal;
import varneth.systems.magic.CrystalCastingCost;
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
    public int getGold() {return playerState.getGold();}

    public void addItem(Item item) {playerState.addItem(item);}
    public void addGold(int amount) {playerState.addGold(amount);}

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
                    int effectiveCost = CrystalCastingCost.calculate(
                            skill.getBaseManaCost(),
                            getWisdom()
                    );
                    if (crystal.canPower(skill.getMagicType(), 1)) {
                        AvailableSpell candidate =
                                AvailableSpell.fromCrystal(skill, crystal, effectiveCost);
                        int existingIndex = findCrystalOptionIndex(availableSpells, skill);
                        if (existingIndex < 0) {
                            availableSpells.add(candidate);
                        } else if (isBetterCrystalOption(
                                candidate,
                                availableSpells.get(existingIndex)
                        )) {
                            availableSpells.set(existingIndex, candidate);
                        }
                    }
                }
            }
        }

        return availableSpells;
    }

    public boolean tryPayCastingCost(AvailableSpell availableSpell) {
        return switch (availableSpell.source()) {
            case ELEMENTAL -> trySpendResource(availableSpell.paymentAmount());
            case CRYSTAL -> tryPayCrystalCastingCost(availableSpell);
        };
    }

    private int findCrystalOptionIndex(List<AvailableSpell> availableSpells, Skill skill) {
        for (int index = 0; index < availableSpells.size(); index++) {
            AvailableSpell option = availableSpells.get(index);
            if (option.source() == SpellSource.CRYSTAL
                    && option.skill().getId().equals(skill.getId())) {
                return index;
            }
        }
        return -1;
    }

    private boolean isBetterCrystalOption(
            AvailableSpell candidate,
            AvailableSpell current
    ) {
        if (candidate.isPartialCast() != current.isPartialCast()) {
            return !candidate.isPartialCast();
        }
        return candidate.paymentAmount() > current.paymentAmount();
    }

    private boolean tryPayCrystalCastingCost(AvailableSpell availableSpell) {
        MagicCrystal crystal = availableSpell.crystal();
        if (!getInventory().contains(crystal)
                || !crystal.canPower(
                        availableSpell.skill().getMagicType(),
                        availableSpell.paymentAmount()
                )
                || !crystal.consumeCharge(availableSpell.paymentAmount())) {
            return false;
        }

        if (availableSpell.crystalBreaks()) {
            playerState.removeItem(crystal);
        }
        return true;
    }
}
