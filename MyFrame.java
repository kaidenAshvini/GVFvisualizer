import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyFrame extends JFrame {
    MyPanel panel;
    ArrayList<Point> verysigam;

    MyFrame(int xsize, int ysize, String title, ArrayList<Point> sdf) {
        this.verysigam = sdf;
        panel = new MyPanel(xsize,ysize, title, sdf);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(xsize,ysize);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(500, 500));
    }
}

