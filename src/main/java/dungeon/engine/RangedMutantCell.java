package dungeon.engine;

import java.util.Random;

/**
 * Ranged mutant: when you step on it you always kill it (+2 score).
 * Also, it has a 50% chance to shoot you for -2 HP before dying.
 */
public class RangedMutantCell extends MapItem {
    private static final Random RNG = new Random();

    @Override
    public ItemInteractionResult interact(GameEngine engine) {
        boolean shot = RNG.nextBoolean();
        if (shot) {
            engine.addHP(-2);
            engine.addScore(2);
            return ItemInteractionResult.ATTACKED_RANGED;
        } else {
            engine.addScore(2);
            return ItemInteractionResult.KILLED_RANGED;
        }
    }
    @Override public char getSymbol() {
        return '.';
    }
    @Override
    public String getImageFilename() {
        return "ranged.png";
    }
}
