package dungeon.engine;

public class EmptySpaceCell extends MapItem {
    @Override public ItemInteractionResult interact(GameEngine engine) {
        return ItemInteractionResult.SUCCESS;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override public String getImageFilename() {
        return "empty.png";
    }
}
