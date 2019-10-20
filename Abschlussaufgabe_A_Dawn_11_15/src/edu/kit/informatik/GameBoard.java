package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents the game board.
 * 
 * @author 
 * @version 1.0
 */
public class GameBoard {

    private String[][] gameBoard = new String[Constant.BOARD_WIDTH][Constant.BOARD_LENGTH];
    private Stack<int[]> neighborStack = new Stack<>();
    private ArrayList<int[]> alreadyVisited = new ArrayList<>();
    private int freeMoves;

    /**
     * Initializes the board.
     */
    public void initializeGameBoard() {
        for (int n = 0; n < gameBoard.length; n++) {
            for (int m = 0; m < gameBoard[0].length; m++) {
                gameBoard[n][m] = Constant.EMPTY_FIELD;
            }
        }
    }

    /**
     * Method to get the current state of a field on the board.
     * 
     * @param x - coordinate
     * @param y - coordinate
     * @return the value on this field
     */
    public String fieldState(final int x, final int y) {
        return gameBoard[x][y];
    }
    
    /**
     * Method to place a stone (MissionControll and Nature)
     * 
     * @param coordinates array with coordinates of the to placed stone
     * @param stoneSymbol the kind of stone which should be placed
     * @return an array with {-1,-1} if the field is empty‚ otherwise the occupied coordinates
     */
    public int[] placeStone(final int[] coordinates, final String stoneSymbol) {

        int[] occupiedFieldArr = {Constant.UNOCCUPIED_FIELD, Constant.UNOCCUPIED_FIELD};

        int x1 = coordinates[0];
        int y1 = coordinates[1];

        if (coordinates.length > 2) {

            int x2 = coordinates[2];
            int y2 = coordinates[3]; 

            boolean placeChecked = false;

            // first time check if fields are free, then write
            for (int k = 0; k <= 1; k++) {
                for (int n = x1; n <= x2; n++) {
                    for (int m = y1; m <= y2; m++) {
                        if (n >= Constant.MIN_INDEX_BOARD && n <= Constant.MAX_INDEX_WIDTH 
                            && m >= Constant.MIN_INDEX_BOARD && m <= Constant.MAX_INDEX_LENGTH) {
                            if (!fieldIsEmpty(n, m)) {
                                occupiedFieldArr[0] = n;
                                occupiedFieldArr[1] = m;
                                return occupiedFieldArr;
                            }
                            if (placeChecked) {
                                gameBoard[n][m] = stoneSymbol;
                            }
                        }
                    }
                }
                placeChecked = true;
            }
        } else {
            if (fieldIsEmpty(x1, y1)) {
                gameBoard[x1][y1] = stoneSymbol;
            } else {
                occupiedFieldArr[0] = x1;
                occupiedFieldArr[1] = y1;
            }
        }
        return occupiedFieldArr;
    }

