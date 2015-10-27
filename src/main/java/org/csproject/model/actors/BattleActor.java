package org.csproject.model.actors;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public abstract class BattleActor extends Actor{
    //not finished, not used till now
    //sets the actor in battle

    protected int level;
    protected int maxHp;
    protected int currentHp;

    // todo more stats

    public BattleActor(String name, String type, int level) {
        super(name, type);

        this.level = level;

        int hp = calcHp(level);
        this.maxHp = hp;
        this.currentHp = hp;
    }

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

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    /**calculates maxHp of battleActor with given level*/
    public abstract int calcHp(int level);


}
