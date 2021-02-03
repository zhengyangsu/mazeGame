package logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * enter ROW and COLUMN creates a maze base on given dimension after generation
 * remove 30% of existing walls place animals(mice and cat) to the designate
 * cell.
 */
public class Maze {

    private final String PATH;
    private final int COLUMN;
    private final int ROW;
    private Cell[][] maze;
    private ArrayList<Cell> trace;

    //current cell loc
    //for generating maze
    private int x;
    private int y;
    private ArrayList<Cell> walls; // lists all walls, for wall removal


    public Maze(int ROW, int COLUMN) {

        this.ROW = ROW;
        this.COLUMN = COLUMN;
        PATH = new String("PATH");
        maze = new Cell[ROW][COLUMN];
        trace = new ArrayList<>();
        walls = new ArrayList<>();
        x = 1;
        y = 1;
        initialize();
        populate();

    }

    public Cell getCell(int y, int x) {
        return maze[y][x];
    }

    /**
     * place mice or cat to a cell from its old location to a new location erase
     * the old occupied state if it was occupied by a cat, change mist state
     * back to true if cell is occupied by a mice, reveals its neighbor and PATH
     */
    public boolean placeAnimal(Animal animal) {
        boolean valid = false;
        int nextY = animal.getNextY();
        int nextX = animal.getNextX();
        int y2 = animal.getY();
        int x2 = animal.getX();

        if (!maze[nextY][nextX].isBorder() && !maze[nextY][nextX].isWall()) {

            if (animal instanceof Cat && maze[nextY][nextX].isCat()) {
                return false;
            }

            maze[y2][x2].setCondition("CLEAR");
            if (!maze[y2][x2].isVisited()) {
                maze[y2][x2].setMist(true);
            }
            maze[nextY][nextX].setCondition(animal.getType());
            maze[nextY][nextX].setMist(false);
            if (animal instanceof Mice) {
                maze[y2][x2].setVisit(true);
                maze[nextY][nextX].revealNeighbor();
            }
            valid = true;// object has been moved to a new location
        }
        return valid;

    }

    public void revealMist() {
        for (Cell[] cells : maze) {
            for (Cell cell : cells) {
                cell.setMist(false);
                cell.setVisit(true);
            }
        }
    }

    private void initialize() {
        // initialization------------------------
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                maze[i][j] = new Cell(i, j, COLUMN, ROW);
            }
        }
        // establish connection
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                maze[i][j].meetNeighbor(maze);
            }
        }
        maze[y][x].setCondition(PATH);// exist coordinate (1, 1)
        maze[y][x].setVisit(true);
        trace.add(maze[y][x]);
        // --------------------------------------
    }

    private void populate() {
        while (trace.size() != 0) {
            if (!maze[y][x].deadEnd()) {
                seekPath();
            } else {
                backTrack();
            }
        }
        wallRemoval(0);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                maze[i][j].setVisit(false);
            }
        }
    }

    /**
     * maze[ROW][COLUMN] y is the y axis, ROW, negative = up, positive = down x
     * is the x axis, COLUMN, negative = left, positive = right 0: up; 1: down;
     * 2: left; 3: right revisit suggest use enum
     */
    private void seekPath() {


        Random die = new Random();
        switch (die.nextInt(4)) {
            case 0:
                if (isPath(y - 1, x, y - 2, x)) {// up
                    y -= 2;
                    trace.add(maze[y][x]);
                }
                ;
                break;
            case 1:
                if (isPath(y + 1, x, y + 2, x)) {// down
                    y += 2;
                    trace.add(maze[y][x]);
                }
                ;
                break;
            case 2:
                if (isPath(y, x - 1, y, x - 2)) {// left
                    x -= 2;
                    trace.add(maze[y][x]);
                }
                ;
                break;
            case 3:
                if (isPath(y, x + 1, y, x + 2)) {// right
                    x += 2;
                    trace.add(maze[y][x]);
                }
                ;
                break;
        }
    }

    private boolean isPath(int y1, int x1, int y2, int x2) {
        if (!maze[y1][x1].isBorder() && !maze[y2][x2].isBorder()
                && !maze[y2][x2].isVisited()) {
            maze[y1][x1].setCondition(PATH);
            maze[y1][x1].setVisit(true);
            maze[y2][x2].setCondition(PATH);
            maze[y2][x2].setVisit(true);
            return true;
        } else {
            return false;
        }
    }

    private void backTrack() {
        trace.remove(trace.size() - 1);
        if (trace.size() > 0) {
            y = trace.get(trace.size() - 1).getY();
            x = trace.get(trace.size() - 1).getX();
        }
    }

    private void wallRemoval(double percentage) {
        for (Cell[] cells : maze) {
            for (Cell cell : cells) {
                if (!cell.isBorder() && cell.isWall()) {
                    walls.add(cell);
                }
            }
        }
        java.util.Collections.shuffle(walls);
        int picks = (int) (walls.size() * percentage);
        for (int i = 0; i < picks; i++) {
            walls.get(i).setCondition(PATH);
        }
    }

    public int getColumn() {
        return COLUMN;
    }

    public int getRow() {
        return ROW;
    }

}
