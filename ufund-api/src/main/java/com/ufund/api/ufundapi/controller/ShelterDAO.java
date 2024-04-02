package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Need;

public interface ShelterDAO {
    
    Need[] getNeeds() throws IOException;
    Need[] findNeeds(String text) throws IOException;
    Need createNeed(Need n) throws IOException;
    Need updateNeed(Need n) throws IOException;
    boolean deleteNeed(Need n) throws IOException;
    Need getNeed(int id) throws IOException;
    boolean deleteNeed(int id) throws IOException;
    boolean addNeedtoBasket(int id, String user) throws IOException;
    boolean removeNeedfromBasket(int id, String user) throws IOException;
    boolean checkout(String user) throws IOException;
    boolean getNeedfromBasket(String user, int n) throws IOException;
    Need[] getBasket(String user) throws IOException;
    Need[] viewCupboard(String user) throws IOException;

}
