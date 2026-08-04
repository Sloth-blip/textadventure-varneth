package varneth.engine.events;

import java.util.List;

public record RewardGranted(
        String recipientName,
        List<String> learnedSkillNames,
        List<String> receivedItemNames,
        int experience,
        int gold,
        int levelsGained,
        int resultingLevel
) implements GameEvent {

    public RewardGranted {
        learnedSkillNames = List.copyOf(learnedSkillNames);
        receivedItemNames = List.copyOf(receivedItemNames);
    }
}
