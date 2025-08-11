import javax.swing.*;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

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
    private final JTextField inputField = new JTextField(15);
    
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
     * Help button for displaying usage information.
     */
    private final JButton helpButton = new JButton("?");
    
    /**
     * Status label for real-time feedback.
     */
    private final JLabel statusLabel = new JLabel("Ready");
    
    /**
     * Main panel for responsive layout.
     */
    private final JPanel mainPanel = new JPanel();
    
    /**
     * Minimum window dimensions for responsive design.
     */
    private static final int MIN_WIDTH = 450;
    private static final int MIN_HEIGHT = 350;

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
        setupResponsiveDesign();
        
        // Force button styling after UI is built
        forceButtonStyling();
    }

    /**
     * Build the Swing interface with accessibility features and modern design.
     * Implements:
     * • FR‑1 – show input field in degrees
     * • FR‑7 – provide Compute / Clear buttons and exit keyword handling
     * • NFR usability needs (≥12 pt font, high contrast)
     * • Accessibility features using Java Accessibility API
     * • Modern UI design principles
     */
    private void buildUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        
        // Modern color scheme
        Color backgroundColor = new Color(248, 249, 250);
        Color primaryColor = new Color(0, 123, 255);
        Color successColor = new Color(40, 167, 69);
        Color borderColor = new Color(222, 226, 230);
        
        // Set modern styling
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(15, 15));
        
        // Main panel with padding
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header panel with title and help
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBackground(backgroundColor);
        
        JLabel titleLabel = new JLabel("Tangent Calculator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(33, 37, 41));
        
        // Help button styling
        helpButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        helpButton.setPreferredSize(new Dimension(35, 35));
        helpButton.setBackground(primaryColor);
        helpButton.setForeground(Color.black);
        helpButton.setBorder(new LineBorder(primaryColor));
        helpButton.setFocusPainted(false);
        helpButton.setToolTipText("Click for help and usage instructions");
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(helpButton, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);

        // Input section
        JLabel inputLabel = new JLabel("Enter angle in degrees:");
        inputLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputLabel.setForeground(new Color(73, 80, 87));
        
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setToolTipText("Enter angle in degrees (e.g., 45, 90, 180, 361, -361)");
        inputField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(borderColor, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        inputField.setPreferredSize(new Dimension(200, 40));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(inputLabel, gbc);
        
        gbc.gridy = 1;
        mainPanel.add(inputField, gbc);

        // Button panel with modern styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(backgroundColor);
        
        // Style buttons with black color
        styleButton(computeButton, Color.BLACK, Color.white);
        styleButton(clearButton, Color.BLACK, Color.white);
        
        // Set preferred button sizes
        computeButton.setPreferredSize(new Dimension(140, 40));
        clearButton.setPreferredSize(new Dimension(100, 40));
        
        buttonPanel.add(computeButton);
        buttonPanel.add(clearButton);

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        // Single result section (removed duplicate title label)
        JPanel resultPanel = new JPanel(new BorderLayout(8, 0));
        resultPanel.setBackground(backgroundColor);
        
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setForeground(successColor);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        resultLabel.setText("Result: ");
        
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        
        gbc.gridy = 3;
        mainPanel.add(resultPanel, gbc);
        
        // Status bar
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        statusLabel.setForeground(new Color(108, 117, 125));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        gbc.gridy = 4;
        mainPanel.add(statusLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Add event listeners
        setupEventListeners();

        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Apply modern styling to buttons.
     */
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setUI(new BasicButtonUI());
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(new LineBorder(bgColor, 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        
        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    /**
     * Force button styling to ensure proper rendering.
     */
    private void forceButtonStyling() {
        // Force the buttons to be black with white text
        computeButton.setBackground(Color.BLACK);
        computeButton.setForeground(Color.WHITE);
        computeButton.setOpaque(true);
        computeButton.setContentAreaFilled(true);
        computeButton.setBorder(new LineBorder(Color.BLACK, 2));
        
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);
        clearButton.setOpaque(true);
        clearButton.setContentAreaFilled(true);
        clearButton.setBorder(new LineBorder(Color.BLACK, 2));
        
        // Force repaint
        computeButton.repaint();
        clearButton.repaint();
    }
    
    /**
     * Setup responsive design features.
     */
    private void setupResponsiveDesign() {
        // Component listener for responsive layout
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayoutForSize();
            }
        });
        
        // Initial layout update
        updateLayoutForSize();
    }
    
    /**
     * Update layout based on window size for responsive design.
     */
    private void updateLayoutForSize() {
        int width = getWidth();
        
        // Adjust font sizes based on window size
        if (width < 500) {
            inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        } else {
            inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        }
        
        // Repaint for immediate visual feedback
        revalidate();
        repaint();
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
        helpButton.getAccessibleContext().setAccessibleDescription(
            "Help button for usage instructions");
        resultLabel.getAccessibleContext().setAccessibleDescription(
            "Label displaying the calculated tangent result");
        statusLabel.getAccessibleContext().setAccessibleDescription(
            "Status indicator showing current application state");
    }

    /**
     * Setup event listeners for GUI components with enhanced functionality.
     */
    private void setupEventListeners() {
        // Compute button action
        computeButton.addActionListener(e -> compute());
        
        // Clear button action
        clearButton.addActionListener(e -> {
            inputField.setText("");
            resultLabel.setText("Result:");
            statusLabel.setText("Ready");
            statusLabel.setForeground(new Color(108, 117, 125));
            inputField.requestFocus();
        });
        
        // Help button action
        helpButton.addActionListener(e -> showHelpDialog());

        // Real-time input validation
        inputField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validateInput(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validateInput(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validateInput(); }
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

        // Enhanced focus listener
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                inputField.selectAll();
                statusLabel.setText("Enter an angle value");
                statusLabel.setForeground(new Color(0, 123, 255));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                validateInput();
            }
        });
    }
    
    /**
     * Real-time input validation with visual feedback.
     */
    private void validateInput() {
        String input = inputField.getText().trim();
        
        if (input.isEmpty()) {
            statusLabel.setText("Ready");
            statusLabel.setForeground(new Color(108, 117, 125));
            inputField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(222, 226, 230), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            return;
        }
        
        try {
            Double.parseDouble(input);
            statusLabel.setText("Valid input - Press Enter or click Compute");
            statusLabel.setForeground(new Color(40, 167, 69));
            inputField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(40, 167, 69), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid number");
            statusLabel.setForeground(new Color(220, 53, 69));
            inputField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 53, 69), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
        }
    }
    
    /**
     * Show help dialog with usage instructions.
     */
    private void showHelpDialog() {
        JDialog helpDialog = new JDialog(this, "Help - Tangent Calculator", true);
        helpDialog.setLayout(new BorderLayout(15, 15));
        helpDialog.getContentPane().setBackground(new Color(248, 249, 250));
        
        JTextArea helpText = new JTextArea();
        helpText.setEditable(false);
        helpText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        helpText.setBackground(new Color(248, 249, 250));
        helpText.setBorder(new EmptyBorder(15, 15, 15, 15));
        helpText.setText(
            "Tangent Calculator Help\n\n" +
            "How to use:\n" +
            "1. Enter an angle in degrees (e.g., 45, 90, 180, 361, -361)\n" +
            "2. Press Enter or click 'Compute tan(x)'\n" +
            "3. View the result\n" +
            "4. Use 'Clear' to reset\n\n" +
            "Valid inputs:\n" +
            "• Any numeric value in degrees\n" +
            "• Decimal values (e.g., 45.5)\n" +
            "• Negative angles (e.g., -30)\n" +
            "• Angles beyond ±360° (e.g., 361°, -361°)\n\n" +
            "Special cases:\n" +
            "• 90° and 270°: Undefined (vertical asymptote)\n" +
            "• Type 'exit' to close the application\n\n" +
            "Keyboard shortcuts:\n" +
            "• Enter: Calculate tangent\n" +
            "• Tab: Navigate between fields\n" +
            "• Escape: Clear input\n\n" +
            "Accessibility:\n" +
            "• Screen reader compatible\n" +
            "• High contrast design\n" +
            "• Keyboard navigation support"
        );
        
        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setBorder(new LineBorder(new Color(222, 226, 230), 2));
        
        JButton closeButton = new JButton("Close");
        styleButton(closeButton, new Color(0, 123, 255), Color.WHITE);
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> helpDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.add(closeButton);
        
        helpDialog.add(scrollPane, BorderLayout.CENTER);
        helpDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        helpDialog.setSize(550, 450);
        helpDialog.setLocationRelativeTo(this);
        helpDialog.setResizable(true);
        helpDialog.setVisible(true);
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
            
            statusLabel.setText("Calculating...");
            statusLabel.setForeground(new Color(0, 123, 255));
            
            // Delegate to core features for mathematical operations
            double result = coreFeatures.calculateTangent(txt);
            
            // Display successful result
            resultLabel.setText(String.format("Result: %.6f", result));
            resultLabel.setForeground(new Color(40, 167, 69));
            statusLabel.setText("Calculation completed successfully");
            statusLabel.setForeground(new Color(40, 167, 69));
            
        } catch (TanCalculatorErrorHandler.UndefinedTangentException ute) {
            // Handle undefined tangent
            errorHandler.handleUndefinedTangent(resultLabel);
            statusLabel.setText("Undefined tangent - angle at asymptote");
            statusLabel.setForeground(new Color(220, 53, 69));
        } catch (TanCalculatorErrorHandler.InvalidInputException iie) {
            // Handle invalid input
            errorHandler.handleInvalidInput(resultLabel);
            statusLabel.setText("Invalid input - please check your entry");
            statusLabel.setForeground(new Color(220, 53, 69));
        } catch (Exception ex) {
            // Handle unexpected errors
            errorHandler.handleUnexpectedError(resultLabel);
            statusLabel.setText("Unexpected error occurred");
            statusLabel.setForeground(new Color(220, 53, 69));
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