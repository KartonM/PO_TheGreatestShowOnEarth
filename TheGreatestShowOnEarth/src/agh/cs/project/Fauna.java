package agh.cs.project;

import java.util.*;
import java.util.stream.Collectors;

public class Fauna implements IPositionChangeObserver {
    Map<Vector2d, List<Animal>> animals;

    public Fauna() {
        this.animals = new HashMap<Vector2d, List<Animal>>();
    }

    public void addAnimal(Animal animal) {
        animal.addObserver(this);
        addAnimalToMemory(animal);
    }

    public void buryDeathAnimals() {
        animals.values().forEach(animalList ->
                animalList.stream().filter(a -> a.energy > 0)
        );
    }

    public Collection<Animal> getAnimals() {
        return animals.values()
                      .stream()
                      .flatMap(Collection::stream)
                      .collect(Collectors.toList());
    }

    public Collection<Vector2d> getAnimalPositions() {
        return animals.keySet();
    }

    public Collection<Animal> getDominantAnimals() {
        return animals.values()
                      .stream()
                      .map(l -> getDominantAnimalsFromCollection(l).iterator().next())
                      .collect(Collectors.toList());
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Animal animal) {
        var animalsOnOldPosition = animals.get(oldPosition);
        animalsOnOldPosition.remove(animal);

        if(animalsOnOldPosition.isEmpty()) {
            animals.remove(oldPosition);
        }

        addAnimalToMemory(animal);
    }

    private void addAnimalToMemory(Animal animal) {
        var animalPosition = animal.getPosition();
        if(animals.containsKey(animalPosition)) {
            animals.get(animalPosition).add(animal);
        } else {
            animals.put(animalPosition, new ArrayList<Animal>(List.of(animal)));
        }
    }
    private Collection<Animal> getDominantAnimalsFromCollection(Collection<Animal> animals) {
        if(animals == null || animals.isEmpty()) {
            return Collections.<Animal>emptyList();
        }
        var maxEnergy = animals.stream().mapToInt(a -> a.energy).max();

        return animals.stream()
                      .filter(a -> a.energy == maxEnergy.getAsInt())
                      .collect(Collectors.toList());
    }
}
