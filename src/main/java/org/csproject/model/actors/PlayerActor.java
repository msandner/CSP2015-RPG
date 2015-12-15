package org.csproject.model.actors;

import org.csproject.model.Constants;
import org.csproject.model.items.ArmorItem;
import org.csproject.model.items.WeaponItem;
import org.csproject.model.magic.Magic;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */

public class PlayerActor extends BattleActor {
    protected int currentMp = 75;
    protected int maxMp;

    protected int currentXp;
    protected int maxXp = 50;

    /**
     * list of all spells
     */
    private List<Magic> allSpells = new ArrayList<>();
    /**
     * list of all avaialable spells for the character because of the leveling up system
     */
    private List<Magic> availableSpells = new ArrayList<>();

    protected WeaponItem weapon;
    protected ArmorItem headArmor;
    protected ArmorItem bodyArmor;
    protected ArmorItem handArmor;
    protected ArmorItem footArmor;

    /**
     * Maike Keune-Staab & Maren Sandner & Nicholas Paquette
     * creates the player actor, which is the actor that the game player is able to use
     * actor gets sets to level 1, adding spells and items
     * @param name: name of the actor
     * @param type: class type of the actor (CLASS_KNIGHT, CLASS_MAGE, CLASS_THIEF)
     * @param attack: attack value of the actor
     */
    public PlayerActor(String name, String type, int attack) {
        super(name, type, 1, attack);

        int mp = calcMp(level);
        this.currentMp = mp;
        this.maxMp = mp;

        weapon = new WeaponItem("", "", 0, 0);
        headArmor = new ArmorItem("", "", "", 0, 0);
        bodyArmor = new ArmorItem("", "", "", 0, 0);
        handArmor = new ArmorItem("", "", "", 0, 0);
        footArmor = new ArmorItem("", "", "", 0, 0);

        currentXp = 0;

        addSpells(type);
        availableSpells.add(allSpells.get(4)); //everyone has basic attack in the first level
     }

    /**
     * Maren Sandner
     * creates the spells for the classes and adds them depending on the actors class to the allSpells list
     * @param type: type of the actor
     */
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

    /**
     * Maren Sandner
     * searches the list for a spell on a specific index
     * @param i: index of the spell
     * @return the spell on the index position
     */
    public Magic getSpell(int i) {
        return allSpells.get(i);
    }

    /**
     * Maren Sandner
     * adds a value to the current mana points
     * @param mp: mana points that should get added
     */
    public void addToCurrentMp(int mp) {
        if((currentMp+mp) > maxMp) {
            currentMp = maxMp;
        } else {
            currentMp += mp;
        }
    }

    /**
     * Maren Sandner
     * calculates the mana points depending on level for the actor
     * @param level: level of the actor
     * @return the calculated mana points
     */
    public int calcMp(int level) {
        return (int) (currentMp+(10*Math.sqrt(level)));
    }

    /**
     * Maren Sandner
     * calculates the health points depending on the level for the actor
     * @param level: level of the actor
     * @return the calculated health points
     */
    @Override
    public int calcHp(int level) {
        return (int) (currentHp+(10*Math.sqrt(level)));
    }

    /**
     * Maren Sandner
     * calculates the maximum amount of experience points the actor can have
     * @param level: level of the actor
     * @return the calculated maximum experience points
     */
    public int calcMaxXp(int level) {
        return (25*(level-1)*(1+(level-1)));
    }

    /**
     * Maren Sandner
     * adds experience points to the current experience points
     * @param value: experience points that should be added
     */
    public void addXP(int value) {
        this.currentXp += value;
        if(this.currentXp > maxXp) {
            levelUp();
        }
    }

    /**
     * Maren Sandner
     * levels up the actor
     * calculates new mana, health and maximum experience points
     * adds spells or increases the spell values depending on the level that is reached
     */
    public void levelUp() {
        System.out.println("Level Up!");
        level += 1;

        maxMp = calcMp(level);
        currentMp = maxMp;

        maxHp = calcHp(level);
        currentHp = maxHp;

        maxXp = calcMaxXp(level);

        if (level == 2) {
            availableSpells.add(allSpells.get(0));
        } else if (level == 4) {
            availableSpells.add(allSpells.get(1));
        } else if (level == 7) {
            availableSpells.add(allSpells.get(2));
        } else if (level == 11) {
            availableSpells.add(allSpells.get(3));
        } else {
             for(Magic m : availableSpells) {
                 //setting the attack value
                 if(m.getName().equals("Heal")) {
                     m.setValue((int)(m.getValue() + (4 * Math.sqrt(level))));
                 } else if(m.getName().equals("Basic")){
                     m.setValue((int) (m.getValue() - (2 * Math.sqrt(level))));
                 } else {
                     m.setValue((int) (m.getValue() - (2 * Math.sqrt(level))));
                 }

                 //setting the mana
                 if(!m.getName().equals("Basic")) {
                     m.setMp((int) (m.getMp() - (4 * Math.sqrt(level))));
                 }
             }
        }
    }

    /**
     * Nicholas Paquette
     * Equips a weapon item to the character
     * @param w the weapon to be equipped
     * @return returns the old weapon that was equipped before if the equipping was successful, returns the weapon that
     *         was to be equipped if equipping was not successful
     */
    public WeaponItem addWeapon(WeaponItem w){
        if(w.getRestrictions() == type){
            WeaponItem r = weapon;
            weapon = w;
            return r;
        } else {
            return w;
        }
    }

    /**
     * Nicholas Paquette
     * Equips an armor item to the character
     * @param a the armor to be equipped
     * @return returns the old armor that was equipped before if the equipping was successful, returns the armor that
     *         was to be equipped if equipping was not successful
     */
    public ArmorItem addArmor(ArmorItem a){
        if(a.getRestrictions() == type){
            ArmorItem r;
            if(a.getPiece() == "Head"){
                r = headArmor;
                headArmor = a;
                return r;
            } else if(a.getPiece() == "Body"){
                r = bodyArmor;
                bodyArmor = a;
                return r;
            } else if(a.getPiece() == "Hands"){
                r = handArmor;
                handArmor = a;
                return r;
            } else {
                r = footArmor;
                footArmor = a;
                return r;
            }
        } else {
            return a;
        }
    }

    /**
     * Maike Keune-Staab
     * prints out the attributes of the actor in a formated string
     * @return string with name, type, current Hp, maximum Hp, current Mp and maximum Mp
     */
    @Override
    public String toString() {
        return name + (type) + "|HP: " + currentHp + "/" + maxHp + "|MP: " + currentMp + "/" + maxMp;
    }

    /**
     *getter and setter generated from IntelliJ IDEA
     */

    public int getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(int mp) {
        currentMp = mp;
    }

    public List<Magic> getSpells() {
        return availableSpells;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getXP() {
        return currentXp;
    }

}
