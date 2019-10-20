package edu.kit.informatik;

import edu.kit.informatik.exception.IllegalMoveException;

/*
 * This class represents the game manager which handels the game phases 
 * and checks if a certain move is allowed.
 * 
 * @author 
 * @version 1.0
 */
public class GameManager {

    private boolean initialize;
    private boolean phaseOne;
    private boolean phaseTwo;
    private boolean actionI;
    private boolean actionII;
    private boolean actionIII;
    private boolean gameFinished;
    private int roundCounter;

    private GameStone ceres = new GameStone(1);
    private GameStone vesta = new GameStone(1);
    private GameStone two   = new GameStone(2);
    private GameStone three = new GameStone(3);
    private GameStone four  = new GameStone(4);
    private GameStone five  = new GameStone(5);
    private GameStone six   = new GameStone(6);
    private GameStone dawn  = new GameStone(7);

    private int[] stoneSetNature = new int[2];
    private int[] stoneSetMisCon = new int[6];
    private int diceSymbol;
    private int stoneLengthMisCon;
    private Player nature;
    private Player misCon;
    private GameBoard gameBoard = new GameBoard();

    /**
     * Initializes the games board, players, stones and phases
     */
    public GameManager() {
        gameBoard.initializeGameBoard();
        initializeStoneSet();
        initializePlayer();
        initializePhase(); 
    }

    // initialize
    private void initializeStoneSet() {
        stoneSetNature[0] = ceres.getStoneLength();
        stoneSetNature[1] = vesta.getStoneLength();
        stoneSetMisCon[0] = two.getStoneLength();
        stoneSetMisCon[1] = three.getStoneLength();
        stoneSetMisCon[2] = four.getStoneLength();
        stoneSetMisCon[3] = five.getStoneLength();
        stoneSetMisCon[4] = six.getStoneLength();
        stoneSetMisCon[5] = dawn.getStoneLength();

    }
    private void initializePlayer() {
        nature = new Player(stoneSetNature);
        misCon = new Player(stoneSetMisCon);
    }
    private void initializePhase() {
        initialize = true;
        phaseOne = true;
        phaseTwo = false;
        actionI = false;
        actionII = false;
        actionIII = false;
        gameFinished = false;
        roundCounter = 1;
    }

    /**
     * To get the current state of a field.
     * 
     * @param coordinat coordinate on the game board
     * @return the value of the coordinate
     */
    public String getState(final int[] coordinat) {
        int x = coordinat[0];
        int y = coordinat[1];
        return gameBoard.fieldState(x, y);
    }

    /**
     * To print the game board.
     * 
     * @return the game board as a String
     */
    public String printBoard() {
        return gameBoard.toString();
    }

    /**
     * Game phase 'Initialize'
     * In this phase nature can place a stone on the board.
     * 
     * @param coordinate coordinate of the chosen cell
     * @throws IllegalMoveException  - if the command isn't allowed yet
     *                               - if the field is occupied
     */
    public void setVC(final int[] coordinate) throws IllegalMoveException {
        
        // skip action iii) if no move from nature stone is possible
        if (!gameBoard.canMove(getCurrentPhaseSymbol()) && !initialize) {
            changeGamePhase();
        }
        if (initialize && !gameFinished) {
            int [] occupiedField = gameBoard.placeStone(coordinate, getCurrentPhaseSymbol());
            if (!(occupiedField[0] == Constant.UNOCCUPIED_FIELD)) {
                throw new IllegalMoveException("field " + occupiedField[0] + ";" 
                        + occupiedField[1] + " is occupied.");
            }
        } else {
            throw new IllegalMoveException(Constant.ERR_ILLIGAL_MOVE);
        }
        changeGamePhase();
    }


    /**
     * Game phase 'Action I'
     * 
     * @param diceEye the chosen dice symbol
     * @throws IllegalMoveException if the command isn't allowed yet 
     */
    public void rollDice(final int diceEye) throws IllegalMoveException {
        
        // skip action iii) if no move from nature stone is possible
        if (!gameBoard.canMove(getCurrentPhaseSymbol()) && !actionI) {
            changeGamePhase();
        }
        if (actionI && !gameFinished) {
            this.diceSymbol = diceEye;
        } else {
            throw new IllegalMoveException(Constant.ERR_ILLIGAL_MOVE);
        }
        changeGamePhase();
    }

