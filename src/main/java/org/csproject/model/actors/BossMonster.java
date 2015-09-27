package org.csproject.model.actors;

/**
 * Created by Twigglon on 16.09.2015.
 */
public class BossMonster extends Monster {

    //temporary, for compiling - Brett
    public BossMonster() {
        super("name", "name2", 12);
    }
    //End changes - Brett

    @Override
    public int calcHp(int level) {
       return level * 1000;

    }
}
