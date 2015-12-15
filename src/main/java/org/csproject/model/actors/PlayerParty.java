package org.csproject.model.actors;

import org.csproject.model.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 10/29/2015.
 */
public class PlayerParty {
    protected PlayerActor char1, char2, char3;
    protected int currency;
    List<PlayerActor> party;
    List<Item> inventory;

    /**
     * Nicholas Paquette
     * creates the player actor and sets his currency and a new empty inventory
     * @param char1: first player actor
     * @param char2: second player actor
     * @param char3: third player actor
     * @param currency: amount of gold the party has
     */
    public PlayerParty(PlayerActor char1, PlayerActor char2, PlayerActor char3, int currency){
        this.char1 = char1;
        this.char2 = char2;
        this.char3 = char3;
        setParty();

        this.currency = currency;
        inventory = new ArrayList<>();
    }

    /**
     * Nicholas Paquette
     * adds the party to the array list
     */
    public void setParty() {
        party = new ArrayList<>();
        party.add(this.char1);
        party.add(this.char2);
        party.add(this.char3);
    }

    /**
     * Maren Sandner
     * detects which actor has the highest level in the party
     * @return the highest level in the party
     */
    public int highestLevel() {
        int max = 0;
        for(BattleActor p : getParty()) {
            if(p.getLevel() > max) {
                max = p.getLevel();
            }
        }
        return max;
    }

    /**
     * Nicholas Paquette
     * adds a specific value to the current currency
     * @param i: amount of gold that should be added
     */
    public void addCurrency(int i) {
        currency += i;
    }

    /**
     * Nicholas Paquette
     * subtracting a value fro the current currency
     * @param i: amount of gold that should be subtracted
     */
    public void subCurrency(int i){
        currency -= i;
        if(currency < 0){
            currency = 0;
        }
    }

    /**
     * Nicholas Paquette
     * adds a item to the inventory
     * @param i: item that should be added
     */
    public void addItem(Item i){
        inventory.add(i);
    }

    /**
     * Nicholas Paquette
     * removes a item from the inventory, fist checking if it exists
     * @param i: the item that should be removed
     * @return if the item has been successfully removed
     */
    public boolean removeItem(Item i){
        if(getInventory().contains(i)){
            getInventory().remove(i);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Nicholas Paquette
     * getting an item out of the list with it's name
     * @param s: name of the item
     * @return the item, when not found return null
     */
    public Item getItem(String s){
        for(Item i : inventory){
            if(i.getItemName() == s){
                Item item = i;
                inventory.remove(i);
                return item;
            }
        }
        return null;
    }

    /**
     * Maren Sandner
     * checks the whole party if every player is dead
     * @return if every player is dead
     */
    public boolean isEveryPlayerDead() {
        for (PlayerActor p : party) {
            if (!p.is_dead()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Maren Sandner
     * checks if there is still a a party memeber who an attack
     * @return if the party can still attack
     */
    public boolean canPartyStillAttack() {
        for(PlayerActor p : party) {
            if(!p.playerHasAttacked() && !p.is_dead()) {
                return true;
            }
        }
        return false;
    }

    public PlayerActor getPlayer(int index) {
        return party.get(index);
    }

    public int getPartySize() {
        return party.size();
    }

    public List<PlayerActor> getParty() {
        return this.party;
    }

    public int getCurrency() {
        return currency;
    }

    public List<Item> getInventory(){
        return inventory;
    }

}
