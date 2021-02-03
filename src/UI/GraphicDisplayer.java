package UI;

import logic.Management;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Leon on 2014-03-17.
 */
public class GraphicDisplayer {

    private final static int COLUMN = 21;
    private final static int ROW = 15;
    private final static int NUM_OF_CHEESE = 5;
    private final static int NUM_OF_CAT = 6;
    private static final int LAYOUT_GAP = 0;
    private static Management game;
    private JFrame frame;
    private IconDisplayer icons;
    private ControlPane control;
    private StatusPane status;

    public GraphicDisplayer() {

        game = new Management(ROW, COLUMN, NUM_OF_CAT, NUM_OF_CHEESE);
        control = new ControlPane(game);
        status = new StatusPane(game);
        icons = new IconDisplayer(game);

        frame = new JFrame();
        frame.setTitle("The Old Man and the Fish");
        frame.setLayout(new BorderLayout(LAYOUT_GAP, LAYOUT_GAP));
        frame.add(icons, BorderLayout.CENTER);
        frame.add(control, BorderLayout.NORTH);
        frame.add(status, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }


    public static void main(String[] args) {
        new GraphicDisplayer();
    }

}
