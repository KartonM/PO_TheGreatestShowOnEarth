package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;

public class Animal {
    private Direction facedDirection;
    private Vector2d position;
    private WorldMap map;
    private Collection<IPositionChangeObserver> positionChangeObservers;

    public int energy = 50;

    public Animal(WorldMap map, Vector2d position) {
        this.map = map;
        this.position = position;
        this.facedDirection = Direction.getRandomDirection();
        this.positionChangeObservers = new ArrayList<IPositionChangeObserver>();
    }

    public Direction getFacedDirection() {
        return facedDirection;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void move() {
        var oldPosition = position;
        position = position.add(facedDirection.toUnitVector());

        //ensure that the animal stays within the map range
        if(!position.precedes(map.upperRightBound) || !position.follows(map.lowerLeftBound)) {
            position = map.lowerLeftBound.add(new Vector2d(
                    (position.x + map.width()) % map.width(),
                    (position.y + map.height()) % map.height()
            ));
        }

        positionChanged(oldPosition);
    }

    public void addObserver(IPositionChangeObserver observer) {
        positionChangeObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        positionChangeObservers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition) {
        for(IPositionChangeObserver obs : positionChangeObservers) {
            obs.positionChanged(oldPosition, this);
        }
    }
}
