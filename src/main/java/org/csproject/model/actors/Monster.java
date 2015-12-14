package org.csproject.model.actors;

public class Monster extends BattleActor {

    protected int grantingXP;
    protected int drops;

    /**
     * creates the monsters for the battle
     * @param name: name of the monster
     * @param type: type of the monster (e.g. "Bat", "Gayzer", "Assassin")
     * @param level: level of the monster
     * @param xp: the xp value the monster grants to the PlayerActor when deafeated
     * @param attack: initial attack value
     * @param drops:
     */
    public Monster(String name, String type, int level, int xp, int attack, int drops) {
        super(name, type, level, attack);
        this.grantingXP = xp;
        this.drops = drops;
    }

    public int getGrantingXP() {
        return (this.grantingXP * level);
    }

    public int getDrops() { return drops; }

    @Override
    public int calcHp(int level) {
        currentHp = currentHp + (int) (11*Math.sqrt(level));
        return currentHp;
    }

    public void calcAttack() {
        attack = attack + (int)(3*Math.sqrt(level));
    }

    public void levelUpMonster() {
        level += 1;
        calcHp(level);
        calcAttack();
    }

}


