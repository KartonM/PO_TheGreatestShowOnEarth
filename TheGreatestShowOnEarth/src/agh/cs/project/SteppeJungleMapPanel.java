package agh.cs.project;

import java.awt.*;

public class SteppeJungleMapPanel extends MapPanel{
    private Vector2d jungleLowerLeftBound;
    private Vector2d jungleUpperRightBound;
    public SteppeJungleMapPanel(SteppeJungleMap map, Point location, Dimension size) {
        super(map, location, size);
        this.jungleLowerLeftBound = map.jungleLowerLeftBound;
        this.jungleUpperRightBound = map.jungleUpperRightBound;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    protected void drawMap(Graphics g) {
        super.drawMap(g);
        g.setColor(Color.GREEN);

        var junglePosition = positionOnMapToPoint(new Vector2d(this.jungleLowerLeftBound.x, this.jungleUpperRightBound.y));
        var jungleWidth = (this.jungleUpperRightBound.x - this.jungleLowerLeftBound.x + 1) * cellSize.width;
        var jungleHeight = (this.jungleUpperRightBound.y - this.jungleLowerLeftBound.y + 1) * cellSize.height;

        g.fillRect(junglePosition.x, junglePosition.y, jungleWidth, jungleHeight);
    }
}
