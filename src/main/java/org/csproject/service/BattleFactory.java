package org.csproject.service;

import org.csproject.model.actors.*;
import org.csproject.model.field.StartPoint;
import org.csproject.model.items.RestorativeItem;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;
import org.csproject.view.BattleScreenController;
import org.csproject.view.CharacterImage;
import org.csproject.view.MasterController;
import org.csproject.view.NewGameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Maren on 13.11.2015.
 */
public class BattleFactory {

    private BattleScreenController battleController;

    private PlayerParty actualplayers;

    public void startBattle() {
        MasterController.setScreen(MasterController.BATTLE_SCREEN_ID);

        actualplayers = MasterController.getScreensController().getParty();
        MonsterParty monsterparty = getMonsters(actualplayers);

        battleController = MasterController.getBattleController();
        battleController.startNewBattle(actualplayers.getParty(), monsterparty.getParty());

        System.out.println("Battle started");

        //endBattle();
    }

    public void endBattle() {
        MasterController.setScreen(MasterController.GAME_SCREEN);
    }

    public void roundBasedBattle(PlayerParty pparty, MonsterParty mparty) {
        while(!pparty.isEveryPlayerDead() && !mparty.isEveryEnemyDead()) {
            for(BattleActor p : pparty.getParty()) {
                if(!p.playerHasAttacked()) {
                    //do stuff
                }
            }
            for(BattleActor p : mparty.getParty()){
                if(!p.playerHasAttacked()) {
                    //dostuff
                }
            }

            for(BattleActor p : pparty.getParty()) {
                p.setHasAttacked(false);
            }

            for(BattleActor p : mparty.getParty()) {
                p.setHasAttacked(false);
            }
        }

        if(pparty.isEveryPlayerDead()) {
            //gameover
        } else if(mparty.isEveryEnemyDead()) {
            int xp = 0;
            for(Monster p : mparty.getParty()) {
                xp += p.getGrantingXP();
            }
            for(PlayerActor p : pparty.getParty()) {
                p.addXP(xp/3);
                //+change screen
            }
        }

    }

    public void attackCharacterWithMagic(PlayerActor attacker, BattleActor victim, OffensiveMagic magic, double extra) {
        //extra is for creating more dmg on the victim (normal = 1, double dmg = 2 etc) )


        if(!attacker.playerHasAttacked()) {
            victim.calcCurrentHp((int)(magic.getValue()*extra));
            System.out.println("Attacked character");

            if (victim.is_dead()) {
                victim = null; //todo
            }
            //at end decreasing mana and setting the hasAttacked boolean true, so the character can't attack in this round anymore
            attacker.calcCurrentMp(magic.getMp());
            attacker.setHasAttacked(true);
        }
    }

