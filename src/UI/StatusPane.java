package UI;


import logic.Management;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by Leon on 2014-03-19.
 */
@SuppressWarnings("serial")
public class StatusPane extends JPanel {

    private JLabel label;
    private Management game;

    public StatusPane(Management game) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        label = new JLabel("To reach a port we must set sail, Sail, not tie at anchor, Sail, not drift.");
        this.add(Box.createHorizontalGlue());
        this.add(label);

        this.game = game;
        displayStatus();
    }

    private void displayStatus() {
        game.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (game.isCaught()) {
                    label.setText("To be, or not to be: that is the question");
                } else {
                    label.setText("You have caught " + game.getCheeseFound() + " out of " + game.getNUMBER_OF_CHEESES() + " fish");
                }
            }
        });
    }


}
