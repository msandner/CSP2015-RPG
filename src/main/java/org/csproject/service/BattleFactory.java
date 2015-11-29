package org.csproject.service;

import org.csproject.model.actors.BattleActor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.actors.PlayerParty;
import org.csproject.model.items.RestorativeItem;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;

import java.util.Random;

/**
 * Created by Maren on 13.11.2015.
 */
public class BattleFactory {

    public void startBattle() {
       System.out.println("Start Battle");
    }

    public boolean isEveryPlayerDead(PlayerParty party) {
        for (PlayerActor p : party.getParty()) {
            if (!p.is_dead()) {
                return false;
            }
        }
        return true;
    }

    public void attackCharacterWithMagic(PlayerActor attacker, BattleActor victim, OffensiveMagic magic) {
        if(!attacker.playerHasAttacked()) {
            Random rand = new Random();
            int critfactor = rand.nextInt(100);
            if (critfactor < 90) {
                victim.calcCurrentHp(magic.getValue());
                System.out.println("Attacked character");
            } else {
                //10 percent chance to get a Critical Hit
                victim.calcCurrentHp(magic.getValue() * 2);
                System.out.println("Attacked character with critical hit");
            }
            if (victim.is_dead()) {
                //character image has to be deleted from the battlescreen
            }
            //at end decreasing mana and setting the hasAttacked boolean true, so the character can't attack in this round anymore
            attacker.calcCurrentMp(magic.getMp());
            attacker.setHasAttacked(true);
        }
    }

    public void healCharactersWithMagic(PlayerActor attacker, BattleActor victim, PlayerParty party, RestorativeMagic magic) {
        if (!attacker.playerHasAttacked()) {
            if (magic.getTarget().equals("Player")) {
                victim.calcCurrentHp(magic.getValue());
                System.out.println("Healed character");
            } else if (magic.getTarget().equals("Team")) {
                //restoring health from every teammember
                for (PlayerActor p : party.getParty()) {
                    p.calcCurrentHp(magic.getValue());
                    System.out.println("Healed party");
                }
            }
            attacker.calcCurrentMp(magic.getMp());
            attacker.setHasAttacked(true);
        }
    }

    public void healCharacterWithPotion(PlayerActor attacker, PlayerActor victim, RestorativeItem potion) {
        if(!attacker.playerHasAttacked()) {
            if (potion.getAttribute().equals("Health")) {
                victim.calcCurrentHp(potion.getValue());
                System.out.println("Inreased health");
            } else if (potion.getAttribute().equals("Mana")) {
                victim.setCurrentMp(potion.getValue());
                System.out.println("Increased mana");
            }
            attacker.setHasAttacked(true);
        }
    }

    public void enemyAttackAI(BattleActor attacker, PlayerParty party, OffensiveMagic magic) {
        Random rand = new Random();
        int chance = rand.nextInt(100);

        //randomly choosing a victim
        int choose = rand.nextInt(3);
        PlayerActor victim = party.getParty().get(choose);

        //todo: implement a hit and miss chance
        if(!attacker.playerHasAttacked()) {
            //the enemy has stronger attacks depending on the character with the highest level
            if (chance < 20) {
                //attacking less than planned
                victim.calcCurrentHp(magic.getValue() * ((party.highestLevel() / 10) / 2));
                System.out.println("Attacked character with half attack");
            } else if (chance > 20 && chance < 90) {
                //general attack
                victim.calcCurrentHp(magic.getValue() * (party.highestLevel() / 10));
                System.out.println("Attacked character");
            } else {
                //critical hit
                victim.calcCurrentHp(magic.getValue() * ((party.highestLevel() / 10) * 2));
                System.out.println("Attacked character with critical hit");
            }
            attacker.setHasAttacked(true);
        }
    }








}
