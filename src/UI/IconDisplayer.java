package UI;

import logic.Management;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Leon on 2014-03-17.
 */

@SuppressWarnings("serial")
public class IconDisplayer extends JPanel {
    /**
     * ImageIcon Size
     */
    private final static int PIXEL_HEIGHT = 45;
    private final static int PIXEL_WIDTH = 45;

    /**
     * Image resources destination
     */
    private static final String DIRECTORY_PATH = new File("").getAbsolutePath();
    private static final String BOAT_PNG = DIRECTORY_PATH + "/docs/boat.png";
    private static final String SHARK_PNG = DIRECTORY_PATH + "/docs/shark.png";
    private static final String FISH_PNG = DIRECTORY_PATH+ "/docs/fish.png";
    private static final String SAND_PNG = DIRECTORY_PATH + "/docs/sand.png";
    private static final String ROCK_PNG = DIRECTORY_PATH + "/docs/rock.png";
    private static final String WATER_PNG = DIRECTORY_PATH + "/docs/water.png";
    private static final String SKELETON_PNG = DIRECTORY_PATH + "/docs/blood.png";
    private static final String BORDER_PNG = DIRECTORY_PATH + "/docs/border.png";

    /**
     * maze with fixed size
     */
    private final int ROW;
    private final int COLUMN;
    private Management game;

    /**
     * Icon types
     * corresponding to maze cell condition
     */
    private ImageIcon[][] icons;
    private ImageIcon border;
    private ImageIcon mist;
    private ImageIcon cat;
    private ImageIcon mice;
    private ImageIcon wall;
    private ImageIcon path;
    private ImageIcon cheese;
    private ImageIcon caught;


    public IconDisplayer(Management game) {
        super(new GridLayout(game.getMaze().getRow(), game.getMaze().getColumn(), 0, 0));
        this.game = game;
        referenceIcon();
        ROW = game.getMaze().getRow();
        COLUMN = game.getMaze().getColumn();
        icons = new ImageIcon[ROW][COLUMN];
        generateIcons();
        mapIcons();

    }

    private void mapIcons() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                this.add(makeIcon(i, j));
            }
        }
    }

    private void generateIcons() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                String condition = game.getMaze().getCell(i, j).getCondition();
                switch (condition) {
                    case "BORDER":
                        icons[i][j] = border;
                        break;
                    case "MICE":
                        icons[i][j] = mice;
                        break;
                    case "CAT":
                        icons[i][j] = cat;
                        break;
                    case "CHEESE":
                        icons[i][j] = cheese;
                        break;
                    case "MIST":
                        icons[i][j] = mist;
                        break;
                    case "WALL":
                        icons[i][j] = wall;
                        break;
                    case "PATH":
                        icons[i][j] = path;
                        break;
                    case "X":
                        icons[i][j] = caught;
                        break;
                }

            }
        }
    }

    private void referenceIcon() {

        border = getScaleImageIcon(new ImageIcon(BORDER_PNG));

        mist = getScaleImageIcon(new ImageIcon(SAND_PNG));

        cat = getScaleImageIcon(new ImageIcon(SHARK_PNG));

        mice = getScaleImageIcon(new ImageIcon(BOAT_PNG));

        wall = getScaleImageIcon(new ImageIcon(ROCK_PNG));

        path = getScaleImageIcon(new ImageIcon(WATER_PNG));

        cheese = getScaleImageIcon(new ImageIcon(FISH_PNG));

        caught = getScaleImageIcon(new ImageIcon(SKELETON_PNG));
    }

    public static ImageIcon getScaleImageIcon(ImageIcon icon) {

        return new ImageIcon(getScaledImage(icon.getImage(), PIXEL_WIDTH, PIXEL_HEIGHT));

    }

    private static Image getScaledImage(Image srcImg, int width, int height) {

        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D context = resizedImg.createGraphics();
        context.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        context.drawImage(srcImg, 0, 0, width, height, null);
        context.dispose();
        return resizedImg;

    }

    private JLabel makeIcon(int row, int column) {
        final int ROW = row;
        final int COLUMN = column;
        final JLabel label = new JLabel(icons[row][column]);
        game.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                generateIcons();
                label.setIcon(icons[ROW][COLUMN]);
            }
        });
        return label;
    }


}
