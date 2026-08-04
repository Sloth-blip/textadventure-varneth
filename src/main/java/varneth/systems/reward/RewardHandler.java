package varneth.systems.reward;

import varneth.systems.actors.enemy.Enemy;
import varneth.systems.actors.player.Player;
import varneth.systems.interactables.PointOfInterest;
import varneth.ui.consolemenus.ConsoleMenuGeneral;

public class RewardHandler {

    ConsoleMenuGeneral consoleMenuGeneral = new ConsoleMenuGeneral();

    public Reward getRewardsFromEnemy(Enemy enemy){
        return enemy.getReward();
    }

    public void grantRewardsFromPOI(PointOfInterest pOI, Player player) {
        if (!pOI.isUsed()) {
            grantRewards(pOI.getRewards(), player);
        }
    }

    public void grantRewards(Reward reward, Player player) {
        if (reward.getSkill() != null) {
            consoleMenuGeneral.consoleMessageSkillLearned(reward.getSkill(), player);
            player.addLearnedSkill(reward.getSkill());
        }
        if (reward.getItem() != null) {
            consoleMenuGeneral.consoleMessageItemReceived(reward.getItem(), player);
            player.addItem(reward.getItem());
        }
        if (reward.getXp() != 0) {
            consoleMenuGeneral.consoleMessageExperienceGranted(reward.getXp(), player);
            player.gainXp(reward.getXp());
        }
        if (reward.getGold() != 0) {
        }
    }


}
