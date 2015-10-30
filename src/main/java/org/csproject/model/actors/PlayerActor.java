package org.csproject.model.actors;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public class PlayerActor extends BattleActor {
    protected int currentMp;
    protected int maxMp;

    private double mpMultiplier;
    private double hpMultiplier;
    //todo more stats

    public PlayerActor(String name, String type, int level, double hpMultiplier, double mpMultiplier) {
        super(name, type, level);
        this.hpMultiplier = hpMultiplier;
        this.mpMultiplier = mpMultiplier;

        int mp = calcMp(level);
        this.currentMp = mp;
        this.maxMp = mp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int calcMp(int level) {
        return (int) (level * mpMultiplier);
    }

    @Override
    public int calcHp(int level) {
        return (int) (100 + level * hpMultiplier);
    }

    @Override
    public String toString() {
        return name + (type) + "|HP: " + currentHp + "/" + maxHp + "|MP: " + currentMp + "/" + maxMp;
    }
}
