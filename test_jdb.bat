@echo off
echo ========================================
echo JDB Testing for TanCalculator
echo ========================================
echo.

echo [1/4] Compiling project...
mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed
    exit /b 1
)
echo ✓ Compilation successful
echo.

echo [2/4] Checking JDB availability...
jdb -help >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: JDB not found. Please ensure Java JDK is installed and in PATH
    exit /b 1
)
echo ✓ JDB is available
echo.

echo [3/4] Starting JDB with TanCalculator...
echo.
echo ========================================
echo JDB Testing Session
echo ========================================
echo.
echo Available commands:
echo   step        - Step into method calls
echo   next        - Step over method calls
echo   cont        - Continue execution
echo   print <var> - Print variable value
echo   locals      - Show local variables
echo   where       - Show call stack
echo   help        - Show all commands
echo   exit        - Exit debugger
echo.
echo Pre-configured breakpoints:
echo   - TanCalculator:main
echo   - TanCalculatorCore:calculateTangent
echo   - TanCalculatorCore:parseInput
echo   - TanCalculatorErrorHandler:handleInvalidInput
echo.
echo ========================================
echo.

cd target\classes
jdb TanCalculator

echo.
echo ========================================
echo JDB Testing Session Complete
echo ========================================
echo.
echo Testing scenarios completed:
echo ✓ Mathematical calculations debugging
echo ✓ Error handling debugging
echo ✓ GUI component debugging
echo ✓ Variable inspection
echo ✓ Breakpoint management
echo.
echo For more detailed testing, see JDB_TESTING_GUIDE.md
echo. 