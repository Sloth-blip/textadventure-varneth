package systems.spells;

public class SkillState {

    private int level;
    private int currentXp;

    public SkillState(
            int level,
            int currentXp
    )
    {
        this.level = level;
        this.currentXp = currentXp;
    }

    public int getLevel() {return level;}
    public int getCurrentXp() {return currentXp;}

    public void setCurrentXp(int currentXp) {this.currentXp = currentXp;}
    public void addCurrentXp(int amount) {this.currentXp += amount;}

    public void setLevel(int level){this.level = level;}
    public void levelUp() {this.level++;}

}
