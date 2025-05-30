package dungeon.engine;

/**
 * Health potion restores 4 HP (capped at 10), then disappears.
 */
public class HealthPotionCell extends MapItem {
    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        engine.addHP(4);
        return ItemInteractionResult.DRANK_POTION;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "potion.png";
    }
}
