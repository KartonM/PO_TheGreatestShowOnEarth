package agh.cs.project;

import java.util.*;

public abstract class WorldMap {

    protected Flora flora;
    protected Fauna fauna;
    protected MapStatistics stats;
    protected AnimalStats chosenAnimalsStats = null;

    public final Vector2d lowerLeftBound = new Vector2d(0,0);
    public final Vector2d upperRightBound;
    public final int animalStartEnergy;
    public final int animalMoveEnergy;


    public WorldMap(int width, int height, int animalStartEnergy, int animalMoveEnergy, int plantEnergy) {
        this.upperRightBound = new Vector2d(width - 1, height - 1);
        this.animalStartEnergy = animalStartEnergy;
        this.animalMoveEnergy = animalMoveEnergy;


        this.stats = new MapStatistics(this);

        flora = new Flora(lowerLeftBound, upperRightBound, plantEnergy);
        fauna = new Fauna();
        fauna.registerAnimalDeathObserver(this.stats);
    }

    public void passDay() {
        fauna.buryDeadAnimals();
        var animals = fauna.getAnimals();

        fauna.move();
        fauna.eat(flora);
        fauna.copulate();
        growPlants();
    }

    public void addStatsToAnimalOnPosition(Vector2d position) {
        for(var animal : getDominantAnimals()) {
            if(animal.getPosition().equals(position)) {
                getAnimals().forEach(a -> a.stats = null);
                animal.stats = new AnimalStats(animal);
                chosenAnimalsStats = animal.stats;
                return;
            }
        }
    }

    public Collection<Vector2d> getPlantPositions() {
        return flora.getPlantPositions();
    }
    public Collection<Animal> getAnimals() { return fauna.getAnimals(); }
    public Collection<Animal> getDominantAnimals() { return fauna.getDominantAnimals(); }
    public MapStatistics getMapStatistics() {return stats;}
    public AnimalStats getChosenAnimalsStats() {return chosenAnimalsStats;}

    public int width() {
        return upperRightBound.x - lowerLeftBound.x + 1;
    }

    public int height() {
        return upperRightBound.y - lowerLeftBound.y + 1;
    }

    protected abstract void growPlants();

    public void spawnRandomAnimals(int count) {
        for(int i = 0; i < count; i++) {
            var animal = new Animal(this, Vector2d.getRandomVectorInRange(lowerLeftBound, upperRightBound));
            fauna.addAnimal(animal);
        }
    }
}
