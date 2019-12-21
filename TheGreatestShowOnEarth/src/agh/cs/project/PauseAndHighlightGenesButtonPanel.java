package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseAndHighlightGenesButtonPanel extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private JButton pauseBtn;
    private JButton highlightGenesButton;



    public PauseAndHighlightGenesButtonPanel(MainWindow window, Point location, Dimension size) {
        this.mainWindow = window;
        pauseBtn = new JButton("Resume/Pause");
        pauseBtn.addActionListener(this);

        highlightGenesButton = new JButton("Highlight most common genes");
        highlightGenesButton.addActionListener(this);


        setLayout(new FlowLayout());
        setPreferredSize(size);
        add(highlightGenesButton);
        add(pauseBtn);
        setLocation(location);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        var source = actionEvent.getSource();
        if(source == pauseBtn) {
            mainWindow.toggleSimulation();
        }
    }
}
