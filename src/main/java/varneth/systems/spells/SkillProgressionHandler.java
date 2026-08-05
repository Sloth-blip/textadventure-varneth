package varneth.systems.spells;

import java.util.Optional;

import varneth.engine.events.EventBus;
import varneth.engine.events.SkillProgressed;

public class SkillProgressionHandler {

    private final EventBus bus;

    public SkillProgressionHandler(EventBus bus) {
        this.bus = bus;
    }

    public Optional<SkillProgressed> recordSuccessfulCast(AvailableSpell availableSpell) {
        if (availableSpell.source() != SpellSource.ELEMENTAL) {
            return Optional.empty();
        }

        Skill skill = availableSpell.skill();
        int experienceGained = skill.getXpPerCast();
        int levelsGained = skill.addCurrentXp(experienceGained);
        SkillProgressed event = new SkillProgressed(
                skill.getId(),
                skill.getName(),
                experienceGained,
                skill.getCurrentXp(),
                skill.getCurrentXpThreshold(),
                levelsGained,
                skill.getLevel()
        );
        bus.publish(event);
        return Optional.of(event);
    }
}
