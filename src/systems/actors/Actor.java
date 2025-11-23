package systems.actors;

import systems.spells.ModifyingAttribute;
import systems.spells.Skill;

import java.util.List;
import java.util.Objects;

public abstract class Actor <D extends ActorDefinition> {

    protected final D def;
    protected final ActorState state;


    protected Actor(
            D def,
            ActorState state
    )
    {
        this.def = def;
        this.state = state;
    }

    @Override
    public String toString() {return def.toString() + state.toString();}

    /** Stats Getter (@Property) **/

    public String getName(){return def.getName();}

    public int getMaxHp(){
        int fromLevel = def.getHpPerLevel() * state.getLevel();
        return def.getBaseHp() + fromLevel;
    }

    public int getCurrentHp(){return state.getCurrentHp();}

    public int getMaxResource(){
        int fromLevel = def.getResourcePerLevel() * state.getLevel();
        return def.getBaseResource() + fromLevel;
    }

    public int getCurrentResource(){return state.getCurrentResource();}

    public int getStrength(){
        int fromLevel = def.getStrengthPerLevel() * state.getLevel();
        return def.getBaseStrength() + fromLevel;
    }

    public int getIntelligence(){
        int fromLevel = def.getIntelligencePerLevel() * state.getLevel();
        return def.getBaseIntelligence() + fromLevel;
    }

    public int getWisdom(){
        int fromLevel = def.getWisdomPerLevel() * state.getLevel();
        return def.getBaseWisdom() + fromLevel;

    }
    public int getCurrentXp(){return state.getCurrentXp();}

    public int getCurrentXpThreshold(){
        return (int) (def.getBaseXpThreshold() * Math.pow(state.getLevel(), def.getXpThresholdExponent()));
    }

    public List<Skill> getLearnedSkills(){return state.getLearnedSkills();}

    public MainAttribute getMainAttribute() {return def.getMainAttribute();}

    /** Combat **/

    public int getMainAttributeValue(){
        MainAttribute mainAttribute = getMainAttribute();
        switch (mainAttribute){
            case STRENGTH -> {return getStrength();}
            case INTELLIGENCE -> {return getIntelligence();}
        }
        return 0;
    }

    public int basicAttack() {
        return getStrength();
    }

    public int calculateDamageDealtWithSkill(Skill skill) {
        MainAttribute mainAttribute = def.getMainAttribute();
        ModifyingAttribute modifyingAttribute = skill.getModifyingAttribute();
        if (Objects.equals(mainAttribute.toString(), modifyingAttribute.toString())){
            return skill.getModifier() + getMainAttributeValue();
        }
        else {
            return skill.getModifier() + getMainAttributeValue()/2;
        }

    }

    public void recieveDamage (int amount) {
        int newHp = Math.max(0, state.getCurrentHp() - amount);
        state.setCurrentHp(newHp);
    }

    /** Misc **/

    public void gainXp(int amount) {
        int newXp = getCurrentXp() + amount;
        state.setCurrentXp(newXp);
        while (getCurrentXp() >= getCurrentXpThreshold()) {
            state.setCurrentXp(getCurrentXp() - getCurrentXpThreshold());
            levelUp();
        }
    }

    public void levelUp() {
        state.levelUp();
        System.out.println(this.getName() + " ist ein Level aufgestiegen und ist nun Level " + this.state.getLevel());
    }

    public void takeRest() {
        state.setCurrentHp(getMaxHp());
        state.setCurrentResource(getMaxResource());
    }

    public void addLearnedSkill(Skill skill){
        state.addLearnedSkill(skill);
    }



}
