package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends JPanel {
    protected Point location;
    protected Dimension panelSize;
    protected Dimension cellSize;
    protected WorldMap map;

    private boolean highlightAnimalsWithMostCommonGenes = false;

    public MapPanel(WorldMap map, Point location, int size) {
        this.map = map;
        this.location = location;
        this.cellSize = new Dimension(size/Math.max(map.width(), map.height()),
                            size/Math.max(map.width(), map.height()));

        this.panelSize = new Dimension(cellSize.width*map.width(), cellSize.height*map.height());

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                map.addStatsToAnimalOnPosition(coordsToPositionOnMap(me.getX(), me.getY()));
            }
        });
    }
    public void toggleCommonGeneHighlight() {
        highlightAnimalsWithMostCommonGenes = !highlightAnimalsWithMostCommonGenes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(panelSize);
        this.setLocation(location);

        drawMap(g);

        //draw plants
        g.setColor(Color.GREEN);
        for(var plantPosition : map.getPlantPositions()) {
            Point plantCellCorner = positionOnMapToPoint(plantPosition);
            g.fillRect(plantCellCorner.x, plantCellCorner.y, cellSize.width, cellSize.height);
        }

        //draw animals
        g.setColor(Color.RED);
        var mostCommonGenes = highlightAnimalsWithMostCommonGenes ?
                (RotationGenes) map.getMapStatistics().mostCommonGenes() :
                null;
        for(var animal : map.getDominantAnimals()) {
            g.setColor(animalRepresentationColor(animal));
            if(animal.getRotationGenes().equals(mostCommonGenes)) {
                g.setColor(new Color(0, 255, 255));
            }
            if(animal.stats != null && animal.stats.owner == animal) {
                g.setColor(Color.PINK);
            }

            Point plantCellCorner = positionOnMapToPoint(animal.getPosition());
            g.fillOval(plantCellCorner.x, plantCellCorner.y, cellSize.width, cellSize.height);
        }
    }

    protected Point positionOnMapToPoint(Vector2d position) {
        var rtn = new Point((position.x ) * cellSize.width,
                panelSize.height - (position.y + 1)*cellSize.height);

        return rtn;
    }

    protected Vector2d coordsToPositionOnMap(int x, int y) {
        return new Vector2d(x/cellSize.width,(panelSize.height - y)/cellSize.height);
    }

    protected void drawMap(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.panelSize.width, this.panelSize.height);
    }

    private Color animalRepresentationColor(Animal animal) {
        int green = (animal.getEnergy() * 255)/(map.animalStartEnergy*4);
        if(green > 255) green = 255;
        return new Color(255, 255 - green, 0);
    }
}
