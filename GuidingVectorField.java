import java.util.ArrayList;
import java.util.*;
public class GuidingVectorField {
    double step = 0.01;
    double distscale = 0.25;
    double scale = 20;
    double t;
    public Double[] getClosestValueOfT(Path p, Point location, double estimaT) {
        double lastDist = location.getDist(p.update(estimaT));
        Point lastpoint = p.update(estimaT);
        double lastHeading = 0.0;
        double lastT = estimaT;
        double xvalue = 0;
        for (int i = (int) (estimaT / step); i < 1 / step; i++) {
            Point current = p.update(estimaT + (i * step));
            double thisdist = current.getDist(location);
            if (thisdist < lastDist) {
                lastDist = thisdist;
                lastT = estimaT + (i * step);
                lastHeading = p.getHeading();
                xvalue = current.x;
            }
        }
        return new Double[]{lastT,lastDist,lastHeading,xvalue};
    }
    public Point getVecParallel(Path p, Point loc) {
        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double lastHeading = tangent[2];
        Point popin = new Point(scale*Math.sqrt(2)*Math.cos(lastHeading), scale*Math.sqrt(2)*Math.sin(lastHeading));
        popin.x += loc.x;
        popin.y += loc.y;
        return popin;
    }

    public Point getVec(Path p, Point loc) {
        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double lastHeading = tangent[2];
        double correction = tangent[1] *distscale;
        if (correction > scale) {
            correction = scale;
        }

        double angleForCos = Math.asin(correction/(scale*Math.sqrt(2)));
        Point popin = new Point(loc.x,loc.y);

        double powerFinal = (scale*Math.sqrt(2));
//        Math.cos(angleForCos) *
        double rotAngle = -angleForCos-angleForCos;
//        double xcomp = powerFinal*Math.cos(lastHeading+angleForCos+rotAngle);
//        double ycomp = powerFinal*Math.sin(lastHeading+angleForCos+rotAngle);

        double xcomp = powerFinal*Math.cos(lastHeading+angleForCos);
        double ycomp = powerFinal*Math.sin(lastHeading+angleForCos);


//        xcomp = powerFinal*Math.cos(lastHeading+angleForCos+rotAngle);
//        ycomp = powerFinal*Math.sin(lastHeading+angleForCos+rotAngle);

        if (tangent[3] > loc.x) {
            double xT = xcomp;
            double yT = ycomp;
            xcomp = Math.cos(rotAngle)*xT - Math.sin(rotAngle)*yT;
            ycomp = Math.sin(rotAngle)*xT + Math.cos(rotAngle)*yT;
        }

        popin.x += xcomp;
        popin.y += ycomp;

        return popin;
    }

}
