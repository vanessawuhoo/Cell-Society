Introduction
============

The main problem to solve in this project is to create a program that simulates Cellular Automata with enough flexibility to accommodate different update rules.  In addition, the program should also be flexible enough to accommodate other types of modification, such as grid type, state type, user interface, etc.  The design goals of this project are to code Cellular Automata to make flexible to new rules and other modifications.  We would consider the code flexible if it is simple to implement new rules without breaking the logic or functionality of the program.  The primary architecture would consist of the following classes:

- Main
- Display (GUI)
- Cell Graph
- Cell
- Grid (Abstract, not in diagram)
- State (Abstract)
- Rule (Abstract)
- XML Reader (Abstract)

All of the classes would ideally be closed to modification except the Rule class, the State class, and the XML Reader class. Each subclass extending from Rules would have its own function for defining how a particular cell should change state for an input of the current cell state and the states of the surrounding cells.  Rules should also define what states are permissible (this would be the omega, or the collection of all possible states) and the colors in the GUI that correspond to each state. State is also abstract because the number of states can vary between different rules, and the states that cells can be may not be discrete. The XML Reader class is also abstract because the way that the XML is parsed can differ depending on the user’s preference.

Overview
========

The general goal of this project is to design a program that has an appropriate structure for simulating cellular automata and also be flexible to modification.  Thus, we wanted to create classes that we knew we would be using, regardless of the simulation type, the possible cell states, or the UI elements that would be desired.  The Main class initializes a central Hub class, which we designed as the main back-end class to take care of all high-level commands and interact with the other classes.  The hub connects to the cell graph, the list of possible rule/state combinations, front-end Display/GUI class, and the XML parser.

The Cell Graph class holds a graph representation of all the Cells and their connections to each other.  We decided to use a graph because it is amenable to different grid types.  Had we used a two dimensional array to store the cells, we would have inherently assumed that the cells are organized in a rectangular grid, with its direct connections being the cells directly up, down, left, right, up-right, down-right, down-left, and up-left.  This is sufficient for the first couple of simulations that are required for Sprint 2, but perhaps a different grid type consisting of triangles or hexagons may be used.  It is difficult to represent the relationships between cells in a two-dimensional array when the cell shapes are triangles or hexagons, but a graph could encapsulate any relationship or grid type, including perhaps a three-dimensional grid.  The cell graph would be represented in a list of Cell instances (explained in the next paragraph), and each Cell instance would hold the connections to other Cells in the Graph.

In addition to the Cell Graph, there would also be a Cell class to, as mentioned above, store the direct connections to the other Cells, as well as store the current state.  In this way, the Cell Graph class can iterate through the list of Cells and compute the next state of a particular Cell as a function of its current state as well the states of the neighboring cells as specified by the direct connections stored in the Cell.  In this way, Cell Graph does not need to concern itself with the structure of the grid, because neighboring cells are already defined in the Cell instances.  It also does not need to concern itself with the definition of neighboring cells (perhaps a neighboring cell is two positions away in a rectangular grid).

One particular point that has not been cleared up yet is the need for a State class.  So far, we think that a particular state can be abstracted as a list of doubles.  The use of the double type allows us to accommodate a discrete space (0.0, 1.0, 2.0, etc.) as well as continuous space.  In addition, the list allows us to store multidimensional states.  Thus, a particular state may need to be abstracted in vector form.  This could allow for complex numbers and multiple features in a single state.  For example, a particular Cell could be a person who has different preference levels for different ice creams, which may change over time depending on the preference levels of neighboring people.  A State class to encapsulate different state types may not be needed if the state is abstracted as a list of doubles.

The Rule class would store the particulars for each simulation type.  It would specify the Grid type (rectangular, triangular, etc.) as well as the function of the next state of a particular cell based on its current state and the current states of surrounding cells.  The Rule class would take arguments that adjust parameters of the function, as specified by the XML data file.  The Rule class would also contain a Grid class (not specified in the accompanying diagram) which assigns each cell its neighboring cells as a function of the grid dimensions.

The XML parser should take an input of data.  This data would, at its most basic, contain the simulation type, the configuration parameters, the dimensions of the grid, as well as the initial values for the state of each cell.  The data would then be fed to the Hub.  The Hub (or a separate class) can handle the creation of the initial cell graph based on the simulation type and the initial parameters.

The Display class takes the states generated by the Cell Graph and displays them on a graphical user interface, and also gives the Hub user inputs for starting the simulation, stopping it, switching between simulations, etc.

The classes are outlined in the diagram below, along with our current assumptions about their general relationships.

