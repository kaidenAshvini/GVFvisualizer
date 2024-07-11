import java.util.ArrayList;
import java.util.*;
public class GuidingVectorField {
    double step = 0.01;
    double distscale = 0.5;
    double t;
    public Double[] getClosestValueOfT(Path p, Point location, double estimaT) {
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
    public Point getVecParallel(Path p, Point loc) {
        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double correctVector = tangent[1];
        Point psfasdfj = p.update(tangent[0]);
        System.out.println(p.getHeading());
        Point popin = new Point(30*Math.sqrt(2)*Math.cos(p.getHeading()), 30*Math.sqrt(2)*Math.sin(p.getHeading()));
        if (psfasdfj.x < loc.x) {
            popin.x *= -1;
        }
        if (psfasdfj.y > loc.y) {
            popin.y *= -1;
        }
        popin.x += loc.x;
        popin.y += loc.y;
        return popin;
    }

    public Point getVec(Path p, Point loc) {
        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double correctVector = tangent[1];
        if (correctVector > 30)
            correctVector = 30;
        Point psfasdfj = p.update(tangent[0]);
        System.out.println(p.getHeading());
        Point popin = new Point(Math.abs(30*Math.sqrt(2)*Math.cos(p.getHeading())), Math.abs(30*Math.sqrt(2)*Math.sin(p.getHeading())));
        if (psfasdfj.x < loc.x) {
            popin.x *= -1;
        }
        popin.x += loc.x;
        popin.y += loc.y;

        Point finalP = new Point(popin.x,popin.y);
        double xc = Math.cos(Math.PI-p.getHeading()) * correctVector;
        double yc = Math.sin(Math.PI-p.getHeading()) * correctVector;
        finalP.x += xc;
        finalP.y += yc;
        return finalP;
    }

}
