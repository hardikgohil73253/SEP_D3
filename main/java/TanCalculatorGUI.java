import javax.swing.*;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * TanCalculatorGUI - GUI component for the tangent calculator application.
 * 
 * This class handles all user interface components, accessibility features,
 * and user interactions. It delegates mathematical operations to the core
 * features class and error handling to the error handling class.
 * 
 * @author TanCalculator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public class TanCalculatorGUI extends JFrame implements Accessible {
    
    /**
     * Serial version UID for serialization compatibility.
     */
    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------------
    // GUI COMPONENTS
    // ------------------------------------------------------------------
    
    /**
     * Input field for degree values.
     */
    private final JTextField inputField = new JTextField(10);
    
    /**
     * Label displaying calculation results.
     */
    private final JLabel resultLabel = new JLabel("Result:");
    
    /**
     * Compute button for triggering calculations.
     */
    private final JButton computeButton = new JButton("Compute tan(x)");
    
    /**
     * Clear button for resetting the interface.
     */
    private final JButton clearButton = new JButton("Clear");

    /**
     * Core features handler for mathematical operations.
     */
    private final TanCalculatorCore coreFeatures;

    /**
     * Error handler for managing exceptions and user feedback.
     */
    private final TanCalculatorErrorHandler errorHandler;

    /**
     * Default constructor for TanCalculatorGUI.
     * Initializes the GUI with accessibility features and dependencies.
     */
    public TanCalculatorGUI() {
        super("tan(x) Calculator v" + TanCalculator.VERSION);
        this.coreFeatures = new TanCalculatorCore();
        this.errorHandler = new TanCalculatorErrorHandler();
        buildUI();
        setupAccessibility();
    }

    /**
     * Build the Swing interface with accessibility features.
     * Implements:
     * • FR‑1 – show input field in degrees
     * • FR‑7 – provide Compute / Clear buttons and exit keyword handling
     * • NFR usability needs (≥12 pt font, high contrast)
     * • Accessibility features using Java Accessibility API
     */
    private void buildUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Set high contrast colors for accessibility
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setForeground(Color.BLACK);

        // Title label
        JLabel titleLabel = new JLabel("Enter angle in degrees:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Input field
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setToolTipText("Enter angle in degrees (e.g., 45)");
        gbc.gridy = 1;
        add(inputField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        computeButton.setFont(new Font("Arial", Font.BOLD, 12));
        computeButton.setToolTipText("Calculate tangent of the entered angle");
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setToolTipText("Clear input field and result");
        
        buttonPanel.add(computeButton);
        buttonPanel.add(clearButton);

        gbc.gridy = 2;
        add(buttonPanel, gbc);

        // Result label
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setForeground(Color.BLUE);
        gbc.gridy = 3;
        add(resultLabel, gbc);

        // Add event listeners
        setupEventListeners();

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Setup accessibility features for all GUI components.
     */
    private void setupAccessibility() {
        // Set accessible descriptions
        inputField.getAccessibleContext().setAccessibleDescription(
            "Text field for entering angle in degrees");
        computeButton.getAccessibleContext().setAccessibleDescription(
            "Button to calculate tangent of the entered angle");
        clearButton.getAccessibleContext().setAccessibleDescription(
            "Button to clear the input field and result");
        resultLabel.getAccessibleContext().setAccessibleDescription(
            "Label displaying the calculated tangent result");
    }

    /**
     * Setup event listeners for GUI components.
     */
    private void setupEventListeners() {
        // Compute button action
        computeButton.addActionListener(e -> compute());
        
        // Clear button action
        clearButton.addActionListener(e -> {
            inputField.setText("");
            resultLabel.setText("Result:");
            inputField.requestFocus();
        });

        // Enter key in input field
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    compute();
                }
            }
        });

        // Focus listener for better accessibility
        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                inputField.selectAll();
            }
        });
    }

    /**
     * Controller method – coordinates GUI, core features, and error handling.
     * Steps:
     *   1. Read and validate input (FR‑6)
     *   2. Convert to radians (FR‑2) and normalise (FR‑9)
     *   3. Compute tan(x) using Maclaurin engine (FR‑3, FR‑4, FR‑5)
     *   4. Format output (FR‑8) or show UNDEFINED / INVALID INPUT
     *   5. Catch any unexpected exceptions (FR‑10)
     */
    private void compute() {
        try {
            String txt = inputField.getText().trim();
            if (txt.equalsIgnoreCase("exit")) { // FR‑7 – exit keyword
                dispose();
                return;
            }
            
            // Delegate to core features for mathematical operations
            double result = coreFeatures.calculateTangent(txt);
            
            // Display successful result
            resultLabel.setText(String.format("Result: %.6f", result));
            resultLabel.setForeground(Color.BLUE);
            
        } catch (TanCalculatorErrorHandler.UndefinedTangentException ute) {
            // Handle undefined tangent
            errorHandler.handleUndefinedTangent(resultLabel);
        } catch (TanCalculatorErrorHandler.InvalidInputException iie) {
            // Handle invalid input
            errorHandler.handleInvalidInput(resultLabel);
        } catch (Exception ex) {
            // Handle unexpected errors
            errorHandler.handleUnexpectedError(resultLabel);
        }
    }

    /**
     * Get the accessible context for this component.
     * 
     * @return the accessible context
     */
    @Override
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJFrame();
        }
        return accessibleContext;
    }

    /**
     * Accessible JFrame implementation.
     */
    private class AccessibleJFrame extends JFrame.AccessibleJFrame {
        @Override
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.FRAME;
        }
    }
} 