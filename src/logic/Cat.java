package logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * subclass of animal movement control by AI coordinates will only be updated if
 * movement has been confirmed
 * revisit should make the cat smarter
 */
public class Cat extends Animal {

    private final static String CAT = new String("CAT");
    private ArrayList<Integer> movementLog;


    public Cat(int y, int x) {
        super(y, x, CAT);
        movementLog = new ArrayList<>();

    }

    public void moveCat() {
        // 0: up; 1: down; 2: left; 3: right
        // revisit suggest use enum
        // is better to pass in maze and receive all possible path
        // or ..
        Random die = new Random();

        switch (die.nextInt(4)) {
            case 0:
                setNextY(getY() - 1);
                log(0);
                break;
            case 1:
                setNextY(getY() + 1);
                log(1);
                break;
            case 2:
                setNextX(getX() - 1);
                log(2);
                break;
            case 3:
                setNextX(getX() + 1);
                log(3);
                break;
        }
    }

    public void confirmMovement(boolean confirmed) {

        if (confirmed) {
            placeAnimal(getNextY(), getNextX());
            movementLog.clear();
        } else {
            setNextY(getY());
            setNextX(getX());
        }
    }

    public boolean isTired() {
        return movementLog.size() > 3;
    }

    private void log(int dir) {
        if (!movementLog.contains(dir)) {
            movementLog.add(dir);
        }
    }
}
