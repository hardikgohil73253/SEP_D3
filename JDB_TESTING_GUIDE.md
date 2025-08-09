# JDB Testing Guide for TanCalculator

## Overview

JDB (Java Debugger) is a command-line debugging tool that comes with the Java Development Kit (JDK). This guide shows you how to test JDB with the TanCalculator project.

## Prerequisites

1. **Java JDK 11+** installed
2. **Compiled TanCalculator classes** in `target/classes/`
3. **JDB executable** (included with JDK)

## Step 1: Compile the Project

First, ensure your project is compiled:

```bash
mvn clean compile
```

## Step 2: Start JDB

### Method 1: Direct JDB Launch
```bash
# Navigate to target/classes directory
cd target/classes

# Start JDB with TanCalculator
jdb TanCalculator
```

### Method 2: JDB with Classpath
```bash
# From project root
jdb -classpath target/classes TanCalculator
```

### Method 3: Using the Debug Script
```bash
# Use the provided debug script
jdb -sourcepath src/main/java TanCalculator
```

## Step 3: Set Breakpoints

Once JDB is running, set breakpoints at key methods:

```bash
# Main application entry point
stop at TanCalculator:main

# GUI methods
stop at TanCalculatorGUI:compute
stop at TanCalculatorGUI:buildUI

# Core mathematical methods
stop at TanCalculatorCore:calculateTangent
stop at TanCalculatorCore:parseInput
stop at TanCalculatorCore:toRadians
stop at TanCalculatorCore:normalizeRadians
stop at TanCalculatorCore:tan
stop at TanCalculatorCore:sin
stop at TanCalculatorCore:cos

# Error handling methods
stop at TanCalculatorErrorHandler:handleUndefinedTangent
stop at TanCalculatorErrorHandler:handleInvalidInput
```

## Step 4: Run the Application

```bash
# Start the application
run TanCalculator
```

## Step 5: Debugging Commands

### Basic Navigation
```bash
step        # Step into method calls
next        # Step over method calls
cont        # Continue execution
step up     # Step out of current method
```

### Variable Inspection
```bash
print <variable>     # Print variable value
locals               # Show all local variables
dump <object>        # Show object details
eval <expression>    # Evaluate expression
```

### Breakpoint Management
```bash
clear <breakpoint>   # Remove breakpoint
clear                # List all breakpoints
stop at <class>:<method>  # Set new breakpoint
```

### Stack and Thread Management
```bash
where               # Show call stack
up                  # Move up call stack
down                # Move down call stack
threads             # List all threads
thread <thread-id>  # Switch to specific thread
```

### Application Control
```bash
suspend             # Suspend all threads
resume              # Resume all threads
kill                # Terminate application
```

## Step 6: Testing Scenarios

### Scenario 1: Test Mathematical Calculations
```bash
# Set breakpoints
stop at TanCalculatorCore:calculateTangent
stop at TanCalculatorCore:tan
stop at TanCalculatorCore:sin
stop at TanCalculatorCore:cos

# Run application
run TanCalculator

# When breakpoint hits, inspect variables
print input
print degrees
print radians
print result

# Step through calculation
step
next
step
```

### Scenario 2: Test Error Handling
```bash
# Set breakpoints
stop at TanCalculatorCore:parseInput
stop at TanCalculatorErrorHandler:handleInvalidInput
stop at TanCalculatorErrorHandler:handleUndefinedTangent

# Run application
run TanCalculator

# Test invalid input
# Enter "abc" in GUI, then check variables
print s
print val

# Test undefined tangent
# Enter "90" in GUI, then check variables
print x
print c
```

### Scenario 3: Test GUI Components
```bash
# Set breakpoints
stop at TanCalculatorGUI:buildUI
stop at TanCalculatorGUI:setupEventListeners
stop at TanCalculatorGUI:compute

# Run application
run TanCalculator

# Inspect GUI components
print inputField
print resultLabel
print computeButton
```

## Step 7: Advanced JDB Features

