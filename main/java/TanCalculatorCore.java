/**
 * TanCalculatorCore - Core mathematical features for tangent calculations.
 * 
 * This class contains all mathematical operations including:
 * - Input validation and parsing
 * - Trigonometric calculations using Maclaurin series
 * - Angle conversions and normalization
 * - Mathematical constants and precision handling
 * 
 * @author TanCalculator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public class TanCalculatorCore {
    
    /**
     * Mathematical constant π required for FR-2, FR-9 and series calculations.
     */
    private static final double PI = 3.14159265358979323846264338327950288419716939937510;

    /**
     * Epsilon threshold (1e-12) used to decide when tan(x) is undefined (FR-5).
     */
    private static final double EPS = 1e-12;

    /**
     * Maximum number of terms for Maclaurin series calculations.
     */
    private static final int MAX_SERIES_TERMS = 15;

    /**
     * Default constructor for TanCalculatorCore.
     */
    public TanCalculatorCore() {
        // No initialization needed
    }

    /**
     * Main calculation method that orchestrates the tangent calculation process.
     * 
     * @param input the input string to process
     * @return the calculated tangent value
     * @throws TanCalculatorErrorHandler.InvalidInputException if input is invalid
     * @throws TanCalculatorErrorHandler.UndefinedTangentException if tangent is undefined
     */
    public double calculateTangent(String input) throws TanCalculatorErrorHandler.InvalidInputException, 
                                                      TanCalculatorErrorHandler.UndefinedTangentException {
        // Step 1: Validate and parse input
        double degrees = parseInput(input);
        
        // Step 2: Convert to radians
        double radians = toRadians(degrees);
        
        // Step 3: Normalize radians
        radians = normalizeRadians(radians);
        
        // Step 4: Calculate tangent
        return tan(radians);
    }

    /**
     * Validate numeric input (implements FR‑6).
     * Rejects empty, non‑numeric strings, NaN, and infinity.
     * 
     * @param s the input string to validate
     * @return the parsed double value
     * @throws TanCalculatorErrorHandler.InvalidInputException if input is invalid
     */
    public double parseInput(String s) throws TanCalculatorErrorHandler.InvalidInputException {
        if (s == null || s.trim().isEmpty()) {
            throw new TanCalculatorErrorHandler.InvalidInputException("Empty input");
        }
        
        double val;
        try {
            val = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            throw new TanCalculatorErrorHandler.InvalidInputException("Non‑numeric input");
        }
        if (Double.isNaN(val) || Double.isInfinite(val)) {
            throw new TanCalculatorErrorHandler.InvalidInputException("NaN or Infinite value");
        }
        return val;
    }

    /**
     * Convert degrees to radians using the hand‑coded π/180 rule (FR‑2).
     * 
     * @param deg angle in degrees
     * @return angle in radians
     */
    public double toRadians(double deg) {
        return deg * PI / 180.0;
    }

    /**
     * Normalise radians into [−π, π] before series evaluation (FR‑9).
     * 
     * @param r angle in radians
     * @return normalized angle in radians
     */
    public double normalizeRadians(double r) {
        double twoPi = 2 * PI;
        r = r % twoPi;
        if (r > PI) {
            r -= twoPi;
        }
        if (r < -PI) {
            r += twoPi;
        }
        return r;
    }

    /**
     * Compute tan(x) = sin(x)/cos(x).
     *   • Uses Maclaurin sin/cos (FR‑3).
     *   • Detects |cos(x)| < EPS to uphold FR‑5 (UNDEFINED).
     * 
     * @param x angle in radians
     * @return tangent value
     * @throws TanCalculatorErrorHandler.UndefinedTangentException when tangent is undefined
     */
    public double tan(double x) throws TanCalculatorErrorHandler.UndefinedTangentException {
        double c = cos(x);                              // FR‑3 – cos via series
        if (Math.abs(c) < EPS) {
            throw new TanCalculatorErrorHandler.UndefinedTangentException("cos≈0"); // FR‑5
        }
        double s = sin(x);                              // FR‑3 – sin via series
        return s / c;                                   // FR‑4
    }

    /**
     * Maclaurin series for sin(x) with configurable terms (FR‑3).
     * 
     * @param x angle in radians
     * @return sine value
     */
    public double sin(double x) {
        double term = x;
        double sum = x;
        for (int n = 1; n < MAX_SERIES_TERMS; n++) {
            term *= -x * x / ((2 * n) * (2 * n + 1));
            sum += term;
        }
        return sum;
    }

    /**
     * Maclaurin series for cos(x) with configurable terms (FR‑3).
     * 
     * @param x angle in radians
     * @return cosine value
     */
    public double cos(double x) {
        double term = 1.0;
        double sum = 1.0;
        for (int n = 1; n < MAX_SERIES_TERMS; n++) {
            term *= -x * x / ((2 * n - 1) * (2 * n));
            sum += term;
        }
        return sum;
    }

    /**
     * Get the mathematical constant π.
     * 
     * @return the value of π
     */
    public double getPI() {
        return PI;
    }

    /**
     * Get the epsilon threshold for undefined tangent detection.
     * 
     * @return the epsilon value
     */
    public double getEPS() {
        return EPS;
    }

    /**
     * Get the maximum number of terms for Maclaurin series.
     * 
     * @return the maximum series terms
     */
    public int getMaxSeriesTerms() {
        return MAX_SERIES_TERMS;
    }
} 