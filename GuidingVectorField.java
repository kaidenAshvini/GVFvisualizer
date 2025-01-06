import java.util.ArrayList;
import java.util.*;

import static java.util.Collections.min;

public class GuidingVectorField {
    double step = 0.01;
    double scale = 100;
    double t;
    Path lastP;
    Point finalPoint;
    Point startingPoint;
    Double[] L;
    double distscale = 0.01;


    private Pair getClosestVec(Path p, Point loc) {
        double minDist = Double.MAX_VALUE;
        double thisDist;
        Point current;
        Point heading = null;
        Point finalPoint = null;
        for (int i = 0; i <=1/step; i++) {
            current = p.update(i*step);
            thisDist = current.getDist(loc);
            if (thisDist < minDist) {
                minDist = thisDist;
                finalPoint = current;
                heading = p.getHeadingVec();
            }
        }
        return new Pair(minDist,finalPoint,heading);
    }

    private Double[] findLine(Point path, Point head) {
        double m = head.getY()/(head.getX()+0.00000000001);
        double b = path.getY()-m*path.getX();
        return new Double[]{m,b};
    }

    public Point getVec(Path p, Point loc, boolean backwards) {
        Pair pair = getClosestVec(p, loc);
        double dist = pair.dist;
        Point path = pair.p;
        Point head = pair.heading;
        double t = path.t;

        if (backwards) {
            head = head.scale(-1);
        }

        Point correction = head.getPerpendicularVec().scale(Math.min(dist*distscale,1/Math.sqrt(2)));

        Double[] line = findLine(path, correction);
        boolean belowLine = !(loc.getY() > loc.getX() * line[0] + line[1]);

        if (backwards) {
            belowLine = !belowLine;
        }

        if (belowLine) {correction.y *= -1;}
        else {correction.x *= -1;}

        if (t == 0 || t >= 0.97) {
            return loc.add(loc.getUnitVec(path).scale(-scale/5));
        }

        return loc.add(head.add(correction).getUnitVec().scale(scale));
    }

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

        for (int i = (int) (estimaT / step); i <= 1 / step; i++) {
            current = p.update(estimaT + (i * step));
            double thisdist = current.getDist(location);
            if (thisdist < lastDist) {
                lastDist = thisdist;
                lastT = estimaT + (i * step);
                lastHeading = p.getHeading();
            }
        }
        t = lastT;
//        if (negPath) {
//            lastHeading = 1/-lastHeading;
//        }
        L = new Double[]{lastT,lastDist,lastHeading,current.x,current.y};
        return new Double[]{lastT,lastDist,lastHeading,current.x,current.y};
    }

    public Point getVecParallel(Path p, Point loc) {
        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double lastHeading = tangent[2];
        Point popin = new Point(-scale*Math.sqrt(2)*Math.cos(lastHeading), -scale*Math.sqrt(2)*Math.sin(lastHeading));
        popin.x += loc.x;
        popin.y += loc.y;
        return popin;
    }

    public Point getVecBad(Path p, Point loc) {
//        System.out.println(t);

        Double[] tangent = getClosestValueOfT(p, loc, 0.0);
        double lastHeading = Math.abs(tangent[2]);
        double powerFinal = (scale*Math.sqrt(2));


        if (tangent[0] > 0.9) {
            double head = loc.getHeading(finalPoint);
            if (loc.x > finalPoint.x) {
                return new Point(loc.x - Math.cos(head)*powerFinal, loc.y - Math.sin(head)*powerFinal);
            }
            return new Point(loc.x + Math.cos(head)*powerFinal, loc.y + Math.sin(head)*powerFinal);
        }

        double correction = tangent[1]*distscale;
        if (correction > scale) {
            correction = scale;
        }
        if (tangent[0] == 0) {
            correction = 0;
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

    public Point backwardsGetVec(Path p, Point loc) {

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

        double correction = tangent[1]*distscale;
        if (correction > scale) {
            correction = scale;
        }
        double angleForCos = Math.PI + Math.asin(correction/(scale*Math.sqrt(2)));

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

        if (loc.y < tangent[2]*loc.x+theirB) {
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


    class Pair {
    public double dist;
    public Point p;
    public Point heading;

    public Pair(double dist, Point p, Point heading) {
        this.dist = dist;
        this.p = p;
        this.heading = heading;
    }
}