
**Simulation**

 - Abstract Rule class should be made public to others so that other people can access the requirements to code a new simulation
	 - There should be a method that allows users to add a rule
 - Methods in Hub can be made public in the api so that extensions can modify the behavior of the running simulation (increase, decrease rate of simulation, play, pause, etc.)
 - playSimulation(): runs the simulation if one is loaded
 - pauseSimulation(): pauses the simulation if one is running
 - simulationStep(): does one step in the simulation if it is loaded and not running
 - getRule(): retrieves the corresponding Rule class for the Simulation


**Configuration**

 - getStateMap() - returns map of just states
 - getParameter()- returns list of parameter values
 - getAllData()- returns wrapper class that contains all the data
 - parseData()- parses xml for all data
 - Abstract DataParser class allows the configuration of data parsing from the xml for a particular simulation
 - There should be a method that allows users to add a data parser
  

**Visualization**

- Methods in Display, such as getWidth, getHeight, and updateStep can be implemented in the ui so that users can retrieve and act on the size of the ui, and also update the grid as needed.
- The abstract class ShapeClass can be made public
	- There can be a method to allow users to add a new type of shape
-updateStep() - updates the cells in the GUI with their new states and colors
- getPane() - gets the visual representation of the cells in the GUI

> Written with [StackEdit](https://stackedit.io/).