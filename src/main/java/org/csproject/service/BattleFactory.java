package org.csproject.service;

import org.csproject.model.actors.*;
import org.csproject.model.items.RestorativeItem;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;
import org.csproject.view.BattleScreenController;
import org.csproject.view.CharacterImage;
import org.csproject.view.MasterController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Maren on 13.11.2015.
 */
public class BattleFactory {

    private BattleScreenController battleController;

    private PlayerParty actualplayers;


    /**
     * Maren Sandner
     * sets the players and monsters and starts the battle with these
     */
    public void startBattle() {

            //TODO check if field isn't town

            MasterController.setScreen(MasterController.BATTLE_SCREEN_ID);

            actualplayers = MasterController.getScreensController().getParty();
            MonsterParty monsterparty = getMonsters(actualplayers);

            battleController = MasterController.getBattleController();
            battleController.startNewBattle(actualplayers, monsterparty.getParty());

            System.out.println("Battle started");

    }


    /**
     * Maren Sandner
     * changes the battle screen back to the game screen
     */
    public void endBattle() {
        MasterController.setScreen(MasterController.GAME_SCREEN);
    }

    public void gameOver() {
        MasterController.setScreen(MasterController.START_MENU_ID);
    }

    /**
     * Maren Sandner
     * method for attacking a specific victim
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     * @param magic: the spell
     * @param extra: value if the spell should do extra damage (e.g. '2' for double damage)
     */
    public void attackCharacterWithMagic(PlayerActor attacker, BattleActor victim, OffensiveMagic magic, double extra) {
        if(!attacker.playerHasAttacked()) {
            victim.addToCurrentHp((int) (magic.getValue() * extra));
            System.out.println("Attacked character with " + magic.getName());
            attacker.addToCurrentMp(magic.getMp());
            attacker.setHasAttacked(true);
        }
    }

