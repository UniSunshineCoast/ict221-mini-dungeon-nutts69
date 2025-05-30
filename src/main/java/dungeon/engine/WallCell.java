package dungeon.engine;

/**
 * A wall blocks movement entirely.  We should never call interact() on a wall,
 * because GameEngine.move() checks for walls and returns NO_MOVE before calling interact.
 */
public class WallCell extends MapItem {
    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        // shouldn’t ever be called—engine blocks walls before interact
        return ItemInteractionResult.NO_MOVE;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "wall.png";
    }
}
