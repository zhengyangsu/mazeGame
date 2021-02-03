package UI;

import logic.Management;

import java.util.Scanner;

/**
 * UI class display the maze base on cell states and its priority displays game
 * states, welcome message and instructions
 */
public class Display {

    private static int row = 15;
    private static int column = 21;
    private static int catNum = 3;
    private static int cheeseNum = 5;
    private static Management game;
    private static String userInput = "";
    private static boolean inValidInput;
    private static boolean inValidDirection;

    public static void main(String[] args) {
        WELCOME();
        DIRECTIONS();
        LENGEND();
        MOVES();
        Scanner scan = new Scanner(System.in);
        game = new Management(row, column, catNum, cheeseNum);
        displayMaze();

        while (!game.over()) {
            while (!inputValid(scan.nextLine())) {
                if (inValidInput) {
                    System.out
                            .println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
                } else if (inValidDirection) {
                    System.out
                            .println("Invalid move: you cannot move through walls!");
                }
                System.out.print("Enter your move [WASD?]: ");


            }
            displayMaze();
            if (game.getCheeseFound() == cheeseNum) {
                System.out.println("\nCongratulations! You won!");
            } else if (game.isCaught()) {
                System.out.println("\nYou have been eaten!");
            }
        }
        displayMaze();
        scan.close();
    }

    private static boolean inputValid(String input) {
        if (input.matches("W|w|S|s|A|a|D|d")) {
            inValidInput = false;
            userInput = input;
            if (game.move(userInput)) {
                inValidDirection = false;
                return true;
            } else {
                inValidDirection = true;
                return false;
            }
        } else if (input.equals("?")) {
            LENGEND();
            MOVES();
            return true;
        } else if (input.equals("m")) {
            game.revealMist();
            return true;
        } else {
            inValidInput = true;
            return false;
        }
    }

    private static void displayMaze() {
        System.out.println("\n\nMaze:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                String condition = game.getMaze().getCell(i, j).getCondition();
                switch (condition) {
                    case "MICE":
                        System.out.print("@");
                        break;
                    case "CAT":
                        System.out.print("!");
                        break;
                    case "CHEESE":
                        System.out.print("$");
                        break;
                    case "MIST":
                        System.out.print(".");
                        break;
                    case "WALL":
                        System.out.print("#");
                        break;
                    case "PATH":
                        System.out.print(" ");
                        break;
                    default:
                        System.out.print("X");
                }

            }
            System.out.println();
        }
        if (!game.over()) {
            System.out.printf("Cheese collected: %d of %d\n",
                    game.getCheeseFound(), cheeseNum);
            System.out.printf("Enter your move [WASD?]: ");
        }

    }

    private static void WELCOME() {
        System.out.println("----------------------------------------"
                + "\nWelcome to Cat and Mouse Maze Adventure!"
                + "\nby Leon Su && Oscar Ping"
                + "\n----------------------------------------");
    }

    private static void DIRECTIONS() {
        System.out.println("DIRECTIONS:"
                + "\n\tFind 5 cheese before a cat eats you!");
    }

    private static void LENGEND() {
        System.out.println("LEGEND:" + "\n\t#: Wall" + "\n\t@: You (a mouse)"
                + "\n\t!: Cat" + "\n\t$: Cheese" + "\n\t.: Unexplored space");
    }

    private static void MOVES() {
        System.out.println("MOVES::"
                + "\n\tUse W (up), A (left), S (down) and D (right) to move."
                + "\n\t(You must press enter after each move).");
    }

}