    /**
     * Get the neighbors of a stone and look if it can move.
     * 
     * @param stoneSymbol the current symbol of Ceres or Vesta
     * @return true if the stone can move
     */
    public boolean canMove(final String stoneSymbol) {
        int[] startSymbol = getNatureStoneCoord(stoneSymbol);       
        String[] neighbors = surroundedBy(startSymbol);
        int occupiedFieldCounter = 0;

        for (String el : neighbors) {
            if (!el.equals(Constant.EMPTY_FIELD)) {
                occupiedFieldCounter++;
            }
        }
        if (occupiedFieldCounter == Constant.MAX_NEIGHBORS) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the move-sequence is legitimate (coherent coordinate sequence).
     * 
     * @param coordinate of the move-sequence
     * @param stoneSymbol the current symbol of Ceres or Vesta
     * @return true if the sequence is okay
     */
    public boolean properMoveSet(final int[] coordinate, final String stoneSymbol) {

        int[] startSymbol = getNatureStoneCoord(stoneSymbol);

        // checks if the first move-coordinate is next to the start symbol
        if (!((Math.abs(startSymbol[0] - coordinate[0]) == 1 
              && Math.abs(startSymbol[1] - coordinate[1]) == 0) 
              || (Math.abs(startSymbol[0] - coordinate[0]) == 0 
              && Math.abs(startSymbol[1] - coordinate[1]) == 1))) {
            return false;
        } else {
            // iterates through the coordinate array and checks if the values are next to each other
            int spaceX;
            int spaceY;
            
            // i+2 and i+3 because the coordinates has the form {x1,y1,...,xn,yn}
            for (int i = 0; i + 3 < coordinate.length; i += 2) {

                spaceX = Math.abs(coordinate[i] - coordinate[i + 2]);
                spaceY = Math.abs(coordinate[i + 1] - coordinate[i + 3]);

                if (!(spaceX == 1 || spaceY == 1)) {
                    return false;
                } else if (spaceX == spaceY) {
                    return false;
                }
            }   
        }
        return true;
    }

    /**
     * Checks if a coordinate sequence contains a occupied field.
     * 
     * @param coords of to placed stone
     * @param stoneSymbol the current symbol of Ceres or Vesta
     * @return the occupied field
     */
    public int[] fieldsAreFree(final int[] coords, final String stoneSymbol) {
        int[] occupiedField = {Constant.UNOCCUPIED_FIELD, Constant.UNOCCUPIED_FIELD};

        for (int i = 0; i + 2 < coords.length; i += 2) {

            if (!(fieldIsEmpty(coords[i], coords[i + 1]) || gameBoard[coords[i]][coords[i + 1]].equals(stoneSymbol))) {
                occupiedField[0] = coords[i];
                occupiedField[1] = coords[i + 1];
                return occupiedField;
            }
        }
        return occupiedField;
    }

    /**
     * Moves the stone to another field.
     * 
     * @param coords of the move-sequence
     * @param stoneSymbol the current symbol of Ceres or Vesta
     */
    public void moveStone(final int[] coords, final String stoneSymbol) {
        int[] stoneCoord = getNatureStoneCoord(stoneSymbol);
        int xOld = stoneCoord[0];
        int yOld = stoneCoord[1];

        int xNew = coords[coords.length - 2];
        int yNew = coords[coords.length - 1];
        gameBoard[xOld][yOld] = Constant.EMPTY_FIELD;
        gameBoard[xNew][yNew] = stoneSymbol;
    }

    /**
     * Method to get the current location of Vesta or Ceres.
     * 
     * @param stoneSymbol the current symbol of Ceres or Vesta
     * @return the coordinate of the nature stone
     */
    public int[] getNatureStoneCoord(final String stoneSymbol) {
        int[] coord = new int[2];
        for (int n = 0; n < gameBoard.length; n++) {
            for (int m = 0; m < gameBoard[0].length; m++) {
                if (gameBoard[n][m].equals(stoneSymbol)) {
                    coord[0] = n;
                    coord[1] = m;
                    return coord;
                }
            }
        }
        return coord;
    }
    
    /**
     * Checks if the stone is horizontal or vertical placeable.
     * 
     * @param stoneLength length of the to placed stone
     * @return true if the requested stone length is placeable
     */
    public boolean isPlaceable(final int stoneLength) {

        String toCheck = "";
        String diceLength = "";

        for (int t = 0; t < stoneLength; t++) {
            diceLength += "-";
        }

        // (horizontal) 
        for (int n = 0; n < gameBoard.length; n++) {
            toCheck = "";
            for (int m = 0; m < gameBoard[0].length; m++) {
                toCheck += gameBoard[n][m];
            }
            if (toCheck.contains(diceLength) 
                    || ((stoneLength == Constant.DAWN && toCheck.startsWith(Constant.EMPTY_FIELD)) 
                    || (stoneLength == Constant.DAWN && toCheck.endsWith(Constant.EMPTY_FIELD)))) {
                return true;
            }
        }
        // vertical 
        for (int m = 0; m < gameBoard[0].length; m++) {
            toCheck = "";
            for (int n = 0; n < gameBoard.length; n++) {
                toCheck += gameBoard[n][m];
            }
            if (toCheck.contains(diceLength) 
                 || ((stoneLength == Constant.DAWN && toCheck.startsWith(Constant.EMPTY_FIELD)) 
                 || (stoneLength == Constant.DAWN && toCheck.endsWith(Constant.EMPTY_FIELD)))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Used for printing the game.
     * 
     * @return the game board as a String.
     */
    public String toString() {
        String board = "";
        for (int n = 0; n < gameBoard.length; n++) {
            for (int m = 0; m < gameBoard[0].length; m++) {
                board += gameBoard[n][m];
            }
            board += "\n";
        }
        board = board.substring(0, board.length() - 1);
        return board;
    }

    /**
     * Calculates the moveable fields which has a Nature-stone left.
     * Starts at Nature-stone, check if these coordinates are in the alreadyVisited array,
     * if not move neighbors in the neighborStack, iterate through the neighbors.
     * 
     * @param vestaCeres the symbol of Ceres or Vesta
     * @return a integer result
     */
    public int freeFieldsToMove(final String vestaCeres) {
        
        neighborStack.clear();
        alreadyVisited.clear();
        freeMoves = 0;
        
        int[] vestaCeresCoord = getNatureStoneCoord(vestaCeres);

        // Neighbors from V/C
        surroundedBy(vestaCeresCoord);
          
        while (!neighborStack.isEmpty()) {
            
            if (alreadyVisited()) {
                neighborStack.pop();
            } else {
                alreadyVisited.add(neighborStack.lastElement());
                surroundedBy(neighborStack.pop());
            }
        }
        freeMoves = alreadyVisited.size(); 
        return freeMoves;
    }
    
    // checks the fields around a coordinate
    private String[] surroundedBy(final int[] coord) {
        
        int x = coord[0];
        int y = coord[1];
        
        String[] neighbors = new String[Constant.MAX_NEIGHBORS];
        
        if (x >= Constant.MIN_INDEX_BOARD + 1 && x <= Constant.MAX_INDEX_WIDTH) {
             
            if (fieldState(x - 1, y).equals(Constant.EMPTY_FIELD)) {
                addToStack(x - 1, y);
            }
            neighbors[0] = fieldState(x - 1, y);
        } else {
            neighbors[0] = Constant.OUT_OF_FIELD;
        }
        if (y >= Constant.MIN_INDEX_BOARD && y <= Constant.MAX_INDEX_LENGTH - 1) { 
            if (fieldState(x, y + 1).equals(Constant.EMPTY_FIELD)) {
                addToStack(x, y + 1);
            }
            neighbors[1] = fieldState(x, y + 1);
        } else {
            neighbors[1] = Constant.OUT_OF_FIELD;
        }
        
        if (x >= Constant.MIN_INDEX_BOARD && x <= Constant.MAX_INDEX_WIDTH - 1) {
            if (fieldState(x + 1, y).equals(Constant.EMPTY_FIELD)) {
                addToStack(x + 1, y);
            }
            neighbors[2] = fieldState(x + 1, y);
        } else {
            neighbors[2] = Constant.OUT_OF_FIELD;
        }
        
        if (y >= Constant.MIN_INDEX_BOARD + 1 && y <= Constant.MAX_INDEX_LENGTH) {
            if (fieldState(x, y - 1).equals(Constant.EMPTY_FIELD)) {
                addToStack(x, y - 1);
            }
            neighbors[3] = fieldState(x, y - 1);
        } else {
            neighbors[3] = Constant.OUT_OF_FIELD;
        }
        return neighbors;
    }
    
    
    private boolean fieldIsEmpty(final int x, final int y) {
        if (fieldState(x, y).equals(Constant.EMPTY_FIELD)) {
            return true;
        } 
        return false;
    }

    private void addToStack(final int x, final int y) {
        int[] neighbourCoord = {x, y};
        neighborStack.add(neighbourCoord);
    }
    
    private boolean alreadyVisited() {
        int xStack = neighborStack.lastElement()[0];
        int yStack = neighborStack.lastElement()[1];
        
        for (int[] el : alreadyVisited) {
            if (el[0] == xStack && el[1] == yStack) {
                return true;
            }
        }
        return false;
    }    
}