package agh.cs.project;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Animal animal);
}
