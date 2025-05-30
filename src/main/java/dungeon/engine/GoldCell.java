package dungeon.engine;

/**
 * Gold gives +2 score then disappears.
 */
public class GoldCell extends MapItem {
    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        engine.addScore(2);
        return ItemInteractionResult.GOT_GOLD;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "gold.png";
    }
}
