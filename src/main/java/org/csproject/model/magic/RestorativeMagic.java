package org.csproject.model.magic;

/**
 * Created by Nick on 10/31/2015.
 */
public class RestorativeMagic extends Magic{
    protected boolean hasTargetPlayer;
    protected boolean hasTargetTeam;

    protected String attributeEffected;

    public RestorativeMagic(String name, boolean targetPlayer, boolean targetTeam, String attribute, int v, int mp){
        super(name, "Restorative", v, mp);
        this.hasTargetPlayer = targetPlayer;
        this.hasTargetTeam = targetTeam;
        this.attributeEffected = attribute;
    }

    public String getTarget(){
        if(hasTargetPlayer){ return "Player"; }
        else if(hasTargetTeam){ return "Team"; }
        else { return "No Target"; }
    }

    public String getAttribute(){
        return attributeEffected;
    }

}
