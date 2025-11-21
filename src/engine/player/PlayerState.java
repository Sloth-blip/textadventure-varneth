package engine.player;

import systems.spells.KnownSpell;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {

    private final String name = "Arenn";
    private int level;
    private int experiencePoints;
    private int healthPointsMax;
    private int healthPointsCurrent;
    private int mana;
    private int manaCurrent;
    private int wisdom;
    private int intelligence;
    private int strength;
    private List<KnownSpell> knownSpells;
    private List<OwnedItem> inventory;

    @Override
    public String toString() {
        return name;
    }

    public PlayerState(int level) {
        this.level = level;
        this.experiencePoints = 0;
        this.healthPointsMax = 20;
        this.healthPointsCurrent = 20;
        this.mana = 10;
        this.manaCurrent = 10;
        this.intelligence = 10;
        this.strength = 5;
        this.knownSpells = new ArrayList<>(List.of());
    }

    /** Clamper **/

    private void clampHealthPoints(){
        if (this.healthPointsCurrent < 0) this.healthPointsCurrent = 0;
        if (this.healthPointsCurrent > this.healthPointsMax) this.healthPointsCurrent = this.healthPointsMax;
    }

    private void clampMana(){
        if (this.manaCurrent < 0) this.manaCurrent = 0;
        if (this.manaCurrent > this.mana) this.manaCurrent = this.mana;
    }

    /** Level-up logic **/

    public void gainExperiencePoints(int amountGained){
        this.experiencePoints += amountGained;
        while (this.experiencePoints >= experiencePointsNeededForNextLevel()) {
            levelUp();
        }
    }

    private void levelUp() {
        this.experiencePoints -= experiencePointsNeededForNextLevel();
        this.level++;
        this.healthPointsMax += 10;
        this.mana += 5;
        this.intelligence += 1;
        this.wisdom += 1;
        this.strength += 1;

        System.out.println(this.name + " ist im Level aufgestiegen!");
    }

    public int experiencePointsNeededForNextLevel() {
        return (int) Math.round(40 * Math.pow(this.level, 1.5));
    }

    /** Getter **/

    public int getLevel() {
        return this.level;
    }
    public String getName() {
        return this.name;
    }
    public int getExperiencePoints() {
        return this.experiencePoints;
    }
    public int getMana() {
        return this.mana;
    }
    public int getManaCurrent(){
        return this.manaCurrent;
    }
    public int getHealthPointsMax() {
        return this.healthPointsMax;
    }
    public int getHealthPointsCurrent() {
        return this.healthPointsCurrent;
    }
    public int getIntelligence() {return this.intelligence;}
    public int getWisdom() {return this.wisdom;}
    public int getStrength() {return strength;}
    public List<KnownSpell> getKnownSpells() {return knownSpells;}

    /** Setter / Adder **/

    public void addSpell(KnownSpell spell){this.knownSpells.add(spell);}

    /** Combat **/

    public int defaultAttack(){
        return this.strength + 1;
    }

    public void recieveDamage(int damage){
        this.healthPointsCurrent -= damage;
        clampHealthPoints();
    }

    public void heal(int heal) {
        this.healthPointsCurrent += heal;
        clampHealthPoints();
    }

    public boolean isDead(){
        return this.healthPointsCurrent == 0;
    }
}

