package agh.cs.project;

import java.util.*;

public abstract class WorldMap {

    Flora flora;
    Fauna fauna;

    public Vector2d lowerLeftBound = new Vector2d(0,0);
    public Vector2d upperRightBound = new Vector2d(99, 99);

    public WorldMap() {
        flora = new Flora(lowerLeftBound, upperRightBound, 50);
        fauna = new Fauna();
        spawnRandomAnimals(160);
    }

    public void passDay() {
        fauna.buryDeadAnimals();
        var animals = fauna.getAnimals();

        //fauna.getAnimals().forEach(a -> a.move());
        fauna.move();
        fauna.eat(flora);
        fauna.copulate();
        //fauna.getDominantAnimals().forEach(a -> a.consume(flora.popPlant(a.getPosition())));
        growPlants();
    }

    public Collection<Vector2d> getPlantPositions() {
        return flora.getPlantPositions();
    }
    public Collection<Animal> getAnimals() { return fauna.getAnimals(); }
    public Collection<Animal> getDominantAnimals() { return fauna.getDominantAnimals(); }

    public int width() {
        return upperRightBound.x - lowerLeftBound.x + 1;
    }

    public int height() {
        return upperRightBound.y - lowerLeftBound.y + 1;
    }

    protected abstract void growPlants();

    protected void spawnRandomAnimals(int count) {
        for(int i = 0; i < count; i++) {
            //TODO make sure that animal doesn't spawn on occupied position
            var animal = new Animal(this, Vector2d.getRandomVectorInRange(lowerLeftBound, upperRightBound));
            fauna.addAnimal(animal);
        }
    }
}
