package org.csproject.model.actors;

public class Monster extends BattleActor {
    //creates the general monsters

    int grantingXP;
    protected int drops;

    public Monster(String name, String type, int level, int xp, int attack, int drops) {
        super(name, type, level, attack);
        this.grantingXP = xp;
        this.drops = drops;
    }


    public int getDrops() {
        return drops;
    }

    public int getGrantingXP() {
        return (this.grantingXP * level)/2;
    }

    @Override
    public int calcHp(int level) {
        return (int) (100 + level);
    }


}


