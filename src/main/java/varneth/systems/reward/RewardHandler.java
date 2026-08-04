package varneth.systems.reward;

import java.util.Optional;

import varneth.engine.events.EventBus;
import varneth.engine.events.RewardGranted;
import varneth.systems.actors.enemy.Enemy;
import varneth.systems.actors.player.Player;
import varneth.systems.interactables.PointOfInterest;

public class RewardHandler {

    private final EventBus bus;

    public RewardHandler(EventBus bus) {
        this.bus = bus;
    }

    public Reward getRewardsFromEnemy(Enemy enemy){
        return enemy.getReward();
    }

    public Optional<RewardGranted> grantRewardsFromPOI(PointOfInterest pointOfInterest, Player player) {
        if (pointOfInterest.isUsed()) {
            return Optional.empty();
        }
        return Optional.of(grantRewards(pointOfInterest.getRewards(), player));
    }

    public RewardGranted grantRewards(Reward reward, Player player) {
        reward.getSkills().forEach(player::addLearnedSkill);
        reward.getItems().forEach(player::addItem);
        player.addGold(reward.getGold());
        int levelsGained = player.gainXp(reward.getXp());

        RewardGranted event = new RewardGranted(
                player.getName(),
                reward.getSkills().stream().map(skill -> skill.getName()).toList(),
                reward.getItems().stream().map(item -> item.getName()).toList(),
                reward.getXp(),
                reward.getGold(),
                levelsGained,
                player.getLevel()
        );
        bus.publish(event);
        return event;
    }
}