    /**
     * Game phase 'Action II'
     * 
     * @param stoneCoordinates coordinates of the stone
     * @param stoneLengthMisCon length of the stone
     * @throws IllegalMoveException - if the command isn't allowed yet 
     *                              - if the chosen length is allowed
     *                              - if the coordinates are on the board
     *                              - if the field is occupied
     *                              - if the stone is even placeable
     */
    public void placeStone(final int[] stoneCoordinates, final int stoneLengthMisCon) throws IllegalMoveException {
        String error = "";
        this.stoneLengthMisCon = stoneLengthMisCon;
        
        if (actionII && !gameFinished) {
            // it could be one stone be placeable or the player could choose between two
            int allowedOne = 0;
            int allowedTwo = 0;
            
            if (misCon.stoneSetContainsStone(diceSymbol)) {
                allowedOne = diceSymbol;
            } else {

                // checks which stones are in stoneSet and writes the placeable ones in the variables
                for (int i = 0; i < misCon.getStoneSet().length; i++) {
                    if (diceSymbol - i >= Constant.DICE_SYMBOL_MIN && allowedOne == 0 
                            && misCon.stoneSetContainsStone(diceSymbol - i)) {
                        allowedOne = diceSymbol - i;
                    } 
                    if (i + diceSymbol <= Constant.DAWN && allowedTwo == 0 
                            && misCon.stoneSetContainsStone(diceSymbol + i)) {
                        allowedTwo = diceSymbol + i;
                    }
                }
            }
            if (!(stoneLengthMisCon == allowedOne || stoneLengthMisCon == allowedTwo)) {
                if (allowedOne != 0 && allowedTwo == 0) {
                    error = "only stone with length " + allowedOne + " is placeable.";
                } else if (allowedTwo != 0 && allowedOne == 0) {
                    error = "only stone with length " + allowedTwo + " is placeable.";
                } else if (!(allowedOne == 0 && allowedTwo == 0)) {
                    error = "only stone with length " + allowedOne + " or " 
                            + allowedTwo + " is placeable.";
                }  
                throw new IllegalMoveException(error);
            } else {
                if (gameBoard.isPlaceable(stoneLengthMisCon)) {

                    // if stone length is 7 then check if the stone is in field
                    if (!(stoneLengthMisCon == dawn.getStoneLength())) {
                        if (!(stoneCoordinates[0] >= Constant.MIN_INDEX_BOARD 
                           && stoneCoordinates[0] <= Constant.MAX_INDEX_WIDTH 
                           && stoneCoordinates[2] >= Constant.MIN_INDEX_BOARD 
                           && stoneCoordinates[2] <= Constant.MAX_INDEX_WIDTH 
                           && stoneCoordinates[1] >= Constant.MIN_INDEX_BOARD 
                           && stoneCoordinates[1] <= Constant.MAX_INDEX_LENGTH
                           && stoneCoordinates[3] >= Constant.MIN_INDEX_BOARD 
                           && stoneCoordinates[3] <= Constant.MAX_INDEX_LENGTH)) {
                            throw new IllegalMoveException("Stone is not in field.");
                        }
                    }

                    int [] occupiedField = gameBoard.placeStone(stoneCoordinates, Constant.MISSION_CONTROL);
                    if (!(occupiedField[0] == Constant.UNOCCUPIED_FIELD)) {
                        throw new IllegalMoveException("field " + occupiedField[0] + ";" 
                                + occupiedField[1] + " is occupied.");
                    }
                } else {
                    throw new IllegalMoveException("this StoneLength is not placeable and the game" 
                            + " not finishable. Please reset the game;");
                }
            }
        } else {
            throw new IllegalMoveException(Constant.ERR_ILLIGAL_MOVE);
        }
        misCon.removeStone(stoneLengthMisCon);
        changeGamePhase();
    }

