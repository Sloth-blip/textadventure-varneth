package varneth.engine.events;

public record SkillProgressed(
        String skillId,
        String skillName,
        int experienceGained,
        int currentExperience,
        int experienceThreshold,
        int levelsGained,
        int resultingLevel
) implements GameEvent {}
