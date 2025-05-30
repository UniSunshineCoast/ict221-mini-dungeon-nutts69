package dungeon.engine;

/**
 * Base class for any cell on the map.  Subclasses must
 * provide a one‐character symbol for TextUI and an image filename for GUI.
 */
public abstract class MapItem {
    /**
     * Interact with this cell.  The engine will then replace it
     * with an EmptySpaceCell if necessary.
     */
    public abstract ItemInteractionResult interact(GameEngine engine);

    /** A single character to print in TextUI. */
    public abstract char getSymbol();

    /** Filename of the 32×32px PNG to show in the GUI. */
    public abstract String getImageFilename();
}
