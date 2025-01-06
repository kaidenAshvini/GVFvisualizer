public abstract class Path {
    double t;
    public double getHeading() {
        return 0.0;
    }
    public Point getHeadingVec() { return new Point(0,0); }

    public Point update(double t) {
        return new Point(0, 0);
    }
}