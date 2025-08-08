import javax.swing.*;
import java.awt.*;

/**
 * TanCalculator - Main application class for calculating tangent values.
 * 
 * This is the main entry point that coordinates the GUI, core features,
 * and error handling components.
 * 
 * Version: 1.0.0
 * 
 * @author TanCalculator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public class TanCalculator {
    
    /**
     * Application version following semantic versioning.
     */
    public static final String VERSION = "1.0.0";

    /**
     * Main method to launch the application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set system look and feel for better accessibility
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fall back to default look and feel
            System.err.println("Warning: Could not set system look and feel: " + e.getMessage());
        }
        
        EventQueue.invokeLater(() -> {
            TanCalculatorGUI gui = new TanCalculatorGUI();
            gui.setVisible(true);
        });
    }
}
