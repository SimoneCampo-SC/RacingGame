package Client;

import Shared.*;
import java.awt.*;

public class RaceTrack {
    private Rectangle grass;
    private Rectangle outerEdge;
    private Rectangle innerEdge;
    private Rectangle midLaneMarker;
    private Rectangle[] checkpoints = new Rectangle[5];

    public RaceTrack() {
        grass = new Rectangle(Configurations.INNER_EDGE_X,Configurations.INNER_EDGE_Y,Configurations.INNER_EDGE_WIDTH,Configurations.INNER_EDGE_HEIGHT);
        outerEdge = new Rectangle(Configurations.OUTER_EDGE_X,Configurations.OUTER_EDGE_Y,Configurations.OUTER_EDGE_WIDTH,Configurations.OUTER_EDGE_HEIGHT);
        innerEdge = new Rectangle(Configurations.INNER_EDGE_X,Configurations.INNER_EDGE_Y,Configurations.INNER_EDGE_WIDTH,Configurations.INNER_EDGE_HEIGHT);
        midLaneMarker = new Rectangle(Configurations.MIDLANE_MARKER_X,Configurations.MIDLANE_MARKER_Y,Configurations.MIDLANE_MARKER_WIDTH,Configurations.MIDLANE_MARKER_HEIGHT);

        // Set checkpoints
        checkpoints[0] = new Rectangle(Configurations.CHECKPOINT_1_X, Configurations.CHECKPOINT_1_Y, Configurations.CHECKPOINT_WIDTH, Configurations.CHECKPOINT_HEIGHT);
        checkpoints[1] = new Rectangle(Configurations.CHECKPOINT_2_X, Configurations.CHECKPOINT_2_Y, Configurations.CHECKPOINT_WIDTH, Configurations.CHECKPOINT_HEIGHT);
        checkpoints[2] = new Rectangle(Configurations.CHECKPOINT_3_X, Configurations.CHECKPOINT_3_Y, Configurations.CHECKPOINT_WIDTH, Configurations.CHECKPOINT_HEIGHT);
        checkpoints[3] = new Rectangle(Configurations.CHECKPOINT_4_X, Configurations.CHECKPOINT_4_Y, Configurations.CHECKPOINT_WIDTH, Configurations.CHECKPOINT_HEIGHT);
        checkpoints[4] = new Rectangle(Configurations.CHECKPOINT_5_X, Configurations.CHECKPOINT_5_Y, Configurations.CHECKPOINT_WIDTH, Configurations.CHECKPOINT_HEIGHT);
    }

    public Rectangle getGrass() {
        return grass;
    }

    public Rectangle getOuterEdge() {
        return outerEdge;
    }

    public Rectangle getInnerEdge() {
        return innerEdge;
    }

    public Rectangle getMidLaneMarker() {
        return midLaneMarker;
    }

    public boolean carInTrack (Car car) {
        return !grass.getBounds2D().intersects(car.carShape) && outerEdge.getBounds2D().contains(car.carShape);
    }

    public boolean checkPointCrossed(Car car) {
        return checkpoints[car.getCheckPoint()].contains(car.getX(), car.getY());
    }
}
