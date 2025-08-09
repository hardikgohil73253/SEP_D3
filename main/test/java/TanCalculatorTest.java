import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;

/**
 * Unit tests for TanCalculator modular components.
 * 
 * Tests cover all mathematical functions, input validation,
 * accessibility features, and GUI behavior across the new
 * modular architecture.
 * 
 * @version 1.0.0
 */
@DisplayName("TanCalculator Modular Tests")
class TanCalculatorTest {

    private TanCalculatorCore coreFeatures;
    private TanCalculatorGUI gui;
    private TanCalculatorErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        // Initialize core components
        coreFeatures = new TanCalculatorCore();
        errorHandler = new TanCalculatorErrorHandler();
        
        // Run GUI tests on EDT
        if (SwingUtilities.isEventDispatchThread()) {
            gui = new TanCalculatorGUI();
        } else {
            try {
                SwingUtilities.invokeAndWait(() -> gui = new TanCalculatorGUI());
            } catch (Exception e) {
                fail("Failed to create GUI: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Core Features Tests")
    class CoreFeaturesTests {

        @Test
        @DisplayName("Test degree to radian conversion")
        void testToRadians() {
            double result = coreFeatures.toRadians(180.0);
            assertEquals(Math.PI, result, 1e-10, "180 degrees should equal π radians");

            result = coreFeatures.toRadians(90.0);
            assertEquals(Math.PI / 2, result, 1e-10, "90 degrees should equal π/2 radians");

            result = coreFeatures.toRadians(0.0);
            assertEquals(0.0, result, 1e-10, "0 degrees should equal 0 radians");
        }

        @Test
        @DisplayName("Test radian normalization")
        void testNormalizeRadians() {
            // Test values within [-π, π]
            double result = coreFeatures.normalizeRadians(Math.PI / 2);
            assertEquals(Math.PI / 2, result, 1e-10, "π/2 should remain π/2");

            // Test values outside range
            result = coreFeatures.normalizeRadians(3 * Math.PI);
            assertEquals(Math.PI, result, 1e-10, "3π should normalize to π");

            result = coreFeatures.normalizeRadians(-3 * Math.PI);
            assertEquals(-Math.PI, result, 1e-10, "-3π should normalize to -π");
        }

        @Test
        @DisplayName("Test sine calculation")
        void testSin() {
            // Test key angles
            double result = coreFeatures.sin(0.0);
            assertEquals(0.0, result, 1e-10, "sin(0) should be 0");

            result = coreFeatures.sin(Math.PI / 2);
            assertEquals(1.0, result, 1e-6, "sin(π/2) should be 1");

            result = coreFeatures.sin(Math.PI);
            assertEquals(0.0, result, 1e-6, "sin(π) should be 0");
        }

        @Test
        @DisplayName("Test cosine calculation")
        void testCos() {
            // Test key angles
            double result = coreFeatures.cos(0.0);
            assertEquals(1.0, result, 1e-10, "cos(0) should be 1");

            result = coreFeatures.cos(Math.PI / 2);
            assertEquals(0.0, result, 1e-6, "cos(π/2) should be 0");

            result = coreFeatures.cos(Math.PI);
            assertEquals(-1.0, result, 1e-6, "cos(π) should be -1");
        }

        @Test
        @DisplayName("Test tangent calculation")
        void testTan() throws TanCalculatorErrorHandler.UndefinedTangentException {
            // Test valid tangent values
            double result = coreFeatures.tan(0.0);
            assertEquals(0.0, result, 1e-10, "tan(0) should be 0");

            result = coreFeatures.tan(Math.PI / 4);
            assertEquals(1.0, result, 1e-6, "tan(π/4) should be 1");

            // Test undefined tangent (should throw exception)
            assertThrows(TanCalculatorErrorHandler.UndefinedTangentException.class, 
                () -> {
                    try {
                        coreFeatures.tan(Math.PI / 2);
                    } catch (TanCalculatorErrorHandler.UndefinedTangentException e) {
                        throw e;
                    }
                }, 
                "tan(π/2) should be undefined");
        }

        @Test
        @DisplayName("Test complete tangent calculation flow")
        void testCalculateTangent() throws TanCalculatorErrorHandler.InvalidInputException, 
                                         TanCalculatorErrorHandler.UndefinedTangentException {
            // Test valid input
            double result = coreFeatures.calculateTangent("45");
            assertEquals(1.0, result, 1e-6, "tan(45°) should be 1");

            result = coreFeatures.calculateTangent("0");
            assertEquals(0.0, result, 1e-10, "tan(0°) should be 0");

            // Test invalid input
            assertThrows(TanCalculatorErrorHandler.InvalidInputException.class,
                () -> {
                    try {
                        coreFeatures.calculateTangent("abc");
                    } catch (TanCalculatorErrorHandler.InvalidInputException e) {
                        throw e;
                    }
                },
                "Non-numeric input should throw InvalidInputException");

            assertThrows(TanCalculatorErrorHandler.InvalidInputException.class,
                () -> {
                    try {
                        coreFeatures.calculateTangent("");
                    } catch (TanCalculatorErrorHandler.InvalidInputException e) {
                        throw e;
                    }
                },
                "Empty input should throw InvalidInputException");

            // Test undefined tangent
            assertThrows(TanCalculatorErrorHandler.UndefinedTangentException.class,
                () -> {
                    try {
                        coreFeatures.calculateTangent("90");
                    } catch (TanCalculatorErrorHandler.UndefinedTangentException e) {
                        throw e;
                    }
                },
                "tan(90°) should be undefined");
        }
    }

    @Nested
    @DisplayName("Error Handler Tests")
    class ErrorHandlerTests {

        @Test
        @DisplayName("Test error handling methods")
        void testErrorHandling() {
            JLabel testLabel = new JLabel("Test");
            
            // Test undefined tangent handling
            errorHandler.handleUndefinedTangent(testLabel);
            assertEquals("Result: UNDEFINED", testLabel.getText());
            assertEquals(Color.RED, testLabel.getForeground());

            // Test invalid input handling
            errorHandler.handleInvalidInput(testLabel);
            assertEquals("Result: INVALID INPUT", testLabel.getText());
            assertEquals(Color.RED, testLabel.getForeground());

            // Test unexpected error handling
            errorHandler.handleUnexpectedError(testLabel);
            assertEquals("Result: ERROR", testLabel.getText());
            assertEquals(Color.RED, testLabel.getForeground());

            // Test custom error handling
            errorHandler.handleError(testLabel, "CUSTOM ERROR");
            assertEquals("Result: CUSTOM ERROR", testLabel.getText());
            assertEquals(Color.RED, testLabel.getForeground());

            // Test error clearing
            errorHandler.clearError(testLabel);
            assertEquals("Result:", testLabel.getText());
            assertEquals(Color.BLUE, testLabel.getForeground());
        }
    }

    @Nested
    @DisplayName("GUI Component Tests")
    class GuiTests {

        @Test
        @DisplayName("Test GUI components exist")
        void testGuiComponentsExist() {
            // Test that GUI is properly initialized
            assertNotNull(gui, "GUI should not be null");
            assertTrue(gui instanceof JFrame, "GUI should be a JFrame");
            
            // Test window properties
            assertEquals("tan(x) Calculator v1.0.0", gui.getTitle());
            assertFalse(gui.isResizable());
            
            // Test that content pane exists
            Container contentPane = gui.getContentPane();
            assertNotNull(contentPane, "Content pane should exist");
        }

        @Test
        @DisplayName("Test accessibility features")
        void testAccessibility() {
            // Test that GUI implements Accessible
            assertTrue(gui instanceof javax.accessibility.Accessible, 
                "GUI should implement Accessible interface");
            
            // Test accessible context
            javax.accessibility.AccessibleContext context = gui.getAccessibleContext();
            assertNotNull(context, "Accessible context should not be null");
            
            // Test accessible role
            assertEquals(javax.accessibility.AccessibleRole.FRAME, 
                context.getAccessibleRole(), "Accessible role should be FRAME");
        }

        @Test
        @DisplayName("Test button functionality")
        void testButtonFunctionality() {
            // Test that buttons exist and are properly configured
            Container contentPane = gui.getContentPane();
            
            // Find buttons in the component hierarchy
            JButton computeButton = findButton(contentPane, "Compute tan(x)");
            JButton clearButton = findButton(contentPane, "Clear");
            
            assertNotNull(computeButton, "Compute button should exist");
            assertNotNull(clearButton, "Clear button should exist");
            
            // Test button properties
            assertTrue(computeButton.isEnabled(), "Compute button should be enabled");
            assertTrue(clearButton.isEnabled(), "Clear button should be enabled");
        }

        private JButton findButton(Container container, String text) {
            for (Component comp : container.getComponents()) {
                if (comp instanceof JButton) {
                    JButton button = (JButton) comp;
                    if (text.equals(button.getText())) {
                        return button;
                    }
                } else if (comp instanceof Container) {
                    JButton found = findButton((Container) comp, text);
                    if (found != null) {
                        return found;
                    }
                }
            }
            return null;
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Test complete calculation flow")
        void testCompleteCalculationFlow() throws TanCalculatorErrorHandler.InvalidInputException, 
                                               TanCalculatorErrorHandler.UndefinedTangentException {
            // This test simulates the complete flow through the modular components
            // Test valid calculation
            double result = coreFeatures.calculateTangent("45");
            assertEquals(1.0, result, 1e-6, "Integration test: tan(45°) should be 1");
            
            // Test error handling integration
            JLabel testLabel = new JLabel();
            try {
                coreFeatures.calculateTangent("90");
                fail("Should have thrown UndefinedTangentException");
            } catch (TanCalculatorErrorHandler.UndefinedTangentException e) {
                errorHandler.handleUndefinedTangent(testLabel);
                assertEquals("Result: UNDEFINED", testLabel.getText());
            }
        }

        @Test
        @DisplayName("Test error handling integration")
        void testErrorHandlingIntegration() {
            JLabel testLabel = new JLabel();
            
            // Test invalid input flow
            try {
                coreFeatures.calculateTangent("invalid");
                fail("Should have thrown InvalidInputException");
            } catch (TanCalculatorErrorHandler.InvalidInputException e) {
                errorHandler.handleInvalidInput(testLabel);
                assertEquals("Result: INVALID INPUT", testLabel.getText());
            } catch (TanCalculatorErrorHandler.UndefinedTangentException e) {
                fail("Unexpected UndefinedTangentException: " + e.getMessage());
            }
            
            // Test undefined tangent flow
            try {
                coreFeatures.calculateTangent("90");
                fail("Should have thrown UndefinedTangentException");
            } catch (TanCalculatorErrorHandler.UndefinedTangentException e) {
                errorHandler.handleUndefinedTangent(testLabel);
                assertEquals("Result: UNDEFINED", testLabel.getText());
            } catch (TanCalculatorErrorHandler.InvalidInputException e) {
                fail("Unexpected InvalidInputException: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Test version information")
    void testVersionInformation() {
        assertEquals("1.0.0", TanCalculator.VERSION, "Version should be 1.0.0");
    }
} 