import java.awt.*;
import java.util.ArrayList;
import java.time.*;

public class Main {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame(700,700, "GuidingVecField Visual");

        BezierCurveHandler bezierCurveHandler = new BezierCurveHandler(new Point(100,100),500,500);
        GuidingVectorField gvf = new GuidingVectorField();
        Graphics2D g2d = (Graphics2D) frame.getGraphics();
        g2d.setColor(Color.RED);
        for (double t = 0.0; t < 1.0; t+=0.00001)
            g2d.drawOval((int) bezierCurveHandler.update(t).x, (int) bezierCurveHandler.update(t).y,1,1);
        g2d.setColor(Color.BLACK);

        while (true) {
            g2d.setColor(Color.RED);
            for (double t = 0.0; t < 1.0; t+=0.00001)
                g2d.drawOval((int) bezierCurveHandler.update(t).x, (int) bezierCurveHandler.update(t).y,1,1);
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
            Point ssigma = gvf.getVecParallel(bezierCurveHandler, new Point(x, y));
            g2d.drawLine(x, y, (int) ssigma.x, (int) ssigma.y);
            g2d.setColor(Color.RED);
            Point ssigma2 = gvf.getVec(bezierCurveHandler, new Point(x, y));
//            g2d.drawLine(x, y, (int) ssigma2.x, (int) ssigma2.y);
            g2d.drawLine(x, y, (int) ssigma2.x, (int) ssigma2.y);

            g2d.setColor(Color.blue);
            g2d.drawLine((int) ssigma2.x, (int) ssigma2.y, (int) ssigma.x, (int) ssigma.y);

            Point temp = bezierCurveHandler.update(gvf.t);
            double theirB = temp.y - (gvf.L[2] * temp.x);
//            g2d.drawLine((int) temp.x, (int) temp.y, (int) (temp.x + 1/Math.abs(gvf.L[2])*100), (int) (temp.y + (gvf.L[2])*100));
//            g2d.drawLine(0, (int) theirB, 700, (int) (theirB + (gvf.L[2])*700));
//            y = mx + b
//            y-b/m = x
//
//        for (int x = 0; x < 700; x+=70) {
//            for (int y = 0; y < 700; y+=70) {
//                g2d.setColor(Color.RED);
//                Point ssigma = gvf.getVec(bezierCurveHandler, new Point(x,y));
//                g2d.drawLine(x,y, (int) ssigma.x, (int) ssigma.y);
//                g2d.setColor(Color.RED);
//                g2d.drawString(String.valueOf(gvf.getClosestValueOfT(bezierCurveHandler,new Point(x,y),0.0)[1].intValue()),x,y-10);
//            }
//        }

//            g2d.setColor(Color.white);
//            g2d.fillRect(0,0,frame.getWidth(),frame.getHeight());
//            g2d.clearRect(0,0,frame.getWidth(),frame.getHeight());
        }

    }
}