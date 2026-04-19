# JavaCalc-Class

A Java GUI calculator application built with Swing, focused on clear structure, event-driven interaction, and an easy-to-use interface.

## Purpose

The purpose of this project is to create a desktop calculator that demonstrates:
- Java Swing GUI development
- Event handling with `ActionListener`
- Object-oriented design
- Separation of presentation, control, and logic

## Features

Current and planned features may include:

- Basic calculator interface
- Button-based input
- Arithmetic operations
- Clear/reset functionality
- Sound effects for button interaction
- Multiple views/tools such as:
  - Calculator
  - Tip calculator
  - Other utility panels

## How It Works

The application is organized around a simple flow:

1. The user interacts with buttons in the GUI.
2. Button clicks trigger actions through event listeners.
3. The controller passes input to the engine/logic layer.
4. The engine evaluates the calculation or performs the requested operation.
5. The view updates to show the result or current state.

This keeps the UI, logic, and interaction handling separate and easier to maintain.

## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure the project SDK is set to Java 25.
3. Run the main application class from the IDE.
4. Use the calculator buttons to perform operations.

If you are running from a compiled output folder, make sure the application is launched from the correct main entry point.

## Class Structure

The project is split into multiple classes to keep responsibilities organized.

### Main
Starts the application.

### CalculatorView
Builds and displays the main calculator interface.

### CalculatorController
Handles button events and connects the view to the calculator logic.

### CalculatorEngine
Contains the calculation logic and processing rules.

### TipView
Provides a tip calculator or related utility view.

### ConverterView
Provides a conversion calculator

### SoundPlayer
Separate class that stores the button sounds

### CalcColors
Separate class that stores the code for the background colors in the calculators

## User Interface

The GUI is built using Java Swing and uses layout management rather than absolute positioning.  
This helps keep the interface organized and easier to resize or maintain.

## Challenges

Some of the challenges in this project include:

- Making the switch sign was hard to do because I (`@Phantom-StormX`) had to make it able to read the negative sign like it wasn't an operator.
  - It gave me these two errors: `ArrayIndexOutOfBoundsException` and ` NumberFormatException` 
    - `ArrayIndexOutOfBoundsException` fails on negatives because array indices must be non-negative integers 
    - `NumberFormatException` often fails with negatives if the input string contains unexpected whitespace, non-numeric characters, or if it is trying to parse a decimal into an integer, causing input validation to fail.
  - I fixed it by making sure that the start of the equation was 0, and 0 would either be a constant or a negative. 
- Connecting the different calculators all onto one frame(or area) was difficult for `@Nunchuk-Tylor` because he had to add different layouts, and make it all connect.

## Future Improvements

Possible improvements for future versions:

- Allowing the entire operation to show rather than just the first constant and operator in simple calculator
- adding more conversions for converter calculator

## Button Sound Credit

*Button Sound:* **Button Press** by DRAGON-STUDIO found on [Pixabay](https://pixabay.com/):  
https://pixabay.com/sound-effects/film-special-effects-button-press-382713/

License information:  
https://pixabay.com/service/license-summary/
