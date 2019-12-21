package agh.cs.project;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Fauna {
    private Map<Vector2d, List<Animal>> animals;
    private List<IAnimalDeathObserver> animalDeathObservers;

    public Fauna() {
        this.animals = new HashMap<Vector2d, List<Animal>>();
        this.animalDeathObservers = new ArrayList<IAnimalDeathObserver>();
    }

    public void addAnimal(Animal animal) {
        addAnimalToMemory(animal);
    }

    public void buryDeadAnimals() {
        getAnimals().stream()
                    .filter(a -> a.getEnergy() <= 0)
                    .forEach(a -> {
                        notifyAnimalDeathObservers(a);
                        removeAnimalFromMemory(a);
                    });
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

    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    public void move() {
        for(var animal : getAnimals()) {
            removeAnimalFromMemory(animal);
            animal.move();
            addAnimalToMemory(animal);
        }
    }

    public void eat(Flora flora) {
        for(var position : getAnimalPositions()) {
            var plantEnergy = flora.popPlant(position);
            if(plantEnergy > 0) {
                var dominantAnimalsOnPosition = getDominantAnimalsFromCollection(animals.get(position));
                for(var animal : dominantAnimalsOnPosition) {
                    animal.consume(plantEnergy/dominantAnimalsOnPosition.size());
                }
            }
        }
    }

    public void copulate() {
        for(var animalList : new ArrayList<List<Animal>>(animals.values())) {
            copulate(new ArrayList<>(animalList));
        }
    }

    public void notifyAnimalDeathObservers(Animal animal) {
        for(var animalDeathObserver : animalDeathObservers) {
            animalDeathObserver.animalDied(animal);
        }
    }
    public void registerAnimalDeathObserver(IAnimalDeathObserver animalDeathObserver) {
        animalDeathObservers.add(animalDeathObserver);
    }
    public void unregisterAnimalDeathObserver(IAnimalDeathObserver animalDeathObserver) {
        animalDeathObservers.remove(animalDeathObserver);
    }

    private void copulate(List<Animal> animalList) {
        if(animalList.size() < 2) return;
        Animal mum, dad;
        var animalsWithMostEnergy = getDominantAnimalsFromCollection(animalList);

        mum = animalsWithMostEnergy.get(ThreadLocalRandom.current().nextInt(0, animalsWithMostEnergy.size()));

        if(animalsWithMostEnergy.size() > 1) {
            animalsWithMostEnergy.remove(mum);
            dad = animalsWithMostEnergy.get(ThreadLocalRandom.current().nextInt(0, animalsWithMostEnergy.size()));
        } else {
            animalList.remove(mum);
            var animalsWithSecondMostEnergy = getDominantAnimalsFromCollection(animalList);
            dad = animalsWithSecondMostEnergy.get(ThreadLocalRandom.current().nextInt(0, animalsWithSecondMostEnergy.size()));
        }

        var baby = mum.copulate(dad, emptyPositionAround(mum.getPosition()));
        if(baby != null) {
            addAnimalToMemory(baby);
        }
    }

    private Vector2d emptyPositionAround(Vector2d position) {
        var dir = Direction.getRandomDirection();
        for(int i = 0; i < Direction.values().length; i++) {
            var positionToBeChecked = position.add(dir.toUnitVector());
            if(!isOccupied(positionToBeChecked)) {
                return positionToBeChecked;
            }
            dir = dir.next();
        }
        return position.add(dir.toUnitVector());
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

    private List<Animal> getDominantAnimalsFromCollection(Collection<Animal> animals) {
        if(animals == null || animals.isEmpty()) {
            return Collections.<Animal>emptyList();
        }
        var maxEnergy = animals.stream().mapToInt(a -> a.getEnergy()).max();

        return animals.stream()
                      .filter(a -> a.getEnergy() == maxEnergy.getAsInt())
                      .collect(Collectors.toList());
    }
}