    /**
     * Maren Sandner
     * basic attack "spell" for every character
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     */
    public void basicAttack(PlayerActor attacker, BattleActor victim) {
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(4), 1);
    }

    /**
     * Maren Sandner
     * shieldbash for the knight -> 60% chance to disable the monster for one round
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     */
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

    /**
     * Maren Sandner
     * whirlwind for the knight -> attacks two monsters
     * @param attacker: the actor that uses the spell
     * @param victim: the first actor that should receive the damage
     * @param victim2: the second actor that should receive the damage
     */
    public void whirlwind(PlayerActor attacker, BattleActor victim, BattleActor victim2){
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(1), 1);
        attackCharacterWithMagic(attacker, victim2, (OffensiveMagic) attacker.getSpell(1), 1);

        attacker.setHasAttacked(true);
    }

    /**
     * Maren Sandner
     * berserk for knight -> double damage when the health of the attacker is under 25%
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     */
    public void berserk(PlayerActor attacker, BattleActor victim) {
        if(attacker.getCurrentHp() < (attacker.getMaxHp()*0.25)) {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(2), 2);
        } else {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(2), 1);
        }

        attacker.setHasAttacked(true);
    }

    /**
     * Maren Sandner
     * chain lightning for the mage -> attacks (depending on level of the attacker) one till six monsters
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     * @param party: the monster party
     */
    public void chainLightning(PlayerActor attacker, BattleActor victim, MonsterParty party) {
        int pos = party.getMonsterPosition((Monster) victim);
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(1), 2);

        if(attacker.getLevel() > 6 && attacker.getLevel() < 9 && party.getPartySize() > 2) {
            attackNextEnemy(attacker,party, 2, pos, 1);
        } else if(attacker.getLevel() > 9 && attacker.getLevel() < 13 && party.getPartySize() > 3){
            attackNextEnemy(attacker,party, 3, pos, 1);
        } else if(attacker.getLevel() > 13 && attacker.getLevel() < 17 && party.getPartySize() > 4) {
            attackNextEnemy(attacker,party, 4, pos, 1);
        } else if(attacker.getLevel() > 17 && attacker.getLevel() < 21 && party.getPartySize() > 5) {
            attackNextEnemy(attacker,party, 5, pos, 1);
        } else if(attacker.getLevel() > 21 && party.getPartySize() == 6) {
            attackNextEnemy(attacker,party, 6, pos, 1);
        }

        attacker.setHasAttacked(true);
    }

    /**
     * Maren Sandner
     * frostbite for the mage -> disables the monster for one round, but does no damage
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     */
    public void frostbite(PlayerActor attacker, BattleActor victim) {
        victim.setHasAttacked(true);
        attacker.addToCurrentMp(attacker.getSpell(3).getMp());

        attacker.setHasAttacked(true);
    }

    /**
     * Maren Sandner
     * ambush for the thief -> 50% chance to hit the monster with double damage
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     */
    public void ambush(PlayerActor attacker, BattleActor victim) {
        Random rand = new Random();
        int critfactor = rand.nextInt(100);
        if(critfactor < 50) {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(0), 2);
        } else {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(0), 1);
        }

        attacker.setHasAttacked(true);
    }

    /**
     * Maren Sandner
     * mutilate for the thief -> does double damage on the monster
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     */
    public void mutilate(PlayerActor attacker, BattleActor victim) {
        attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(1), 2);

        attacker.setHasAttacked(true);
    }

    /**
     * Maren Sandner
     * execute for the thief -> if the health from the monster is under 25% it instantly kills it
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should receive the damage
     */
    public void execute(PlayerActor attacker, BattleActor victim) {
        if(victim.getCurrentHp() < (victim.getMaxHp()*0.25)) {
            victim.setCurrentHp(0);
        } else {
            attackCharacterWithMagic(attacker, victim, (OffensiveMagic) attacker.getSpell(2), 1);
        }

        attacker.setHasAttacked(true);
    }

    /**
     * Maren Sandner
     * shuriken Toss for the thief -> does low damage on all the monsters on the battlefield
     * @param attacker: the actor that uses the spell
     * @param party: monster party
     */
    public void shurikenToss(PlayerActor attacker, MonsterParty party) {
        for(int i = 0; i < party.getPartySize(); i++) {
            attackCharacterWithMagic(attacker, party.getMonster(i), (OffensiveMagic) attacker.getSpell(3), 1);
        }
        attacker.setHasAttacked(true);
    }

    //for attacking the character next to the victim

    /**
     * Maren Sandner
     * for attacking actors that stand next to the desired victim
     * @param attacker: the actor that uses the spell
     * @param party: monster party
     * @param amount: amount of monsters that should get hit
     * @param pos: position of the original monster
     * @param spellnumber: number of the spell from the attacker
     */
    public void attackNextEnemy(PlayerActor attacker, MonsterParty party, int amount, int pos, int spellnumber) {
        for (int j = 0; j < amount; j++) {
            if (pos + amount < party.getPartySize()) {
                attackCharacterWithMagic(attacker, party.getMonster(pos + j), (OffensiveMagic) attacker.getSpell(spellnumber), 1);
            } else {
                attackCharacterWithMagic(attacker, party.getMonster(pos - j), (OffensiveMagic) attacker.getSpell(spellnumber), 1);
            }
        }
    }

    /**
     * Maren Sandner
     * healing actors or the whole party
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should get healed
     * @param party: player party
     * @param magic: the spell for the healing
     */
    public void healCharactersWithMagic(PlayerActor attacker, BattleActor victim, PlayerParty party, RestorativeMagic magic) {
        if (!attacker.playerHasAttacked()) {
            if (magic.getTarget().equals("Player") && victim.getCurrentHp() > 0) {
                victim.addToCurrentHp(magic.getValue());
                System.out.println("Healed character");

            } else if (magic.getTarget().equals("Team")) {

                for (PlayerActor p : party.getParty()) {
                    if (p.getCurrentHp() > 0) {
                        p.addToCurrentHp(magic.getValue());
                    }
                }
                System.out.println("Healed party");
            }
            attacker.addToCurrentMp(magic.getMp());
            attacker.setHasAttacked(true);
        }
    }

    /**
     * Maren Sandner
     * healing actors or the whole party
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should get healed
     * @param party: player party
     * @param item: the item for the healing
     */
    public void healCharactersWithItem(PlayerActor attacker, PlayerActor victim, PlayerParty party, RestorativeItem item) {
        if (!attacker.playerHasAttacked()) {
            if (item.getTarget().equals("Player") && victim.getCurrentHp() > 0) {
                if(item.getAttribute().equals("Health")) {
                    victim.addToCurrentHp(item.getValue());
                    System.out.println("Healed character");
                } else if(item.getAttribute().equals("Mana")) {
                    victim.addToCurrentMp(item.getValue());
                    System.out.println("Restored Mana");
                }

            } else if (item.getTarget().equals("Team")) {

                for (PlayerActor p : party.getParty()) {
                    if (p.getCurrentHp() > 0) {
                        p.addToCurrentHp(item.getValue());
                    }
                }
                System.out.println("Healed party");
            }
            attacker.setHasAttacked(true);
        }
    }

    /**
     * Maren Sandner
     * heal the actors mana or health with a potion
     * @param attacker: the actor that uses the spell
     * @param victim: the actor that should get healed
     * @param potion: potion to heal the actor
     */
    public void healCharacterWithPotion(PlayerActor attacker, PlayerActor victim, RestorativeItem potion) {
        if(!attacker.playerHasAttacked()) {
            if (potion.getAttribute().equals("Health")) {
                victim.addToCurrentHp(potion.getValue());
                System.out.println("Inreased health");
            } else if (potion.getAttribute().equals("Mana")) {
                victim.setCurrentMp(potion.getValue());
                System.out.println("Increased mana");
            }
            attacker.setHasAttacked(true);
        }
    }

    /**
     * Maren Sandner
     * method for the enemyAI (very basic), having a 20% chance to hit with half of the attack value, 70% chance to
     * hit with normal attack value and 10% chance to hit with double attack value
     * @param attacker: the actor that attacks
     * @param party: player party, because the victim gets randomly choosen
     * @param magic: the spell the actor uses
     */
    public void enemyAttackAI(BattleActor attacker, PlayerParty party, OffensiveMagic magic) {
        Random rand = new Random();

        int choose = rand.nextInt(3);
        BattleActor victim = party.getParty().get(choose);

        int chance = rand.nextInt(100);
        if(!attacker.playerHasAttacked()) {
            if (chance < 20) {
                victim.addToCurrentHp(magic.getValue() / 2);
                System.out.println("Attacked character with half attack");
            } else if (chance > 20 && chance < 90) {
                victim.addToCurrentHp(magic.getValue());
                System.out.println("Attacked character");
            } else {
                victim.addToCurrentHp(magic.getValue() * 2);
                System.out.println("Attacked character with critical hit");
            }
            attacker.setHasAttacked(true);
        }
    }

    /**
     * Nicholas Paquette & Maren Sandner
     * creates the monsters that are able to spawn in the battle
     * the monsters get set on the partys highest level + a maximum levels of two
     * @param party: player party
     * @return the monsters that the party will battle against
     */
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
            int chance = rand.nextInt(2);
            for(int i = 0; i < party.highestLevel() + chance; i++) {
                monster.levelUpMonster();
            }
        }

        return p;
    }









}
