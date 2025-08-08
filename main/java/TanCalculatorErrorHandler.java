import javax.swing.*;
import java.awt.*;

/**
 * TanCalculatorErrorHandler - Error handling and user feedback management.
 * 
 * This class handles all exceptions and provides appropriate user feedback
 * through the GUI. It contains custom exceptions and methods for displaying
 * error messages with proper styling.
 * 
 * @author TanCalculator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public class TanCalculatorErrorHandler {
    
    /**
     * Default constructor for TanCalculatorErrorHandler.
     */
    public TanCalculatorErrorHandler() {
        // No initialization needed
    }

    /**
     * Handle undefined tangent exception by updating the result label.
     * 
     * @param resultLabel the label to update with error message
     */
    public void handleUndefinedTangent(JLabel resultLabel) {
        resultLabel.setText("Result: UNDEFINED");
        resultLabel.setForeground(Color.RED);
    }

    /**
     * Handle invalid input exception by updating the result label.
     * 
     * @param resultLabel the label to update with error message
     */
    public void handleInvalidInput(JLabel resultLabel) {
        resultLabel.setText("Result: INVALID INPUT");
        resultLabel.setForeground(Color.RED);
    }

    /**
     * Handle unexpected errors by updating the result label.
     * 
     * @param resultLabel the label to update with error message
     */
    public void handleUnexpectedError(JLabel resultLabel) {
        resultLabel.setText("Result: ERROR");
        resultLabel.setForeground(Color.RED);
    }

    /**
     * Handle specific error with custom message.
     * 
     * @param resultLabel the label to update with error message
     * @param message the custom error message
     */
    public void handleError(JLabel resultLabel, String message) {
        resultLabel.setText("Result: " + message);
        resultLabel.setForeground(Color.RED);
    }

    /**
     * Clear error state and reset to default appearance.
     * 
     * @param resultLabel the label to reset
     */
    public void clearError(JLabel resultLabel) {
        resultLabel.setText("Result:");
        resultLabel.setForeground(Color.BLUE);
    }

    // ------------------------------------------------------------------
    // Custom exceptions implementing FR‑5 and FR‑6 behaviours
    // ------------------------------------------------------------------
    
    /**
     * Exception thrown when input is invalid.
     * Implements FR-6 behavior for input validation.
     */
    public static class InvalidInputException extends Exception {
        private static final long serialVersionUID = 1L;
        
        /**
         * Constructor for InvalidInputException.
         * 
         * @param message the error message
         */
        public InvalidInputException(String message) {
            super(message);
        }
    }
    
    /**
     * Exception thrown when tangent is undefined.
     * Implements FR-5 behavior for undefined tangent detection.
     */
    public static class UndefinedTangentException extends Exception {
        private static final long serialVersionUID = 1L;
        
        /**
         * Constructor for UndefinedTangentException.
         * 
         * @param message the error message
         */
        public UndefinedTangentException(String message) {
            super(message);
        }
    }
} 