package com.ufund.api.ufundapi.model;

public class Need {
    private int id;
    private String name;
    private String type;
    private float cost;
    private int quantity;
    private Animal animal;

    /**
     * Make a new need
     * @param name name of need
     * @param type type of need
     * @param cost cost (should be rounded to nearest 2 decimal places)
     * @param quantity amount of need
     * @param animal the animal the helper will be helping with
     */
    public Need(int id, String name, String type, float cost, int quantity
    ,Animal animal){
        this.id = id;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.quantity = quantity;
        this.animal = animal;
    }

    public Need() {
        this.name = null;
        this.type = null;
        this.animal = new Animal();
    }
    @Override
    public int hashCode(){
        return name.hashCode();
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass()!= this.getClass()) 
            return false;
        Need other = (Need) obj;
        return other.name.equals(this.name);
    }
    /**
     * @return name
     */
    public String getName(){
        return name;
    }

    public int getId() {
        return this.id;
    }

    /**
     * @return type
     */
    public String getType(){
        return type;
    }
    /**
     * @return cost
     */
    public float getCost(){
        return cost;
    }
    /**
     * @return quantity
     */
    public int getQuantity(){
        return quantity;
    }
    /**
     * @return animal
     */
    public Animal getAnimal(){
        return animal; 
    }
    /**
     * @param amount set quantity to amount
     */
    public void setQuantiy(int amount){
        quantity = amount;
    }
    /**
     * @param amount set cost to amount
     */
    public void setCost(float amount){
        cost = amount;
    }
    /**
     * @param amount set type to new type
     */
    public void setType(String newType){
        type = newType;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setAnimal(Animal a) {
        this.animal = a;
    }
}
