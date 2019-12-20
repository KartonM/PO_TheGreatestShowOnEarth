package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;

public class SteppeJungleMap extends WorldMap {

    public Vector2d jungleLowerLeftBound = new Vector2d(33,33);
    public Vector2d jungleUpperRightBound = new Vector2d(66,66);



    public SteppeJungleMap() {
        super();
    }

    @Override
    public void growPlants() {
        flora.growPlant(emptyJunglePositions());
        flora.growPlant(emptySteppePositions());
    }

    private Collection<Vector2d> emptyJunglePositions() {
        var allJunglePositions = Vector2d.allVectorsInRange(jungleLowerLeftBound, jungleUpperRightBound);
        allJunglePositions.removeAll(fauna.getAnimalPositions());
        return allJunglePositions;
    }

    private Collection<Vector2d> emptySteppePositions() {
        var allMapPositions = Vector2d.allVectorsInRange(lowerLeftBound, upperRightBound);
        var allJunglePositions = Vector2d.allVectorsInRange(jungleLowerLeftBound, jungleUpperRightBound);

        allMapPositions.removeAll(allJunglePositions);
        allMapPositions.removeAll(fauna.getAnimalPositions());
        return allMapPositions;
    }
}
