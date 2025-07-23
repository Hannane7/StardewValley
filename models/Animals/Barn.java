package com.untildawn.models.Animals;

import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Position;


import java.util.ArrayList;

public class Barn extends ItemInstance {
    private ArrayList<Animal> animals;
    private Position position;

    public Barn(ItemDefinition definition, Position position) {
        super(definition);
        this.position = position;
        animals = new ArrayList<>();
    }
    public int getX(){
        return (int) position.getX();
    }
    public int getY(){
        return (int) position.getY();
    }
    public Position getPosition(){
        return position;
    }
    public void setAnimal(Animal animal){
        animals.add(animal);
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }
}
