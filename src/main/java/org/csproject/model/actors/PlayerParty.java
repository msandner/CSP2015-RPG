package org.csproject.model.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 10/29/2015.
 */
public class PlayerParty {
    protected PlayerActor char1, char2, char3;
    protected int currency;
    List<PlayerActor> party = new ArrayList<>();

    public PlayerParty(PlayerActor char1, PlayerActor char2, PlayerActor char3, int currency){
        this.char1 = char1;
        this.char2 = char2;
        this.char3 = char3;
        this.currency = currency;
        setParty();
    }

    public void setParty() {
        party.add(this.char1);
        party.add(this.char2);
        party.add(this.char3);
    }

    public int highestLevel() {
        int max = 0;
        for(BattleActor p : getParty()) {
            if(p.getLevel() > max) {
                max = p.getLevel();
            }
        }
        return max;
    }

    public PlayerActor getPlayer(int index) {
        return party.get(index);
    }

    public List<PlayerActor> getParty() {
        return party;
    }

    public int getPartySize() {
        return party.size();
    }

    public boolean isEveryPlayerDead() {
        for (PlayerActor p : party) {
            if (!p.is_dead()) {
                return false;
            }
        }
        return true;
    }



}