    /**
     * Game phase 'Action III'
     * 
     * @param coordinates array of a coordinate sequence
     * @throws IllegalMoveException - if the command isn't allowed yet 
     *                              - if the sequence is connected
     *                              - if the sequence starts at the same position as the stone
     *                              - if the length of the move isn't allowed
     */
    public void move(final int[] coordinates) throws IllegalMoveException {

        if (actionIII && !gameFinished) {
            if (gameBoard.canMove(getCurrentPhaseSymbol())) {

                // the length has to be between 1 and the placed mission-control stone length
                int moveAmount = coordinates.length / 2;
                if (moveAmount >= 1 && moveAmount <= stoneLengthMisCon) {

                    // the sequence couldn't start on nature-stone
                    if (!(coordinates[0] == gameBoard.getNatureStoneCoord(getCurrentPhaseSymbol())[0] 
                            && coordinates[1] == gameBoard.getNatureStoneCoord(getCurrentPhaseSymbol())[1])) {

                        if (gameBoard.properMoveSet(coordinates, getCurrentPhaseSymbol())) {

                            int[] occupiedField = gameBoard.fieldsAreFree(coordinates, 
                                    getCurrentPhaseSymbol());
                            if (occupiedField[0] == Constant.UNOCCUPIED_FIELD) {
                                gameBoard.moveStone(coordinates, getCurrentPhaseSymbol());
                            } else {
                                throw new IllegalMoveException("field " + occupiedField[0] 
                                        + ";" + occupiedField[1] + " is occupied.");
                            }
                        } else {
                            throw new IllegalMoveException("the move sequence must be connected.");
                        }
                    } else {
                        throw new IllegalMoveException("move sequence couldn't start at the same "
                                + "position as the nature stone.");
                    }
                } else {
                    throw new IllegalMoveException("move length had to be between 1 and " + stoneLengthMisCon + ".");
                }

            } else {
                changeGamePhase();
                throw new IllegalMoveException("stone couldn't move.");
            }

        } else {
            throw new IllegalMoveException(Constant.ERR_ILLIGAL_MOVE);
        }
        changeGamePhase();
    }

    /**
     * Shows the result of the game.
     * @return the calculated result
     * @throws IllegalMoveException - if the command isn't allowed yet 
     */
    public int showResult() throws IllegalMoveException {
        // skip action iii) if no move from nature stone is possible
        if (!gameBoard.canMove(getCurrentPhaseSymbol()) && !gameFinished) {
            changeGamePhase();
        }
        if (gameFinished) {
            int freeFieldsVesta = gameBoard.freeFieldsToMove(Constant.VESTA);
            int freeFieldsCeres = gameBoard.freeFieldsToMove(Constant.CERES);
            int result = Math.max(freeFieldsCeres, freeFieldsVesta) 
                      + (Math.max(freeFieldsCeres, freeFieldsVesta) 
                      - Math.min(freeFieldsCeres, freeFieldsVesta));
            return result;
        } else {
            throw new IllegalMoveException(Constant.ERR_ILLIGAL_MOVE);
        }
    }

    /**
     * Restores the initial values of the game.
     */
    public void resetGame() {
        gameBoard.initializeGameBoard();
        initializeStoneSet();
        initializePlayer();
        initializePhase();
    }

    private void changeGamePhase() {
        if (initialize) {
            initialize = false;
            actionI = true;
            return;
        }
        if (actionI) {
            actionI = false;
            actionII = true;
            return;
        }
        if (actionII) {
            actionII = false;
            actionIII = true;
            return;
        }
        if (actionIII) {
            if (phaseOne && roundCounter == Constant.MAX_GAME_ROUNDS) {
                phaseOne = false;
                phaseTwo = true;
                actionIII = false;
                initialize = true;
                roundCounter = 1;
                initializeStoneSet();
            } else if (phaseTwo && roundCounter == Constant.MAX_GAME_ROUNDS) {
                gameFinished = true;
            } else {
                actionIII = false;
                actionI = true;
                roundCounter++;
            }
            return;
        }
    }

    private String getCurrentPhaseSymbol() {
        if (phaseOne) {
            return Constant.VESTA;
        } else {
            return Constant.CERES;
        }
    }
}