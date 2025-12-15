package systems.actors;

import systems.spells.Skill;

import java.util.List;

public class ActorState {

    private int currentHp;
    private int currentResource;
    private int level;
    private int currentXp;
    private List<Skill> learnedSkills;


    public ActorState(
            int currentHp,
            int currentResource,
            int level,
            int currentXp,
            List<Skill> learnedSkills
    )
    {
        this.currentHp = currentHp;
        this.currentResource = currentResource;
        this.level = level;
        this.currentXp = currentXp;
        this.learnedSkills = learnedSkills;
    }

    /** Getter **/

    protected int getCurrentHp() {return currentHp;}
    protected int getCurrentResource() {return currentResource;}
    protected int getLevel() {return level;}
    protected int getCurrentXp() {return currentXp;}
    protected List<Skill> getLearnedSkills() {return learnedSkills;}

    /** Setter **/

    protected void setCurrentHp(int currentHp) {this.currentHp = currentHp;}
    protected void setCurrentResource(int currentResource) {this.currentResource = currentResource;}
    protected void setLevel(int level) {this.level = level;}
    protected void levelUp() {this.level++;}
    protected void setCurrentXp(int currentXp) {this.currentXp = currentXp;}
    protected void setLearnedSkills(List<Skill> learnedSkills) {this.learnedSkills = learnedSkills;}
    protected void addLearnedSkill(Skill skill){this.learnedSkills.add(skill);}
}
