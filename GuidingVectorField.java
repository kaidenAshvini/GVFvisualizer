import java.util.ArrayList;
import java.util.*;

import static java.util.Collections.min;

public class GuidingVectorField {
    double step = 0.01;
    double distscale = 0.25;
    double scale = 30;
    double t;
    Path lastP;
    Point finalPoint;
    Point startingPoint;
    Double[] L;
    public Double[] getClosestValueOfT(Path p, Point location, double estimaT) {
        if (lastP != p) {
            startingPoint = p.update(0.1);
            finalPoint = p.update(1);
        }
        double lastDist = location.getDist(p.update(estimaT));
        Point lastpoint = p.update(estimaT);
        double lastHeading = 0.0;
        double lastT = estimaT;
        double xvalue = 0;
        Point current = new Point(0,0);

        for (int i = (int) (estimaT / step); i < 1 / step; i++) {
            current = p.update(estimaT + (i * step));
            double thisdist = current.getDist(location);
            if (thisdist < lastDist) {
                lastDist = thisdist;
                lastT = estimaT + (i * step);
                lastHeading = p.getHeading();
            }
        }
        t = lastT;
        L = new Double[]{lastT,lastDist,lastHeading,current.x,current.y};
        return new Double[]{lastT,lastDist,lastHeading,current.x,current.y};
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
        System.out.println(t);

        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double lastHeading = Math.abs(tangent[2]);
        double powerFinal = (scale*Math.sqrt(2));

        if (tangent[0] == 0.0) {
            double head = loc.getHeading(startingPoint);
            if (loc.x > startingPoint.x) {
                return new Point(loc.x - Math.cos(head)*powerFinal, loc.y - Math.sin(head)*powerFinal);
            }
            return new Point(loc.x + Math.cos(head)*powerFinal, loc.y + Math.sin(head)*powerFinal);
        }
        if (tangent[0] > 0.9) {
            double head = loc.getHeading(finalPoint);
            if (loc.x > finalPoint.x) {
                return new Point(loc.x - Math.cos(head)*powerFinal, loc.y - Math.sin(head)*powerFinal);
            }
            return new Point(loc.x + Math.cos(head)*powerFinal, loc.y + Math.sin(head)*powerFinal);
        }

        double correction = tangent[1];
        if (correction > scale) {
            correction = scale;
        }
        double angleForCos = Math.asin(correction/(scale*Math.sqrt(2)));

        if (tangent[2] < 0) {
            angleForCos = -angleForCos;
        }

        Point popin = new Point(loc.x,loc.y);
        popin = new Point(loc.x,loc.y);

        double rotAngle = -angleForCos-angleForCos;
        double xcomp = powerFinal*Math.cos((lastHeading)+angleForCos);
        double ycomp = powerFinal*Math.sin((lastHeading)+angleForCos);

        Point temp = p.update(t);
        double theirB = temp.y - (tangent[2] * temp.x);
//        System.out.println(loc.y < tangent[2]*loc.x+theirB);

        if (loc.y > tangent[2]*loc.x+theirB) {
            double xT = xcomp;
            double yT = ycomp;
            xcomp = Math.cos(rotAngle)*xT - Math.sin(rotAngle)*yT;
            ycomp = Math.sin(rotAngle)*xT + Math.cos(rotAngle)*yT;
        }

        if (tangent[2] < 0) {
            ycomp = -ycomp;
        }
        popin.x += xcomp;
        popin.y += ycomp;
        return popin;
    }

}
