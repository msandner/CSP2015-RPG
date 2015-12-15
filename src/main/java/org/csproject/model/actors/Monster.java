package org.csproject.model.actors;

public class Monster extends BattleActor {
    protected int grantingXP;
    protected int drops;

    /**
     * Maren Sandner & Nicholas Paquette
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

    /**
     * Nicholas Paquette & Maren Sandner
     * calculates the experience points a monster gives the actor that kills it
     */
    public int calcXp() {
        return (this.grantingXP += (int) (3 * Math.sqrt(level)));
    }

    /**
     * Maren Sandner
     * calculates the health points of the monster
     * @param level: level of the monster
     * @return calculated health points
     */
    @Override
    public int calcHp(int level) {
        currentHp += (int) (11*Math.sqrt(level));
        return currentHp;
    }

    /**
     * Maren Sandner
     * calaculated the attack value of the monster depending on the level
     */
    public void calcAttack() {
        attack += (int)(3*Math.sqrt(level));
    }

    /**
     * Maren Sandner
     * levels up the monster -> calculates health points and attack value new
     */
    public void levelUpMonster() {
        level += 1;
        calcHp(level);
        calcAttack();
    }

    public int getDrops() { return drops; }


}


