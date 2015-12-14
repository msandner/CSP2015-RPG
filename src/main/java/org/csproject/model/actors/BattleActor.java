package org.csproject.model.actors;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public abstract class BattleActor extends Actor {
    protected int level;
    protected int maxHp;
    /**
     * currentHP is set to 100 because that is the start health point value
     */
    protected int currentHp = 100;
    protected int attack;
    /**
     * hasAttacked: a boolean to check if the character already used one attack and is no longer allowed to attack
     */
    private boolean hasAttacked;

    /**
     *
     * @param name: name of the battle actor
     * @param type: type of the battle actor (Constants.CLASS_KNGHT, Constants.CLASS_MAGE, Constants.CLASS_THIEF
     *              for PlayerActors, e.g. "Bat", "Puppet", "Gayzer" for Monsters)
     * @param level: level value of the Character
     * @param attack: initial attack value of the character
     */
    public BattleActor(String name, String type, int level, int attack) {
        super(name, type);

        this.level = level;
        int hp = calcHp(level);
        this.maxHp = hp;
        this.currentHp = hp;
        this.hasAttacked = false;
        this.attack = attack;
    }

    /**
     * Maren Sandner
     * @param currentHp: the new currentHP
     * also checks if the new value is higher than the maximum health points
     */
    public void setCurrentHp(int currentHp) {
        if (currentHp > this.maxHp) {
            this.currentHp = this.maxHp;
        } else {
            this.currentHp = currentHp;
        }
    }

    /**
     * Maren Sandner
     * @param currentHp: the value that should be added to the current health points
     * also checks if the new value is higher than the maximum health points
     */
    public void addToCurrentHp(int currentHp) {
        if ((this.currentHp + currentHp) > this.maxHp) {
            this.currentHp = this.maxHp;
        } else {
            this.currentHp += currentHp;
        }
    }

    /**
     * calculates maxHp of battleActor with given level
     */
    public abstract int calcHp(int level);

    /**
     * Maren Sandner
     * @return if the playeractor is dead (his health is 0 or smaller)
     */
    public boolean is_dead() {
        if (getCurrentHp() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Maren Sandner
     * @return if the player has already attacked in a round
     */
    public boolean playerHasAttacked() {
        return hasAttacked;
    }

    /**
     * Maren Sandner
     * @param attacked: sets if the player has already attacked or not
     */
    public void setHasAttacked(boolean attacked) {
        this.hasAttacked = attacked;
    }

    public int getAttack() { return attack; }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

}
