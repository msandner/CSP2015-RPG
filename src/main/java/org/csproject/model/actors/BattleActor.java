package org.csproject.model.actors;

import org.csproject.model.magic.Magic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public abstract class BattleActor extends Actor{
    //not finished, not used till now
    //sets the actor in battle

    protected int level;
    protected int maxHp;
    protected int currentHp = 100;
    protected int attack;


    //a boolean to check if the character already used one attack and is no longer allowed to attack
    private boolean hasAttacked;

    // todo more stats

    public BattleActor(String name, String type, int level, int attack) {
        super(name, type);

        this.level = level;

        int hp = calcHp(level);
        this.maxHp = hp;
        this.currentHp = hp;
        this.hasAttacked = false;
        this.attack = attack;
    }


    public int getAttack() {
        return this.attack;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setCurrentHp(int currentHp) {
        if(currentHp > this.maxHp) {
            this.currentHp = this.maxHp;
        } else {
            this.currentHp = currentHp;
        }
    }

    public void calcCurrentHp(int currentHp) {
        if((this.currentHp + currentHp) > this.maxHp) {
            this.currentHp = this.maxHp;
        } else {
            this.currentHp += currentHp;
        }
    }

    /**calculates maxHp of battleActor with given level*/
    public abstract int calcHp(int level);

    public boolean is_dead() {
        if(getCurrentHp() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean playerHasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean attacked) {
        this.hasAttacked = attacked;
    }



}
