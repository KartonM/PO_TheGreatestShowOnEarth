package agh.cs.project;

import java.util.*;
import java.util.stream.Collectors;

public abstract class WorldMap {

    Flora flora;
    Fauna fauna;

    public Vector2d lowerLeftBound = new Vector2d(0,0);
    public Vector2d upperRightBound = new Vector2d(9, 9);

    public WorldMap() {
        flora = new Flora(lowerLeftBound, upperRightBound, 50);
        fauna = new Fauna();
        spawnRandomAnimals(6);
    }

    public void passDay() {
        fauna.getAnimals().forEach(a -> a.move());
        growPlants();
    }

    public Collection<Vector2d> getPlantPositions() {
        return flora.getPlantPositions();
    }
    public Collection<Animal> getAnimals() { return fauna.getAnimals(); }

    public int width() {
        return upperRightBound.x - lowerLeftBound.x + 1;
    }

    public int height() {
        return upperRightBound.y - lowerLeftBound.y + 1;
    }

    protected abstract void growPlants();

    protected void spawnRandomAnimals(int count) {
        for(int i = 0; i < count; i++) {
            var animal = new Animal(this, Vector2d.getRandomVectorInRange(lowerLeftBound, upperRightBound));
            fauna.addAnimal(animal);
        }
    }
}
