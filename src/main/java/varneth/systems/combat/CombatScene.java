package varneth.systems.combat;


import java.util.List;
import java.util.Optional;

import varneth.engine.events.CombatantSnapshot;
import varneth.engine.events.CombatSnapshot;
import varneth.engine.events.CombatStateChanged;
import varneth.engine.events.CrystalShattered;
import varneth.engine.events.DamageDealt;
import varneth.engine.events.EventBus;
import varneth.systems.actors.Actor;
import varneth.systems.actors.enemy.Enemy;
import varneth.systems.actors.player.Player;
import varneth.systems.reward.RewardHandler;
import varneth.systems.spells.AvailableSpell;
import varneth.systems.spells.Skill;
import varneth.systems.spells.SkillProgressionHandler;
import varneth.ui.consolemenus.CombatConsoleMenu;
import varneth.ui.enums.CombatAction;

public class CombatScene {

    private final EventBus bus;

    public CombatScene(EventBus bus) {
        this.bus = bus;
    }

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
        publishCombatState(player, enemies);
        RewardHandler rewardHandler = new RewardHandler(bus);
        SkillProgressionHandler skillProgressionHandler = new SkillProgressionHandler(bus);

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
                    int hpBefore = target.getCurrentHp();
                    target.recieveDamage(dmg);
                    bus.publish(new DamageDealt(player, target, dmg, null, hpBefore, target.getCurrentHp()));
                    if(target.isDead()){
                        rewardHandler.grantRewards(rewardHandler.getRewardsFromEnemy(target), player);
                    }
                    publishCombatState(player, enemies);
                    if (enemies.stream().allMatch(Enemy::isDead)){
                        return CombatResult.WON;
                    }
                }
                case SPELL -> {
                    Optional<AvailableSpell> maybeAvailableSpell =
                            combatMenu.consoleMenuSpellChooser(player.getAvailableSpells());
                    if (maybeAvailableSpell.isEmpty()){
                        continue;
                    }
                    AvailableSpell availableSpell = maybeAvailableSpell.get();
                    Skill spell = availableSpell.skill();
                    Optional<Enemy> maybeTarget = combatMenu.consoleMenuTargetChooser(enemies);
                    if (maybeTarget.isEmpty()){
                        continue;
                    }
                    if (!player.tryPayCastingCost(availableSpell)) {
                        continue;
                    }
                    Enemy target = maybeTarget.get();
                    int fullDamage = player.calculateDamageDealtWithSkill(spell);
                    int dmg = availableSpell.scaleDamage(fullDamage);
                    int hpBefore = target.getCurrentHp();
                    target.recieveDamage(dmg);
                    bus.publish(new DamageDealt(player, target, dmg, spell, hpBefore, target.getCurrentHp()));
                    if (availableSpell.crystalBreaks()) {
                        bus.publish(new CrystalShattered(
                                player.getName(),
                                availableSpell.crystal().getName()
                        ));
                    }
                    skillProgressionHandler.recordSuccessfulCast(availableSpell);
                    if(target.isDead()){
                        rewardHandler.grantRewards(rewardHandler.getRewardsFromEnemy(target), player);
                    }
                    publishCombatState(player, enemies);
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


            for (Enemy enemy : enemies) {
                if(!enemy.isDead()) {
                    int dmg = enemy.basicAttack();
                    int hpBefore = player.getCurrentHp();
                    player.recieveDamage(dmg);
                    bus.publish(new DamageDealt(enemy, player, dmg, null, hpBefore, player.getCurrentHp()));
                    publishCombatState(player, enemies);
                    if (player.isDead()) {
                        return CombatResult.LOST;
                    }
                }
            }
        }
    }

    private void publishCombatState(Player player, List<Enemy> enemies) {
        CombatantSnapshot playerSnapshot = snapshot(player);
        List<CombatantSnapshot> enemySnapshots = enemies.stream()
                .map(this::snapshot)
                .toList();
        bus.publish(new CombatStateChanged(
                new CombatSnapshot(playerSnapshot, enemySnapshots)
        ));
    }

    private CombatantSnapshot snapshot(Actor<?> actor) {
        return new CombatantSnapshot(
                actor.getName(),
                actor.getCurrentHp(),
                actor.getMaxHp(),
                actor.getCurrentResource(),
                actor.getMaxResource()
        );
    }
}
