import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Point> list = new ArrayList<>();
        BezierCurveHandler bezierCurveHandler = new BezierCurveHandler(new Point(100,100), new Point(60,60), new Point(150,150), new Point(90,90));
        list.add(new Point(100,100));
        list.add(new Point(60,60));
        list.add(new Point(150,150));
        list.add(new Point(90,90));
        for (double t = 0.0; t < 1.0; t+=0.05)
            list.add(bezierCurveHandler.update(t));
        for (int x = 0; x < 700; x+=10) {
            for (int y = 0; y < 700; y+=10) {
                Point loc = new Point(x,y);
                list.add(loc);
            }
        }
        MyFrame frame = new MyFrame(700,700, "GuidingVecField Visual", list);
    }
}