![enter image description here](https://lh3.googleusercontent.com/-gmbVQUiK0hM/VfQ2lKrcGCI/AAAAAAAAAvs/LjJoTM_9boc/s0/Screenshot+2015-09-11+22.50.17.png "brainstorm.png")

User Interface
==============

The user will interact with the program through several buttons under the visual display of the grid, which is initially empty sans the grid lines. The buttons will be to Load an XML file with a particular simulation and set of rules, Step through the simulation, Run it at a particular frame rate, Reset the current simulation and to Stop the simulation. If any errors occur, popup dialogs will appear to indicate to the user that the program has encountered an error before resetting the interface to its default.

Design Details
==============

The main class is the starting point of the program which initializes an instance of the Hub.  The Hub is the central back-end class of the program.  It handles every high-level detail and command, and handles the interactions between the main components of the program, which include xml parsing, rule handling, user interface, and cell graph updating.  The Hub initializes an instance of the Display (UI) class, and relays user inputs from the UI class to xml parsing class to decide which simulation to initiate as well as what xml data will be used as the starting point.  In this way, the user can choose which simulation to run at any given time.

The XML Parser, which handles xml parsing, would take in the data and read the simulation type, simulation parameters, and the initial states of the cells.  It would then relay this information through a Java data structure (perhaps another class) back to the Hub.  The Hub then calls the appropriate Rule class and initializes an instance of a Cell Graph, passing the xml information to the Cell Graph instance.  In addition, the Hub would modify the cell update function for the simulation based on the parameters given in the xml file.  The Cell Graph class then initializes the graph by creating instances of the Cells with the appropriate state and appropriate connections to other cells based on the grid type and dimensions of the simulation.

The Hub then has a timer, and commands the Cell Graph to update rules to middle and edge cells, based on the function in the Rule class.  The Cell Graph does not need to concern itself with the concept of middle and edge cells, as long as each Cell knows its connections to other Cells.  The next state will be recorded in a separate data structure in Cell Graph, and then the Hub will command the Cell Graph to update all of its Cells at a later period of time.

The Cell Graph will then relay the current graph state to the Hub.  This information will be passed on to the Display class, which handles displaying the current graph state in the UI.  The UI may also manually specify to create a grid update using a button called “step” (shown below) rather than having the updates automated with a timer in the Hub class.

![enter image description here](https://lh3.googleusercontent.com/-S6q2WSxPVXo/VfQ267DI-pI/AAAAAAAAAv4/ai9vCZnkJ4M/s0/Screenshot+2015-09-12+10.29.50.png "cell_society_ui.png")

Design Considerations
=====================

There were two major design considerations discussed by the group. How would we be able to abstract each part and use case of the problem and how that would impact how each class was connected to the others. We decided that in this situation based on what we had been learning in class that generalizing each part as much as possible was the most important. This is especially important given that we would be given an extra thing to add on in the given weeks.
 
We first decided that we wanted to split up the classes of each function as much as possible. For example, we would have an extra class between the abstracted class between hub and rule/state. The pro of having the extra classes is that allows us to abstract each individual step to a greater extent, however it would require more work in terms of organizing which classes are responsible for which functions. After talking to the TAs who recommended that we don’t worry about the number of classes we decided to err on the side of having more classes.

Next we had to decide which classes would require abstraction and polymorphism and how those sub classes would be chosen. Specifically we decided to abstract rule/states and each connected class including cells and grid. This is still an issue that needs to be addressed because we are still unsure how much of an influence the rule/state will have over the other instances of the classes. A listed version of each discussed abstraction is below.

- Rule/State- We definitely wanted to abstract this class because it was the obvious change that was occuring
- Cells- we are considering abstracting this class due potential changes in states it holds as well as the shape of the cell
- States- we are considering abstracting this class to account for potential discrete and non-discrete states
- Cell Graph- for similar reasons as cell we want to abstract the cell graph to account for different shapes and designs
- Hub- Potential for abstraction in case new design requirements change the way the program updates
- Display UI- Potential for abstraction in case we need to add new functionality that the person controls
- XML parser- potential for abstraction in case there are new ways of organizing xmls or differently formatted xmls 

Most of the pros and cons we discusses are at a very basic level. Mainly, how much do we need to abstract this class for us to account for future requirements. Very little discussion has happened about the pros and cons related to the structure of the architecture.

Team Responsibilities
=====================

UI- Vanessa
XML Parser- Kevin
Hub Functions- Chris (Initial Responsibility), Kevin, Vanessa

Schedule:
9/13- Skeleton
9/14- Start work on features, XML parser, display and Hub
9/15- Finish XML parser, continue to work on display, cell graph
9/16- start work on rule/state list, continue work on cell graph,
9/17- finish all basic functionality, bug checking
9/18- finish any remaining work
9/19- turn in


> Written with [StackEdit](https://stackedit.io/).