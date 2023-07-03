//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Rectangle;

public class RaceTrack {
    private Rectangle grass = new Rectangle(150, 200, 551, 300);
    private Rectangle outerEdge = new Rectangle(50, 100, 750, 500);
    private Rectangle innerEdge = new Rectangle(150, 200, 551, 300);
    private Rectangle midLaneMarker = new Rectangle(100, 150, 645, 400);
    private Rectangle[] checkpoints = new Rectangle[5];

    public RaceTrack() {
        this.checkpoints[0] = new Rectangle(700, 500, 100, 100);
        this.checkpoints[1] = new Rectangle(700, 100, 100, 100);
        this.checkpoints[2] = new Rectangle(50, 100, 100, 100);
        this.checkpoints[3] = new Rectangle(50, 500, 100, 100);
        this.checkpoints[4] = new Rectangle(450, 500, 100, 100);
    }

    public Rectangle getGrass() {
        return this.grass;
    }

    public Rectangle getOuterEdge() {
        return this.outerEdge;
    }

    public Rectangle getInnerEdge() {
        return this.innerEdge;
    }

    public Rectangle getMidLaneMarker() {
        return this.midLaneMarker;
    }

    public boolean carInTrack(Car car) {
        return !this.grass.getBounds2D().intersects(car.carShape) && this.outerEdge.getBounds2D().contains(car.carShape);
    }

    public boolean checkPointCrossed(Car car) {
        return this.checkpoints[car.getCheckPoint()].contains(car.getX(), car.getY());
    }
}