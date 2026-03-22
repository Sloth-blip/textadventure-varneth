package varneth.application.engine;

import java.util.ArrayList;
import java.util.List;

import varneth.application.intent.PlayerIntent;
import varneth.application.state.ActionOption;
import varneth.systems.actors.player.Player;
import varneth.systems.reward.Reward;
import varneth.systems.rooms.Room;
import varneth.systems.spells.Skill;

public class CombatEngine {

    public void initialize(
        List<ActionOption> actions,
        List<String> devLog,
        CombatSessionData data,
        Room room,
        Player player
    ) {
        data.setSelectedEnemyIndex(0);
        data.setContextText(buildCombatContext(room, data));
        rebuildActions(actions, room);
        devLog.add("Combat initialisiert");
    }

    public CombatResult handleIntent(
        PlayerIntent intent,
        List<ActionOption> actions,
        List<String> devLog,
        CombatSessionData data,
        Room room,
        Player player
    ) {

        String actionId = intent.getValue();

        switch (actionId) {
            case "ATTACK" -> {
            /*     data.setContextText("Du greifst an.");
                devLog.add("ATTACK ausgeführt");
                return false; */
                buildCombatActions(actions, player);
                return CombatResult.ongoing();
            }

            case "BASIC_ATTACK" -> {
                return attackEnemy(intent, actions, devLog, data, room, player);
            }

            case "TARGET_NEXT" -> {
                int size = room.getEnemies().size();
                int index = data.getSelectedEnemyIndex();
                data.setSelectedEnemyIndex((index + 1) % size);

                data.setContextText(buildCombatContext(room, data));
                rebuildActions(actions, room);
                return CombatResult.ongoing();
            }

            case "TARGET_PREV" -> {
                int size = room.getEnemies().size();
                int index = data.getSelectedEnemyIndex();
                data.setSelectedEnemyIndex((index - 1 + size) % size);

                data.setContextText(buildCombatContext(room, data));
                rebuildActions(actions, room);
                return CombatResult.ongoing();
            }

            case "END_COMBAT" -> {
                room.getEnemies().clear();
                devLog.add("Devtool: Combat beendet");
                return CombatResult.finished(new CombatRewardBundle(List.of(), List.of()));
            }

        }

         if (actionId.startsWith("SPELL_")) {
                devLog.add(actionId + " ausgewählt");
                return attackEnemy(intent, actions, devLog, data, room, player);
            }
        
        devLog.add("Ungültige Aktion: " + actionId);
        return CombatResult.ongoing();
    }

    private void rebuildActions(List<ActionOption> actions, Room room) {
        actions.clear();

        actions.add(new ActionOption("ATTACK", "Angreifen"));

        if (room.getEnemies().size() > 1) { 
            actions.add(new ActionOption("TARGET_PREV", "Ziel ↑"));
            actions.add(new ActionOption("TARGET_NEXT", "Ziel ↓"));
        }
        actions.add(new ActionOption("END_COMBAT", "Devtool: Kampf beenden"));
    }

    private void buildCombatActions(List<ActionOption> actions, Player player) {
        actions.clear();

        actions.add(new ActionOption("BASIC_ATTACK", "Normaler Angriff"));

        for (Skill skill : player.getLearnedSkills()) {
            actions.add(new ActionOption("SPELL_" + skill.getName(), skill.getName() + "\n" + skill.getDescription()));    
        }
    }

    private String buildCombatContext(Room room, CombatSessionData data) {
        
        StringBuilder sb = new StringBuilder();

        sb.append("KAMPF!\n\n");

        int selected = data.getSelectedEnemyIndex();

        for (int i = 0; i < room.getEnemies().size(); i++){

            var enemy = room.getEnemies().get(i);

            if (i == selected){
                sb.append(" > ");
            } else {
                sb.append("   ");
            }
        
            sb.append(enemy.getName())
            .append(" HP ")
            .append(enemy.getCurrentHp())
            .append("/")
            .append(enemy.getMaxHp())
            .append("\n");
        

        }

        return sb.toString();
    }

    private int calculateDamage(PlayerIntent intent, Player player) {

        if (intent.getValue().equals("BASIC_ATTACK")) {
            return player.basicAttack();
        }

        String skillId = intent.getValue().substring("SPELL_".length());

        for (Skill skill : player.getLearnedSkills()) {
            if (skillId.equals(skill.getName())){
                return player.calculateDamageDealtWithSkill(skill);
            }
        }
        return 0;
    }


    private CombatResult attackEnemy(PlayerIntent intent, List<ActionOption> actions, List<String> devLog, CombatSessionData data, Room room, Player player) {

        if (room.getEnemies().isEmpty()) {
            data.setContextText("Keine Gegner mehr.");
            return CombatResult.ongoing();
        }

        int targetIndex = data.getSelectedEnemyIndex();

        if (targetIndex >= room.getEnemies().size()) {
            targetIndex = 0;
            data.setSelectedEnemyIndex(0);
        }

        var enemy = room.getEnemies().get(targetIndex);

        int damage = calculateDamage(intent, player);

        enemy.recieveDamage(damage);

        devLog.add(enemy.getName() + " erhält " + damage + " Schaden.");

        List<Reward> rewards = new ArrayList<>();
        List<String> rewardMessages = new ArrayList<>();

        if (enemy.isDead()) {
            devLog.add(enemy.getName() + " wurde besiegt!");

            var reward = enemy.getReward();
            rewardMessages.add(enemy.getName() + " besiegt");
            rewards.add(reward);
            if (reward.getGold() > 0){
                devLog.add("Reward vorbereitet: Gold " + reward.getGold());
                rewardMessages.add(reward.getGold() + " Gold erhalten.");
            }

            if(reward.getXp() > 0) {
                devLog.add("Reward vorbereitet: XP " + reward.getXp());
                rewardMessages.add(reward.getXp() + " Erfahrung erhalten.");
            }

            if(reward.getSkill() != null) {
                devLog.add("Reward vorbereitet: Skill " + reward.getSkill().getName());
                rewardMessages.add(reward.getSkill().getName() + " erlernt!");
            }
            room.getEnemies().remove(enemy);
        }

        if (room.getEnemies().isEmpty()) {
            CombatRewardBundle rewardBundle = new CombatRewardBundle(rewards, rewardMessages);
            data.setContextText("Alle Gegner besiegt!");
            return CombatResult.finished(rewardBundle);
        }

        for (var currentEnemy : room.getEnemies()) {
            int enemyDamage =  1;// currentEnemy.basicAttack();
            player.recieveDamage(enemyDamage);

            devLog.add(currentEnemy.getName() + " hat dir " + enemyDamage + " Schaden zugefügt.");

            if (player.isDead()) {
                data.setContextText("Du wurdest besiegt...");
                devLog.add("Spieler wurder besiegt");
                rebuildActions(actions, room);
                return CombatResult.playerDefeated();
            }
        }

        data.setContextText(buildCombatContext(room, data));
        rebuildActions(actions, room);

        return CombatResult.ongoing();
    }


}
