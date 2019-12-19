package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Flora {
    private int[][] plants;
    private Vector2d lowerLeftBound;
    private int width;
    private int height;
    private int minPlantEnergy;
    private int maxPlantEnergy;


    public Flora(Vector2d lowerLeftBound, Vector2d upperRightBound, int plantEnergy) {
        this(lowerLeftBound, upperRightBound, plantEnergy, plantEnergy);
    }

    public Flora(Vector2d lowerLeftBound, Vector2d upperRightBound, int minPlantEnergy, int maxPlantEnergy) {
        this.lowerLeftBound = lowerLeftBound;
        this.width = upperRightBound.x - lowerLeftBound.x + 1;
        this.height = upperRightBound.y - lowerLeftBound.y + 1;

        this.minPlantEnergy = minPlantEnergy;
        this.maxPlantEnergy = maxPlantEnergy;

        plants = new int[width][height];
    }

    public void growPlant(Collection<Vector2d> unoccupiedPositions) {
        var positionsToDrawFrom = unoccupiedPositions
                                    .stream()
                                    .filter(v -> plants[v.x][v.y] <= 0)
                                    .collect(Collectors.toList());

        Optional<Vector2d> drawnPosition = positionsToDrawFrom.stream()
                                    .skip((int) (positionsToDrawFrom.size() * Math.random()))
                                    .findFirst();

        if(drawnPosition.isPresent()) {
            var v = drawnPosition.get();
            plants[v.x][v.y] = ThreadLocalRandom.current().nextInt(minPlantEnergy, maxPlantEnergy + 1);
        }
    }

    public int killPlant(Vector2d position) {
        var relativePosition = position.subtract(lowerLeftBound);
        var x = relativePosition.x;
        var y = relativePosition.y;

        var energy = plants[x][y];
        plants[x][y] = 0;
        return energy;
    }

    public Collection<Vector2d> getPlantPositions() {
        var plantPositions = new ArrayList<Vector2d>();
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++) {
                if(plants[i][j] > 0) {
                    plantPositions.add(lowerLeftBound.add(new Vector2d(i, j)));
                }
            }
        return plantPositions;
    }
}
