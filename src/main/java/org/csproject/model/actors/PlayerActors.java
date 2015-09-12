package org.csproject.model.actors;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public abstract class PlayerActors extends BattleActors {
    protected int currentMp;
    protected int maxMp;
    //todo more stats

    public PlayerActors(String name, String type, int level) {
        super(name, type, level);

        int mp = calcMp(level);
        this.currentMp = mp;
        this.maxMp= mp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public abstract int calcMp(int level);
}
