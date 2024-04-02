package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class Cupboard {
    HashMap<Integer, Need> cupboard;
    /**
     * Creates a new cupboard (should only be 1)
     */
    public Cupboard(){
        cupboard = new HashMap<Integer, Need>();
    }
    public boolean alterNeed(String name, String nType, float cost, int quantity, Animal animal){
        if(getSpecificNeed(name).equals(null)){
            return false;
        }
        else{
            Need oldNeed = getSpecificNeed(name);
            oldNeed.setType(nType);
            oldNeed.setCost(cost);
            oldNeed.setQuantiy(quantity);
            oldNeed.setAnimal(animal);
            return true;
        }
    }
    /**
     * Add need to cupboard
     * @param need need to add to cupboard
     */
    public void addNeed(Need need){
        cupboard.put(need.hashCode(), need);
    }
    /**
     * Delete need from cupboard
     * @return True if a need was deleted
     */
    public boolean deleteNeed(Need need){
        Need removed = cupboard.remove(need.hashCode());
        return removed != null ? true : false;
    }
    /**
     * Get need by name
     * @param name
     * @return Need with matching name
     */
    public Need getSpecificNeed(String name){
        return cupboard.get(name.hashCode());
    }
    /**
     * Get every need in the cupboard
     * @return list of needs
     */
    public List<Need> getCupboard(){
        return new ArrayList<Need>(cupboard.values());
    }
    /**
     * Gets every need with a name containing the substring
     * @param subString the substring to search with
     * @return list of needs
     */
    public List<Need> getSubNeeds(String subString){
        List<Need> allNeeds = getCupboard();
        List<Need> specificNeeds = new ArrayList<Need>();
        for (Need need : allNeeds) {
            if(need.getName().contains(subString)){
                specificNeeds.add(need);
            }
        }
        return specificNeeds;
    }
}
