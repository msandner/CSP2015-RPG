package org.csproject.model.actors;

public class Monster extends BattleActor {
    //creates the general monsters

    int grantingXP;

    public Monster(String name, String type, int level, int xp) {
        super(name, type, level);
        this.grantingXP = xp;
    }

    public int getGrantingXP() {
        return (this.grantingXP * level)/2;
    }

    @Override
    public int calcHp(int level) {
        return (int) (100 + level);
    }


}


