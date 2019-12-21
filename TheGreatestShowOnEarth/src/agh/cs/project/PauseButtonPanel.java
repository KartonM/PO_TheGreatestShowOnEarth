package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseButtonPanel extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private JButton btn;
    public PauseButtonPanel(MainWindow window, Point location, Dimension size) {
        this.mainWindow = window;
        btn = new JButton("Resume/Pause");
        btn.addActionListener(this);
        setLayout(new FlowLayout());
        setPreferredSize(size);
        add(btn);
        setLocation(location);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mainWindow.toggleSimulation();
    }
}
