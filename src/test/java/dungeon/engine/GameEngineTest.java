package dungeon.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    private GameEngine engine;

    @BeforeEach
    void setUp() {
        // start with difficulty=0 (no ranged mutants) and maxSteps=10 for predictability
        engine = new GameEngine(0, 10);
    }

    @Test
    void testInitialStats() {
        assertEquals(10, engine.getPlayerHP(),    "Player should start with 10 HP");
        assertEquals(0,  engine.getScore(),       "Score starts at 0");
        assertEquals(0,  engine.getStepsTaken(),  "No steps taken at start");
        assertEquals(1,  engine.getLevel(),       "Should start on level 1");
        assertTrue(engine.getStepsTaken() < engine.getMaxSteps());
    }

    @Test
    void testSimpleMoveAndStepCount() {
        // guarantee an empty map around the entry for this test:
        // move up, then down — those both count as steps but should succeed
        MoveResult r1 = engine.move(Direction.UP);
        assertEquals(MoveResult.SUCCESS, r1);
        assertEquals(1, engine.getStepsTaken());

        MoveResult r2 = engine.move(Direction.DOWN);
        assertEquals(MoveResult.SUCCESS, r2);
        assertEquals(2, engine.getStepsTaken());
    }

    @Test
    void testWallBlocksMove() {
        // we know walls border the edges; force the player against an edge:
        // try moving left (if entry is at col=0) or right otherwise
        int startCol = engine.getPlayerCol();
        Direction dir = startCol == 0 ? Direction.LEFT : Direction.RIGHT;
        MoveResult res = engine.move(dir);
        assertEquals(MoveResult.NO_MOVE, res, "Hitting a wall must return NO_MOVE");
        assertEquals(0, engine.getStepsTaken(), "Blocked moves should not increment steps");
    }

    @Test
    void testHPDecreaseOnTrap() {
        // rebuild a map with a trap next to the entry:
        GameMap map = engine.getMap();
        int r = engine.getPlayerRow();
        int c = engine.getPlayerCol();
        map.replaceCurrentCell(1, r, c+1, new TrapCell());

        MoveResult res = engine.move(Direction.RIGHT);
        assertEquals(MoveResult.TRAPPED, res);
        assertEquals(8, engine.getPlayerHP(), "Trap should cost 2 HP");
        assertEquals(1, engine.getStepsTaken());
    }

    @Test
    void testCollectGold() {
        GameMap map = engine.getMap();
        int r = engine.getPlayerRow();
        int c = engine.getPlayerCol();
        map.replaceCurrentCell(1, r, c+1, new GoldCell());

        MoveResult res = engine.move(Direction.RIGHT);
        assertEquals(MoveResult.GOT_GOLD, res);
        assertEquals(2, engine.getScore(), "Gold should add 2 to score");
    }

    @Test
    void testHealthPotion() {
        engine.addHP(-5);  // drop to 5
        assertEquals(5, engine.getPlayerHP());

        GameMap map = engine.getMap();
        int r = engine.getPlayerRow();
        int c = engine.getPlayerCol();
        map.replaceCurrentCell(1, r, c+1, new HealthPotionCell());

        MoveResult res = engine.move(Direction.RIGHT);
        assertEquals(MoveResult.DRANK_POTION, res);
        assertEquals(9, engine.getPlayerHP(), "Potion restores 4 HP, capped at 10");
    }

    @Test
    void testLevelUpAndExit() {
        // sprinkle a LadderCell next to player
        GameMap map = engine.getMap();
        int r = engine.getPlayerRow();
        int c = engine.getPlayerCol();
        map.replaceCurrentCell(1, r, c+1, new LadderCell());

        MoveResult up = engine.move(Direction.RIGHT);
        assertEquals(MoveResult.LEVEL_UP, up);
        assertEquals(2, engine.getLevel());

        // now place another ladder to exit
        r = engine.getPlayerRow();
        c = engine.getPlayerCol();
        map.replaceCurrentCell(2, r, c+1, new LadderCell());
        MoveResult exit = engine.move(Direction.RIGHT);
        assertEquals(MoveResult.EXIT_WIN, exit);
    }
}
