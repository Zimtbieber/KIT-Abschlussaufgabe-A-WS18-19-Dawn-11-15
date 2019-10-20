package edu.kit.informatik.exception;

/**
 * This exception is used for illegal game moves.
 * 
 * @author 
 * @version 1.0
 */
public class IllegalMoveException extends Exception {

    private static final long serialVersionUID = 1273589374058308457L;

    /**
     * Exception gets thrown if the user executes illegal game moves.
     * 
     * @param message The error message.
     */
    public IllegalMoveException(String message) {
        super(message);
    }

}
