package systems.combat;


import systems.actors.enemy.Enemy;

import systems.actors.player.Player;
import systems.reward.RewardHandler;
import systems.spells.Skill;
import ui.enums.CombatAction;
import ui.consolemenus.CombatConsoleMenu;

import java.util.List;
import java.util.Optional;

public class CombatScene {

    public enum CombatResult {
        WON,
        LOST,
        FLED;
    }

    public CombatResult combatLoop(Player player, List<Enemy> enemies){

        CombatConsoleMenu combatMenu = new CombatConsoleMenu();
        combatMenu.consoleMenuCombatSceneBegin(player, enemies);
        RewardHandler rewardHandler = new RewardHandler();

        while (true){

            /** Player Combat **/

            CombatAction cAction = combatMenu.consoleMenuCombatMenu();

            switch (cAction){
                case BASICATTACK -> {
                    Optional<Enemy> maybeTarget = combatMenu.consoleMenuTargetChooser(enemies);
                    if (maybeTarget.isEmpty()){
                        continue;
                    }

                    Enemy target = maybeTarget.get();
                    int dmg = player.basicAttack();
                    System.out.println(target.getName() + " hat " + dmg + " Schaden erhalten!");
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        System.out.println(target.getName() + " besiegt! ");
                        rewardHandler.grantRewards(rewardHandler.getRewardsFromEnemy(target), player);

                    }
                    if (enemies.stream().allMatch(Enemy::isDead)){
                        return CombatResult.WON;
                    }
                }
                case SPELL -> {
                    Optional<Skill> maybeSpell = combatMenu.consoleMenuSpellChooser(player.getLearnedSkills());
                    if (maybeSpell.isEmpty()){
                        continue;
                    }
                    Skill spell = maybeSpell.get();
                    Optional<Enemy> maybeTarget = combatMenu.consoleMenuTargetChooser(enemies);
                    if (maybeTarget.isEmpty()){
                        continue;
                    }
                    Enemy target = maybeTarget.get();
                    int dmg = player.calculateDamageDealtWithSkill(spell);
                    System.out.println(spell.getName() + " von " + player.getName() + " hat " + dmg + " Schaden an " + target.getName() + " verursacht!");
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        System.out.println(target.getName() + " besiegt! ");
                        rewardHandler.grantRewards(rewardHandler.getRewardsFromEnemy(target), player);
                    }
                    if (enemies.stream().allMatch(Enemy::isDead)){
                        return CombatResult.WON;
                    }
                }
                case ITEM -> System.out.println("ToDo");
                case FLEE -> {
                    return CombatResult.FLED;
                }
            }


            /** Enemy Combat **/


            combatMenu.consoleMenuCombatSceneState(player, enemies);
            for (Enemy enemy : enemies) {
                if(!enemy.isDead()) {
                    int dmg = enemy.basicAttack();
                    System.out.println(enemy.getName() + " hat " + player.getName() + " " + dmg + " Schaden zugefügt!");
                    player.recieveDamage(dmg);

                    if (player.isDead()) {
                        return CombatResult.LOST;
                    }
                }
            }
        }
    }
}