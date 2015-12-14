package org.csproject.model.magic;

/**
 * Created by Nick on 10/31/2015.
 * Restorative magic class.
 * Restorative magic are skills used by characters to support the party in combat.
 * Restorative magic can restore health or mana, and can target a single character or the entire team.
 */
public class RestorativeMagic extends Magic{
    protected boolean hasTargetPlayer;
    protected boolean hasTargetTeam;

    protected String attributeEffected;

    public RestorativeMagic(String name, boolean targetPlayer, boolean targetTeam, String attribute, int v, int mp, int levelres){
        super(name, "Restorative", v, mp, levelres);
        this.hasTargetPlayer = targetPlayer;
        this.hasTargetTeam = targetTeam;
        this.attributeEffected = attribute;
    }

    public String getTarget(){
        if(hasTargetPlayer){ return "Player"; }
        else if(hasTargetTeam){ return "Team"; }
        else { return "No Target"; }
    }

    public void setTarget(boolean player, boolean party) {
        hasTargetPlayer =  player;
        hasTargetTeam = party;
    }

    public String getAttribute(){
        return attributeEffected;
    }

}
