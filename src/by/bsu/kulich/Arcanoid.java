package by.bsu.kulich;

import javax.swing.*;

public class Arcanoid extends JFrame {
    public final static int WINDOW_WIDTH = 800;
    public final static int WINDOW_HEIGHT = 700;

    public static final double FT_SLICE = 1.0;
    public static final double FT_STEP = 1.0;

    public Arcanoid() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.createBufferStrategy(2);
        this.setBounds(400, 100, 0, 0);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String[] args) {
        new Arcanoid().run();
    }

    public void run() {

    }
}
