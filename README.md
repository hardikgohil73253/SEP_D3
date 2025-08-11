# TanCalculator - Java GUI Application

A Java GUI application for calculating tangent values with comprehensive development tools integration.

## Version Information

- Current Version: 1.0.0
- Semantic Versioning: MAJOR.MINOR.PATCH format
- Java Version: 11+

## Features

- GUI Interface: User-friendly Swing-based interface
- Mathematical Functions: Accurate tangent calculations using Maclaurin series
- Accessibility: Full Java Accessibility API integration
- Error Handling: Comprehensive input validation and error messages
- Unit Testing: Complete JUnit test coverage

## Integrated Tools and Technologies

### 1. Checkstyle - Code Style Enforcement
- Configuration: `checkstyle.xml`
- Usage: `mvn checkstyle:check`
- Purpose: Enforces Java coding standards and style guidelines

### 2. PMD - Static Code Analysis
- Configuration: `pmd.xml`
- Usage: `mvn pmd:check`
- Purpose: Detects code quality issues, potential bugs, and code smells

### 3. JDB - Java Debugger
- Configuration: `debug.jdb`
- Usage: `jdb -sourcepath src/main/java TanCalculator`
- Purpose: Step-by-step debugging and variable inspection

### 4. JUnit - Unit Testing Framework
- Test Location: `src/test/java/TanCalculatorTest.java`
- Usage: `mvn test`
- Purpose: Comprehensive unit testing with test coverage

### 5. Java Accessibility API
- Implementation: Full accessibility support in GUI
- Features: Screen reader compatibility, keyboard navigation, tooltips

### 6. Semantic Versioning
- Format: MAJOR.MINOR.PATCH (1.0.0)
- Location: `TanCalculator.VERSION` constant
- Purpose: Clear version tracking and release management

## Project Structure

```
d3cursor1/
├── src/
│   ├── main/java/
│   │   └── TanCalculator.java
│   └── test/java/
│       └── TanCalculatorTest.java
├── pom.xml
├── checkstyle.xml
├── pmd.xml
├── debug.jdb
└── README.md
```

## Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build the Project
```bash
mvn clean compile
```

### Run the Application
```bash
mvn exec:java -Dexec.mainClass="TanCalculator"
```

### Run Tests
```bash
mvn test
```

### Run Code Quality Checks
```bash
# Checkstyle
mvn checkstyle:check

# PMD
mvn pmd:check

# All quality checks
mvn verify
```

## Debugging with JDB

### Start Debugging
```bash
# Compile with debug information
javac -g src/main/java/TanCalculator.java

# Start JDB
jdb -sourcepath src/main/java TanCalculator
```

### JDB Commands
- `step` - Step into method calls
- `next` - Step over method calls
- `cont` - Continue execution
- `print <variable>` - Print variable value
- `locals` - Show local variables
- `where` - Show call stack
- `threads` - List all threads
- `exit` - Exit debugger

### Using the Debug Configuration
```bash
jdb -sourcepath src/main/java -classpath . @debug.jdb
```

## Code Quality Tools

### Checkstyle
Enforces coding standards including:
- Naming conventions
- Import organization
- Code formatting
- Documentation requirements
- Complexity limits

### PMD
Detects issues such as:
- Unused variables and imports
- Dead code
- Performance issues
- Security vulnerabilities
- Code complexity

## Accessibility Features

The application implements the Java Accessibility API with:
- Accessible Descriptions: All GUI components have descriptive text
- Keyboard Navigation: Full keyboard support for all operations
- Screen Reader Support: Compatible with screen readers
- High Contrast: Clear visual design with proper contrast
- Tooltips: Helpful tooltips for all interactive elements

## Testing Strategy

### Unit Tests
- Mathematical Functions: Test all trigonometric calculations
- Input Validation: Test edge cases and invalid inputs
- GUI Components: Test component creation and accessibility
- Integration: Test complete user workflows

### Test Coverage
- Mathematical accuracy
- Error handling
- Accessibility compliance
- GUI functionality

## Version History

### 1.0.0 (Current)
- Initial release
- Complete GUI implementation
- Full accessibility support
- Comprehensive unit testing
- All development tools integrated


## Troubleshooting

### Common Issues

Checkstyle Errors
- Run `mvn checkstyle:check` to see specific issues
- Follow the style guide in `checkstyle.xml`

PMD Warnings
- Run `mvn pmd:check` to see code quality issues
- Address warnings to improve code quality

JDB Connection Issues
- Ensure Java is compiled with debug information (`-g` flag)
- Check classpath configuration

Test Failures
- Run `mvn test` to see detailed test results
- Check test environment setup