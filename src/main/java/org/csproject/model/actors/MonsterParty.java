package org.csproject.model.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maren on 29.11.2015.
 */
public class MonsterParty{

    protected Monster monster1, monster2, monster3, monster4, monster5, monster6;
    List<Monster> party = new ArrayList<>();

    public MonsterParty(Monster m1, Monster m2, Monster m3, Monster m4, Monster m5, Monster m6) {
        this.monster1 = m1;
        this.monster2 = m2;
        this.monster3 = m3;
        this.monster4 = m4;
        this.monster5 = m5;
        this.monster6 = m6;
        setMonsterParty();
    }

    public void setMonsterParty(){
        party.add(monster1);
        party.add(monster2);
        party.add(monster3);
        party.add(monster4);
        party.add(monster5);
        party.add(monster6);
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


}
