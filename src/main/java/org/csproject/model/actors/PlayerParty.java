package org.csproject.model.actors;

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
}
