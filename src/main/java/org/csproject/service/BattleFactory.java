package org.csproject.service;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.csproject.model.Constants;
import org.csproject.model.actors.*;
import org.csproject.model.items.RestorativeItem;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;
import org.csproject.view.BattleScreenController;
import org.csproject.view.MasterController;

import java.util.Random;

/**
 * Created by Maren on 13.11.2015.
 */
public class BattleFactory {

    ScreensController screencontroller;

    public void startBattle() {
        screencontroller = new ScreensController();
        MasterController.setScreen(MasterController.BATTLE_SCREEN_ID);

        Monster bat1 = new Monster("Bat", "", 1, 10);
        Monster bat2 = new Monster("Bat", "", 1, 10);
        Monster bat3 = new Monster("Bat", "", 1, 10);

        MonsterParty monsterparty = new MonsterParty(bat1, bat2, bat3, null, null, null);

        PlayerActor knight = new PlayerActor("Jim the Knight", Constants.CLASS_KNIGHT, 1, 2, 2);
        PlayerActor thief = new PlayerActor("Bladerunner", Constants.CLASS_THIEF, 1, 2, 2);
        PlayerActor mage = new PlayerActor("Tim", Constants.CLASS_MAGE, 1 , 2 , 2);

        PlayerParty party = new PlayerParty(knight, thief, mage, 100);

        roundBasedBattle(party, monsterparty);

        System.out.println("Battle startet");
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
                for (BattleActor p : party.getParty()) {
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










}
