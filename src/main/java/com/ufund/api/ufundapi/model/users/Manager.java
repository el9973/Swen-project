package com.ufund.api.ufundapi.model.users;
import com.ufund.api.ufundapi.model.Cupboard;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Animal;

public class Manager extends User {

    Cupboard cupBoard = new Cupboard();
    public Manager() {
        super("admin");
    }
    public void addNeed(Need need){
        cupBoard.addNeed(need);
    }
    public boolean deleteNeed(Need need){
        return cupBoard.deleteNeed(need);
    }
    public boolean alterNeed(String name, String type, float cost, int quantity, Animal animal ){
        return cupBoard.alterNeed(name, type, cost, quantity, animal);
    }

    //add methods to edit cupboard
    //add methods to view and edit calendar
    
}
