package systems.combat;

import engine.player.PlayerState;
import systems.actors.enemy.EnemyState;
import systems.spells.KnownSpell;
import ui.ConsoleMenu;

import java.util.List;
import java.util.Optional;

public class CombatScene {

    public enum CombatResult {
        WON,
        LOST,
        FLED;
    }

    public CombatResult combatLoop(PlayerState player, List<EnemyState> enemies){

        ui.ConsoleMenu CMenu = new ConsoleMenu();
        CMenu.consoleMenuCombatSceneBegin(player, enemies);

        while (true){

            /** Player Combat **/

            ConsoleMenu.CombatAction cAction = CMenu.consoleMenuCombatMenu();

            switch (cAction){
                case BASICATTACK -> {
                    Optional<EnemyState> maybeTarget = CMenu.consoleMenuTargetChooser(enemies);
                    if (maybeTarget.isEmpty()){
                        continue;
                    }

                    EnemyState target = maybeTarget.get();
                    int dmg = player.defaultAttack();
                    System.out.println(target + " hat " + dmg + " Schaden erhalten!");
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        System.out.println(target + " besiegt! " + target.getXpReward() + " Erfahrung gewonnen.");
                        player.gainExperiencePoints(target.getXpReward());
                    }
                    if (enemies.stream().allMatch(EnemyState::isDead)){
                        return CombatResult.WON;
                    }
                }
                case SPELL -> {
                    Optional<KnownSpell> maybeSpell = CMenu.consoleMenuSpellChooser(player.getKnownSpells());
                    if (maybeSpell.isEmpty()){
                        continue;
                    }
                    KnownSpell spell = maybeSpell.get();
                    Optional<EnemyState> maybeTarget = CMenu.consoleMenuTargetChooser(enemies);
                    if (maybeTarget.isEmpty()){
                        continue;
                    }
                    EnemyState target = maybeTarget.get();
                    int dmg = spell.damageSpell(player);
                    System.out.println(spell + " von " + player + " hat " + dmg + " Schaden an " + target + " verursacht!");
                    target.recieveDamage(dmg);
                    if(target.isDead()){
                        System.out.println(target + " besiegt! " + target.getXpReward() + " Erfahrung gewonnen.");
                        player.gainExperiencePoints(target.getXpReward());
                    }
                    if (enemies.stream().allMatch(EnemyState::isDead)){
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
            for (EnemyState enemy : enemies) {
                if(!enemy.isDead()) {
                    int dmg = enemy.defaultAttack();
                    System.out.println(enemy + " hat " + dmg + " " + player + " Schaden zugefügt!");
                    player.recieveDamage(dmg);

                    if (player.isDead()) {
                        return CombatResult.LOST;
                    }
                }
            }
        }
    }
}