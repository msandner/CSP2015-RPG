package org.csproject.model.actors;

import org.csproject.model.Constants;
import org.csproject.model.magic.Magic;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public class PlayerActor extends BattleActor {
    //sets the actor as a general player
    protected int currentMp = 75; //value in the first level
    protected int maxMp;

    protected int currentXp;
    protected int maxXp = 50; //value in the first level

    private List<Magic> allSpells = new ArrayList<>();
    private List<Magic> availableSpells = new ArrayList<>();

    public PlayerActor(String name, String type, int attack) {
        super(name, type, 1, attack);

        int mp = calcMp(level);
        this.currentMp = mp;
        this.maxMp = mp;

        currentXp = 0;

        addSpells(type);
        availableSpells.add(allSpells.get(4)); //everyone has basic attack in the first level
     }

    private void addSpells(String type) {
        switch(type) {
            case Constants.CLASS_KNIGHT:
                allSpells.add(new OffensiveMagic("Shield Bash", "", -15, -10, 2));        //shield attack, %chance to stun the enemy
                allSpells.add(new OffensiveMagic("Whirlwind", "", -20, -15, 4));          //dmg on 2 targets
                allSpells.add(new OffensiveMagic("Berserk", "", -25, -20, 7));            //more dmg when actor under %xx hp
                allSpells.add(new OffensiveMagic("Massive Sword Slash", "", -30, -30, 11));//more mana, therefore more dmg
                break;
            case Constants.CLASS_MAGE:
                allSpells.add(new OffensiveMagic("Fireball", "", -15, -5, 2));            //dmg on 1 target
                allSpells.add(new OffensiveMagic("Chain Lightning", "", -15, -15, 4));    //dmg on multiple targets
                allSpells.add(new RestorativeMagic("Heal", true, true, "", 20, 10, 7));  //healing in the first levels one actor, later multiple
                allSpells.add(new OffensiveMagic("Frostbite", "", 0, -25, 11));            //disable enemy for one round
                break;
            case Constants.CLASS_THIEF:
                allSpells.add(new OffensiveMagic("Ambush", "", -10, -10, 2));             //more %crit chance
                allSpells.add(new OffensiveMagic("Mutilate", "", -10, -20, 4));           //double bladed attack
                allSpells.add(new OffensiveMagic("Execute", "", -25, -30, 7));            //more dmg under targets %xx hp
                allSpells.add(new OffensiveMagic("Shuriken Toss", "", -10, -25, 11));          //low dmg on all targets
        }
        allSpells.add(new OffensiveMagic("Basic", "", -10, 0, 0));
    }

    public List<Magic> getSpells() {
        return availableSpells;
    }

    public Magic getSpell(int i) {
        return availableSpells.get(i);
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

    public int calcMp(int level) {
        return (int) (currentMp*(10*Math.sqrt(level)));
    }

    @Override
    public int calcHp(int level) {
        return (int) (currentHp*(10*Math.sqrt(level)));
    }

    public int calcMaxXp(int level) {
        return (25*(level-1)*(1+(level-1)));
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void addXP(int value) {
        this.currentXp += value;
        if(this.currentXp > maxXp) {
            levelUp();
        }
    }

    public void levelUp() {
        level += 1;

        //calculating new Mp
        currentMp = calcMp(level);
        maxMp = calcMp(level);

        //calculating new Hp
        currentHp = calcHp(level);
        maxHp = calcHp(level);

        //calculating new MaxXp
        maxXp = calcMaxXp(level);

        //first checking if the new level is a level where you get a new spell
        if (level == 2) {
            availableSpells.add(allSpells.get(0));
        } else if (level == 4) {
            availableSpells.add(allSpells.get(1));
        } else if (level == 7) {
            availableSpells.add(allSpells.get(2));
        } else if (level == 11) {
            availableSpells.add(allSpells.get(3));
        } else { //if not a new spell, then altering the available spells
             for(Magic m : availableSpells) {
                 //decreasing the attack and mana
                 m.setValue((int)(m.getValue()*(4*Math.sqrt(level))));
                 m.setMp((int)(m.getMp()*(4*Math.sqrt(level))));
             }
        }
    }

    @Override
    public String toString() {
        return name + (type) + "|HP: " + currentHp + "/" + maxHp + "|MP: " + currentMp + "/" + maxMp;
    }


}
