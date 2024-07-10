
public class Point {
    public double x,y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(double x, double y, double heading) {
        this.x = x;
        this.y = y;
    }
    public double getX() {return x;}
    public double getY() {return y;}

    public double getHeading(Point p) {
//        double heading = 0;
//        if (y <= p.y) {
//            if (x <= p.x) {
//                heading+=0;
//            }
//            else {
//                heading+=90;
//            }
//        }
//        else {
//            if (x <= p.x) {
//                heading+=270;
//            }
//            else {
//                heading+=180;
//            }
//        }
        return Math.abs(Math.atan(Math.abs(y-p.y)/Math.abs(x-p.x)));
    }

    public double getDist(Point p) {
        return Math.sqrt(Math.abs(y-p.y) + Math.abs(x-p.x));
    }
    public Point scale(double s) {
        return new Point(x*s,y*s);
    }
}
