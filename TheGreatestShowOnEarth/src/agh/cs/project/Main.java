package agh.cs.project;

public class Main {
    public static void main(String[] args) {
        var worldParameters = new JsonWorldParametersParser().parse("parameters.json");
        System.out.println(worldParameters.toJSONString());
        for(int i = 0; i < Integer.parseInt(worldParameters.get("simulations").toString()); i++) {
            var map = new SteppeJungleMap(
                    Integer.parseInt(worldParameters.get("width").toString()),
                    Integer.parseInt(worldParameters.get("height").toString()),
                    Integer.parseInt(worldParameters.get("startEnergy").toString()),
                    Integer.parseInt(worldParameters.get("moveEnergy").toString()),
                    Integer.parseInt(worldParameters.get("plantEnergy").toString()),
                    Float.parseFloat(worldParameters.get("jungleRatio").toString())
            );
            for(int j = 0; j < Integer.parseInt(worldParameters.get("initialPlants").toString()); j++) {
                map.growPlants();
            }
            map.spawnRandomAnimals(Integer.parseInt(worldParameters.get("spawnAnimals").toString()));
            new MainWindow(map, Integer.parseInt(worldParameters.get("animationFrameDuration").toString()), i);
        }
    }
}
