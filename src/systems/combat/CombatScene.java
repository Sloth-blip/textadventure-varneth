package systems.combat;


import systems.actors.enemy.Enemy;

import systems.actors.player.Player;
import systems.spells.Skill;
import ui.ConsoleMenu;

import java.util.List;
import java.util.Optional;

public class CombatScene {

    public enum CombatResult {
        WON,
        LOST,
        FLED;
    }

    public CombatResult combatLoop(Player player, List<Enemy> enemies){

        ui.ConsoleMenu CMenu = new ConsoleMenu();
        CMenu.consoleMenuCombatSceneBegin(player, enemies);

        while (true){

            /** Player Combat **/

            ConsoleMenu.CombatAction cAction = CMenu.consoleMenuCombatMenu();

            switch (cAction){
                case BASICATTACK -> {
                    Optional<Enemy> maybeTarget = CMenu.consoleMenuTargetChooser(enemies);
                    if (maybeTarget.isEmpty()){
                        continue;
                    }

                    Enemy target = maybeTarget.get();
                    int dmg = player.basicAttack();
                    System.out.println(target.getName() + " hat " + dmg + " Schaden erhalten!");
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        System.out.println(target.getName() + " besiegt! ");
                    }
                    if (enemies.stream().allMatch(Enemy::isDead)){
                        return CombatResult.WON;
                    }
                }
                case SPELL -> {
                    Optional<Skill> maybeSpell = CMenu.consoleMenuSpellChooser(player.getLearnedSkills());
                    if (maybeSpell.isEmpty()){
                        continue;
                    }
                    Skill spell = maybeSpell.get();
                    Optional<Enemy> maybeTarget = CMenu.consoleMenuTargetChooser(enemies);
                    if (maybeTarget.isEmpty()){
                        continue;
                    }
                    Enemy target = maybeTarget.get();
                    int dmg = player.calculateDamageDealtWithSkill(spell);
                    System.out.println(spell.getName() + " von " + player.getName() + " hat " + dmg + " Schaden an " + target.getName() + " verursacht!");
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        System.out.println(target.getName() + " besiegt! ");
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


            CMenu.consoleMenuCombatSceneState(player, enemies);
            for (Enemy enemy : enemies) {
                if(!enemy.isDead()) {
                    int dmg = enemy.basicAttack();
                    System.out.println(enemy.getName() + " hat " + dmg + " " + player.getName() + " Schaden zugefügt!");
                    player.recieveDamage(dmg);

                    if (player.isDead()) {
                        return CombatResult.LOST;
                    }
                }
            }
        }
    }
}