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
    List<PlayerActor> party = new ArrayList<>();
    List<Item> inventory;

    public PlayerParty(PlayerActor char1, PlayerActor char2, PlayerActor char3, int currency){
        this.char1 = char1;
        this.char2 = char2;
        this.char3 = char3;
        this.currency = currency;
        inventory = new ArrayList<>();
        setParty();
    }

    public void setParty() {
        party.add(this.char1);
        party.add(this.char2);
        party.add(this.char3);
    }

    public List<PlayerActor> getParty() {
        return this.party;
    }

    public int highestLevel() {
        int max = 0;
        for(BattleActor p : getParty()) {
            if(p.getLevel() > max) {
                max = p.getLevel();
            }
        }
        return max;
    }

    public PlayerActor getPlayer(int index) {
        return party.get(index);
    }

    public int getPartySize() {
        return party.size();
    }

    public int getCurrency() { return currency; }

    public void addCurrency(int i) {currency += i; }

    public void subCurrency(int i){
        currency -= i;
        if(currency < 0){
            currency = 0;
        }
    }

    public List<Item> getInventory(){
        return inventory;
    }

    public void addItem(Item i){
        inventory.add(i);
    }

    public boolean removeItem(Item i){
        if(getInventory().contains(i)){
            getInventory().remove(i);
            return true;
        } else {
            return false;
        }
    }

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

    public boolean isEveryPlayerDead() {
        for (PlayerActor p : party) {
            if (!p.is_dead()) {
                return false;
            }
        }
        return true;
    }

    public boolean canPartyStillAttack() {
        for(PlayerActor p : party) {
            if(!p.playerHasAttacked()) {
                return true;
            }
        }
        return false;
    }


}
