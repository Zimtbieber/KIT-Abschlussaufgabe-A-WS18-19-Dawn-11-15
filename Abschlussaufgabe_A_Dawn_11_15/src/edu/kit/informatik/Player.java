package edu.kit.informatik;

/**
 * This class represents a player.
 * 
 * @author 
 * @version 1.0
 */
public class Player {

    private int[] stoneSet;

    /**
     * To initialize a stone with a certain length.
     * 
     * @param stoneSet sets a players stone set
     */
    public Player(final int[] stoneSet) {
        this.stoneSet = stoneSet;
    }

    /**
     * Checks if a stone is in the set.
     * 
     * @param stoneLength a
     * @return true if the stone is present
     */
    public boolean stoneSetContainsStone(final int stoneLength) {
        for (int stone : stoneSet) {
            if (stone == stoneLength) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a stone from the stone set.
     * 
     * @param stoneLength the stone to remove
     */
    public void removeStone(final int stoneLength) {
        for (int i = 0; i < stoneSet.length; i++) {
            if (stoneSet[i] == stoneLength) {
                stoneSet[i] = 0;
            }
        }
    }

    /**
     * To get the stone set of a player.
     * 
     * @return the stone set
     */
    public int[] getStoneSet() {
        return stoneSet;
    }

    /**
     * Sets a stone set.
     * 
     * @param stoneSet the to set stone set
     */
    public void setStoneSet(final int[] stoneSet) {
        this.stoneSet = stoneSet;
    }

}
