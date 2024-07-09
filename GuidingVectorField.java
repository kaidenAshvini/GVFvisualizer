import java.util.ArrayList;
import java.util.*;
public class GuidingVectorField {
    double step = 0.01;
    double distscale = 0.5;
    double t;
    Double[] getClosestValueOfT(Path p, Point location, double estimaT) {
        double lastDist = location.getDist(p.update(estimaT));
        Point lastpoint = p.update(estimaT);
        double lastT = estimaT;
        for (int i = (int) (estimaT / step); i < 1 / step; i++) {
            double thisdist = p.update(estimaT + (i * step)).getDist(location);
            if (thisdist < lastDist) {
                lastDist = thisdist;
                lastT = estimaT + (i * step);
            }
        }
        return new Double[]{lastT,lastDist};
    }
    public Point getVec(Path p, Point loc) {
        Point finalVec;
        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double correctVector = tangent[1];
        double powerVector = p.update(tangent[0]).getDist(new Point(0,0));
        double theta = Math.asin(correctVector / powerVector);
        return new Point(Math.sin(theta), Math.cos(theta));
    }

}
