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
        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double correctVector = tangent[1];
        Point psfasdfj = p.update(tangent[0]);
        System.out.println(p.getHeading());
        Point popin = new Point(3*Math.cos(p.getHeading()), 3*Math.sin(p.getHeading()));
        if (psfasdfj.x < 0) {
            popin.x *= -1;
        }
        if (psfasdfj.y < 0) {
            popin.y *= -1;
        }
        popin.x += loc.x;
        popin.y += loc.y;
        return popin;
    }

}
