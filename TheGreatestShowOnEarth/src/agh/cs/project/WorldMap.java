package agh.cs.project;

import java.util.*;
import java.util.stream.Collectors;

public abstract class WorldMap {

    Flora flora;

    public Vector2d lowerLeftBound = new Vector2d(0,0);
    public Vector2d upperRightBound = new Vector2d(19, 19);

    public WorldMap() {
        flora = new Flora(lowerLeftBound, upperRightBound, 50);
    }

    public Collection<Vector2d> getPlantPositions() {
        return flora.getPlantPositions();
    }

    public abstract void growPlants();

}
