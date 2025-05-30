package dungeon.engine;

/**
 * The possible outcomes returned to the caller of GameEngine.move().
 */
public enum MoveResult {
    SUCCESS,         // moved with no special effect
    NO_MOVE,         // movement blocked (wall/out‐of‐bounds/no steps left)
    LEVEL_UP,        // reached ladder on level 1
    EXIT_WIN,        // reached ladder on level 2 (game win)
    GOT_GOLD,        // picked up gold
    DRANK_POTION,    // drank a health potion
    TRAPPED,         // fell into a trap
    KILLED_MELEE,    // defeated a melee mutant
    KILLED_RANGED,   // defeated a ranged mutant without being hit
    ATTACKED_RANGED  // defeated a ranged mutant but took damage first
}
