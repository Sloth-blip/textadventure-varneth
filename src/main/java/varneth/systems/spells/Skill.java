package varneth.systems.spells;

import varneth.systems.actors.MainAttribute;
import varneth.systems.magic.MagicType;

public class Skill {

    private final SkillDefinition def;
    private final SkillState state;

    public Skill(
            SkillDefinition def,
            SkillState state
    ){
        this.def = def;
        this.state = state;

    }

    @Override
    public String toString(){return def.toString();}

    public String getId() {return def.getId();}
    public String getName() {return def.getName();}
    public String getDescription() {return def.getDescription();}
    public MagicType getMagicType() {return def.getMagicType();}
    public int getBaseManaCost() {return def.getBaseManaCost();}
    public int getModifier() {return def.getBaseModifier() + def.getModifierPerLevel() * state.getLevel();}
    public int getCurrentXp() {return state.getCurrentXp();}
    public int getLevel() {return state.getLevel();}

    public int getCurrentXpThreshold(){
        return (int) (def.getBaseXpThreshold() * Math.pow(state.getLevel(), def.getXpThresholdExponent()));
    }

    /** Combat **/

    public MainAttribute getModifyingAttribute() {return def.getModifyingAttribute();}

    /** Level-Up Logic **/

    public void addCurrentXp(int amount) {
        int newXp = getCurrentXp() + amount;
        state.setCurrentXp(newXp);
        while (getCurrentXp() >= getCurrentXpThreshold()) {
            state.setCurrentXp(getCurrentXp() - getCurrentXpThreshold());
            levelUp();
            }
        }

    public void levelUp() {state.levelUp();}



}
