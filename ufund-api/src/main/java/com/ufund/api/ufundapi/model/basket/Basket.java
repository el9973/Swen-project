package com.ufund.api.ufundapi.model.basket;

import java.util.ArrayList;
import java.util.Collection;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Cupboard;

public class Basket {
    
    private ArrayList<Need> needs;
    private Cupboard cupboard;
    /**
     * Creates a new basket
     */
    public Basket() {
        this.cupboard = new Cupboard();
        this.needs = new ArrayList<>();
    }
    /**
     * Creates a basket with specific needs
     * @param needs List of needs to add
     */
    public Basket(Collection<Need> needs) {
        this.needs = new ArrayList<>(needs);
    }
    /**
     * Gets all the needs of the basket
     * @return List of needs
     */
    public ArrayList<Need> getNeeds() {
        return this.needs;
    }
    /**
     * 
     * @return how many needs are in the basket
     */
    public int size() {
        return this.needs.size();
    }
    /**
     * Adds need to basket
     * @param n need to add
     */
    public void add(Need n) {
        this.needs.add(n);
    }
    /**
     * Removes need from basket
     * @param n the need to remove
     * @return true if there is a need to be removed
     */
    public boolean remove(Need n) {
        if (this.needs.contains(n)) {
            return this.needs.remove(n);
        }
        return false;
    }
    /**
     * search for a need
     * @param n Need to search for
     * @return true if the need exists
     */
    public boolean search(Need need) {
        for (Need n : needs) {
            if (n != null && need != null && n.getId() == need.getId()) {
                return true;
            }
        }
        return false;    }
    /**
     * Search for a need with the id
     * @param id id to look for
     * @return true if id exists
     */
    public boolean searchId(int id){
        for(int i = 0; i < needs.size(); i++){
            if(needs.get(i).getId() == id){
                return true;
            }
        }
        return false;
    }
    /**
     * Calculates the total cost of the basket
     * @return the total cost of all needs in the basket
     */
    public double totalCost() {
        double total = 0;
        for (Need n : this.needs) {
            total += n.getCost();
        }
        return total;
    }
    /**
     * To string
     */
    @Override
    public String toString() {
        return "Cost: $" + this.totalCost() +
        "\n" + this.needs.toString();
    }
    /**
     * Checks out the user and makes a new basket
     */
    public void checkout() {
        for(Need n: needs){
            Need oldNeed = cupboard.getSpecificNeed(n.getName());
            if (oldNeed != null) {
                cupboard.alterNeed(n.getName(), n.getType(), n.getCost(), n.getQuantity()+oldNeed.getQuantity(), n.getAnimal());
            }
        }
        this.needs.clear();
    }


}
