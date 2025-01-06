
public class Point {
    public double x,y;
    public double t;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(double x, double y, double t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }
    public double getX() {return x;}
    public double getY() {return y;}

    public double getHeading(Point p) {
        return (Math.atan2((y-p.y),(x-p.x)));
    }

    public double getDist(Point p) {
        return Math.sqrt(Math.pow(Math.abs(y-p.y),2) + Math.pow(Math.abs(x-p.x),2));
    }

    public Point getUnitVec(Point p) {
        double xD = this.x - p.x;
        double yD = this.y - p.y;
        double magnitude = Math.sqrt(Math.pow(xD, 2) + Math.pow(yD, 2));
        return new Point(xD/magnitude, yD/magnitude);
    }

    public Point getUnitVec() {
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Point(x/magnitude, y/magnitude);
    }
    public Point getUnitVec(double t) {
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Point(x/magnitude, y/magnitude, t);
    }


    public Point getPerpendicularVec() {
        return new Point(y, x);
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public void setT(double t) {
        this.t = t;
    }


    public Point scale(double s) {
        return new Point(x*s,y*s);
    }
}
