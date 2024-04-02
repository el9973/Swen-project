package com.ufund.api.ufundapi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.basket.Basket;

@Component
public class ShelterFileDAO implements ShelterDAO {
    private static final Logger LOG = Logger.getLogger(ShelterFileDAO.class.getName());
    private Map<Integer, Need> needs;
    private ObjectMapper mapper;
    private static int nextID;
    private String filename;
    private Map<String, Basket> baskets;


    public ShelterFileDAO(@Value("${shelter.file}") String filename, ObjectMapper mapper) throws IOException {
        this.filename = filename;
        this.mapper = mapper;
        this.baskets = new HashMap<String,Basket>();

        load();
    }


    public Need getNeed(int id) {
        synchronized(needs) {
            if (needs.containsKey(id))
                return needs.get(id);
            else
                return null;
        }
    }

    @Override
    public boolean deleteNeed(int id) throws IOException {
        synchronized(needs) {
            if (needs.containsKey(id)) {
                needs.remove(id);
                return save();
            }
            else
                return false;
        }
    }

    private boolean load() throws IOException {
        needs = new TreeMap<>();
        nextID = 0;

        // Deserializes the JSON objects from the file into an array of heroes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Need[] needArray = mapper.readValue(new File(filename),Need[].class);

        // Add each hero to the tree map and keep track of the greatest id
        for (Need n : needArray) {
            needs.put(n.getId(), n);
            if (n.getId() > nextID)
                nextID = n.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextID;
        return true;
    }

    private boolean save() throws IOException {
        Need[] needArray = getNeeds();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        mapper.writeValue(new File(filename), needArray);
        return true;
    }

    @Override
    public Need[] getNeeds() throws IOException {
        synchronized (needs) {
            return getNeedsArray(null);
        }
    }

    private Need[] getNeedsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Need> needsList = new ArrayList<>();
        for (Need n : needs.values()) {
            if (containsText == null || n.getName().contains(containsText)) {
                needsList.add(n);
            }
        }
        Need[] needArray = new Need[needsList.size()];
        needsList.toArray(needArray);
        return needArray;
    }

    @Override
    public Need[] findNeeds(String text) throws IOException {
       return getNeedsArray(text);
    }

    private synchronized static int nextID() {
        int id = nextID;
        ++nextID;
        return id;
    }

    @Override
    public Need createNeed(Need n) throws IOException {
        synchronized (needs) {
            Need newNeed = new Need(nextID(), n.getName(), n.getType(), n.getCost(), n.getQuantity(), n.getAnimal());
            needs.put(newNeed.getId(), newNeed);
            save();
            return newNeed;
        }
    }

    @Override
    public Need updateNeed(Need n) throws IOException {
        synchronized(needs) {
            if (n == null) {
                return null;
            }
            if (needs.containsKey(n.getId()) == false)
                return null;  // hero does not exist

            needs.put(n.getId(), n);
            save(); // may throw an IOException
            return n;
        }
    }

    @Override
    public boolean deleteNeed(Need n) throws IOException {
        synchronized (needs) {
            if (needs.containsKey(n.getId())) {
                needs.remove(n.getId());
                return save();
            }
            else
                return false;
        }
    }
    /**
     * Adds a need to a user's basket
     */
    @Override
    public boolean addNeedtoBasket(int id, String user) throws IOException {
        synchronized (needs) {
            if(baskets.get(user) == null){
                baskets.put(user, new Basket());
                save();
            }
            if(baskets.get(user).searchId(id) == true){
                return false;
            }
            if(needs.containsKey(id)){
                baskets.get(user).add(needs.get(id));
                return save();
            }
            return false;
        }
    }
    /**
     * remove a need from a user's basket
     */
    @Override
    public boolean removeNeedfromBasket(int id, String user) throws IOException {
        synchronized (needs) {
            if(baskets.get(user) == null){
                return false;
            }
            if(baskets.get(user).searchId(id) == true){
                return false;
            }
            if(baskets.get(user).searchId(id) || needs.containsKey(id)){
                baskets.get(user).remove(needs.get(id));
                return save();
            }
            return false;
        }
    }
    /**
     *Checkout the needs
     */
    @Override
    public boolean checkout(String user) throws IOException {
        synchronized (needs) {
            if(baskets.get(user) == null){
                return false;
            }
            baskets.get(user).checkout();
            return save();
        }
    }
    /**
     * gets a need from a user's basket
     */
    @Override
    public boolean getNeedfromBasket(String user, int id) throws IOException {
        synchronized (needs) {
            if(baskets.get(user) == null){
                baskets.put(user, new Basket());
                save();
            }
            return baskets.get(user).searchId(id);
        }
    }
    /**
     * gets every need from the users basket
     */
    @Override
    public Need[] getBasket(String user) throws IOException {
        synchronized (needs) {
            if(baskets.get(user) == null){
                baskets.put(user, new Basket());
                save();
            }
            return (Need[]) baskets.get(user).getNeeds().toArray();
        }
    }

        @Override
        public Need[] viewCupboard(String user) {
            if (user.equals("admin")) {
                Need[] contents = new Need[needs.values().size()];
                needs.values().toArray(contents);
                return contents;
            }
            return null;
    }
}
