import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MyPanel extends JPanel {
    int xsize, ysize;
    String title;
    ArrayList<Point> list;

    MyPanel(int xsize, int ysize, String title, ArrayList<Point> list) {
        this.list = list;
        this.xsize = xsize;
        this.ysize = ysize;
        this.setPreferredSize(new Dimension(xsize,ysize));
        this.title = title;
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawString(title,xsize / 2, 50);
        for (int p = 0; p < list.size(); p++) {
            Point pon = list.get(p);
            if (p < 1 / 0.05) {
                g2D.setColor(new Color(125,0,200));
            }
            else {
                g2D.setColor(new Color(0,0,0));
            }
            g2D.fillOval((int) pon.x, (int) pon.y,3,3);
        }
    }
}
