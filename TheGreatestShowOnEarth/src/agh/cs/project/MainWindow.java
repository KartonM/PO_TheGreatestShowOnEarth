package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener {


    public JFrame mainFrame;
    public JPanel mapPanel;
    private WorldMap map;

    Timer timer=new Timer(20, this);

    public MainWindow(WorldMap map) {
        setUpMainFrame();
        this.map = map;
        mapPanel = new SteppeJungleMapPanel((SteppeJungleMap)map,
                                            new Point(100, 50),
                                            new Dimension(800, 800));
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
        System.out.println(map.getAnimals().size());
        map.passDay();
        mapPanel.repaint();
    }
}
