package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;

public class Animal {
    private static final int POSSIBLE_ROTATIONS_COUNT = 8;

    private Direction facedDirection;
    private Vector2d position;
    private WorldMap map;
    private IGenes rotationGenes;

    public int energy;

    public Animal(WorldMap map, Vector2d position) {
        this.map = map;
        this.position = position;
        this.facedDirection = Direction.getRandomDirection();
        this.rotationGenes = (new RotationGenes()).crossBreed(new RotationGenes());
        this.energy = 50;
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

    public int getEnergy() {
        return energy;
    }

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
        this.energy -= 1;
    }

    public void consume(int energy) {
        this.energy += energy;
    }

    public Animal copulate(Animal partner, Vector2d babyPosition) {
        if(this.energy < 25 || partner.energy < 25) return null;

        var baby = new Animal(this.map, babyPosition, this.rotationGenes.crossBreed(partner.rotationGenes), (int)(this.energy*0.25 + partner.energy*0.25));
        this.energy *= 0.75;
        partner.energy *= 0.75;
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
}
