package logic;

/**
 * subclass of animal movement control by users inputs
 */
public class Mice extends Animal {

    private final static String MICE = new String("MICE");

    public Mice(int y, int x) {
        super(y, x, MICE);

    }

    public void moveMice(String direction) {

        switch (direction) {
            case "UP":
                setNextY(getY() - 1);
                break;
            case "DOWN":
                setNextY(getY() + 1);
                break;
            case "LEFT":
                setNextX(getX() - 1);
                break;
            case "RIGHT":
                setNextX(getX() + 1);
                break;
        }
    }

    public void confirmMovement(boolean confirmed) {

        if (confirmed) {
            placeAnimal(getNextY(), getNextX());
        } else {
            setNextY(getY());
            setNextX(getX());
        }
    }

}
