package org.csproject.model.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maren on 29.11.2015.
 */
public class MonsterParty{

    private List<Monster> party = new ArrayList<>();

    /**
     * Maren Sandner
     * @param monsters: list of up to 6 monsters
     */
    public MonsterParty(List<Monster> monsters) {
        this.party = monsters;
    }

    /**
     * Maren Sandner
     * @param monster: the monster you want to get the position
     * @return the position of the monster in the list, returns a unusable number if the monster is not in the list
     */
    public int getMonsterPosition(Monster monster) {
        for(int i = 0; i < party.size(); i++) {
            if(party.get(i).equals(monster)) {
                return i;
            }
        }
        System.out.println("Monster is not in list");
        return 7;
    }

    /**
     * Maren Sandner
     * get a monster ata specific index in the monsterlist
     * @param index: the index of the monster in the list
     * @return monster in the list
     */
    public Monster getMonster(int index) {
        return party.get(index);
    }

    /**
     * Maren Sandner
     * gives the size of the monster party (maximum is 6)
     * @return size
     */
    public int getPartySize() {
        return party.size();
    }

    /**
     * Maren Sandner
     * checks if the whole MonsterParty is dead
     * @return false if the party is not dead and true if it is
     */
    public boolean isEveryEnemyDead() {
        for (Monster p : party) {
            if (!p.is_dead()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Nicholas Paquette
     * calculates the loot
     * @return the added drop values of every monster in the party
     */
    public int getLoot(){
        int loot = 0;
        for(Monster m : party){
            loot += m.getDrops();
        }
        return loot;
    }

    /**
     * Maren Sandner
     * adds all the granting experience points of the monsters in the party
     * @return the added value for the XP a whole monster party grants
     */
    public int getXP(){
        int xp = 0;
        for(Monster m : party){
            xp += m.getGrantingXP();
        }
        return xp;
    }


    public List<Monster> getParty() {
        return party;
    }


}
