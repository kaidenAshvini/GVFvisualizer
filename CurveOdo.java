import java.sql.Time;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Timer;

import static java.lang.Math.*;

public class CurveOdo {
    double vel;
    double time = System.currentTimeMillis();
    double pastTime = 1;

    double dX;
    double d2X;
    double dY;
    double d2Y;

    double prevX;
    double prevY;
    double prevPrevX;
    double prevPrevY;

    public CurveOdo() {
        dX = 0;
        dY = 0;
        d2X = 0;
        d2Y = 0;
        vel = 0;
        time = System.currentTimeMillis();
    }
    public double update(double x, double y) {
        pastTime = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();

        dX = centralDifference(x,prevPrevX);
        dY = centralDifference(y,prevPrevY);

        d2X = secondCentralDifference(x, prevX, prevPrevX);
        d2Y = secondCentralDifference(y, prevY, prevPrevY);

        prevPrevX = prevX;
        prevPrevY = prevY;
        prevX = x;
        prevY = y;

        return abs(dX * d2Y - dY * d2X) / sqrt(pow(pow(dX,2) + pow(dY,2),3));
    }

    private double centralDifference(double x, double x2) {
        return (x - x2)/2*pastTime;
    }
    private double forwardDifference(double x, double x2) {
        return (x-x2)/pastTime;
    }
    private double secondCentralDifference(double x, double x2, double x3) {
        return (x - 2*x2 + x3)/(pastTime*pastTime);
    }

}
