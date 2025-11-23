package systems.reward;

import systems.actors.Actor;
import systems.actors.enemy.Enemy;
import systems.interactables.PointOfInterest;
import ui.consolemenus.ConsoleMenuGeneral;

public class RewardHandler {

    ConsoleMenuGeneral consoleMenuGeneral = new ConsoleMenuGeneral();

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
            consoleMenuGeneral.consoleMessageSkillLearned(reward.getSkill(), actor);
            actor.addLearnedSkill(reward.getSkill());
        }
        if (reward.getXp() != 0) {
            consoleMenuGeneral.consoleMessageExperienceGranted(reward.getXp(), actor);
            actor.gainXp(reward.getXp());
        }
        if (reward.getGold() != 0) {
        }
    }


}
