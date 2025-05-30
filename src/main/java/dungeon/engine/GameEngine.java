package dungeon.engine;

import java.util.Random;

/**
 * Core engine for MiniDungeon.
 */
public class GameEngine {
    public static final int SIZE = 10;

    private final int difficulty;
    private final int maxSteps;
    private GameMap map;
    private int level;
    private int playerR, playerC;
    private int hp;
    private int score;
    private int stepsTaken;

    /** Default ctor: difficulty=3, maxSteps=100 */
    public GameEngine() {
        this(3, 100);
    }

    /**
     * Main ctor.
     *
     * @param difficulty how many ranged mutants in level 1 (d), plus 2 per level
     * @param maxSteps maximum allowed steps
     */
    public GameEngine(int difficulty, int maxSteps) {
        this.difficulty = difficulty;
        this.maxSteps = maxSteps;
        initNewGame();
    }

    /** (Re)initialise everything for a brand-new game. */
    private void initNewGame() {
        this.level = 1;
        this.hp = 10;
        this.score = 0;
        this.stepsTaken = 0;
        this.map = new GameMap(new Random());
        buildLevelOne();
    }

    /** Build Level 1 using initial difficulty. */
    private void buildLevelOne() {
        map.clear();
        map.buildLevelOne(difficulty);
        placePlayerAtEntry();
    }

    /** Build Level 2 (same algo, but harder). */
    private void buildLevelTwo() {
        map.clear();
        map.buildLevelOne(difficulty + 2);
        placePlayerAtEntry();
    }

    private void placePlayerAtEntry() {
        int[] entry = map.findEntry();
        playerR = entry[0];
        playerC = entry[1];
    }

    /** Advance from Level 1 to Level 2. */
    public void advanceLevel() {
        this.level = 2;
        buildLevelTwo();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Public Getters for GUI / TextUI
    // ─────────────────────────────────────────────────────────────────────────

    public GameMap getMap() {
        return map;
    }

    /** 1 or 2 */
    public int getLevel() {
        return level;
    }

    public int getPlayerRow() {
        return playerR;
    }

    public int getPlayerCol() {
        return playerC;
    }

    public int getPlayerHP() {
        return hp;
    }

    public int getScore() {
        return score;
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Movement & Interaction Logic
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Try to move in the given direction.
     * @return what happened (no-move, success, level-up, exit)
     */
    public MoveResult move(Direction dir) {
        // out of moves?
        if (stepsTaken >= maxSteps) {
            return MoveResult.NO_MOVE;
        }

        int nr = playerR + dir.dr;
        int nc = playerC + dir.dc;

        // blocked by border?
        if (map.outOfBounds(nr, nc)) {
            return MoveResult.NO_MOVE;
        }
        MapItem target = map.getCell(level, nr, nc);
        // blocked by wall?
        if (target instanceof WallCell) {
            return MoveResult.NO_MOVE;
        }

        // stepping on ladder?
        if (target instanceof LadderCell) {
            if (level == 1) {
                advanceLevel();
                return MoveResult.LEVEL_UP;
            } else {
                return MoveResult.EXIT_WIN;
            }
        }

        // otherwise interact with whatever's there
        stepsTaken++;
        ItemInteractionResult ir = target.interact(this);
        // clear the item (except walls & ladder which we handled above)
        map.replaceCurrentCell(level, nr, nc, new EmptySpaceCell());
        playerR = nr;
        playerC = nc;
        return ir.toMoveResult();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Helpers for cells to call
    // ─────────────────────────────────────────────────────────────────────────

    /** add HP (capped at 10) */
    public void addHP(int delta) {
        hp = Math.min(10, hp + delta);
    }
    /** add to score */
    public void addScore(int delta) {
        score += delta;
    }
}
