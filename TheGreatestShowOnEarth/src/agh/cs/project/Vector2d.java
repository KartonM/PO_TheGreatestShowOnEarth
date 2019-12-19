package agh.cs.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean precedes(Vector2d other) {
        return other.x >= this.x && other.y >= this.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite() {
        return new Vector2d(this.x*-1, this.y*-1);
    }

    public static Vector2d getRandomVectorInRange(Vector2d lowerLeftBound, Vector2d upperRightBound) {
        Random random = new Random();
        return new Vector2d(random.nextInt((upperRightBound.x - lowerLeftBound.x)) + lowerLeftBound.x,
                            random.nextInt((upperRightBound.y - lowerLeftBound.y)) + lowerLeftBound.y);
    }

    public static Collection<Vector2d> allVectorsInRange(Vector2d lowerLeftBound, Vector2d upperRightBound) {
        var vectors = new ArrayList<Vector2d>();

        for(int i = lowerLeftBound.x; i <= upperRightBound.x; i++) {
            for(int j = lowerLeftBound.y; j <= upperRightBound.y; j++) {
                vectors.add(new Vector2d(i, j));
            }
        }
        return vectors;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}