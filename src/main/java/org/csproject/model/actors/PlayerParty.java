package org.csproject.model.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 10/29/2015.
 */
public class PlayerParty {
    protected PlayerActor char1, char2, char3;
    protected int currency;

    public PlayerParty(PlayerActor char1, PlayerActor char2, PlayerActor char3, int currency){
        this.char1 = char1;
        this.char2 = char2;
        this.char3 = char3;
        this.currency = currency;
    }

    public List<PlayerActor> getParty() {
        List<PlayerActor> party = new ArrayList<>();
        party.add(this.char1);
        party.add(this.char2);
        party.add(this.char3);

        return party;
    }

    public int highestLevel() {
        int max = 0;
        for(PlayerActor p : getParty()) {
            if(p.getLevel() > max) {
                max = p.getLevel();
            }
        }
        return max;
    }
}
