package logic;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Leon on 2014-03-19.
 */
public class Controller {

    public static final String SCREAMING_WAV = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/screaming.wav";
    public static final String WIN_WAV = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/win.wav";
    public static final String ACCESSDENIED_WAV = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/accessdenied.wav";
    public static final String OCEAN_WAV = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/ocean.wav";
    private static final String FISHING_WAV = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/fishing.wav";
    private static final String LOSE_WAV = "/Users/Leon/Documents/WorkSpace2/MazeGame/docs/lose.wav";

    public enum Sound {
        DIE, WIN, NOACCESS, FISHING, OCEAN, LOSE;
    }

    private static final String[] KEYS = {"UP", "DOWN", "LEFT", "RIGHT", "M"};
    private JPanel panel;
    private Management game;

    public Controller(JPanel panel) {
        this.panel = panel;


    }

    public void registerKeyPresses(Management game) {
        this.game = game;
        for (int i = 0; i < KEYS.length; i++) {
            String key = KEYS[i];
            panel.getInputMap().put(KeyStroke.getKeyStroke(key), key);
            panel.getActionMap().put(key, getKeyListener(key));
        }
    }

    @SuppressWarnings("serial")
    private AbstractAction getKeyListener(final String direction) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!game.over()) {
                    game.move(direction);
                }
            }
        };
    }

    static public void playSound(Sound type) {

        File sound = new File("");

        switch (type) {
            case DIE:
                sound.exists();
                sound = new File(SCREAMING_WAV);
                break;
            case WIN:
                sound.exists();
                sound = new File(WIN_WAV);
                break;
            case NOACCESS:
                sound.exists();
                sound = new File(ACCESSDENIED_WAV);
                break;
            case FISHING:
                sound.exists();
                sound = new File(FISHING_WAV);
                break;
            case OCEAN:
                sound.exists();
                sound = new File(OCEAN_WAV);
                break;
            case LOSE:
                sound.exists();
                sound = new File(LOSE_WAV);
            default:
                break;
        }

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
