package dungeon.engine;

/**
 * Melee mutant: -2 HP, +2 score, then mutant is defeated (cell turns to empty).
 */
public class MeleeMutantCell extends MapItem {
    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        engine.addHP(-2);
        engine.addScore(2);
        return ItemInteractionResult.KILLED_MELEE;
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "melee.png";
    }
}
