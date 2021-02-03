package logic;

/**
 * super class for mice and cat contains setter and getter for coordinates type
 * indicates which animals
 */
public abstract class Animal {

    private int y;
    private int x;
    private int nextY;
    private int nextX;
    private String Type;

    public Animal(int y, int x, String type) {
        this.Type = type;
        this.y = y;
        this.x = x;
        setNextY(y);
        setNextX(x);
    }

    public String getType() {
        return Type;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getNextY() {
        return nextY;

    }

    public int getNextX() {
        return nextX;
    }

    public void placeAnimal(int y, int x) {
        this.y = y;
        this.x = x;
    }

    protected void setNextY(int nextY) {
        this.nextY = nextY;
    }

    protected void setNextX(int nextX) {
        this.nextX = nextX;
    }
}
