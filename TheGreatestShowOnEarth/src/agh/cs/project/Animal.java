package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;

public class Animal {
    private static final int POSSIBLE_ROTATIONS_COUNT = 8;

    private Direction facedDirection;
    private Vector2d position;
    private WorldMap map;
    private IGenes rotationGenes;
    private int livedDays = 0;
    private int begottenChildren = 0;
    private int energy;

    public AnimalStats stats = null;

    public Animal(WorldMap map, Vector2d position) {
        this.map = map;
        this.position = position;
        this.facedDirection = Direction.getRandomDirection();
        this.rotationGenes = (new RotationGenes()).crossBreed(new RotationGenes());
        this.energy = map.animalStartEnergy;
    }

    private Animal(WorldMap map, Vector2d position, IGenes rotationGenes, int energy) {
        this.map = map;
        this.position = position;
        this.facedDirection = Direction.getRandomDirection();
        this.rotationGenes = rotationGenes;
        this.energy = energy;
    }

    public Direction getFacedDirection() {
        return facedDirection;
    }

    public Vector2d getPosition() {
        return position;
    }
    public int getLivedDays() {
        return livedDays;
    }
    public int getEnergy() {
        return energy;
    }
    public int getBegottenChildren() {
        return begottenChildren;
    }
    public IGenes getRotationGenes() {return rotationGenes;}

    public void move() {
        rotate();
        position = position.add(facedDirection.toUnitVector());

        //ensure that the animal stays within the map range
        if(!position.precedes(map.upperRightBound) || !position.follows(map.lowerLeftBound)) {
            position = map.lowerLeftBound.add(new Vector2d(
                    (position.x + map.width()) % map.width(),
                    (position.y + map.height()) % map.height()
            ));
        }
        this.energy -= map.animalMoveEnergy;
        this.livedDays++;

        if(stats != null && stats.owner == this) {
            if(energy <= 0) {
                stats.hasDied = true;
            } else {
                stats.daysOfObservation++;
            }
        }
    }

    public void consume(int energy) {
        this.energy += energy;
    }

    public Animal copulate(Animal partner, Vector2d babyPosition) {
        if(this.energy < 0.5*map.animalStartEnergy || partner.energy < 0.5*map.animalStartEnergy) return null;

        var baby = new Animal(this.map, babyPosition, this.rotationGenes.crossBreed(partner.rotationGenes), (int)(this.energy*0.25 + partner.energy*0.25));
        this.energy *= 0.75;
        this.begottenChildren++;

        partner.energy *= 0.75;
        partner.begottenChildren++;

        if(this.stats != null) {
            passStats(this, baby);
        } else if(partner.stats != null) {
            passStats(partner, baby);
        }

        return baby;
    }

    private void rotate() {
        var rotations = mapToNumberOfRotations(rotationGenes.makeDecision());

        for(int i = 0; i < rotations; i++) {
            facedDirection = facedDirection.next();
        }
    }

    private int mapToNumberOfRotations(byte genesDecision) {
        return (genesDecision - Byte.MIN_VALUE) / ((Byte.MAX_VALUE - Byte.MIN_VALUE + 1)/POSSIBLE_ROTATIONS_COUNT);
    }

    private static void passStats(Animal from, Animal to) {
        to.stats = from.stats;
        from.stats.descendants++;
        if(from.stats.owner == from) {
            from.stats.children++;
        }
    }
}
