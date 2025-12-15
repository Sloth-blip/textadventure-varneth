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
        WON("gewonnen!"),
        LOST("verloren!"),
        FLED("geflohen!");

        private final String displayName;

        CombatResult(String displayName){this.displayName = displayName;}

        @Override
        public String toString(){return displayName;}
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
                    combatMenu.damagePrintDefault(player, target, dmg);
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        combatMenu.actorDied(target);
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
                    combatMenu.damagePrintSkill(player, spell, target, dmg);
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        combatMenu.actorDied(target);
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
                    combatMenu.damagePrintDefault(enemy, player, dmg);
                    player.recieveDamage(dmg);

                    if (player.isDead()) {
                        combatMenu.actorDied(player);
                        return CombatResult.LOST;
                    }
                }
            }
        }
    }
}