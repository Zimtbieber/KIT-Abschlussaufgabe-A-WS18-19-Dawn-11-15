package edu.kit.informatik;

/**
 * This class represents the game stone.
 * 
 * @author
 * @version 1.0
 */
public class GameStone {

    private int stoneLength;

    /**
     * Sets the length of the game-stone
     * 
     * @param stoneLength sets the length
     */
    public GameStone(final int stoneLength) {
        this.stoneLength = stoneLength;
    }

    /**
     * 
     * @return the length of the game-stone.
     */
    public int getStoneLength() {
        return stoneLength;
    }
}
