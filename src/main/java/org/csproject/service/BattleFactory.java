package org.csproject.service;

import org.csproject.model.actors.*;
import org.csproject.model.items.RestorativeItem;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;

import java.util.ArrayList;
import java.util.List;
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

    public MonsterParty getMonsters(){
        Random rand = new Random();
        int r = rand.nextInt(24) + 1;
        List<Monster> m = new ArrayList<Monster>();
        MonsterParty p;

        switch (r){
            case 1:
                m.add(new Monster("Bat", "dungeon", 1, 1, 2, 2));
                m.add(new Monster("Bat", "dungeon", 1, 1, 2, 2));
                m.add(new Monster("Bat", "dungeon", 1, 1, 2, 2));
                p = new MonsterParty(m);
                return p;
            case 2:
                m.add(new Monster("Gayzer", "dungeon", 1, 1, 2, 3));
                p = new MonsterParty(m);
                return p;
            case 3:
                m.add(new Monster("Imp", "field", 1, 2, 2, 2));
                m.add(new Monster("Imp", "field", 1, 2, 2, 2));
                p = new MonsterParty(m);
                return p;
            case 4:
                m.add(new Monster("Hornet", "field", 1, 3, 2, 2));
                m.add(new Monster("Hornet", "field", 1, 3, 2, 2));
                p = new MonsterParty(m);
                return p;
            case 5:
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                p = new MonsterParty(m);
                return p;
            case 6:
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                p = new MonsterParty(m);
                return p;
            case 7:
                m.add(new Monster("Bandit", "field", 2, 3, 4, 3));
                m.add(new Monster("Bandit", "field", 2, 3, 4, 3));
                p = new MonsterParty(m);
                return p;
            case 8:
                m.add(new Monster("Gargoyle", "dungeon", 2, 2, 4, 5));
                m.add(new Monster("Gargoyle", "dungeon", 2, 2, 4, 5));
                m.add(new Monster("Gargoyle", "dungeon", 2, 2, 4, 5));
                p = new MonsterParty(m);
                return p;
            case 9:
                m.add(new Monster("Plant", "field", 2, 3, 4, 3));
                m.add(new Monster("Plant", "field", 2, 3, 4, 3));
                p = new MonsterParty(m);
                return p;
            case 10:
                m.add(new Monster("Skeleton", "dungeon", 2, 4, 4, 4));
                m.add(new Monster("Skeleton", "dungeon", 2, 4, 4, 4));
                p = new MonsterParty(m);
                return p;
            case 11:
                m.add(new Monster("Zombie", "dungeon", 2, 3, 4, 4));
                m.add(new Monster("Zombie", "dungeon", 2, 3, 4, 4));
                m.add(new Monster("Zombie", "dungeon", 2, 3, 4, 4));
                p = new MonsterParty(m);
                return p;
            case 12:
                m.add(new Monster("Assassin", "dungeon", 3, 4, 8, 6));
                m.add(new Monster("Assassin", "dungeon", 3, 4, 8, 6));
                p = new MonsterParty(m);
                return p;
            case 13:
                m.add(new Monster("Mimic", "dungeon", 3, 4, 8, 10));
                p = new MonsterParty(m);
                return p;
            case 14:
                m.add(new Monster("Orc", "field", 3, 3, 8, 4));
                m.add(new Monster("Orc", "field", 3, 3, 8, 4));
                m.add(new Monster("Orc", "field", 3, 3, 8, 4));
                p = new MonsterParty(m);
                return p;
            case 15:
                m.add(new Monster("Rogue", "field", 3, 4, 8, 7));
                m.add(new Monster("Rogue", "field", 3, 4, 8, 7));
                p = new MonsterParty(m);
                return p;
            case 16:
                m.add(new Monster("Spider", "dungeon", 3, 4, 8, 5));
                m.add(new Monster("Spider", "dungeon", 3, 4, 8, 5));
                m.add(new Monster("Spider", "dungeon", 3, 4, 8, 5));
                p = new MonsterParty(m);
                return p;
            case 17:
                m.add(new Monster("Snake", "field", 2, 4, 4, 4));
                m.add(new Monster("Snake", "field", 2, 4, 4, 4));
                p = new MonsterParty(m);
                return p;
            case 18:
                m.add(new Monster("Fanatic", "dungeon", 4, 4, 16, 8));
                m.add(new Monster("Fanatic", "dungeon", 4, 4, 16, 8));
                p = new MonsterParty(m);
                return p;
            case 19:
                m.add(new Monster("Ghost", "dungeon", 4, 5, 16, 9));
                m.add(new Monster("Ghost", "dungeon", 4, 5, 16, 9));
                p = new MonsterParty(m);
                return p;
            case 20:
                m.add(new Monster("Scorpion", "dungeon", 4, 3, 16, 9));
                m.add(new Monster("Scorpion", "dungeon", 4, 3, 16, 9));
                m.add(new Monster("Scorpion", "dungeon", 4, 3, 16, 9));
                p = new MonsterParty(m);
                return p;
            case 21:
                m.add(new Monster("Werewolf", "field", 4, 5, 16, 10));
                m.add(new Monster("Werewolf", "field", 4, 5, 16, 10));
                p = new MonsterParty(m);
                return p;
            case 22:
                m.add(new Monster("Puppet", "dungeon", 2, 5, 4, 4));
                p = new MonsterParty(m);
                return p;
            case 23:
                m.add(new Monster("Mage", "boss", 5, 6, 32, 14));
                p = new MonsterParty(m);
                return p;
            case 24:
                m.add(new Monster("Vampire", "boss", 5, 7, 32, 16));
                p = new MonsterParty(m);
                return p;
        }

        m.add(new Monster("Slime", "field", 1, 1, 2, 1));
        p = new MonsterParty(m);
        return p;
    }








}
