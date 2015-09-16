package org.csproject.model.actors;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public abstract class BattleActors extends Actor{
    protected int level;
    protected int maxHp;
    protected int currentHp;

    // todo more stats

    public BattleActors(String name, String type, int level) {
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

    public abstract int calcHp(int level);


}
