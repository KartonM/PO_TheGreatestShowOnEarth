package agh.cs.project;

import java.util.*;
import java.util.stream.Collectors;

public class Fauna {
    private Map<Vector2d, List<Animal>> animals;

    public Fauna() {
        this.animals = new HashMap<Vector2d, List<Animal>>();
    }

    public void addAnimal(Animal animal) {
        addAnimalToMemory(animal);
    }

    public void buryDeadAnimals() {
        getAnimals().stream()
                    .filter(a -> a.energy <= 0)
                    .forEach(a -> removeAnimalFromMemory(a));
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

    public void moveAnimals() {
        for(var animal : getAnimals()) {
            removeAnimalFromMemory(animal);
            animal.move();
            addAnimalToMemory(animal);
        }
    }

    private void addAnimalToMemory(Animal animal) {
        var animalPosition = animal.getPosition();
        if(animals.containsKey(animalPosition)) {
            animals.get(animalPosition).add(animal);
        } else {
            animals.put(animalPosition, new ArrayList<Animal>(List.of(animal)));
        }
    }

    private void removeAnimalFromMemory(Vector2d position, Animal animal) {
        var animalsOnPosition = animals.get(position);
        animalsOnPosition.remove(animal);
        if(animalsOnPosition.isEmpty()) {
            animals.remove(position);
        }
    }

    private void removeAnimalFromMemory(Animal animal) {
        removeAnimalFromMemory(animal.getPosition(), animal);
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
