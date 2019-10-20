package edu.kit.informatik;

import java.util.Scanner;
import edu.kit.informatik.exception.IllegalMoveException;
import edu.kit.informatik.exception.UserInputException;

/**
 * This class handels and prepare the user input
 * Also it contains the program loop class.
 * 
 * @author 
 * @version 1.0
 */
public final class UserInterface {
    
    private boolean runtime = true;
    private String userInput;
    private String inputCommand;
    private GameManager gameManager = new GameManager(); 
    
    /**
     * Creates the game-loop.
     */
    public void start() {

        while (runtime) {

            userInput = new Scanner(System.in).nextLine();
            inputCommand = getInputCommand(userInput);

            switch (inputCommand) {

                case Constant.STATE: {   
                    state(userInput);
                    break;
                }
                case Constant.PRINT: {
                    print();
                    break;
                }
                case Constant.SETVC: {
                    setVC(userInput);
                    break;
                }
                case Constant.ROLL: {
                    roll();
                    break;
                }
                case Constant.PLACE: {
                    place();
                    break;
                }
                case Constant.MOVE: {
                    move();
                    break;
                }
                case Constant.SHOWRESULT: {
                    showResult();
                    break;
                }
                case Constant.RESET: {
                    reset();
                    break;
                }
                case Constant.QUIT: {
                    quit();
                    break;
                }
                default: {
                    if (runtime) {
                        System.out.println(Constant.ERR_NO_COMMAND);
                    }
                }
            }
        }
    }
    
    private void state(final String userInput) {
        try {
            String inputValue = checkInputFormat(userInput, Constant.WITHOUT_BOUNDARIES, Constant.ERR_STATE);
            isOnBoard(inputValue, Constant.BOARD_BOUNDARIES);
            System.out.println(gameManager.getState(preperateCoordinates(inputValue)));
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void print() {
        try {
            withoutParam(Constant.PRINT.length());
            System.out.println(gameManager.printBoard());
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setVC(final String userInput) {
        try {
            String inputValue = checkInputFormat(userInput, Constant.WITHOUT_BOUNDARIES, Constant.ERR_SETVC);
            isOnBoard(inputValue, Constant.BOARD_BOUNDARIES);
            gameManager.setVC(preperateCoordinates(inputValue));
            System.out.println(Constant.OK);
        } catch (UserInputException | IllegalMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void roll() {
        try {
            String inputValue = checkInputFormat(userInput, Constant.ROLLDICE, Constant.ERR_ROLL);
            if (inputValue.equals("DAWN")) {
                gameManager.rollDice(Constant.DAWN);
            } else {
                gameManager.rollDice(Integer.parseInt(inputValue));
            }
            System.out.println(Constant.OK);
        } catch (UserInputException | IllegalMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void place() {
        try {
            String inputValue = checkInputFormat(userInput, Constant.PLACESTONE_FORM, Constant.ERR_PLACE);
            isOnBoard(inputValue, Constant.PLACESTONE_REQUIRED);
            
            // Sorts the coordinates that the smaller one is at first.
            // Also checks if the stone is horizontal or vertical placed.
            int [] coordinates = preperateCoordinates(inputValue);
            int [] sortedCoords = new int[coordinates.length];
            int x1 = coordinates[0];
            int y1 = coordinates[1];
            int x2 = coordinates[2];
            int y2 = coordinates[3];
            int stoneLength = 0;
            
            if (!(x1 == x2 || y1 == y2)) {
                throw new IllegalMoveException(Constant.ERR_PLACE_DIAGONAL);
            } else {
                stoneLength = Math.abs(x1 - x2 + y1 - y2) + 1;

                if (x1 > x2) {
                    x1 = coordinates[2];
                    x2 = coordinates[0];
                } else if (y1 > y2) {
                    y1 = coordinates[3];
                    y2 = coordinates[1];
                }
                sortedCoords[0] = x1;
                sortedCoords[1] = y1;
                sortedCoords[2] = x2;
                sortedCoords[3] = y2;
            }
            gameManager.placeStone(sortedCoords, stoneLength);
            System.out.println(Constant.OK);
        } catch (UserInputException | IllegalMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void move() {
        try {
            String inputValue = checkInputFormat(userInput, Constant.MOVESTONE_FORM, Constant.ERR_MOVE);
            isOnBoard(inputValue, Constant.MOVESTONE_REQUIRED);
            gameManager.move(preperateCoordinates(inputValue));
            System.out.println(Constant.OK);
        } catch (UserInputException | IllegalMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showResult() {
        try {
            withoutParam(Constant.SHOWRESULT.length());
            System.out.println(gameManager.showResult());
        } catch (UserInputException | IllegalMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void reset() {
        try {
            withoutParam(Constant.RESET.length());
            gameManager.resetGame();
            System.out.println(Constant.OK);
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void quit() {
        try {
            withoutParam(Constant.QUIT.length());
            runtime = false;
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // returns the command
    private String getInputCommand(final String userInput) {
        String[] command = userInput.split("\\s");
        return command[0];
    }
    
    // checks if a command has a parameter but non is required
    private void withoutParam(final int lengthCommand) throws UserInputException {
        String[] inputValue = userInput.split("\\s");
        if (inputValue.length > 1 || lengthCommand < userInput.length()) {
            throw new UserInputException(Constant.ERR_INPUT + inputValue[0] + Constant.ERR_NO_PARAM);
        } 
    }

    private String checkInputFormat(final String userInput, final String pattern, 
                                    final String errorMsg) throws UserInputException {
        // check for blank symbols
        int blankCount = 0;
        for (int i = 0; i < userInput.length(); i++) {
            if (userInput.charAt(i) == ' ') {
                blankCount++;
            }
        }
        if (blankCount > 1) {
            throw new UserInputException(Constant.ERR_BLANK);
        }
        
        // check if regex pattern matches
        String[] inputValue = userInput.split("\\s");
        if (!(inputValue.length > 1 && inputValue[1].matches(pattern))) {
            throw new UserInputException(Constant.ERR_INPUT + inputValue[0] + errorMsg);
        } 
        return inputValue[1];
    }

    private void isOnBoard(String inputCoordinates, String regex) throws UserInputException {
        if (!inputCoordinates.matches(regex)) {
            throw new UserInputException(Constant.ERR_OUT_OF_FIELD);
        } 
    }

    /**
     * Returns the input coordinates in a integer array in the form {x1,y1,x2,y2,...} 
     */    
    private int[] preperateCoordinates(final String inputCoordinates) throws UserInputException {
        String[] coord = inputCoordinates.split(":|;");
        int[] coordInt = new int[coord.length];

        for (int i = 0; i < coord.length; i++) {
            if (coord[i].equals("-0")) {
                throw new UserInputException(Constant.ERR_MINUS_ZERO);
            }
            coordInt[i] = Integer.parseInt(coord[i]);
        }
        return coordInt;
    }   
}