public class BezierCurveHandler extends Path{
//    one line = P(1-t) + P(T)
//    t is parameterized!

//    A quick explanation of bezier splines, curves, wtv;
//    A bezier curve is given by a set of 4 points (quadrilateral) given in an order to form lines;
//
//    Each line is parameterized;
//    The initial formation will have 3 lines;
//    From those 3 lines, parameterize and draw new lines for the points(t) along it;
//    From those 2 lines, parameterize and draw a new line for the points(t) along it;
//    Once you have 1 line, the value (t) on it is your final point
//    The heading is simply the slope of that last line *I think

//    Bezier curves/points go from one point to the other end, easy to connect a string of them
//
//    Bezier class extends path, which i plan to make into an abstract class so we may test other splines;
//    I did not use apache commons math for this, i used my own classes Point and Path found in this package

    Point p1, p2, p3, p4;
    Point p1a,p2a,p3a,p1b,p2b;
    double heading;
//    the order given by l1, l2, r2, r1
    public BezierCurveHandler(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }
//    the order given by starting point and then - or + width / height
    public BezierCurveHandler(Point start, double width, double height) {
        this.p1 = start;
        double x = start.x;
        double y = start.y;
        this.p2 = new Point(x+width,y);
        this.p3 = new Point(x+width,y+height);
        this.p4 = new Point(x+width,y+height);
    }
    public Point update(double t) {
        p1a = getPointOnLine(p1,p2,t);
        p2a = getPointOnLine(p2,p3,t);
        p3a = getPointOnLine(p3,p4,t);

        p1b = getPointOnLine(p1a,p2a,t);
        p2b = getPointOnLine(p2a,p3a,t);

        heading = p1b.getHeading(p2b);

        return getPointOnLine(p1b,p2b,t);
    }
    Point getPointOnLine(Point point0, Point point1, double t) {
        double x1 = point0.x; double y1 = point0.y;
        double x2 = point1.x; double y2 = point1.y;

        double nx = x1 + t * (x2-x1);
        double ny = y1 + t * (y1-y2);

        return new Point(nx,ny);
    }

    public double getHeading() {
        return heading;
    }
}
