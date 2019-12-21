package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener {
    public JFrame mainFrame;
    public MapPanel mapPanel;
    public PauseAndHighlightGenesButtonPanel statsPanel;
    private WorldMap map;
    private boolean simulationPaused = false;
    private int simulationNum;

    Timer timer;

    public MainWindow(WorldMap map, int animationFrameDuration, int simulationNum) {
        this.map = map;
        this.simulationNum = simulationNum;
        setUpMainFrame();

        this.timer = new Timer(animationFrameDuration, this);
        mapPanel = new SteppeJungleMapPanel((SteppeJungleMap)map,
                                            new Point(100, 100),
                                           800);
        mainFrame.add(mapPanel);
        timer.start();
        mapPanel.repaint();
    }

    public void toggleSimulation() {
        simulationPaused = !simulationPaused;
    }
    public void toggleMostCommonGenesHighlight() {mapPanel.toggleCommonGeneHighlight();}

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(!simulationPaused) {
            map.passDay();
            logMapStats();
            if(map.chosenAnimalsStats != null) {
                logChosenAnimalsStats();
            }
        }
        mapPanel.repaint();
    }

    private void setUpMainFrame() {
        mainFrame = new JFrame("The Greatest Show On Earth - " + simulationNum);
        mainFrame.setSize(1000, 1000);
        statsPanel = new PauseAndHighlightGenesButtonPanel(this, new Point(200, 20), new Dimension(100, 30));
        mainFrame.add(statsPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private void logMapStats() {
        System.out.println("\n\n\n");
        System.out.println("Simulation " + simulationNum);
        System.out.println("Animals count: " + map.getMapStatistics().animalsCount());
        System.out.println("Plants count: " + map.getMapStatistics().plantsCount());
        System.out.println("Most common genes: " + map.getMapStatistics().mostCommonGenes().toString());
        System.out.println("Average days lived: " + map.getMapStatistics().averageDaysLived());
        System.out.println("Average children count: " + map.getMapStatistics().averageChildrenCount());
    }

    private void logChosenAnimalsStats() {
        var s = map.getChosenAnimalsStats();
        System.out.print("\n");
        if(s.hasDied) {
            System.out.println("Chosen animal died after " + s.daysOfObservation + " days of observation :(");
        } else {
            System.out.println("Chosen animal has been observed for " + s.daysOfObservation + " days");
            System.out.println("Chosen animal has " + s.children + " children");
            System.out.println("Chosen animal has " + s.descendants + " descendants");
        }
    }
}
