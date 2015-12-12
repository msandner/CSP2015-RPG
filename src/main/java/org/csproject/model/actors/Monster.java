package org.csproject.model.actors;

public class Monster extends BattleActor {
    //creates the general monsters

    protected int grantingXP;
    protected int drops;

    public Monster(String name, String type, int level, int xp, int attack, int drops) {
        super(name, type, level, attack);
        this.grantingXP = xp;
        this.drops = drops;
    }

    public int getGrantingXP() {
        return (this.grantingXP * level)/2;
    }

    public int getDrops() { return drops; }

    @Override
    public int calcHp(int level) {
        return 100 + level;
    }

    public void attack(PlayerActor p, int factor) {
        p.setCurrentHp((int) (getCurrentHp()-(getLevel() + 50 * factor)));
    }

}


