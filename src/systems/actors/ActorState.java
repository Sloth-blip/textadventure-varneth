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

    public int getCurrentHp() {return currentHp;}
    public int getCurrentResource() {return currentResource;}
    public int getLevel() {return level;}
    public int getCurrentXp() {return currentXp;}
    public List<Skill> getLearnedSkills() {return learnedSkills;}
    //For Testing
    public Skill getLearnedSkill() {return this.learnedSkills.getFirst();}

    /** Setter **/

    public void setCurrentHp(int currentHp) {this.currentHp = currentHp;}
    public void setCurrentResource(int currentResource) {this.currentResource = currentResource;}
    public void setLevel(int level) {this.level = level;}
    public void levelUp() {this.level++;}
    public void setCurrentXp(int currentXp) {this.currentXp = currentXp;}
    public void setLearnedSkills(List<Skill> learnedSkills) {this.learnedSkills = learnedSkills;}
    public void addLearnedSkill(Skill skill){this.learnedSkills.add(skill);}
}
