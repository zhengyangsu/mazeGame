package logic;

import java.util.ArrayList;

/**
 * Cell class the most fundamental block of the maze each cell has eight
 * neighbors 6 Occupied states order by priority Mice, cat, cheese, mist, wall,
 * path 2 properties border and visited
 */
public class Cell {

    private final int X;
    private final int Y;

    private ArrayList<Cell> neighbors;

    /**
     * cell properties
     * can have multiple properties
     * can not be wall and path at the same time
     */
    private boolean visited = false;
    private boolean border = false;
    private boolean wall;
    private boolean path;
    private boolean mice;
    private boolean cat;
    private boolean cheese;
    private boolean mist;

    public Cell(int y, int x, int column, int row) {

        setCondition("WALL");// default
        this.X = x;
        this.Y = y;
        visited = false;
        border = false;

        // ----------------
        wall = true;
        path = false;
        mice = false;
        cat = false;
        cheese = false;
        mist = true;
        // ----------------

        if (this.X == 0 || this.X == column - 1 || this.Y == 0
                || this.Y == row - 1) {
            border = true;
            visited = true;
            mist = false;
        }
        neighbors = new ArrayList<>();
    }

    /**
     * position references standard keyboard number pad
     *
     * @param maze
     */
    public void meetNeighbor(Cell[][] maze) {
        if (!isBorder()) {
            neighbors.add(maze[Y][X - 1]);// left
            neighbors.add(maze[Y][X + 1]);// right
            neighbors.add(maze[Y - 1][X]);// up
            neighbors.add(maze[Y + 1][X]);// down
            neighbors.add(maze[Y - 1][X - 1]);// Home
            neighbors.add(maze[Y + 1][X - 1]);// End
            neighbors.add(maze[Y - 1][X + 1]);// PgUp
            neighbors.add(maze[Y + 1][X + 1]);// PgDn
        }
    }


    public void revealNeighbor() {
        for (Cell cell : neighbors) {
            cell.setMist(false);
            cell.setVisit(true);
        }
    }

    public void setVisit(boolean visit) {
        visited = visit;
    }

    public void setMist(boolean mist) {
        this.mist = mist;
    }

    public void setCheese(boolean isCheese) {
        if (isCheese) {
            cheese = true;
            mist = false;
        } else {
            cheese = false;
            mist = true;
        }
    }

    public void setCondition(String type) {
        switch (type) {

            case "WALL":
                wall = true;
                path = false;
                break;
            case "PATH":
                path = true;
                wall = false;
                break;

            case "MICE":
                mice = true;
                wall = false;
                path = true;
                setMist(false);
                revealNeighbor();
                break;
            case "CAT":
                cat = true;
                wall = false;
                path = true;
                setMist(false);
                break;
            default:
                mice = false;
                cat = false;
                break;
        }
    }

    public boolean isCheese() {
        return cheese;
    }

    public boolean isMice() {
        return mice;
    }

    public boolean isCat() {
        return cat;
    }

    public boolean isWall() {
        return wall;
    }

    public boolean isBorder() {
        return border;
    }

    public boolean isVisited() {
        return visited;
    }

    public String getCondition() {
        // this indicates this cell is occupied by both cat and mice
        if (border) {
            return "BORDER";
        } else if (mice && cat) {
            return "X";
        } else if (mice) {
            return "MICE";
        } else if (cat) {
            return "CAT";
        } else if (cheese) {
            return "CHEESE";
        } else if (mist) {
            return "MIST";
        } else if (wall) {
            return "WALL";
        } else if (path) {
            return "PATH";
        } else {
            return "UNKNOWN";
        }
    }

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }

    public boolean deadEnd() {// when dead end returns true, there is no more
        // movable direction
        boolean deadEnd = (neighbors.get(0).isBorder() || neighbors.get(0)
                .getLeft().isVisited())
                && (neighbors.get(1).isBorder() || neighbors.get(1).getRight()
                .isVisited())
                && (neighbors.get(2).isBorder() || neighbors.get(2).getUp()
                .isVisited())
                && (neighbors.get(3).isBorder() || neighbors.get(3).getDown()
                .isVisited());
        if (deadEnd) {
            return true;
        } else {
            return false;
        }
    }

    private Cell getLeft() {
        return neighbors.get(0);
    }

    private Cell getRight() {
        return neighbors.get(1);
    }

    private Cell getUp() {
        return neighbors.get(2);
    }

    private Cell getDown() {
        return neighbors.get(3);
    }

}
