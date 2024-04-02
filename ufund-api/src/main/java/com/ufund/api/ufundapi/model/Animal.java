package com.ufund.api.ufundapi.model;

public class Animal {
    private String name;
    private String specie;
    private String location;

    /**
     * Make a new need
     * @param n name of animal
     * @param s the specie of the animal
     * @param l the location of the animal 
     */
    public Animal(String n, String s, String l){
        name = n;
        specie = s; 
        location = l;
    }

    public Animal() {
        this.name = null;
        this.location = null;
        this.specie = null;
    }

    /**
     * @return name
     */
    public String getName(){
        return name;
    }
    /**
     * @return specie
     */
    public String getSpecie(){
        return specie;
    }
    /**
     * @return location
     */
    public String getLocation(){
        return location;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setLocation(String l) {
        this.location = l;
    }

    public void setSpecies(String s) {
        this.specie = s;
    }


}
