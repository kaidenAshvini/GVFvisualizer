import java.awt.*;
import java.util.ArrayList;
import java.time.*;

public class Main {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame(700,700, "GuidingVecField Visual");

        BezierCurveHandler bezierCurveHandler = new BezierCurveHandler(new Point(0,0),new Point(350,0), new Point(0,700),new Point(700,700));
        GuidingVectorField gvf = new GuidingVectorField();
        Graphics2D g2d = (Graphics2D) frame.getGraphics();
        g2d.setColor(Color.RED);
        for (double t = 0.0; t < 1.0; t+=0.00001)
            g2d.drawOval((int) bezierCurveHandler.update(t).x, (int) bezierCurveHandler.update(t).y,1,1);
        g2d.setColor(Color.BLACK);
        for (int x = 0; x < 700; x+=70) {
            for (int y = 0; y < 700; y+=70) {
                g2d.setColor(Color.BLACK);
                Point ssigma = gvf.getVecParallel(bezierCurveHandler, new Point(x,y));
                g2d.drawLine(x,y, (int) ssigma.x, (int) ssigma.y);
                g2d.setColor(Color.RED);
                Point ssigma2 = gvf.getVec(bezierCurveHandler, new Point(x,y));
                g2d.drawLine(x,y, (int) ssigma2.x,(int) ssigma2.y);

                g2d.drawString(gvf.getClosestValueOfT(bezierCurveHandler,new Point(x,y),0.0)[0].toString(), x, y);
                g2d.setColor(Color.RED);
                g2d.drawString(String.valueOf(gvf.getClosestValueOfT(bezierCurveHandler,new Point(x,y),0.0)[1].intValue()),x,y-10);

                g2d.setColor(Color.blue);
                g2d.drawLine((int) ssigma2.x, (int) ssigma2.y, (int) ssigma.x, (int) ssigma.y);
            }
        }

        for (int x = 0; x < 700; x+=70) {
            for (int y = 0; y < 700; y+=70) {
                g2d.setColor(Color.RED);
                Point ssigma = gvf.getVec(bezierCurveHandler, new Point(x,y));
                g2d.drawLine(x,y, (int) ssigma.x, (int) ssigma.y);
                g2d.setColor(Color.RED);
                g2d.drawString(String.valueOf(gvf.getClosestValueOfT(bezierCurveHandler,new Point(x,y),0.0)[1].intValue()),x,y-10);
            }
        }
        frame.remove(0);

    }
}