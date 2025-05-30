package dungeon.engine;

import java.io.Serializable;
import java.util.Random;

/**
 * Holds two 10×10 levels of map items.
 */
public class GameMap implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int SIZE = 10;

    // [levelIndex 0==level1,1==level2][row][col]
    private final MapItem[][][] cells = new MapItem[2][SIZE][SIZE];
    private final Random rng;

    public GameMap(Random rng) {
        this.rng = rng;
        clear();
    }

    /** Wipe both levels to empty floors. */
    public void clear() {
        for (int L = 0; L < 2; L++)
            for (int r = 0; r < SIZE; r++)
                for (int c = 0; c < SIZE; c++)
                    cells[L][r][c] = new EmptySpaceCell();
    }

    /**
     * Scatter walls, entry, ladder, traps, gold, mutants, potions.
     * This is a very naive example — feel free to improve!
     */
    public void buildLevelOne(int difficulty) {
        int L = 0; // always build only level-1 here; engine handles level-2 via advanceLevel()

        // 1) border walls
        for (int i = 0; i < SIZE; i++) {
            cells[L][0][i] = new WallCell();
            cells[L][SIZE-1][i] = new WallCell();
            cells[L][i][0] = new WallCell();
            cells[L][i][SIZE-1] = new WallCell();
        }

        // 2) random traps (5), gold (5), potions (2)
        placeRandom(L, new TrapCell(), 5);
        placeRandom(L, new GoldCell(), 5);
        placeRandom(L, new HealthPotionCell(), 2);

        // 3) mutants: melee 3, ranged = difficulty
        placeRandom(L, new MeleeMutantCell(), 3);
        placeRandom(L, new RangedMutantCell(), difficulty);

        // 4) entry on bottom‐left inside the wall
        cells[L][SIZE-2][1] = new EntryCell();

        // 5) ladder somewhere random not on walls
        int rr, cc;
        do {
            rr = rng.nextInt(SIZE-2) + 1;
            cc = rng.nextInt(SIZE-2) + 1;
        } while (!(cells[L][rr][cc] instanceof EmptySpaceCell));
        cells[L][rr][cc] = new LadderCell();
    }

    /** Helper to drop N copies of item into random empty spaces. */
    private void placeRandom(int L, MapItem item, int count) {
        int placed = 0;
        while (placed < count) {
            int r = rng.nextInt(SIZE-2) + 1;
            int c = rng.nextInt(SIZE-2) + 1;
            if (cells[L][r][c] instanceof EmptySpaceCell) {
                cells[L][r][c] = item;
                placed++;
            }
        }
    }

    /** Return the (r,c) of the EntryCell in the current level. */
    public int[] findEntry() {
        int L = 0;
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (cells[L][r][c] instanceof EntryCell)
                    return new int[]{r, c};
        // fallback to 1,1
        return new int[]{1,1};
    }

    /** True if that (r,c) is outside 0<=r<10 or 0<=c<10. */
    public boolean outOfBounds(int r, int c) {
        return r<0 || c<0 || r>=SIZE || c>=SIZE;
    }

    /** Read-only access for engine/textui. */
    public MapItem getCell(int level, int r, int c) {
        return cells[level-1][r][c];
    }

    /** Overwrite that cell with floor. */
    public void replaceCurrentCell(int level, int r, int c, MapItem m) {
        cells[level-1][r][c] = m;
    }
}
