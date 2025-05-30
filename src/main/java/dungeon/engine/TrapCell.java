package dungeon.engine;

/**
 * Trap removes 2 HP each time you step on it; it stays active.
 */
public class TrapCell extends MapItem {
    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        engine.addHP(-2);
        return ItemInteractionResult.TRAPPED;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "trap.png";
    }
}
