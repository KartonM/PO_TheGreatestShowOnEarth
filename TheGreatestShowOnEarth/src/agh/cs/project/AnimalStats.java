package agh.cs.project;

public class AnimalStats {
    public Animal owner;
    public int children = 0;
    public int descendants = 0;
    public int daysOfObservation = 0;
    public boolean hasDied = false;
    public AnimalStats(Animal owner) {
        this.owner = owner;
    }
}
