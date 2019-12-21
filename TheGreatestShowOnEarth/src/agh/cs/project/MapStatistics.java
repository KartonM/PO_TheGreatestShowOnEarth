package agh.cs.project;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class MapStatistics implements IAnimalDeathObserver {
    private WorldMap map;
    private List<Integer> daysLived;

    public MapStatistics(WorldMap map) {
        this.map = map;
        daysLived = new ArrayList<Integer>();
    }

    public int animalsCount() {
        return map.getAnimals().size();
    }

    public int plantsCount() {
        return map.getPlantPositions().size();
    }

    public IGenes mostCommonGenes() {
        var allGenes = map.getAnimals()
                .stream()
                .map(a -> a.getRotationGenes())
                .filter(g -> g instanceof RotationGenes)
                .map(g -> (RotationGenes)g)
                .collect(Collectors.toList());

        var genesCounts = new HashMap<RotationGenes, Integer>();
        for(var genes : allGenes) {
            var count = genesCounts.containsKey(genes) ? genesCounts.get(genes) : 0;
            genesCounts.put(genes, count + 1);
        }
        var maxCount = genesCounts.values().stream().mapToInt(c -> c).max().getAsInt();
        for(var genes : genesCounts.keySet()) {
            if(genesCounts.get(genes) == maxCount)
                return genes;
        }
        return null;
    }

    public double averageDaysLived() {
        var avg = daysLived
                .stream()
                .mapToDouble(a -> a)
                .average();

        return avg.isPresent() ? avg.getAsDouble() : 0;
    }

    public double averageChildrenCount() {
        var avg = map.getAnimals()
                .stream()
                .mapToDouble(a -> a.getBegottenChildren())
                .average();

        return avg.isPresent() ? avg.getAsDouble() : 0;
    }


    @Override
    public void animalDied(Animal animal) {
        daysLived.add(animal.getLivedDays());
    }
}
