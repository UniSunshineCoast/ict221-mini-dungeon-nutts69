package dungeon.engine;

import java.util.Scanner;


public class TextUI {
    private static final String COMMAND_PROMPT =
            "Enter move (u/d/l/r), or q to quit: ";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Difficulty (0–10, default 3)? ");
        int difficulty = in.nextInt();
        System.out.print("Max steps (e.g. 100)? ");
        int max = in.nextInt();

        GameEngine engine = new GameEngine(difficulty, max);

        // game loop
        while (true) {
            System.out.printf(
                    "HP: %d  Score: %d  Steps: %d/%d  Level: %d%n",
                    engine.getPlayerHP(),
                    engine.getScore(),
                    engine.getStepsTaken(),
                    engine.getMaxSteps(),
                    engine.getLevel()
            );

            printMap(engine);

            System.out.print(COMMAND_PROMPT);
            String cmd = in.next().toLowerCase();

            if (cmd.equals("q")) {
                System.out.println("Goodbye!");
                break;
            }

            Direction dir;
            switch (cmd) {
                case "u": dir = Direction.UP;    break;
                case "d": dir = Direction.DOWN;  break;
                case "l": dir = Direction.LEFT;  break;
                case "r": dir = Direction.RIGHT; break;
                default:
                    System.out.println("Unknown command.");
                    continue;
            }

            MoveResult result = engine.move(dir);
            // Print the enum name (or you can map to a friendlier string via switch)
            System.out.println("Result: " + result);

            if (result == MoveResult.EXIT_WIN) {
                System.out.println("You escaped! Final score: " + engine.getScore());
                break;
            }
            if (engine.getPlayerHP() <= 0) {
                System.out.println("You died. Game over.");
                break;
            }
        }

        in.close();
    }


    private static void printMap(GameEngine engine) {
        GameMap map = engine.getMap();
        int level = engine.getLevel();
        int size = GameMap.SIZE;

        int pr = engine.getPlayerRow();
        int pc = engine.getPlayerCol();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (r == pr && c == pc) {
                    System.out.print("P ");
                } else {
                    MapItem item = map.getCell(level, r, c);
                    System.out.print(item.getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }
}
