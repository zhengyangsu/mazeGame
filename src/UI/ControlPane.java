package UI;

import logic.Controller;
import logic.Management;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Demo how to handle arrow keys.
 */
@SuppressWarnings("serial")
public class ControlPane extends JPanel {
    // Names of arrow key actions.

    private static final String GAME_PNG = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/new.png";
    private static final String HELP_PNG = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/help.png";
    private static final String ABOUT_PNG = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/about.png";
    private JButton newGame;
    private JButton help;
    private JButton about;
    private Management game;
    private Controller controller;
    private ImageIcon newGameIcon;
    private ImageIcon helpIcon;
    private ImageIcon aboutIcon;


    public ControlPane(Management game) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.game = game;

        newGameIcon = new ImageIcon(GAME_PNG);
        helpIcon = new ImageIcon(HELP_PNG);
        aboutIcon = new ImageIcon(ABOUT_PNG);


        makeButton();


        this.add(newGame);
        this.add(help);
        this.add(Box.createHorizontalGlue());
        this.add(about);

        controller = new Controller(this);
        controller.registerKeyPresses(game);


    }

    private void makeButton() {
        newGame = new JButton("New Game");
        help = new JButton("Help");
        about = new JButton("About");
        newGame.setFocusable(false);
        help.setFocusable(false);
        about.setFocusable(false);
        buttonAssignment();
    }

    private void buttonAssignment() {

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (game.over()) {
                    game.reset();
                } else {

                    int decision = JOptionPane.showConfirmDialog(
                            null,
                            "Do you really want to abandon the current game?",
                            "New Game",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            newGameIcon);

                    if (decision == JOptionPane.YES_OPTION) {
                        game.reset();
                    }
                }


            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(
                        null,
                        "DIRECTIONS:" +
                                "\nFind 5 Fish before a Shark eats you" +
                                "\nCONTROL: " +
                                "\nUP, DOWN, LEFT, RIGHT",
                        "Help",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.OK_OPTION,
                        helpIcon);
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(
                        null,
                        "Game created by Leon" +
                                "\nSN 301161940" +
                                "\nThird Assignment part 2 for CMPT 213" +
                                "\nImages by ICONFINDER" +
                                "\nLink: https://www.iconfinder.com" +
                                "\nSound effect by FREESOUND" +
                                "\nLink: https://www.freesound.org",
                        "About Me",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        aboutIcon);
            }
        });
    }

}
