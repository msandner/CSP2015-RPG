package org.csproject.model.actors;

import org.csproject.model.Constants;
import org.csproject.model.magic.Magic;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;

import java.util.List;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public class PlayerActor extends BattleActor {
    //sets the actor as a general player
    protected int currentMp;
    protected int maxMp;

    private double mpMultiplier;
    private double hpMultiplier;

    //todo more stats

    public PlayerActor(String name, String type, int level, double hpMultiplier, double mpMultiplier) {
        super(name, type, level);
        this.hpMultiplier = hpMultiplier;
        this.mpMultiplier = mpMultiplier;

        int mp = calcMp(level);
        this.currentMp = mp;
        this.maxMp = mp;

        addSpells(type);
    }

    public void addSpells(String type) {
        switch(type) {
            case Constants.CLASS_KNIGHT:
                spells.add(new OffensiveMagic("Shield Bash", "", -15, -10));        //shield attack, %chance to stun the enemy
                spells.add(new OffensiveMagic("Whirlwind", "", -20, -15));          //dmg on 2 targets
                spells.add(new OffensiveMagic("Berserk", "", -25, -20));            //more dmg when actor under %xx hp
                spells.add(new OffensiveMagic("Massive Sword Slash", "", -30, -30));//more mana, therefore more dmg
                break;
            case Constants.CLASS_MAGE:
                spells.add(new OffensiveMagic("Fireball", "", -10, -5));            //dmg on 1 target
                spells.add(new OffensiveMagic("Chain Lightning", "", -10, -15));    //dmg on multiple targets
                spells.add(new RestorativeMagic("Heal", true, false, "", 20, 10));  //healing in the first levels one actor, later multiple
                spells.add(new OffensiveMagic("Frostbite", "", 0, -25));            //disable enemy for one round
                break;
            case Constants.CLASS_THIEF:
                spells.add(new OffensiveMagic("Ambush", "", -10, -10));             //more %crit chance
                spells.add(new OffensiveMagic("Mutilate", "", -10, -20));           //double bladed attack
                spells.add(new OffensiveMagic("Execute", "", -25, -30));            //more dmg under targets %xx hp
                spells.add(new OffensiveMagic("Frostbite", "", -10, -25));          //low dmg on all targets
        }
    }

    public List<Magic> getSpells() {
        return spells;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(int mp) {
        this.currentMp = mp;
    }

    public void calcCurrentMp(int mp) {
        if((this.currentMp+mp) > this.maxMp) {
            this.currentMp = this.maxMp;
        } else {
            this.currentMp += mp;
        }
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int calcMp(int level) {
        return (int) (level * mpMultiplier);
    }

    @Override
    public int calcHp(int level) {
        return (int) (100 + level * hpMultiplier);
    }

    @Override
    public String toString() {
        return name + (type) + "|HP: " + currentHp + "/" + maxHp + "|MP: " + currentMp + "/" + maxMp;
    }
}
