import java.awt.*;
import java.util.ArrayList;
import java.time.*;

public class Main {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame(700,700, "GuidingVecField Visual");

        BezierCurveHandler bezierCurveHandler = new BezierCurveHandler(new Point(100,100), new Point(100,100), new Point(600,100), new Point(600,600));
//        BezierCurveHandler bezierCurveHandler = new BezierCurveHandler(new Point(400,400), 300,100);
//        bezierCurveHandler = new BezierCurveHandler(new Point(0,0),new Point(100,100),new Point(0,0),new Point(100,100));
            GuidingVectorField gvf = new GuidingVectorField();
        Graphics2D g2d = (Graphics2D) frame.getGraphics();
        CurveOdo centripedal = new CurveOdo();
        double time = System.currentTimeMillis();
        double prevTime = time;
        double k = 0;

        int cnt = 0;

        while (true) {
            cnt+=1;
            g2d.setColor(Color.RED);
            for (double t = 0.0; t < 1.0; t+=0.01)
                g2d.drawOval((int) bezierCurveHandler.update(t).x, (int) bezierCurveHandler.update(t).y,2,2);
            g2d.setColor(Color.BLACK);
//
            PointerInfo a = MouseInfo.getPointerInfo();
            int x = a.getLocation().x - 700;
            int y = a.getLocation().y - 350;

            if (x <= 0)
                x=0;
            if (y <= 0)
                y=0;
            if (x >= frame.getWidth())
                x=frame.getWidth()-1;
            if (y >= frame.getHeight())
                y=frame.getHeight()-1;

            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - 5, y - 5, 10, 10);
            Point ssigma = gvf.backwardsGetVec(bezierCurveHandler, new Point(x, y));
            g2d.drawLine(x, y, (int) ssigma.x, (int) ssigma.y);
            g2d.setColor(Color.RED);
            Point ssigma2 = gvf.getVec(bezierCurveHandler, new Point(x, y), true);
            g2d.drawLine(x, y, (int) ssigma2.x, (int) ssigma2.y);
//            g2d.setColor(Color.blue);
//            g2d.drawLine((int) ssigma2.x, (int) ssigma2.y, (int) ssigma.x, (int) ssigma.y);

            g2d.fillRect(x,y,30,(int)(k*Math.pow(10,17)));
//            k = centripedal.update(x, y);
            time = System.currentTimeMillis();

            if (cnt == 15) {
                cnt = 0;
                frame.repaint();
            }
            if (time - prevTime > 1000) {
                prevTime = time;
                k = centripedal.update(x, y);
                System.out.println(k);
            }
        }

    }
}