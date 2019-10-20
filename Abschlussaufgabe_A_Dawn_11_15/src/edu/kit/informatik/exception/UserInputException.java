package edu.kit.informatik.exception;

/**
 * This exception is used for invalid user input.
 * 
 * @author 
 * @version 1.0
 */
public class UserInputException extends Exception {

    private static final long serialVersionUID = 1456873413137684312L;

    /**
     * Exception gets thrown if the user enters invalid input.
     *
     * @param message The error message.
     */
    public UserInputException(String message) {
        super(message);
    }

}
