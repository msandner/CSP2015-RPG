package org.csproject.model.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maren on 29.11.2015.
 */
public class MonsterParty{

    protected Monster monster1, monster2, monster3, monster4, monster5, monster6;
    List<Monster> party = new ArrayList<>();


    public MonsterParty(List<Monster> monsters) {
        this.party = monsters;
    }

    public int getMonsterPosition(Monster monster) {
        for(int i = 0; i < party.size(); i++) {
            if(party.get(i).equals(monster)) {
                return i;
            }
        }
        return 7; //if the monster isn't inside the party, it will return a unusable number
    }

    public Monster getMonster(int index) {
        return party.get(index);
    }

    public List<Monster> getParty() {
        return party;
    }

    public int getPartySize() {
        return party.size();
    }

    public boolean isEveryEnemyDead() {
        for (Monster p : party) {
            if (!p.is_dead()) {
                return false;
            }
        }
        return true;
    }

    public int getLoot(){
        int loot = 0;
        for(Monster m : party){
            loot += m.getDrops();
        }
        return loot;
    }

    public int getXP(){
        int xp = 0;
        for(Monster m : party){
            xp += m.getGrantingXP();
        }
        return xp;
    }


}
