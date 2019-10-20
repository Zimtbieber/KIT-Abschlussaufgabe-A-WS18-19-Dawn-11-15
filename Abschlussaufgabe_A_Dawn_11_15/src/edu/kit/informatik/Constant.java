package edu.kit.informatik;

/**
 * This class contains all constants of the game.
 * 
 * @author 
 * @version 1.0
 */
public class Constant {
    
    // commands for user interaction
    
    /**
     * The sate command, which returns the symbol of the requested field.
     */
    public static final String STATE = "state";
    
    /**
     * The print command, which returns the game board as a String.
     */
    public static final String PRINT = "print";
    
    /**
     * The set-vc command, with which a nature stone could be placed on the board.
     */
    public static final String SETVC = "set-vc";
    
    /**
     * The roll command, to roll a dice-symbol.
     */
    public static final String ROLL = "roll";
    
    /**
     * The place command, which could place a mission-control stone on the board.
     */
    public static final String PLACE = "place";
    
    /**
     * The move command, which could move a nature stone on the board.
     */
    public static final String MOVE = "move";
    
    /**
     * The show-result command, to get the result of the game.
     */
    public static final String SHOWRESULT = "show-result";
    
    /**
     * The reset command, to reset the game and start a new one.
     */
    public static final String RESET = "reset";
    
    /**
     * The quit command, to end game.
     */
    public static final String QUIT = "quit";
    
    /**
     * If a command is executed properly the Terminal class prints 'OK'.
     */
    public static final String OK = "OK";
    
    
    // Error messages
    
    /**
     * Error message if a parameter input doesn't fit the regex.
     */
    public static final String ERR_INPUT = "incorrect input format. Predicted format: ";
    
    /**
     * The predicted format of the state command.
     */
    public static final String ERR_STATE = " <m>;<n>";
    
    /**
     * The predicted format of the set-vc command.
     */
    public static final String ERR_SETVC = " <m>;<n>";
    
    /**
     * The predicted format of the roll command.
     */
    public static final String ERR_ROLL = " <symbol>";
    
    /**
     * The predicted format of the place command.
     */
    public static final String ERR_PLACE = " <x1>;<y1>:<x2>;<y2>";
    
    /**
     * The predicted format of the move command.
     */
    public static final String ERR_MOVE = " <m1>;<n1>:<mi>;<ni>";
    
    /**
     * Error if the input command doesn't exist.
     */
    public static final String ERR_NO_COMMAND = "No valid command!";
    
    /**
     * Error if the input contains multiple blanks.
     */
    public static final String ERR_BLANK = "no multiple blanks allowed.";
    
    /**
     * Error if the command doesn't allow parameters.
     */
    public static final String ERR_NO_PARAM = " without parameters.";
    
    /**
     * Error if a mission-control stone isn't placed horizontal or vertical.
     */
    public static final String ERR_PLACE_DIAGONAL = "stones must be placed horizontal or vertival.";
    
    /**
     * Error if a token is not on the game board.
     */
    public static final String ERR_OUT_OF_FIELD = "the token is not completely on the board.";
    
    /**
     * Error if user input contains -0.
     */
    public static final String ERR_MINUS_ZERO = "no -0 allowed.";
    
    /**
     * Error if the game move is not allowed yet.
     */
    public static final String ERR_ILLIGAL_MOVE = "this move isn't allowed yet.";
    
    
    // Regex
    
    /**
     * Regex for the dice, symbols from 2 to 6 and DAWN (which represents the 7).
     */
    public static final String ROLLDICE = "[2-6]|DAWN";
    
    /**
     * Regex for the allowed digits to enter (from -99 to 99).
     */
    public static final String ALLOWED_DIGITS = "-?(0|[1-9][0-9]{0,1})";
   
    /**
     * Regex for a coordinate without the board boundaries (x;y).
     */
    public static final String WITHOUT_BOUNDARIES = ALLOWED_DIGITS + ";" + ALLOWED_DIGITS;
    
    /**
     * Regex for the board boundaries digits to enter (x from 0 to 10; y from 0 to 14).
     */
    public static final String BOARD_BOUNDARIES = "([0-9]|10);([0-9]|1[0-4])";
    
    /**
     * Regex for the place command to validate the rough form.
     */
    public static final String PLACESTONE_FORM = "(" + WITHOUT_BOUNDARIES + ":" + WITHOUT_BOUNDARIES + ")|(" 
                                                     + WITHOUT_BOUNDARIES + ":" + WITHOUT_BOUNDARIES + ")";
   
    /**
     * Regex for the place command to validate that even one coordinate is on the game board.
     */
    public static final String PLACESTONE_REQUIRED = "(" + WITHOUT_BOUNDARIES + ":" + BOARD_BOUNDARIES + ")|(" 
                                                         + BOARD_BOUNDARIES + ":" + WITHOUT_BOUNDARIES + ")";
    
    /**
     * Regex for the move command to validate the rough form.
     */
    public static final String MOVESTONE_FORM = "(" + WITHOUT_BOUNDARIES + ")|(" + WITHOUT_BOUNDARIES 
                                             + "(:" + WITHOUT_BOUNDARIES + "){1,6})";
    
    /**
     * Regex for the move command to validate that the coordinates are on the game board.
     */
    public static final String MOVESTONE_REQUIRED = "(" + BOARD_BOUNDARIES + ")|(" + BOARD_BOUNDARIES 
                                                 + "(:" + BOARD_BOUNDARIES + "){1,6})";
    
    // GameBoard and GameManager constants
    
    /**
     * The width of the game board.
     */
    public static final int BOARD_WIDTH = 11;
    
    /**
     * The length of the game board.
     */
    public static final int BOARD_LENGTH = 15;
    
    /**
     * The minimum index of the game board array.
     */
    public static final int MIN_INDEX_BOARD = 0;
    
    /**
     * The maximum index of the game board width.
     */
    public static final int MAX_INDEX_WIDTH = 10;
    
    /**
     * The maximum index of the game board length.
     */
    public static final int MAX_INDEX_LENGTH = 14;

    /**
     * The symbol which represents a empty field.
     */
    public static final String EMPTY_FIELD = "-";
    
    /**
     * The symbol which represents Vesta.
     */
    public static final String VESTA = "V";
    
    /**
     * The symbol which represents Ceres.
     */
    public static final String CERES = "C";
    
    /**
     * The symbol which represents a mission-control stone.
     */
    public static final String MISSION_CONTROL = "+";
    
    /**
     * The symbol which represents the outer field.
     */
    public static final String OUT_OF_FIELD = "*";
    
    /**
     * The number which represents the minimum dice symbol.
     */
    public static final int DICE_SYMBOL_MIN = 2;
    
    /**
     * The number which represents the DAWN dice symbol.
     */
    public static final int DAWN = 7;
    
    /**
     * The number if a field is unoccupied.
     */
    public static final int UNOCCUPIED_FIELD = -1;
   
    /**
     * The number of maximal neighbor fields, used in canMove.
     */
    public static final int MAX_NEIGHBORS = 4;
    
    /**
     * The number of maximal game rounds.
     */
    public static final int MAX_GAME_ROUNDS = 6;
}
