package dungeon.engine;

/**
 * The tile that either advances you to the next level, or exits the dungeon if on level 2.
 */
public class LadderCell extends MapItem {
    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        // GameEngine.move() handles ladder logic itself—so just return SUCCESS here,
        // though in practice GameEngine checks instanceof LadderCell before calling interact.
        return ItemInteractionResult.SUCCESS;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "ladder.png";
    }
}
