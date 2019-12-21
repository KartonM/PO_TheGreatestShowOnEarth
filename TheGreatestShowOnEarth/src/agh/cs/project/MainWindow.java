package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener {
    public JFrame mainFrame;
    public JFrame statsFrame;
    public JPanel mapPanel;
    public PauseAndHighlightGenesButtonPanel statsPanel;
    //public JPanel pauseButtonPanel;
    private WorldMap map;
    private boolean simulationPaused = false;

    Timer timer;

    public MainWindow(WorldMap map, int animationFrameDuration) {
        this.map = map;
        setUpMainFrame();


        this.timer = new Timer(animationFrameDuration, this);
        mapPanel = new SteppeJungleMapPanel((SteppeJungleMap)map,
                                            new Point(100, 100),
                                           800);
        mainFrame.add(mapPanel);
        //statsPanel.render();
        timer.start();
        mapPanel.repaint();


    }

    public void toggleSimulation() {
        simulationPaused = !simulationPaused;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //System.out.println(map.getAnimals().size());
        if(!simulationPaused) {
            map.passDay();
            mapPanel.repaint();
            logStats();
        }
    }

    private void setUpMainFrame() {
        mainFrame = new JFrame("The Greatest Show On Earth");
        mainFrame.setSize(1000, 1000);
        statsPanel = new PauseAndHighlightGenesButtonPanel(this, new Point(200, 20), new Dimension(100, 30));
        mainFrame.add(statsPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

//        statsFrame = new JFrame("Stats");
//        statsFrame.setSize(200, 500);
//        statsFrame.add(statsPanel);
//        statsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        statsFrame.setVisible(true);

    }

    private void logStats() {
        System.out.println("Animals count: " + Integer.toString(map.getMapStatistics().animalsCount()));
        System.out.println("Plants count: " + Integer.toString(map.getMapStatistics().plantsCount()));
        System.out.println("Most common genes: " + map.getMapStatistics().mostCommonGenes().toString());
        System.out.println("Average days lived: " + Double.toString(map.getMapStatistics().averageDaysLived()));
        System.out.println("Average children count: " + Double.toString(map.getMapStatistics().averageChildrenCount()));
        System.out.println("\n\n\n");
    }
}
