package dungeon.engine;

/**
 * The starting tile on a level.  Stepping on it again just succeeds.
 */
public class EntryCell extends MapItem {
    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        return ItemInteractionResult.SUCCESS;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "entry.png";
    }
}
