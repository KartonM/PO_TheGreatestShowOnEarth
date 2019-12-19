package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;

public class SteppeJungleMap extends WorldMap {
    public Vector2d steppeLowerLeftBound = new Vector2d(0,0);
    public Vector2d steppeUpperRightBound = new Vector2d(10,10);

    public Vector2d jungleLowerLeftBound = new Vector2d(9,9);
    public Vector2d jungleUpperRightBound = new Vector2d(16,16);



    public SteppeJungleMap() {
        super();
    }

    @Override
    public void growPlants() {
        flora.growPlant(emptyJunglePositions());
        flora.growPlant(emptySteppePositions());
    }

    private Collection<Vector2d> emptyJunglePositions() {
        return Vector2d.allVectorsInRange(jungleLowerLeftBound, jungleUpperRightBound);
    }

    private Collection<Vector2d> emptySteppePositions() {
        var allMapPositions = Vector2d.allVectorsInRange(lowerLeftBound, upperRightBound);
        allMapPositions.removeAll(Vector2d.allVectorsInRange(jungleLowerLeftBound, jungleUpperRightBound));
        return allMapPositions;
    }
}
