import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Point> list = new ArrayList<>();
        BezierCurveHandler bezierCurveHandler = new BezierCurveHandler(new Point(350,350),new Point(300,350),new Point(300,300),new Point(350,300));
        GuidingVectorField gvf = new GuidingVectorField();
        for (double t = 0.0; t < 1.0; t+=0.0005)
            list.add(bezierCurveHandler.update(t));
        for (int x = 0; x < 700; x+=70) {
            for (int y = 0; y < 700; y+=70) {
                Point loc = new Point(x,y);
                list.add(loc);
                Point guided = gvf.getVec(bezierCurveHandler, loc);
                guided = guided.scale(10);
                list.add(new Point(guided.x + loc.x, guided.y + loc.y));
            }
        }

        MyFrame frame = new MyFrame(700,700, "GuidingVecField Visual", list);
    }
}