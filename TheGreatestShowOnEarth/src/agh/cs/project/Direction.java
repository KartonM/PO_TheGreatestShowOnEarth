package agh.cs.project;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public Direction next() {
        int thisIntValue = (this.ordinal() + 1) % Direction.values().length;
        return Direction.values()[thisIntValue];
    }

    public Direction previous() {
        int thisIntValue = ((this.ordinal() - 1) + Direction.values().length) % Direction.values().length;
        return Direction.values()[thisIntValue];
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH:
                return new Vector2d(0,1);
            case NORTHEAST:
                return new Vector2d(1,1);
            case EAST:
                return new Vector2d(1,0);
            case SOUTHEAST:
                return new Vector2d(-1,1);
            case SOUTH:
                return new Vector2d(0,-1);
            case SOUTHWEST:
                return new Vector2d(-1,-1);
            case WEST:
                return new Vector2d(-1,0);
            case NORTHWEST:
                return new Vector2d(1,-1);
        }
        return null;
    }

    public static Direction getRandomDirection() {
        var dir = NORTH;
        for(int i = 0; i < ThreadLocalRandom.current().nextInt(0, Direction.values().length); i++) {
            dir = dir.next();
        }
        return dir;
    }
}
