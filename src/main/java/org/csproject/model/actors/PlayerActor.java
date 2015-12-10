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

    protected int currentXp;

    private double mpMultiplier;
    private double hpMultiplier;

    String classtype = null;

    //todo more stats

    public PlayerActor(String name, String type, int level, int attack, double hpMultiplier, double mpMultiplier) {
        super(name, type, level, attack);
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
                allSpells.add(new OffensiveMagic("Shield Bash", "", -15, -10, 2));        //shield attack, %chance to stun the enemy
                allSpells.add(new OffensiveMagic("Whirlwind", "", -20, -15, 4));          //dmg on 2 targets
                allSpells.add(new OffensiveMagic("Berserk", "", -25, -20, 7));            //more dmg when actor under %xx hp
                allSpells.add(new OffensiveMagic("Massive Sword Slash", "", -30, -30, 11));//more mana, therefore more dmg
                break;
            case Constants.CLASS_MAGE:
                allSpells.add(new OffensiveMagic("Fireball", "", -10, -5, 2));            //dmg on 1 target
                allSpells.add(new OffensiveMagic("Chain Lightning", "", -10, -15, 4));    //dmg on multiple targets
                allSpells.add(new RestorativeMagic("Heal", true, false, "", 20, 10, 7));  //healing in the first levels one actor, later multiple
                allSpells.add(new OffensiveMagic("Frostbite", "", 0, -25, 11));            //disable enemy for one round
                break;
            case Constants.CLASS_THIEF:
                allSpells.add(new OffensiveMagic("Ambush", "", -10, -10, 2));             //more %crit chance
                allSpells.add(new OffensiveMagic("Mutilate", "", -10, -20, 4));           //double bladed attack
                allSpells.add(new OffensiveMagic("Execute", "", -25, -30, 7));            //more dmg under targets %xx hp
                allSpells.add(new OffensiveMagic("Frostbite", "", -10, -25, 11));          //low dmg on all targets
        }
        allSpells.add(new OffensiveMagic("Basic", "", -5, 0, 0));
    }

    public List<Magic> getSpells() {
        return allSpells;
    }

    public Magic getSpell(int i) {
        return allSpells.get(i);
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
        return (int) (100 + level * mpMultiplier);
    }

    @Override
    public int calcHp(int level) {
        return (int) (100 + level * hpMultiplier);
    }

    @Override
    public String toString() {
        return name + (type) + "|HP: " + currentHp + "/" + maxHp + "|MP: " + currentMp + "/" + maxMp;
    }

    public void addXP(int value) {
        this.currentXp += value;
        if(this.currentXp > (Constants.LEVEL_POINTS_CALCULATE*(this.level^2))) {
            levelUp();
        }
    }

    public void levelUp() {
    //todo:level up
    }


}
