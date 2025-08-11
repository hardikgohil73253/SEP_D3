@echo off
echo ========================================
echo TanCalculator Build and Quality Check
echo ========================================
echo.

echo [1/6] Cleaning previous builds...
mvn clean
if %errorlevel% neq 0 (
    echo ERROR: Clean failed
    exit /b 1
)

echo.
echo [2/6] Compiling source code...
mvn compile
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed
    exit /b 1
)

echo.
echo [3/6] Running unit tests...
mvn test
if %errorlevel% neq 0 (
    echo ERROR: Tests failed
    exit /b 1
)

echo.
echo [4/6] Running Checkstyle analysis...
mvn checkstyle:check
if %errorlevel% neq 0 (
    echo WARNING: Checkstyle found issues
) else (
    echo Checkstyle passed successfully
)

echo.
echo [5/6] Running PMD static analysis...
mvn pmd:check
if %errorlevel% neq 0 (
    echo WARNING: PMD found issues
) else (
    echo PMD passed successfully
)

echo.
echo [6/6] Building final JAR...
mvn package
if %errorlevel% neq 0 (
    echo ERROR: Package failed
    exit /b 1
)

echo.
echo ========================================
echo Build Summary
echo ========================================
echo ✓ Source code compiled successfully
echo ✓ Unit tests passed
echo ✓ Code quality checks completed
echo ✓ JAR file created
echo.
echo To run the application:
echo   java -jar target/tan-calculator-1.0.0.jar
echo.
echo To debug with JDB:
echo   jdb -sourcepath src/main/java TanCalculator
echo.
echo ======================================== 