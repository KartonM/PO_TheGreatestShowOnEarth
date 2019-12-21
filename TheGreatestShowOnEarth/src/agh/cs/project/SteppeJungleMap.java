package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;

public class SteppeJungleMap extends WorldMap {

    public Vector2d jungleLowerLeftBound;
    public Vector2d jungleUpperRightBound;



    public SteppeJungleMap(int width, int height, int animalStartEnergy, int animalMoveEnergy, int plantEnergy, float jungleRatio) {
        super(width, height, animalStartEnergy, animalMoveEnergy, plantEnergy);
        setJungleBounds(jungleRatio);
    }

    @Override
    public void growPlants() {
        flora.growPlant(emptyJunglePositions());
        flora.growPlant(emptySteppePositions());
    }

    private void setJungleBounds(float jungleRatio) {
        int jungleWidth = (int) (this.width() * jungleRatio);
        int jungleHeight = (int) (this.height() * jungleRatio);
        jungleLowerLeftBound = new Vector2d((width()-jungleWidth)/2, (height() - jungleHeight)/2);
        jungleUpperRightBound = jungleLowerLeftBound.add(new Vector2d(jungleWidth - 1, jungleHeight - 1));
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