    public void basicAttack(PlayerActor attacker, PlayerActor victim) {
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(4), 1);
    }
    //spells for the knight
    public void shieldBash(PlayerActor attacker, BattleActor victim) {
        Random rand = new Random();
        int chance = rand.nextInt(100);

        if(chance < 60) {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(0), 1);
            victim.setHasAttacked(true);
        } else {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(0), 1);
        }

        attacker.setHasAttacked(true);
    }

    public void whirlwind(PlayerActor attacker, BattleActor victim, BattleActor victim2){
        //dmg on two victims
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(1), 1);
        attackCharacterWithMagic(attacker, victim2, (OffensiveMagic) attacker.getSpell(1), 1);

        attacker.setHasAttacked(true);
    }

    public void berserk(PlayerActor attacker, BattleActor victim) {
        //double dmg when the health is under 25%
        if(attacker.getCurrentMp() < (attacker.getMaxHp()*0.25)) {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(2), 2);
        } else {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(2), 1);
        }

        attacker.setHasAttacked(true);
    }

    //spells for the mage
    public void chainLightning(PlayerActor attacker, BattleActor victim, MonsterParty party) {
        int pos = party.getMonsterPosition((Monster) victim);
        int partysize = party.getPartySize()-1;
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(2), 2);
        if(attacker.getLevel() < 6) {

        } else if(attacker.getLevel() > 6 && attacker.getLevel() < 9) {
            attackNextEnemy(attacker,party, 2, pos, 1);
        } else if(attacker.getLevel() > 9 && attacker.getLevel() < 13){
            attackNextEnemy(attacker,party, 3, pos, 1);
        } else if(attacker.getLevel() > 13 && attacker.getLevel() < 17) {
            attackNextEnemy(attacker,party, 4, pos, 1);
        } else if(attacker.getLevel() > 17 && attacker.getLevel() < 21) {
            attackNextEnemy(attacker,party, 5, pos, 1);
        } else if(attacker.getLevel() > 21 ) {
            attackNextEnemy(attacker,party, 6, pos, 1);
        }

        attacker.setHasAttacked(true);
    }

    public void frostbite(PlayerActor attacker, BattleActor victim) {
        victim.setHasAttacked(true);

        attacker.setHasAttacked(true);
    }

    //spells for thief
    public void ambush(PlayerActor attacker, BattleActor victim) {
        Random rand = new Random();
        int critfactor = rand.nextInt(100);
        //50% chance to have a hit with double damage
        if(critfactor < 50) {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(0), 2);
        } else {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(0), 1);
        }

        attacker.setHasAttacked(true);
    }

    public void mutilate(PlayerActor attacker, BattleActor victim) {
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(1), 2);

        attacker.setHasAttacked(true);
    }

    public void execute(PlayerActor attacker, BattleActor victim) {
        if(victim.getCurrentHp() < (victim.getMaxHp()*0.25)) {
            victim.setCurrentHp(0);
        } else {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(2), 1);
        }

        attacker.setHasAttacked(true);
    }

    public void shurikenToss(PlayerActor attacker, MonsterParty party) {
        for(int i = 0; i < party.getPartySize(); i++) {
            attackCharacterWithMagic(attacker, party.getMonster(i), (OffensiveMagic) attacker.getSpell(3), 1);
        }

        attacker.setHasAttacked(true);
    }

    public void attackNextEnemy(PlayerActor attacker, MonsterParty party, int i, int pos, int spellnumber) {
        for (int j = 0; j < i; i++) {
            if (pos + i < party.getPartySize()) {
                attackCharacterWithMagic(attacker, party.getMonster(pos + j), (OffensiveMagic) attacker.getSpell(spellnumber), 1);
            } else {
                attackCharacterWithMagic(attacker, party.getMonster(pos - j), (OffensiveMagic) attacker.getSpell(spellnumber), 1);
            }
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
        BattleActor victim = party.getParty().get(choose);

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

    public MonsterParty getMonsters(PlayerParty party){
        Random rand = new Random();
        int r = rand.nextInt(24) + 1;

        List<Monster> m = new ArrayList<Monster>();

        switch (r){
            case 1:
                m.add(new Monster("Bat", "dungeon", 1, 1, 2, 2));
                m.add(new Monster("Bat", "dungeon", 1, 1, 2, 2));
                m.add(new Monster("Bat", "dungeon", 1, 1, 2, 2));
                break;
            case 2:
                m.add(new Monster("Gayzer", "dungeon", 1, 1, 2, 3));
                m.add(new Monster("Bat", "dungeon", 1, 1, 2, 2));
                break;
            case 3:
                m.add(new Monster("Imp", "field", 1, 2, 2, 2));
                m.add(new Monster("Imp", "field", 1, 2, 2, 2));
                break;
            case 4:
                m.add(new Monster("Hornet", "field", 1, 3, 2, 2));
                m.add(new Monster("Hornet", "field", 1, 3, 2, 2));
                break;
            case 5:
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                m.add(new Monster("Slime", "field", 1, 1, 2, 1));
                break;
            case 6:
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                m.add(new Monster("Rat", "dungeon", 1, 2, 2, 2));
                break;
            case 7:
                m.add(new Monster("Bandit", "field", 2, 3, 4, 3));
                m.add(new Monster("Bandit", "field", 2, 3, 4, 3));
                break;
            case 8:
                m.add(new Monster("Gargoyle", "dungeon", 2, 2, 4, 5));
                m.add(new Monster("Gargoyle", "dungeon", 2, 2, 4, 5));
                m.add(new Monster("Gargoyle", "dungeon", 2, 2, 4, 5));
                break;
            case 9:
                m.add(new Monster("Plant", "field", 2, 3, 4, 3));
                m.add(new Monster("Plant", "field", 2, 3, 4, 3));
                break;
            case 10:
                m.add(new Monster("Skeleton", "dungeon", 2, 4, 4, 4));
                m.add(new Monster("Skeleton", "dungeon", 2, 4, 4, 4));
                break;
            case 11:
                m.add(new Monster("Zombie", "dungeon", 2, 3, 4, 4));
                m.add(new Monster("Zombie", "dungeon", 2, 3, 4, 4));
                m.add(new Monster("Zombie", "dungeon", 2, 3, 4, 4));
                break;
            case 12:
                m.add(new Monster("Assassin", "dungeon", 3, 4, 8, 6));
                m.add(new Monster("Assassin", "dungeon", 3, 4, 8, 6));
                break;
            case 13:
                m.add(new Monster("Mimic", "dungeon", 3, 4, 8, 10));
                break;
            case 14:
                m.add(new Monster("Orc", "field", 3, 3, 8, 4));
                m.add(new Monster("Orc", "field", 3, 3, 8, 4));
                m.add(new Monster("Orc", "field", 3, 3, 8, 4));
                break;
            case 15:
                m.add(new Monster("Rogue", "field", 3, 4, 8, 7));
                m.add(new Monster("Rogue", "field", 3, 4, 8, 7));
                break;
            case 16:
                m.add(new Monster("Spider", "dungeon", 3, 4, 8, 5));
                m.add(new Monster("Spider", "dungeon", 3, 4, 8, 5));
                m.add(new Monster("Spider", "dungeon", 3, 4, 8, 5));
                break;
            case 17:
                m.add(new Monster("Snake", "field", 2, 4, 4, 4));
                m.add(new Monster("Snake", "field", 2, 4, 4, 4));
                break;
            case 18:
                m.add(new Monster("Fanatic", "dungeon", 4, 4, 16, 8));
                m.add(new Monster("Fanatic", "dungeon", 4, 4, 16, 8));
                break;
            case 19:
                m.add(new Monster("Ghost", "dungeon", 4, 5, 16, 9));
                m.add(new Monster("Ghost", "dungeon", 4, 5, 16, 9));
                break;
            case 20:
                m.add(new Monster("Scorpion", "dungeon", 4, 3, 16, 9));
                m.add(new Monster("Scorpion", "dungeon", 4, 3, 16, 9));
                m.add(new Monster("Scorpion", "dungeon", 4, 3, 16, 9));
                break;
            case 21:
                m.add(new Monster("Werewolf", "field", 4, 5, 16, 10));
                m.add(new Monster("Werewolf", "field", 4, 5, 16, 10));
                break;
            case 22:
                m.add(new Monster("Puppet", "dungeon", 2, 5, 4, 4));
                break;
            case 23:
                m.add(new Monster("Mage", "boss", 5, 6, 32, 14));
                break;
            case 24:
                m.add(new Monster("Vampire", "boss", 5, 7, 32, 16));
                break;
        }

        MonsterParty p = new MonsterParty(m);

        for(Monster monster : p.getParty()) {
            monster.setLevel(party.highestLevel()+rand.nextInt(2));
        }

        return p;
    }

    public void EnemyAttackAI(MonsterParty monster, PlayerParty party) {
        Random rand = new Random();
        for(Monster m : monster.getParty()) {
            int chance = rand.nextInt(100);
            if (chance < 80) {
                m.attack(party.getPlayer(rand.nextInt(3)), 1);
                m.setHasAttacked(false);
            } else {
                m.attack(party.getPlayer(rand.nextInt(3)), 2);
                m.setHasAttacked(false);
            }
        }
    }








}