### Conditional Breakpoints
```bash
# Break only when input is "45"
stop at TanCalculatorCore:calculateTangent
condition 1 input.equals("45")
```

### Exception Breakpoints
```bash
# Break on any exception
catch java.lang.Exception

# Break on specific exceptions
catch TanCalculatorErrorHandler$InvalidInputException
catch TanCalculatorErrorHandler$UndefinedTangentException
```

### Method Entry/Exit
```bash
# Break on method entry
stop in TanCalculatorCore:calculateTangent

# Break on method exit
stop in TanCalculatorCore:calculateTangent
```

## Step 8: Debugging Tips

### 1. Use Help Command
```bash
help                # Show all commands
help <command>      # Show help for specific command
```

### 2. Monitor Variables
```bash
# Watch a variable for changes
watch TanCalculatorCore.PI
watch TanCalculatorCore.EPS
```

### 3. Inspect Objects
```bash
# Show object details
dump coreFeatures
dump errorHandler
dump gui
```

### 4. Evaluate Expressions
```bash
# Test mathematical expressions
eval Math.PI
eval 180.0 * Math.PI / 180.0
eval Math.sin(Math.PI/2)
```

## Step 9: Common JDB Commands Reference

| Command | Description |
|---------|-------------|
| `step` | Step into method call |
| `next` | Step over method call |
| `cont` | Continue execution |
| `print <var>` | Print variable value |
| `locals` | Show local variables |
| `where` | Show call stack |
| `up` | Move up call stack |
| `down` | Move down call stack |
| `threads` | List all threads |
| `clear` | List breakpoints |
| `stop at <class>:<method>` | Set breakpoint |
| `clear <breakpoint>` | Remove breakpoint |
| `catch <exception>` | Break on exception |
| `help` | Show help |
| `exit` | Exit debugger |

## Step 10: Testing Checklist

### ✅ Basic JDB Testing
- [ ] JDB starts successfully
- [ ] Can set breakpoints
- [ ] Can run application
- [ ] Can step through code
- [ ] Can inspect variables

### ✅ Mathematical Function Testing
- [ ] Break at `calculateTangent`
- [ ] Inspect input validation
- [ ] Step through `toRadians`
- [ ] Step through `normalizeRadians`
- [ ] Step through `tan`, `sin`, `cos`
- [ ] Verify mathematical results

### ✅ Error Handling Testing
- [ ] Break at `parseInput`
- [ ] Test invalid input scenarios
- [ ] Break at error handler methods
- [ ] Verify error messages
- [ ] Test exception flow

### ✅ GUI Component Testing
- [ ] Break at GUI initialization
- [ ] Inspect component creation
- [ ] Test event handling
- [ ] Verify accessibility setup

## Step 11: Troubleshooting

### Common Issues

1. **"Class not found"**
   ```bash
   # Solution: Use correct classpath
   jdb -classpath target/classes TanCalculator
   ```

2. **"No source found"**
   ```bash
   # Solution: Add sourcepath
   jdb -sourcepath src/main/java -classpath target/classes TanCalculator
   ```

3. **"Breakpoint not hit"**
   ```bash
   # Solution: Check method signature
   methods TanCalculatorCore
   stop at TanCalculatorCore:calculateTangent
   ```

4. **"Cannot evaluate expression"**
   ```bash
   # Solution: Use simpler expressions
   print input
   eval input.length()
   ```

## Step 12: Integration with IDE

### VS Code Integration
```json
{
    "type": "java",
    "request": "attach",
    "name": "Attach to JDB",
    "hostName": "localhost",
    "port": 5005
}
```

### IntelliJ IDEA Integration
- Use "Remote JVM Debug" configuration
- Set host: localhost, port: 5005

## Conclusion

JDB is a powerful debugging tool that allows you to:
- **Step through code** line by line
- **Inspect variables** and object states
- **Set conditional breakpoints** for specific scenarios
- **Debug exceptions** and error handling
- **Monitor application flow** in real-time

This testing approach ensures your TanCalculator application is robust, well-tested, and ready for production use. 