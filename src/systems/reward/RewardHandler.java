package systems.reward;

import systems.actors.Actor;
import systems.actors.enemy.Enemy;
import systems.actors.player.Player;
import systems.interactables.PointOfInterest;
import ui.ConsoleMenu;

import java.util.Optional;

public class RewardHandler {

    ConsoleMenu consoleMenu = new ConsoleMenu();

    public Reward getRewardsFromEnemy(Enemy enemy){
        return enemy.getReward();
    }

    public void grantRewardsFromPOI(PointOfInterest pOI, Actor actor) {
        if (!pOI.isUsed()) {
            grantRewards(pOI.getRewards(), actor);
        }
    }

    public void grantRewards(Reward reward, Actor actor) {
        if (reward.getSkill() != null) {
            consoleMenu.consoleMessageSkillLearned(reward.getSkill(), actor);
            actor.addLearnedSkill(reward.getSkill());
        }
        if (reward.getXp() != 0) {
            consoleMenu.consoleMessageExperienceGranted(reward.getXp(), actor);
            actor.gainXp(reward.getXp());
        }
        if (reward.getGold() != 0) {
        }
    }


}
