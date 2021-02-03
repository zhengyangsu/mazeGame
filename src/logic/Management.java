package logic;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Management class, creates maze base on given dimension, generates cats and
 * cheese base on given size, manages cat and mouse movements, spawn cheeses in
 * random location, states: caught, found, and victory
 */
public class Management {

    private final int NUMBER_OF_CATS;


    private final int NUMBER_OF_CHEESES;
    private final int COLUMN;
    private final int ROW;
    private final int DEFAULT_MICE_X;
    private final int DEFAULT_MICE_Y;


    private Cat[] cats;
    private int cheeseFound;
    private boolean caught;
    private Maze maze;
    private Mice mice;

    private List<ChangeListener> listeners = new ArrayList<>();


    public Management(int ROW, int COLUMN, int catNum, int cheeseNum) {
        this.ROW = ROW;
        this.COLUMN = COLUMN;
        this.NUMBER_OF_CATS = catNum;
        this.NUMBER_OF_CHEESES = cheeseNum;
        DEFAULT_MICE_X = 1;
        DEFAULT_MICE_Y = 1;
        initialization(ROW, COLUMN, catNum);
    }

    private void initialization(int ROW, int COLUMN, int catNum) {
        cheeseFound = 0;
        caught = false;
        maze = new Maze(ROW, COLUMN);
        // Initialize positions for cats
        // ------------------------------------------------------------------------
        cats = new Cat[catNum];
        int[] catX = new int[]{19, 19, 1, 1, 19, 19};// starting cat COLUMN coordinates
        int[] catY = new int[]{1, 2, 13, 12, 12, 13};// starting cat ROW coordinates
        for (int i = 0; i < catNum; i++) {
            cats[i] = new Cat(catY[i], catX[i]);
            maze.getCell(catY[i], catX[i]).setCondition("PATH");
            maze.placeAnimal(cats[i]);
        }
        // ------------------------------------------------------------------------
        // Randomize the position of the cheese
        spawnCheese();
        // ------------------------------------------------------------------------
        // Initialize the mouse position
        // ------------------------------------------------------------------------
        maze.getCell(DEFAULT_MICE_Y, DEFAULT_MICE_X).setCondition("PATH");
        mice = new Mice(DEFAULT_MICE_Y, DEFAULT_MICE_X);
        maze.placeAnimal(mice);
        // ------------------------------------------------------------------------
        notifyListeners();
    }

    public int getCheeseFound() {
        return cheeseFound;
    }

    public int getNUMBER_OF_CHEESES() {
        return NUMBER_OF_CHEESES;
    }

    public Maze getMaze() {
        return maze;
    }

    public boolean isCaught() {
        return caught;
    }

    /**
     * moves mice and cats to the next valid location if mice and cheese locates
     * at the same grid, increment found if cat and mice locates at the same
     * grid, caught is true
     */
    public boolean move(String direction) {

        mice.moveMice(direction);
        if (maze.placeAnimal(mice)) {
            mice.confirmMovement(true);
            chaseMice();

            if (!caught) {

                Cell cell = maze.getCell(mice.getY(), mice.getX());
                if (cell.isCheese() && cell.isMice()) {
                    cheeseFound++;
                    cell.setCheese(false);
                    Controller.playSound(Controller.Sound.FISHING);
                    if (!over()) {
                        spawnCheese();
                    }
                }
                for (Cat cat : cats) {
                    moveCat(cat);
                    chaseMice();
                }
            }
            notifyListeners();
            return true;
        } else {
            mice.confirmMovement(false);
            Controller.playSound(Controller.Sound.NOACCESS);
            return false;
        }

    }

    /**
     * @return true is the game is over
     */
    public boolean over() {
        if (cheeseFound != NUMBER_OF_CHEESES && caught != true) {
            return false;
        } else {
            if (cheeseFound == NUMBER_OF_CHEESES) {
                Controller.playSound(Controller.Sound.WIN);
            }
            maze.revealMist();
            notifyListeners();
            return true;
        }

    }

    public void revealMist() {
        maze.revealMist();
    }

    public void reset() {
        initialization(ROW, COLUMN, NUMBER_OF_CATS);
        notifyListeners();
    }

    private void moveCat(Cat cat) {
        cat.moveCat();
        if (!maze.placeAnimal(cat)) {
            cat.confirmMovement(false);
            if (!cat.isTired()) {
                moveCat(cat);
            }
        } else {
            cat.confirmMovement(true);
        }
    }

    private void chaseMice() {
        for (int i = 0; i < NUMBER_OF_CATS; i++) {
            if (mice.getX() == cats[i].getX() && mice.getY() == cats[i].getY()) {
                caught = true;
                Controller.playSound(Controller.Sound.DIE);
                maze.revealMist();
            }
        }
    }

    private void spawnCheese() {
        Random die = new Random();
        int cheeseX = die.nextInt(COLUMN - 1) + 1;
        int cheeseY = die.nextInt(ROW - 1) + 1;
        if (!maze.getCell(cheeseY, cheeseX).isWall()
                && !maze.getCell(cheeseY, cheeseX).isMice()) {
            maze.getCell(cheeseY, cheeseX).setCheese(true);
        } else {
            spawnCheese();
        }
        notifyListeners();
    }

    // Observer Functions
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        ChangeEvent event = new ChangeEvent(this);

        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);

        }
    }
}
