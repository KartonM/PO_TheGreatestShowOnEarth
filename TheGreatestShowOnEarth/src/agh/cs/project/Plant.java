package agh.cs.project;

public class Plant implements IMapObject {
    private Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
}
