package varneth.application.engine;

import java.util.ArrayList;
import java.util.List;

import varneth.systems.reward.Reward;

public class CombatRewardBundle {
    
    private final List<Reward> rewards;
    private final List<String> messages;

    public CombatRewardBundle(List<Reward> rewards, List<String> messages) {
        this.rewards = rewards;
        this.messages = messages;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public List<String> getMessages() {
        return messages;
    }
    
    public static CombatRewardBundle empty() {
        return new CombatRewardBundle(new ArrayList<>(), new ArrayList<>());
    }
}
