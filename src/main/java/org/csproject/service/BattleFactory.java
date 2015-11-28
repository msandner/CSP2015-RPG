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

    //following methods should be implemented when pressing a button to use magic

    public void attackCharacterWithMagic(BattleActor attacker, BattleActor victim, OffensiveMagic magic) {
        if(!attacker.playerHasAttacked()) {
            Random rand = new Random();
            int critfactor = rand.nextInt(100);
            if (critfactor < 90) {
                victim.setCurrentHp(victim.getCurrentHp() - magic.getValue());
                System.out.println("Attacked character");
            } else {
                //10 percent chance to get a Critical Hit
                victim.setCurrentHp(victim.getCurrentHp() - (magic.getValue() * 2));
                System.out.println("Attacked character with critical hit");
            }

            if (victim.is_dead()) {
                //character image has to be deleted from the battlescreen
            }
            attacker.setHasAttacked(true);
        }
    }

    public void healCharactersWithMagic(BattleActor attacker, BattleActor victim, PlayerParty party, RestorativeMagic magic) {
        if(!attacker.playerHasAttacked()) {
            if (magic.getTarget().equals("Player")) {
                //if character has already maxHP there shouldn't be any effect
                if (victim.getCurrentHp() < victim.getMaxHp()) {
                    victim.setCurrentHp(victim.getCurrentHp() + magic.getValue());
                    System.out.println("Healed character");
                }
            } else if (magic.getTarget().equals("Team")) {
                //restoring health from every teammember
                for (PlayerActor p : party.getParty()) {
                    if (p.getCurrentHp() < p.getMaxHp()) {
                        p.setCurrentHp(p.getCurrentHp() + magic.getValue());
                        System.out.println("Healed party");
                    }
                }
            }
            attacker.setHasAttacked(true);
        }
    }

    public void healCharacterWithPotion(BattleActor attacker, PlayerActor victim, RestorativeItem potion) {
        if(!attacker.playerHasAttacked()) {
            if (potion.getAttribute().equals("Health")) {
                victim.setCurrentHp(victim.getCurrentHp() + potion.getValue());
                System.out.println("Inreased health");
            } else if (potion.getAttribute().equals("Mana")) {
                victim.setCurrentMp(victim.getCurrentMp() + potion.getValue());
                System.out.println("Increased mana");
            }
            attacker.setHasAttacked(true);
        }
    }

    public void enemyAttackAI(BattleActor attacker, BattleActor victim, PlayerParty party, OffensiveMagic magic) {
        Random rand = new Random();
        int chance = rand.nextInt(100);

        if(!attacker.playerHasAttacked()) {
            //the enemy has stronger attacks depending on the character with the highest level
            if (chance < 20) {
                //attacking less than planned
                victim.setCurrentHp(victim.getCurrentHp() - (magic.getValue() * (party.highestLevel() / 10)) / 2);
                System.out.println("Attacked character with half attack");
            } else if (chance > 20 && chance < 90) {
                //general attack
                victim.setCurrentHp(victim.getCurrentHp() - (magic.getValue() * (party.highestLevel() / 10)));
                System.out.println("Attacked character");
            } else {
                //critical hit
                victim.setCurrentHp(victim.getCurrentHp() - (magic.getValue() * (party.highestLevel() / 10)) * 2);
                System.out.println("Attacked character with critical hit");
            }
            attacker.setHasAttacked(true);
        }
    }

    //end of following methods
}
