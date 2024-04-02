package com.ufund.api.ufundapi.model.users;
import com.ufund.api.ufundapi.model.Cupboard;
import com.ufund.api.ufundapi.model.Need;

import java.util.List;

import com.ufund.api.ufundapi.model.basket.Basket;
public class Helper extends User {
    Cupboard cupBoard = new Cupboard();
    Basket basket = new Basket();
    public Helper(String userName) {
        super(userName);
    }

    //add methods to access cupboard
    public List<Need> seeCupBoard(){
        return cupBoard.getCupboard(); //return the list of needs from the cupboard to the user
    }
    //helper functionality
    public void addNeedToBasket(Need need){
        basket.add(need);
    }
    public void deleteNeedFromBasket(Need need){
        basket.remove(need);
    }
    public void checkout(){
        basket.checkout();
    }
    


}
