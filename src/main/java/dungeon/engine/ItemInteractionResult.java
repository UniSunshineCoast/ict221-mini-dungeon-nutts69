package dungeon.engine;

/**
 * The result of stepping on/interacting with a MapItem.
 */
public enum ItemInteractionResult {
    SUCCESS,          // moved with no special effect
    NO_MOVE,          // couldn't move (wall, out‐of‐bounds, no steps left)
    LEVEL_UP,         // stepped on ladder on level 1
    EXIT_WIN,         // stepped on ladder on level 2 and won
    GOT_GOLD,         // picked up gold
    DRANK_POTION,     // drank a health potion
    TRAPPED,          // fell into a trap
    KILLED_MELEE,     // fought and killed a melee mutant
    KILLED_RANGED,    // killed a ranged mutant without getting hit
    ATTACKED_RANGED;  // fought a ranged mutant but took damage first

    /**
     * Convert this to the MoveResult that RunGame/GUI expects.
     */
    public MoveResult toMoveResult() {
        switch (this) {
            case SUCCESS:         return MoveResult.SUCCESS;
            case NO_MOVE:         return MoveResult.NO_MOVE;
            case LEVEL_UP:        return MoveResult.LEVEL_UP;
            case EXIT_WIN:        return MoveResult.EXIT_WIN;
            case GOT_GOLD:        return MoveResult.GOT_GOLD;
            case DRANK_POTION:    return MoveResult.DRANK_POTION;
            case TRAPPED:         return MoveResult.TRAPPED;
            case KILLED_MELEE:    return MoveResult.KILLED_MELEE;
            case KILLED_RANGED:   return MoveResult.KILLED_RANGED;
            case ATTACKED_RANGED: return MoveResult.ATTACKED_RANGED;
            default:              return MoveResult.SUCCESS;
        }
    }
}
