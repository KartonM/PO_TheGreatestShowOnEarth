package agh.cs.project;

import java.util.*;

public abstract class WorldMap {

    protected Flora flora;
    protected Fauna fauna;

    public final Vector2d lowerLeftBound = new Vector2d(0,0);
    public final Vector2d upperRightBound;// = new Vector2d(99, 99);
    public final int animalStartEnergy;
    public final int animalMoveEnergy;
    //public final int plantEnergy;

    public WorldMap(int width, int height, int animalStartEnergy, int animalMoveEnergy, int plantEnergy) {
        this.upperRightBound = new Vector2d(width - 1, height - 1);
        this.animalStartEnergy = animalStartEnergy;
        this.animalMoveEnergy = animalMoveEnergy;
        //this.plantEnergy = plantEnergy;

        flora = new Flora(lowerLeftBound, upperRightBound, plantEnergy);
        fauna = new Fauna();

        //spawnRandomAnimals(160);
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

    public void spawnRandomAnimals(int count) {
        for(int i = 0; i < count; i++) {
            //TODO make sure that animal doesn't spawn on occupied position
            var animal = new Animal(this, Vector2d.getRandomVectorInRange(lowerLeftBound, upperRightBound));
            fauna.addAnimal(animal);
        }
    }
}
