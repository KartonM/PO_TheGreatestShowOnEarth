package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener {
    public JFrame mainFrame;
    public JPanel mapPanel;
    private WorldMap map;

    Timer timer;

    public MainWindow(WorldMap map, int animationFrameDuration) {
        setUpMainFrame();
        this.map = map;
        this.timer = new Timer(animationFrameDuration, this);
        mapPanel = new SteppeJungleMapPanel((SteppeJungleMap)map,
                                            new Point(100, 50),
                                           800);
        mainFrame.add(mapPanel);
        timer.start();
        mapPanel.repaint();
    }

    private void setUpMainFrame() {
        mainFrame = new JFrame("The Greatest Show On Earth");
        mainFrame.setSize(1000, 1000);
        //mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private void addPanel(JPanel panel) {
        mainFrame.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //System.out.println(map.getAnimals().size());
        map.passDay();
        mapPanel.repaint();
    }
}
