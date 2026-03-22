package varneth.systems.actors.enemy;

import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.reward.Reward;

public class EnemyDefinition extends ActorDefinition {

    private final Reward reward;

    public EnemyDefinition(
            String name,
            int baseHp,
            int hpPerLevel,
            int baseResource,
            int resourcePerLevel,
            int baseStrength,
            int strengthPerLevel,
            int baseIntelligence,
            int intelligencePerLevel,
            int baseWisdom,
            int wisdomPerLevel,
            int baseXpThreshold,
            int xpThresholdExponent,
            MainAttribute mainAttribute,
            Reward reward
    )
    {
        super(
                name,
                baseHp,
                hpPerLevel,
                baseResource,
                resourcePerLevel,
                baseStrength,
                strengthPerLevel,
                baseIntelligence,
                intelligencePerLevel,
                baseWisdom,
                wisdomPerLevel,
                baseXpThreshold,
                xpThresholdExponent,
                mainAttribute);
        this.reward = reward;
    }

    public Reward getReward() {return this.reward;}
}
