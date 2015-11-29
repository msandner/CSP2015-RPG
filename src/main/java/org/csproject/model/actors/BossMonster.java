package org.csproject.model.actors;

/**
 * Created by Twigglon on 16.09.2015.
 */
public class BossMonster extends Monster {
    //creates the enemy in an battle

    public BossMonster(int level, int xp) {
        super("name", "name2", level, xp);
    }

    @Override
    public int calcHp(int level) {
       return level * 1000;

    }
}
