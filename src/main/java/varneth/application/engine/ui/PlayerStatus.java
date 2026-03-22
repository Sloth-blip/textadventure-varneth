package varneth.application.engine.ui;

import java.util.List;

public class PlayerStatus {

    private final int level;
    private final int currentHp;
    private final int maxHp;
    private final int currentRessource;
    private final int maxRessource;
    private final int xp;
    private final int nextXpThreshold;
    private final List<String> skills;

    public PlayerStatus(
        int level,
        int currentHp,
        int maxHp, 
        int currentRessource, 
        int maxRessource, 
        int xp, 
        int nextXpThreshold,
        List<String> skills
    ) {
        this.level = level;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.currentRessource = currentRessource;
        this.maxRessource = maxRessource;
        this.xp = xp;
        this.nextXpThreshold = nextXpThreshold;
        this.skills = skills;
    }

    public int getlevel() {return level;}
    public int getCurrentHp() {return currentHp;}
    public int getMaxHp() {return maxHp;}
    public int getCurrentRessource() {return currentRessource;}
    public int getMaxRessource() {return maxRessource;}
    public int getXp() {return xp;}
    public int getNextXpThreshold() {return nextXpThreshold;}
    public List<String> getSkills() {return skills;}

}
