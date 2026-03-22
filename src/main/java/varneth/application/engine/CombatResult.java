package varneth.application.engine;

public class CombatResult {

    private final boolean combatFinished;
    private final boolean playerDefeated;
    private final CombatRewardBundle rewardBundle;

    public CombatResult(
        boolean combatFinished,
        boolean playerDefeated,
        CombatRewardBundle rewardBundle
    ) {
        this.combatFinished = combatFinished;
        this.playerDefeated = playerDefeated;
        this.rewardBundle = rewardBundle;
    }

    public boolean isCombatFinished() {return combatFinished;}
    public boolean isPlayerDefeated() {return playerDefeated;}
    public CombatRewardBundle getRewardBundle() {return rewardBundle;}

    public static CombatResult ongoing() {return new CombatResult(false, false, CombatRewardBundle.empty());}
    public static CombatResult finished(CombatRewardBundle rewardBundle) {return new CombatResult(true, false, rewardBundle);}
    public static CombatResult playerDefeated() {return new CombatResult(false, true, CombatRewardBundle.empty());}
}