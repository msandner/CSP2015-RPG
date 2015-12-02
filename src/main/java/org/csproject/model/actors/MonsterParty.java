package org.csproject.model.actors;

import java.util.List;

/**
 * Created by Maren on 29.11.2015.
 */
public class MonsterParty {

    protected List<Monster> monsters;

    public MonsterParty(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public List<Monster> getMonsters(){
        return monsters;
    }

    public int getXP(){
        int xp = 0;
        for(Monster m : monsters){
            xp += m.getGrantingXP();
        }
        return xp;
    }

    public int getLoot(){
        int loot = 0;
        for(Monster m : monsters){
            loot += m.getDrops();
        }
        return loot;
    }
}
