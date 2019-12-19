package agh.cs.project;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    protected Point location;
    protected Dimension panelSize;
    protected Dimension cellSize;
    protected WorldMap map;

    public MapPanel(WorldMap map, Point location, Dimension size) {
        this.map = map;
        this.location = location;
        this.panelSize = size;
        this.cellSize = new Dimension(panelSize.width/(map.upperRightBound.x - map.lowerLeftBound.x + 1),
                            panelSize.height/(map.upperRightBound.y - map.lowerLeftBound.y + 1));
        System.out.println(cellSize.width);
        System.out.println(cellSize.height);
//        cellSize.width -= 1;
//        cellSize.height -= 1;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(panelSize);
        this.setLocation(location);

        drawMap(g);

        g.setColor(Color.BLACK);
        for(var plantPosition : map.getPlantPositions()) {
            Point plantCellCorner = positionOnMapToPoint(plantPosition);
            g.fillRect(plantCellCorner.x, plantCellCorner.y, cellSize.width, cellSize.height);
        }

        g.setColor(Color.RED);
        for(var animal : map.getAnimals()) {
            Point plantCellCorner = positionOnMapToPoint(animal.getPosition());
            g.fillOval(plantCellCorner.x, plantCellCorner.y, cellSize.width, cellSize.height);
        }
    }

    protected Point positionOnMapToPoint(Vector2d position) {
        var rtn = new Point((position.x ) * cellSize.width,
                panelSize.height - (position.y + 1)*cellSize.height);

        return rtn;
    }

    protected void drawMap(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(0,0, this.panelSize.width, this.panelSize.height);
    }
}
